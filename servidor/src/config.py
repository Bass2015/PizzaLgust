import os
from dotenv import load_dotenv

load_dotenv()

# DATABASE
DB_URI = os.getenv('db_uri')

DOCUMENT_NOT_FOUND_CODE = 410
UNAUTHORIZED_CODE = 401

# JWT AUTHORIZATION
# JWT_SECRET_KEY = os.getenv('JWT_SECRET_KEY')
INVALID_TOKEN_CODE = 403
N_REQ_CLAIMS = 4


