
# from jwt import (decode, 
#                  InvalidAlgorithmError, 
#                  InvalidSignatureError,
#                  ExpiredSignatureError, 
#                  MissingRequiredClaimError)
# from config import JWT_SECRET_KEY, N_REQ_CLAIMS
# from services.database import is_document_in_collection
# from events.events import TOKEN_VERIFIED_EVENT

import bcrypt


def hash_password(password: str):
    bpassword = password.encode()
    salt = bcrypt.gensalt()
    return bcrypt.hashpw(bpassword, salt)

def check_password(password: str, hashed_pwd):
    bpassword = password.encode()
    if bcrypt.checkpw(bpassword, hashed_pwd):
        return True
    raise InvalidPasswordError()

# def verify(headers: dict) -> str:
#     """
#     Verify and extract the user's token from the request headers.

#     Parameters:
#     - headers (dict): The headers of the request.

#     Returns:
#     - str: The extracted user token.

#     This function checks the 'Authorization' header in the request headers to ensure it starts with 'Bearer ' and extracts the token. If the header is missing or invalid, it raises an InvalidTokenError.
#     """
#     auth_header = headers.get('Authorization')
#     if auth_header is None or not auth_header.startswith('Bearer '):
#         raise InvalidTokenError("Invalid or missing Bearer token")
#     token = auth_header.split(' ')[1]
#     __verify_token(token)
#     return token

# def __verify_token(token: str) -> str:
#     """
#     Get the user ID from a provided token.

#     Parameters:
#     - token (str): The user token.

#     Returns:
#     - str: The user ID.

#     This function decodes the provided user token using a secret key, checks for required claims, and verifies the user's existence in the database. If the token is invalid or missing required claims, it raises an InvalidTokenError.
#     """
#     try:
#         decoded_token = decode(token, 
#                             JWT_SECRET_KEY, 
#                             algorithms='HS256', 
#                             options={'require': ['id', 'email', 'language', 'iat']})
#         if len(decoded_token.keys()) > int(N_REQ_CLAIMS):
#             raise InvalidTokenError("Too many claims in payload")
#         if not is_document_in_collection('user', 'users', _id=decoded_token['id'], email=decoded_token['email']):
#             raise InvalidTokenError("User not found in database")
#     except (InvalidAlgorithmError, 
#             InvalidSignatureError,
#             ExpiredSignatureError, 
#             MissingRequiredClaimError) as e:
#         raise InvalidTokenError(e)
    
#     TOKEN_VERIFIED_EVENT.trigger(user_id=decoded_token['id'], token=token)

# class InvalidTokenError(Exception):
#     def __init__(self, message="Token is invalid"):
#         self.message = message
#         super().__init__(self.message)

class InvalidPasswordError(Exception):
    def __init__(self, message="Password is invalid"):
        self.message = message
        super().__init__(self.message)