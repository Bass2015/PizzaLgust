# TEA 2 - Implementación login

## Sistema de comunicación utilizado
- Tecnología: RestAPI implementado en una aplicación de Flask, usando python.
- Razón por elegir esta tecnología: Experienca previa, simplicidad, facilidad de comunicación entre diferentes lenguajes
- IP: Localhost
- puerto: 5002
- Prefijo para todos los endpoints: pizzalgust/
- Ejemplo: `http://localhost:5002/pizzalgust/login

## Sistema de autenticación
- Tecnología: JSON Web Token (JWT)
- Razón por elegir esta tecnología: Experienca previa.
- Notas: Normalmente un JWT se ha de pasar en los `headers` de una petición. 
  - Por simplicidad, en este TEA el token se pasa dentro del `body`. 
  - En el próximo TEA planeo arreglar esto.
  
## Descripción de los endpoints
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
    "token": "token_de_autenticacion"
}
```

- Código 400: Se devuelve si los datos de inicio de sesión son incorrectos o faltantes.
- Código 410: Se devuelve si el usuario no está en la base de datos.
- Código 401: Se devuelve si la contraseña del usuario es incorrecta.
- Código 500: Se devuelve con cualquier otro error no contemplado en los ya mencionados.

####  POST `/logout`
Inicia sesión con las credenciales proporcionadas y devuelve un token de autenticación junto con los detalles del usuario.

**Parámetros de Entrada**
token (cadena): token de autenticación enviado al `login`

```POST /login HTTP/1.1
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

## Diagrama UML clases
- Nota: En python todos los métodos y variables son públicos
  
| User |
| --- |
| + _id: string |
| + is_admin: boolean |
| + email: string |
| + user_name: string |
| + first_name: string |
| + last_name: string |
| + password: string |



# Llegeixme
## Patrones
| Patrón utilizado | Archivos                  |
|------------------|---------------------------|
| MVC              | controllers.py, models.py |
| Observador       | events.py                 |
| ORM              | models.py, database.py    |

## Punto de conexión
`src/main.py`