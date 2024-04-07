from flask import request, jsonify

from events.events import TOKEN_VERIFIED_EVENT
from utils.async_utils import run_task_in_background
from config import (UNAUTHORIZED_CODE, 
                    DOCUMENT_NOT_FOUND_CODE, 
                    INVALID_TOKEN_CODE)
from services.database import DocumentNotFoundError

from utils.auth_utils import verify_token, InvalidTokenError
from utils.auth_utils import InvalidPasswordError

from .controllers import (LoginController,
                          LogoutController,
                          GetAllUsersController,
                          UserNotLoggedInError,
                          UserNotAdminError)

def __make_response(controller):
    try:
        controller = controller(request)
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

def get_all_users():
    return __make_response(GetAllUsersController)
def test():
    nombre = request.json['nombre']
    return jsonify({'msg': f'Hola {nombre}, todo ok!'}), 200