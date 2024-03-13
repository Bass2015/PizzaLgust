"""
Description: The models module defines data models and database
schema for your application. It typically includes classes that 
represent the structure of your application's data, such as user 
profiles, chat conversations, or messages.

Purpose: To define the data structure and relationships for your 
application's database.
"""
from abc import ABC, abstractmethod, abstractclassmethod
from services import database as db
from events.events import TokenVerifiedEventListener
from utils.async_utils import run_task_in_background
from utils.auth_utils import hash_password, check_password
from inspect import getfullargspec
import bcrypt

class DBModel(ABC):
    def __init__(self) -> None:
        super().__init__()
   
    @abstractclassmethod
    def read(self, *args):
        pass

    @abstractmethod
    def store(self):
        pass
    
    def store_in_background(self):
        run_task_in_background(self.store)
    
    @abstractmethod
    def update(self):
        pass
    
    def update_in_background(self):
        run_task_in_background(self.update)

    @abstractmethod
    def delete(self):
        pass

class User(DBModel):
    _database = 'user'
    _collection = 'users'

    def __init__(self,
                 _id=None,
                 is_admin=False,
                 user_name="",
                 email="",
                 first_name="",
                 last_name="",
                 password="") -> None:
        self._id = _id
        self.is_admin = is_admin
        self.email = email
        self.user_name = user_name
        self.first_name = first_name
        self.last_name  = last_name 
        self.password = hash_password(password)

    @classmethod
    def new_user(cls, is_admin, user_name, email, first_name, last_name, password):
        user = cls(is_admin=is_admin,
                   user_name = user_name,
                   email=email, 
                   first_name=first_name, 
                   last_name=last_name, 
                   password=password, 
                   )
        user.__dict__.pop('_id')
        user._id = db.insert_one(User._database, User._collection, **user.__dict__)
        return user
    
    @classmethod
    def read(cls, user_id, password):
        retrieved_user = db.get_document_from_database(User._database, 
                                      User._collection, 
                                      _id=user_id)
        hashed_password = retrieved_user.pop('password')
        check_password(password=password, hashed_pwd=hashed_password)
        fields = {k:v for k,v in retrieved_user.items() 
                      if k in getfullargspec(cls.__init__).args}
        user = cls(**fields)
        return user

    def store(self):
        raise NotImplementedError()

    def update(self):
        raise NotImplementedError()
    
    def delete(self):
        raise NotImplementedError()