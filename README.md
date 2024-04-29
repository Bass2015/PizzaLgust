# PizzaLgust

## Servidor
### Instalación y puesta en marcha
1. Asegurarse de tener python instalado y operativo
2. Cada vez que se haga un pull, asegurarse de tener los paquetes instalados
   1. En la terminal, ir a la carpeta del proyecto
   2. Correr `pip install -r requirements.txt`
3. Para iniciar el servidor, correr `python servidor/src/main.py`

### Métodos
####  POST `/login`
Inicia sesión con las credenciales proporcionadas y devuelve un token de autenticación junto con los detalles del usuario.

**Parámetros de Entrada**
email (cadena): Correo electrónico del usuario.
password (cadena): Contraseña del usuario.
```POST /login HTTP/1.1
Content-Type: application/json

{
    "email": "bwayne@gotham.com",
    "password": "batman"
}
```
**Respuestas**
- Código 200: Se devuelve cuando el inicio de sesión es exitoso. Se proporciona un token de autenticación junto con los detalles del usuario.
```
{
    "msg": "Inicio de sesión exitoso",
    "user_name": "bwayne",
    "first_name": "Bruce",
    "last_name": "Wayne",
    "is_admin": true,
    "user_type": "admin
    "token": "token_de_autenticacion"
}
```

- Código 400: Se devuelve si los datos de inicio de sesión son incorrectos o faltantes.
- Código 410: Se devuelve si el usuario no está en la base de datos.
- Código 401: Se devuelve si la contraseña del usuario es incorrecta.
- Código 500: Se devuelve con cualquier otro error no contemplado en los ya mencionados.

####  POST `/logout`
Cierra sesión.

**Parámetros de Entrada**
token (cadena): token de autenticación enviado al `login`

```POST /logout HTTP/1.1
Content-Type: application/json

{
    "token": "token",
}
```
**Respuestas**
- Código 200: Se devuelve cuando el inicio de sesión es exitoso. Se proporciona un token de autenticación junto con los detalles del usuario.
```
{
    "msg": "Logout realizado con éxito",
}
```

- Código 400: Se devuelve si los datos de inicio de sesión son incorrectos o faltantes.
- Código 410: Se devuelve si el usuario no está en la base de datos.
- Código 401: Se devuelve si el usuario no ha hecho log in. 
- Código 500: Se devuelve con cualquier otro error no contemplado en los ya mencionados.

####  POST `/get-all-users`
Devuelve una lista con todos los usuarios. Sólo funciona si la petición viene de un usuario admin.

**Parámetros de Entrada**
token (cadena): token de autenticación enviado al `login`

```POST /get-all-users HTTP/1.1
Content-Type: application/json

{
    "token": "token",
}
```
**Respuestas**
- Código 200: Se devuelve cuando el inicio de sesión es exitoso. Se proporciona un token de autenticación junto con los detalles del usuario.
```
{'users': 
    [{'_id': '65ef5d26bd17175093180e23',
  'email': 'bwayne@gotham.com',
  'first_name': 'Bruce',
  'is_admin': True,
  'last_name': 'Wayne',
  'user_name': 'bwayne',
  'user_type': 'admin'},
 {'_id': '65ef5f88ee038e9346215ec9',
  'email': 'pparker@newyork.com',
  'first_name': 'Peter',
  'is_admin': False,
  'last_name': 'Parker',
  'user_name': 'pparker',
  'user_type': 'cliente'},
 {'_id': '65ef5f89ee038e9346215ecb',
  'email': 'ckent@world.com',
  'first_name': 'Clark',
  'is_admin': False,
  'last_name': 'Kent',
  'user_name': 'ckent',
  'user_type': 'cocinero'}],
}
```

- Código 400: Se devuelve si los datos de inicio de sesión son incorrectos o faltantes.
- Código 410: Se devuelve si el usuario no está en la base de datos.
- Código 401: Se devuelve si el usuario no ha hecho log in o si el usuario no es admin.
- Código 500: Se devuelve con cualquier otro error no contemplado en los ya mencionados.

####  POST `/create-user`
Crea un nuevo usuario en la base de datos.

**Parámetros de Entrada**
user_name (cadena): El nombre de usuario del usuario.
email (cadena): El email del usuario.
first_name (cadena): El nombre del usuario.
last_name (cadena): El apellido del usuario.
password (cadena): La contraseña elegida por el usuario.

```POST /create-user HTTP/1.1
Content-Type: application/json

{
    'user_name': 'homer',
    'email': 'hsimpson@springfield.com',
    'first_name': 'Homer',
    'last_name': 'Simpson',
    'password': 'stupidflanders'
}
```
**Respuestas**
- Código 200: Se devuelve cuando el inicio de sesión es exitoso. Se proporciona un token de autenticación junto con los detalles del usuario.
```
{
    "msg": "Usuario creado con éxito",
    "user_id": "65ef5f89ee038e9346215ecb"
}
```

- Código 400: Se devuelve si los datos de inicio de sesión son incorrectos o faltantes.
- Código 410: Se devuelve si el usuario no está en la base de datos.
- Código 500: Se devuelve con cualquier otro error no contemplado en los ya mencionados.

####  DELETE `/delete-user`
Borra el usuario correspondiente al user_id enviado. Sólo funciona si la petición viene de un usuario admin.

**Parámetros de Entrada**
token (cadena): token de autenticación enviado al `login`
user_id (cadena): Id del usuario que se quiere borrar. Se puede conseguir con `get-all-users`

```DELETE /delete-user HTTP/1.1
Content-Type: application/json

{
    "token": "token",
    "user_id": "65ef5f89ee038e9346215ecb"
}
```
**Respuestas**
- Código 200: Se devuelve cuando el usuario se ha borrado con éxito.
```
{'msg': 'Usuario borrado con éxito'}
```

- Código 400: Se devuelve si los datos de inicio de sesión son incorrectos o faltantes.
- Código 410: Se devuelve si el usuario no está en la base de datos.
- Código 401: Se devuelve si el usuario no ha hecho log in o si el usuario no es admin.
- Código 500: Se devuelve con cualquier otro error no contemplado en los ya mencionados.

####  PUT `/update-user`
Actualiza los datos del usuario. Los parámetros de entrada son opcionales (se puede mandar lo que se quiera).

Si la petición viene de un usuario que *no* es admin, se actualizan los datos del propio usuario. 

Si la petición viene de un usuario administrador, es **OBLIGATORIO** enviar un parámetro `user_id` para saber los datos
de qué usuario hay que actualizar.

**Parámetros de Entrada**
token (cadena): token de autenticación enviado al `login`

Cualquier campo de los contemplados en create_user, menos password:
user_name (cadena)
email (cadena)
first_name
last_name (cadena)

*Solo si la petición viene de un admin*:
user_id (cadena): Id del usuario que se quiere actualizar. Se puede conseguir con `get-all-users`

```PUT /update-user HTTP/1.1
Content-Type: application/json

{
    "token": "token",
    "first_name": "Wenceslao",
    "last_name": "Jiménez"
}
```
**Respuestas**
- Código 200: Se devuelve cuando el usuario se ha borrado con éxito.
```
{'msg': 'Usuario actualizado con éxito'}
```

- Código 400: Se devuelve si los datos de inicio de sesión son incorrectos o faltantes.
- Código 410: Se devuelve si el usuario no está en la base de datos.
- Código 401: Se devuelve si el usuario no ha hecho log in o si el usuario no es admin.
- Código 500: Se devuelve con cualquier otro error no contemplado en los ya mencionados.


# NUEVO
______________


####  POST `/get-all-pizzas`
Devuelve una lista con todas las pizzas.

**Parámetros de Entrada**
token (cadena): token de autenticación enviado al `login`

```POST /get-all-pizzas HTTP/1.1
Content-Type: application/json

{
    "token": "token",
}
```
**Respuestas**
- Código 200: Se devuelve cuando el inicio de sesión es exitoso. Se proporciona un token de autenticación junto con los detalles del usuario.
```
{
   'pizzas': [
        {'_id': '662cc64aaaa4cf8c845499ac',
         'description': 'Pizza con tomate, queso mozzarela y albahaca',
         'name': 'Margarita',
         'price': 10.5},
        {'_id': '662cc65eaaa4cf8c845499b3',
         'description': 'Pizza con huevo, bacon y parmesano',
         'name': 'Carbonara',
         'price': 12.5},
        {'_id': '662cc676aaa4cf8c845499cb',
         'description': 'Pizza con pollo, bacon y piña',
         'name': 'Hawaianna',
         'price': 14.5},
    ],
}
```

- Código 400: Se devuelve si los datos de inicio de sesión son incorrectos o faltantes.
- Código 410: Se devuelve si el usuario no está en la base de datos.
- Código 401: Se devuelve si el usuario no ha hecho log in.
- Código 500: Se devuelve con cualquier otro error no contemplado en los ya mencionados.

####  POST `/create-pizza`
Crea un nueva usuario en la base de datos.

**Parámetros de Entrada**
token (cadena): El token de login del usuario.
name (cadena): El nombre de la pizza
price (número decimal): El precio de la pizza.
descr (cadena): DEscripción básica de la pizza.

```POST /create-pizza HTTP/1.1
Content-Type: application/json

{
    'token':'token',
    'name':"Marina",
    'price':10.5,
    'descr': "Pizza con marisco, pescado y queso"
}
```
**Respuestas**
- Código 200: Se devuelve cuando la pizza se crea con éxito.
```
{
    'msg': 'Pizza creada con éxito', 
    'pizza_id': '662fc94943bf55d878cb248e'
}
```

- Código 400: Se devuelve si los datos de inicio de sesión son incorrectos o faltantes.
- Código 410: Se devuelve si el usuario no está en la base de datos.
- Código 500: Se devuelve con cualquier otro error no contemplado en los ya mencionados.

####  DELETE `/delete-pizza`
Borra el usuario correspondiente al user_id enviado. Sólo funciona si la petición viene de un usuario admin.

**Parámetros de Entrada**
token (cadena): token de autenticación enviado al `login`
pizza_id (cadena): Id de la pizza que se quiere borrar. Se puede conseguir con `get-all-pizzas`

```DELETE /delete-pizza HTTP/1.1
Content-Type: application/json

{
    "token": "token",
    "pizza_id": "65ef5f89ee038e9346215ecb"
}
```
**Respuestas**
- Código 200: Se devuelve cuando la pizza se ha borrado con éxito.
```
{'msg': 'Pizza borrada con éxito'}
```

- Código 400: Se devuelve si los datos de inicio de sesión son incorrectos o faltantes.
- Código 410: Se devuelve si el usuario no está en la base de datos.
- Código 401: Se devuelve si el usuario no ha hecho log in o si el usuario no es admin.
- Código 500: Se devuelve con cualquier otro error no contemplado en los ya mencionados.

####  PUT `/update-pizza`
Actualiza los datos de la pizza. Los parámetros de entrada son opcionales (se puede mandar lo que se quiera).

Es **OBLIGATORIO** enviar un parámetro `pizza_id` para saber los datos
de qué pizza hay que actualizar.

**Parámetros de Entrada**
token (cadena): token de autenticación enviado al `login`

Cualquier campo de los contemplados en create_pizza:
name (cadena): El nombre de la pizza
price (número decimal): El precio de la pizza.
descr (cadena): DEscripción básica de la pizza.

*Solo si la petición viene de un admin*:
user_id (cadena): Id del usuario que se quiere actualizar. Se puede conseguir con `get-all-users`

```PUT /update-pizza HTTP/1.1
Content-Type: application/json

{
    'token': token,
    'name': 'Milán',
    'pizza_id': "65ef5f89ee038e9346215ecb",
    'description': "Pizza con un bistec, bacon y queso"
}
```
**Respuestas**
- Código 200: Se devuelve cuando el usuario se ha borrado con éxito.
```
{'msg': 'Pizza actualizada con éxito'}
```

- Código 400: Se devuelve si los datos de inicio de sesión son incorrectos o faltantes.
- Código 410: Se devuelve si el usuario no está en la base de datos.
- Código 401: Se devuelve si el usuario no ha hecho log in o si el usuario no es admin.
- Código 500: Se devuelve con cualquier otro error no contemplado en los ya mencionados.
