from flask import request, jsonify

from events.events import TOKEN_VERIFIED_EVENT
from utils.async_utils import run_task_in_background
from config import INVALID_TOKEN_CODE, DOCUMENT_NOT_FOUND_CODE
from services.database import DocumentNotFoundError

# from utils.auth_utils import verify, InvalidTokenError

from .controllers import BaseController

# def __make_response(controller):
#     try:
#         controller = controller(request)
#         verify(request.headers)  
#         data, code = controller.run()      
#         response =  jsonify(data), code
#     except InvalidTokenError as e:
#         response =  jsonify({'msg': str(e.message)}), INVALID_TOKEN_CODE
#     except DocumentNotFoundError as e:
#         response =  jsonify({'msg': str(e.message)}), DOCUMENT_NOT_FOUND_CODE
#     except Exception as e:
#         response = jsonify({'msg': f"{e.__class__.__name__}: {str(e)}"}), 500
#     finally:
#         TOKEN_VERIFIED_EVENT.clear_observers()
#         return response

# ##############################################################
# #                                                            #
# #                 LOGIN ENDPOINTS                            #
# #                 ``````````````````````                     #
# ##############################################################
# def login():
#     return __make_response(BaseController)

def test():
    nombre = request.json['nombre']
    return jsonify({'msg': f'Hola {nombre}, todo ok!'}), 200