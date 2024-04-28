from flask import request, jsonify

from config import (UNAUTHORIZED_CODE, 
                    DOCUMENT_NOT_FOUND_CODE, 
                    INVALID_TOKEN_CODE)
from events.events import TOKEN_VERIFIED_EVENT
from utils.async_utils import run_task_in_background
from utils.encrypt_utils import encrypt, decrypt
from services.database import DocumentNotFoundError
from utils.auth_utils import verify_token, InvalidTokenError
from utils.auth_utils import InvalidPasswordError
from .user_controllers import *
from .pizza_controllers import *

def test():
    nombre = request.json['nombre']
    return jsonify({'msg': f'Hola {nombre}, todo ok!'}), 200

def __make_response(controller, verify=True):
    try:
        controller = controller(request)
        if verify:
            verify_token(request.json['token'])  
        data, code = controller.run()      
        response =  jsonify(data), code
    except InvalidTokenError as e:
        response =  jsonify({'msg': str(e.message)}), INVALID_TOKEN_CODE
    except UserNotLoggedInError as e:
        response =  jsonify({'msg': str(e.message)}), UNAUTHORIZED_CODE
    except DocumentNotFoundError as e:
        response =  jsonify({'msg': str(e.message)}), DOCUMENT_NOT_FOUND_CODE
    except UserNotAdminError as e:
        response =  jsonify({'msg': str(e.message)}), UNAUTHORIZED_CODE
    except Exception as e:
        response = jsonify({'msg': f"{e.__class__.__name__}: {str(e)}"}), 500
    finally:
        TOKEN_VERIFIED_EVENT.clear_observers()
        return response

# ##############################################################
# #                                                            #
# #                 LOGIN ENDPOINTS                            #
# #                 ``````````````````````                     #
# ##############################################################
def login():
    try:
        controller = LoginController(request)
        data, code = controller.run()      
        response =  jsonify(data), code
    except DocumentNotFoundError as e:
        response =  jsonify({'msg': str(e.message)}), DOCUMENT_NOT_FOUND_CODE
    except InvalidPasswordError as e:
        response =  jsonify({'msg': str(e.message)}), UNAUTHORIZED_CODE
    except Exception as e:
        response = jsonify({'msg': f"{e.__class__.__name__}: {str(e)}"}), 500
    finally:
        TOKEN_VERIFIED_EVENT.clear_observers()
        return response

def logout():
    return __make_response(LogoutController)

# ##############################################################
# #                                                            #
# #                 USER ENDPOINTS                             #
# #                 ``````````````                             #
# ##############################################################
def get_all_users():
    return __make_response(GetAllUsersController)

def create_user():
    return __make_response(CreateUserController, verify=False)

def delete_user():
    return __make_response(DeleteUserController)

def update_user():
    return __make_response(UpdateUserController)


# ##############################################################
# #                                                            #
# #                 PIZZA ENDPOINTS                            #
# #                 ``````````````                             #
# ##############################################################
def get_all_pizzas():
    return __make_response(GetAllPizzasController)

def create_pizza():
    return __make_response(CreatePizzaController)

def delete_pizza():
    return __make_response(DeletePizzaController)

def update_pizza():
    return __make_response(UpdatePizzaController)





def encrypt_test():
    nombre = decrypt(request.json['nombre'])
    msg = encrypt(f"Hola {nombre}, todo ok!")
    return jsonify({'msg':msg}), 200