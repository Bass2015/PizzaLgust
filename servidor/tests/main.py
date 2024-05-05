import unittest
import requests
from unittest import TestCase

URL = 'http://localhost:5002/pizzalgust/'
HEADERS = {'Content-Type': 'application/json'}

class PizzalgustTests(TestCase):
    """
    PizzalgustTests: Clase para realizar pruebas de unidad.
    """
    def test_login(self):
        """
        Método para probar la funcionalidad de inicio de sesión.
        """
        body = {'email': 'bwayne@gotham.com',
                'password': 'batman'}
        response = requests.post(url=URL + '/login',
                                 json=body,
                                 headers=HEADERS)
        assert response.status_code == 200
        assert 'msg' in response.json().keys()
        assert 'user_name' in response.json().keys()
        assert 'first_name' in response.json().keys()
        assert 'last_name' in response.json().keys()
        assert 'is_admin' in response.json().keys()
        assert 'token' in response.json().keys()
        assert 'user_type' in response.json().keys()

    def test_bad_password(self):
        """
        Método para probar el manejo de una contraseña incorrecta.
        """
        body = {'email': 'bwayne@gotham.com',
                'password': 'fake_password'}
        response = requests.post(url=URL + '/login',
                                 json=body,
                                 headers=HEADERS)
        assert response.status_code == 401
    
    def test_logout(self):
        """
        Método para probar la funcionalidad de cierre de sesión.
        """
        body = {'email': 'bwayne@gotham.com',
                'password': 'batman'}
        response = requests.post(url=URL + '/login',
                                 json=body,
                                 headers=HEADERS)
        token = response.json()['token']

        body = {'token': token}
        response = requests.post(url=URL + '/logout',
                                 json=body,
                                 headers=HEADERS)
        assert response.status_code == 200

    def test_bad_token(self):
        """
        Método para probar el manejo de un token incorrecto.
        """
        token = 'Not a token'
        body = {'token': token}
        response = requests.post(url=URL + '/logout',
                                 json=body,
                                 headers=HEADERS)
        assert response.status_code == 500

    def test_get_all_users(self):  
        """
        Método para probar la solicitud de obtener todos los usuarios.
        """
        body = {'email': 'bwayne@gotham.com',
                'password': 'batman'}
        response = requests.post(url=URL + '/login',
                                 json=body,
                                 headers=HEADERS)
        token = response.json()['token']

        body = {'token': token}
        response = requests.post(url=URL + '/get-all-users',
                                 json=body,
                                 headers=HEADERS)
        assert response.status_code == 200
        assert 'users' in response.json().keys()
        assert isinstance(response.json()['users'], list)
    
    def test_get_all_users_no_admin(self):  
        """
        Método para probar el endpoint `get-all-users` sin ser admin.
        """
        body = {"email": "pparker@newyork.com",
                "password": "spiderman",}
        response = requests.post(url=URL + '/login',
                                 json=body,
                                 headers=HEADERS)
        token = response.json()['token']

        body = {'token': token}
        response = requests.post(url=URL + '/get-all-users',
                                 json=body,
                                 headers=HEADERS)
        assert response.status_code == 401
    
    def test_create_user(self):
        """
        Método para probar la creación de usuario.
        """
        body = dict(user_name="homer",
            email="hsimpson@springfield.com",
            first_name="Homer",
            last_name="Simpson",
            password="stupidflanders",)
        response = requests.post(url=URL + '/create-user',
                                 json=body,
                                 headers=HEADERS)
        assert response.status_code == 200
        assert response.json()['msg'] == 'Usuario creado con éxito'

    def test_delete_user(self):
        """
        Método para probar la eliminación de usuario.
        """
        body = {'email': 'bwayne@gotham.com',
                'password': 'batman'}
        response = requests.post(url=URL + '/login',
                                 json=body,
                                 headers=HEADERS)
        token = response.json()['token']
        body = dict(user_name="homer",
            email="hsimpson@springfield.com",
            first_name="Homer",
            last_name="Simpson",
            password="stupidflanders",)
        response = requests.post(url=URL + '/create-user',
                                 json=body,
                                 headers=HEADERS)
        body = {'token': token,
                'user_id': response.json()['user_id']}
        response = requests.delete(url=URL + '/delete-user',
                                 json=body,
                                 headers=HEADERS)
        assert response.status_code == 200
        assert response.json()['msg'] == 'Usuario borrado con éxito'
    
    def test_update_user(self):
        """
        Método para probar la actualización de usuario.
        """
        body = dict(user_name="homer",
            email="hsimpson@springfield.com",
            first_name="Homer",
            last_name="Simpson",
            password="stupidflanders",)
        response = requests.post(url=URL + '/create-user',
                                 json=body,
                                 headers=HEADERS)
        
        body = {'email': 'hsimpson@springfield.com',
                'password': 'stupidflanders'}
        response = requests.post(url=URL + '/login',
                                 json=body,
                                 headers=HEADERS)
        token = response.json()['token']

        body = {'token': token,
                'user_name': "max_power",
                'first_name': "Max",
                'last_name': "Power"}
        
        response = requests.put(url=URL + '/update-user',
                                 json=body,
                                 headers=HEADERS)
        assert response.status_code == 200
        assert response.json()['msg'] == 'Usuario actualizado con éxito'

    def test_create_pizza(self):
        """
        Método para probar la creación de pizzas.
        """
        body = {'email': 'bwayne@gotham.com',
                'password': 'batman'}
        response = requests.post(url=URL + '/login',
                                 json=body,
                                 headers=HEADERS)
        token = response.json()['token']
        body = dict(
            token=token,
            name="Margarita",
            price=10.5,
            description="Pizza con tomate, queso mozzarela y albahaca"
            )
        response = requests.post(url=URL + '/create-pizza',
                                 json=body,
                                 headers=HEADERS)
        assert response.status_code == 200
        assert response.json()['msg'] == 'Pizza creada con éxito'

    def test_get_all_pizzas(self):  
        """
        Método para probar la solicitud de obtener todos los pizzas.
        """
        body = {'email': 'bwayne@gotham.com',
                'password': 'batman'}
        response = requests.post(url=URL + '/login',
                                 json=body,
                                 headers=HEADERS)
        token = response.json()['token']

        body = {'token': token}
        response = requests.post(url=URL + '/get-all-pizzas',
                                 json=body,
                                 headers=HEADERS)
        assert response.status_code == 200
        assert 'pizzas' in response.json().keys()
        assert isinstance(response.json()['pizzas'], list)

    def test_delete_pizza(self):
        """
        Método para probar la eliminación de pizzas.
        """
        body = {'email': 'bwayne@gotham.com',
                'password': 'batman'}
        response = requests.post(url=URL + '/login',
                                 json=body,
                                 headers=HEADERS)
        token = response.json()['token']
        body = dict(
            token=token,
            name="Carbonara",
            price=12.5,
            description="Pizza con huevo, bacon y parmesano"
            )
        response = requests.post(url=URL + '/create-pizza',
                                 json=body,
                                 headers=HEADERS)
        body = {'token': token,
                'pizza_id': response.json()['pizza_id']}
        response = requests.delete(url=URL + '/delete-pizza',
                                 json=body,
                                 headers=HEADERS)
        assert response.status_code == 200
        assert response.json()['msg'] == 'Pizza borrada con éxito'
    
    def test_update_pizza(self):
        """
        Método para probar la actualización de Pizza.
        """
        body = {'email': 'bwayne@gotham.com',
                'password': 'batman'}
        response = requests.post(url=URL + '/login',
                                 json=body,
                                 headers=HEADERS)
        token = response.json()['token']
        body = dict(
            token=token,
            name="Hawaianna",
            price=14.5,
            description="Pizza con pollo, bacon y piña"
            )
        response = requests.post(url=URL + '/create-pizza',
                                 json=body,
                                 headers=HEADERS)
        body = {'token': token,
                'name': 'Hawai',
                'pizza_id': response.json()['pizza_id'],
                'description': "Pizza con piña, pollo y queso"}
        
        response = requests.put(url=URL + '/update-pizza',
                                 json=body,
                                 headers=HEADERS)
        assert response.status_code == 200
        assert response.json()['msg'] == 'Pizza actualizada con éxito'

if __name__ == '__main__':
    unittest.main()