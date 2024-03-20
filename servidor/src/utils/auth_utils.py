
from jwt import (decode, 
                 encode,
                 InvalidAlgorithmError, 
                 InvalidSignatureError,
                 ExpiredSignatureError, 
                 MissingRequiredClaimError)
from config import JWT_SECRET_KEY, N_REQ_CLAIMS
from services.database import is_document_in_collection
from events.events import TOKEN_VERIFIED_EVENT
from time import time

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

def create_token(user_id, user_mail, ):
    token = encode(
            payload={'id': user_id,
                    'email': user_mail,
                    'iat':int(time())},
            key= JWT_SECRET_KEY,
            algorithm='HS256')
    return token

def verify_token(token: str) -> str:
    """
    Get the user ID from a provided token.

    Parameters:
    - token (str): The user token.

    This function decodes the provided user token using a secret key, checks for required claims, and verifies the user's existence in the database. If the token is invalid or missing required claims, it raises an InvalidTokenError.
    """
    try:
        decoded_token = decode(token, 
                            JWT_SECRET_KEY, 
                            algorithms='HS256', 
                            options={'require': ['id', 'email', 'iat']})
        if len(decoded_token.keys()) > int(N_REQ_CLAIMS):
            raise InvalidTokenError("Too many claims in payload")
        if not is_document_in_collection('user', 'users', _id=decoded_token['id']):
            raise InvalidTokenError("User not found in database")
    except (InvalidAlgorithmError, 
            InvalidSignatureError,
            ExpiredSignatureError, 
            MissingRequiredClaimError) as e:
        raise InvalidTokenError(e)
    
    TOKEN_VERIFIED_EVENT.trigger(user_id=decoded_token['id'], token=token)

class InvalidTokenError(Exception):
    def __init__(self, message="Token is invalid"):
        self.message = message
        super().__init__(self.message)

class InvalidPasswordError(Exception):
    def __init__(self, message="Password is invalid"):
        self.message = message
        super().__init__(self.message)