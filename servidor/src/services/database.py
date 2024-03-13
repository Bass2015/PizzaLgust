"""
Description: This module is responsible for managing database 
connections and database-related services, including data storage,
retrieval, and manipulation.
Purpose: To provide database-related services for your application.
"""

from contextlib import contextmanager   
from pymongo.server_api import ServerApi
from pymongo.mongo_client import MongoClient
from bson import ObjectId
from config import DB_URI

def insert_one(database: str, collection: str, **fields) -> str:
    fields = __ensure_object_id_in_fields(**fields) 
    with __get_db(database) as db:
        result = db[collection].insert_one(fields)
    return str(result.inserted_id)

def update_one(database:str, collection:str, _id:str, **updated_fields):
    _id = __ensure_object_id(_id)
    fields = __ensure_object_id_in_fields(**updated_fields)
    with __get_db(database) as db:
        db[collection].update_one({'_id': _id}, update={'$set':dict(**fields)})

def is_document_in_collection(db: str, 
                              collection: str,  
                              **fields) -> bool:
    """
    Check if a document exists in a database collection based on provided filters.

    Parameters:
    - db (str): The name of the database.
    - collection (str): The name of the collection.
    - **fields: Keyword arguments representing additional filters.

    Returns:
    - bool: True if the document is found in the collection, False otherwise.

    This function checks whether a document exists in the specified database collection based on the provided filters. It returns True if the document is found, otherwise, it returns False.
    """
    fields = {k: ObjectId(v) 
              if k.endswith('_id') else v 
              for k,v in dict(**fields).items()}
    with __get_db(db) as db:
        result = db[collection].find_one(dict(**fields))
    return result != None

def get_document_from_database(database:str, collection:str, **query) -> dict:
    """
    Retrieve a document from a database collection based on specified query parameters.

    Parameters:
    - database (str): The name of the database.
    - collection (str): The name of the collection.
    - **query: Keyword arguments representing query parameters.

    Returns:
    - dict: A dictionary containing the retrieved document.

    This function retrieves a document from the specified database collection based on the provided query parameters. The query parameters are used to match the document, and the retrieved document is returned as a dictionary.
    """

    query = {k: ObjectId(v) if k.endswith('_id') else v for k,v in dict(**query).items()} 
    with __get_db(database) as db:
        result = db[collection].find_one(query)
    if result == None:
        raise DocumentNotFoundError()
    result = {k: str(v) if isinstance(v, ObjectId) else v for k,v in result.items()}
    return result
    
def delete_document_from_db(db_name: str, collection: str, **filters) -> bool:
    """
    Delete documents from a database collection based on specified filters.

    Parameters:
    - db_name (str): The name of the database to operate on.
    - collection (str): The name of the collection to delete documents from.
    - **filters: Keyword arguments representing filters for document deletion.

    Returns:
    - bool: True if one or more documents were deleted, False if no documents were deleted.

    This function deletes documents from the specified database collection based on the provided filters.
    The filters are applied to match documents for deletion, and it returns True if one or more documents were deleted, otherwise, it returns False.
    """
    filters = {k: __ensure_object_id(v) if k.endswith('_id') else v for k,v in filters.items()}
    with __get_db(db_name) as db:
        result = db[collection].delete_many(dict(**filters))
    return result.deleted_count > 0


def get_all_documents_from_database(database:str, collection: str, projection=None, **query) -> list:
    query = {k: ObjectId(v) if k.endswith('_id') else v for k,v in dict(**query).items()} 
    with __get_db(database) as db:
        results = db[collection].find(query, projection=projection)
        results = [{k: str(v) 
                if isinstance(v, ObjectId) else v 
                for k,v in result.items()} 
                for result in results]
    return results

def get_many_documents(database: str, collection: str, ids_array:list):
    # llogic from anothr DB
    ids = [ObjectId(id) for id in ids_array]
    with __get_db(database) as db:
        messages = db.messages.find({'_id': {'$in': ids}})
        messages = list(messages)
    messages =[{k:str(v) if isinstance(v, ObjectId) else v for k,v in message.items()} for message in messages]
    return messages

def get_recording_urls(user_id:str, number_of_recordings=3):
    user_id = __ensure_object_id(user_id)
    with __get_db('recording') as db:
        recordings = list(db.recordings.find({'user_id': user_id, 
                                       'recording_url': {'$ne': ''}}, 
                                     projection={'recording_url': 1, '_id':0}))
        recordings = [r['recording_url'] for r in recordings[:number_of_recordings]]
    return recordings

# –––––––––––––– PRIVATE METHODS ––––––––––––––––––––––    
def __ensure_object_id(id:str) -> ObjectId:
    if not isinstance(id, ObjectId):
        return ObjectId(id)
    return id

def __ensure_object_id_in_fields(**fields) -> dict:
    return {k: ObjectId(v) 
              if k.endswith('_id') else v 
              for k,v in dict(**fields).items()} 

@contextmanager
def __get_db(db_name: str):
    """
    Context manager for connecting to the MongoDB converstaions database.

    Args:
        db_name: The name of the wanted database. 

    Yields:
        The database.

    Note:
        The connection is automatically closed when exiting the context.
    """
    client = MongoClient(DB_URI, server_api=ServerApi('1'))
    try:
        yield client[db_name]
    finally:
        client.close()

class DocumentNotFoundError(Exception):
    def __init__(self, message="Document not found."):
        self.message = message
        super().__init__(self.message)



