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

