from flask import request, jsonify

from config import (UNAUTHORIZED_CODE, 
                    DOCUMENT_NOT_FOUND_CODE, 
                    INVALID_TOKEN_CODE)
from events.events import TOKEN_VERIFIED_EVENT
from utils.async_utils import run_task_in_background
from utils.encrypt_utils import encrypt_body, decrypt_body
from utils.auth_utils import (verify_token, 
                              InvalidTokenError,
                              InvalidPasswordError)
from services.database import DocumentNotFoundError
from .user_controllers import *
from .pizza_controllers import *

def test():
    nombre = request.json['nombre']
    return jsonify({'msg': f'Hola {nombre}, todo ok!'}), 200

def __make_response(controller, verify=True):
    try:
        body = decrypt_body(request.json)
        controller = controller(body)
        if verify:
            verify_token(request.json['token'])  
        data, code = controller.run()      
        data = encrypt_body(data)
        response =  jsonify(data), code
    except InvalidTokenError as e:
        data = {'msg': str(e.message)}
        data = encrypt_body(data)
        response =  jsonify(data), INVALID_TOKEN_CODE
    except UserNotLoggedInError as e:
        data = {'msg': str(e.message)}
        data = encrypt_body(data)
        response =  jsonify(data), UNAUTHORIZED_CODE
    except DocumentNotFoundError as e:
        data = {'msg': str(e.message)}
        data = encrypt_body(data)
        response =  jsonify(data), DOCUMENT_NOT_FOUND_CODE
    except UserNotAdminError as e:
        data = {'msg': str(e.message)}
        data = encrypt_body(data)
        response =  jsonify(data), UNAUTHORIZED_CODE
    except Exception as e:
        data = {'msg': f"{e.__class__.__name__}: {str(e)}"}
        data = encrypt_body(data)
        response = jsonify(data), 500
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
        body = decrypt_body(request.json)
        controller = LoginController(body)
        data, code = controller.run()      
        data = encrypt_body(data)
        response =  jsonify(data), code
    except InvalidPasswordError as e:
        data = {'msg': str(e.message)}
        data = encrypt_body(data)
        response =  jsonify(data), UNAUTHORIZED_CODE
    except DocumentNotFoundError as e:
            data = {'msg': str(e.message)}
            data = encrypt_body(data)
            response =  jsonify(data), DOCUMENT_NOT_FOUND_CODE
    except Exception as e:
            data = {'msg': f"{e.__class__.__name__}: {str(e)}"}
            data = encrypt_body(data)
            response = jsonify(data), 500
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
