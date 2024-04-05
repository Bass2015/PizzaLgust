import unittest
import requests
from unittest import TestCase

URL = 'http://localhost:5002/pizzalgust/'
HEADERS = {'Content-Type': 'application/json'}

class TEA2Tests(TestCase):
    """
    TEA2Tests: Clase para realizar pruebas de unidad relacionadas con TEA2.

    Métodos:
    - test_login(self): Método para probar la funcionalidad de inicio de sesión.
    - test_bad_password(self): Método para probar el manejo de una contraseña incorrecta.
    - test_logout(self): Método para probar la funcionalidad de cierre de sesión.
    - test_bad_token(self): Método para probar el manejo de un token incorrecto.
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

if __name__ == '__main__':
    unittest.main()