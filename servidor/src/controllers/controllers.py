from utils.async_utils import run_task_in_background
from abc import ABC, abstractmethod, abstractclassmethod
from services import database as db
from models.models import DBModel, User


class BaseController(ABC):
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
        
class LoginController(BaseController):
    def __init__(self, request, config=None):
        self.user = User.read_from_email(email=request.json['email'],
                                         password=request.json['password'])


    def run(self) -> tuple:
        # message = DBModel # Some kind of DBModel
        # run_task_in_background(self.__persist, 
        #                        message=message)
        data = {'msg': 'Login succesful'}
        return data, 200

    def __example(self):
        pass        
    
    def _persist(self, model: DBModel):
        message_id = model.store()


