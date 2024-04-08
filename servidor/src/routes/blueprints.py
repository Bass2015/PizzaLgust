"""Descripción: Este módulo define blueprints de Flask para organizar rutas y vistas en tu aplicación. Los blueprints se utilizan para agrupar rutas y vistas relacionadas, facilitando la gestión y organización de los puntos finales de tu aplicación.
Propósito: Definir y registrar blueprints que manejen diferentes partes de la funcionalidad de tu aplicación."""

from flask import Blueprint
from controllers import main_controller as controller

blueprint = Blueprint('blueprint', __name__, url_prefix='/pizzalgust')



blueprint.route('/login', methods=['POST'])(controller.login)
blueprint.route('/logout', methods=['POST'])(controller.logout)
blueprint.route('/create-user', methods=['POST'])(controller.create_user)
blueprint.route('/get-all-users', methods=['POST'])(controller.get_all_users)
blueprint.route('/test', methods=['POST'])(controller.test)
