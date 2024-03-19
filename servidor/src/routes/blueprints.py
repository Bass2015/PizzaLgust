"""
Description: This module defines Flask blueprints for organizing 
routes and views in your application. Blueprints are used to group
related routes and views together, making it easier to manage and
organize your application's endpoints.
Purpose: To define and register blueprints that handle different 
parts of your application's functionality.
"""

from flask import Blueprint
from controllers import main_controller as controller

blueprint = Blueprint('blueprint', __name__, url_prefix='/pizzalgust')


blueprint.route('/login', methods=['POST'])(controller.login)
blueprint.route('/test', methods=['POST'])(controller.test)
