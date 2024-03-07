from utils.async_utils import run_task_in_background
from abc import ABC, abstractmethod, abstractclassmethod
from models.models import DBModel


class BaseController(ABC):
    """
    Description: This module handles the creation of new conversations
    or chat sessions in the application. It may include routes and 
    functions for initiating new conversations.
    
    Purpose: To provide functionality for creating new chat sessions
    """
    def __init__(self, request, config=None):
        pass


    def run(self) -> tuple:
        message = DBModel # Some kind of DBModel
        run_task_in_background(self.__persist, 
                               message=message)

    def __example(self):
        pass        
    
    def _persist(self, model: DBModel):
        message_id = model.store()
        


