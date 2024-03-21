"""
Descripción: El módulo de modelos define modelos de datos y el esquema de la base de datos para tu aplicación. Normalmente incluye clases que representan la estructura de los datos de tu aplicación, como perfiles de usuario, conversaciones de chat o mensajes.

Propósito: Definir la estructura de datos y las relaciones para la base de datos de tu aplicación.
"""
from abc import ABC, abstractmethod, abstractclassmethod
from services import database as db
from events.events import TokenVerifiedEventListener
from utils.async_utils import run_task_in_background
from utils.auth_utils import hash_password, check_password
from inspect import getfullargspec

class DBModel(ABC):
    """
    DBModel: Clase abstracta base para definir la funcionalidad básica de un modelo de base de datos.

    Métodos abstractos:
    - read(self, *args): Método para leer datos del modelo.
    - store(self): Método para almacenar el modelo en la base de datos.
    - update(self): Método para actualizar el modelo en la base de datos.
    - delete(self): Método para eliminar el modelo de la base de datos.
    """
    def __init__(self) -> None:
        super().__init__()
   
    @abstractclassmethod
    def read(self, *args):
        pass

    @abstractmethod
    def store(self):
        """
        Método para almacenar el modelo en la base de datos.
        """
        pass
    
    def store_in_background(self):
        """
        Método para guardar el modelo en la base de datos en segundo plano.
        """
        run_task_in_background(self.store)
    
    @abstractmethod
    def update(self):
        """
        Método para actualizar el modelo en la base de datos.
        """
        pass
    
    def update_in_background(self):
        """
        Método para actualizar el modelo en la base de datos en segundo plano.
        """
        run_task_in_background(self.update)

    @abstractmethod
    def delete(self):
        """
        Método para eliminar el modelo en la base de datos.
        """
        pass

class User(DBModel):
    _database = 'user'
    _collection = 'users'

    def __init__(self,
                 _id=None,
                 is_admin=False,
                 user_name="",
                 email="",
                 first_name="",
                 last_name="",
                 password="") -> None:
        self._id = _id
        self.is_admin = is_admin
        self.email = email
        self.user_name = user_name
        self.first_name = first_name
        self.last_name  = last_name 
        self.password = hash_password(password)

    @classmethod
    def new_user(cls, is_admin, user_name, email, first_name, last_name, password):
        """
        Método para crear un nuevo usuario.

        Parámetros:
        - is_admin (bool): Indica si el usuario es administrador.
        - user_name (str): Nombre de usuario.
        - email (str): Correo electrónico del usuario.
        - first_name (str): Nombre del usuario.
        - last_name (str): Apellido del usuario.
        - password (str): Contraseña del usuario.

        Retorna:
        - user: Objeto de usuario creado.
        """
        user = cls(is_admin=is_admin,
                   user_name = user_name,
                   email=email, 
                   first_name=first_name, 
                   last_name=last_name, 
                   password=password, 
                   )
        user.__dict__.pop('_id')
        user._id = db.insert_one(User._database, User._collection, **user.__dict__)
        return user
    
    @classmethod
    def read(cls, user_id, password):
        """
        Método para leer un usuario por su ID y contraseña.

        Parámetros:
        - user_id (str): ID del usuario.
        - password (str): Contraseña del usuario.

        Retorna:
        - user: Objeto de usuario leído.
        """
        retrieved_user = db.get_document_from_database(User._database, 
                                      User._collection, 
                                      _id=user_id)
        hashed_password = retrieved_user.pop('password')
        check_password(password=password, hashed_pwd=hashed_password)
        fields = {k:v for k,v in retrieved_user.items() 
                      if k in getfullargspec(cls.__init__).args}
        user = cls(**fields)
        return user
    
    @classmethod
    def read_from_email(cls, email, password):
        """
        Método para leer un usuario por su correo electrónico y contraseña.

        Parámetros:
        - email (str): Correo electrónico del usuario.
        - password (str): Contraseña del usuario.

        Retorna:
        - user: Objeto de usuario leído.
        """
        retrieved_user = db.get_document_from_database(User._database, 
                                      User._collection, 
                                      email=email)
        hashed_password = retrieved_user.pop('password')
        check_password(password=password, hashed_pwd=hashed_password)
        fields = {k:v for k,v in retrieved_user.items() 
                      if k in getfullargspec(cls.__init__).args}
        user = cls(**fields)
        return user

    def store(self):
        raise NotImplementedError()

    def update(self):
        raise NotImplementedError()
    
    def delete(self):
        raise NotImplementedError()