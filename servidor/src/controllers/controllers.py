from utils.async_utils import run_task_in_background
from utils.auth_utils import create_token
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
    logged_in_user_tokens = []
    def __init__(self, request, config=None):
        self.user = User.read_from_email(email=request.json['email'],
                                         password=request.json['password'])


    def run(self) -> tuple:
        token = create_token(self.user._id, 
                             self.user.email)
        LoginController.logged_in_user_tokens.append(token)
        data = {'msg': 'Login succesful', 
                'user_name': self.user.user_name,
                'first_name': self.user.first_name,
                'last_name': self.user.last_name,
                'is_admin': self.user.is_admin,
                'token': token}
        return data, 200
class LogoutController(BaseController):
    def __init__(self, request, config=None):
        self.token = request.json['token']


    def run(self) -> tuple:
        if not self.token in LoginController.logged_in_user_tokens:
            raise UserNotLoggedInError()
        data = {'msg': 'Logout succesful',}
        LoginController.logged_in_user_tokens.remove(self.token)
        return data, 200

class UserNotLoggedInError(Exception):
    def __init__(self, message="The user is not logged in"):
        self.message = message
        super().__init__(self.message)

