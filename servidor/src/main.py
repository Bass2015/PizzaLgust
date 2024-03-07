"""
Entry point of the Flask application. It is where you 
create the Flask app instance, configure it, and run the application.
Purpose: To initialize and run the Flask application.
"""

from flask import Flask
from routes.blueprints import blueprint
from flask_cors import CORS


app = Flask(__name__) 
CORS(app)

app.register_blueprint(blueprint)

if __name__ == '__main__': 
    app.run(host='localhost', port=5002)