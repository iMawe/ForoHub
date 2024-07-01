# ForoHub - API REST para Foro de Discusión

## Descripción del Proyecto

ForoHub es una API REST diseñada para gestionar un foro de discusión. Los usuarios pueden crear, listar, ver detalles, actualizar y eliminar tópicos. Los tópicos están asociados a cursos y tienen respuestas, autores y estados.

## Estructura de la Base de Datos

- **Usuario**: Información del usuario (id, nombre, correo electrónico, contraseña, perfiles).
- **Curso**: Información del curso (id, nombre, categoría).
- **Tópico**: Información del tópico (id, título, mensaje, fecha de creación, estado, autor, curso, respuestas).
- **Respuesta**: Información de la respuesta (id, mensaje, fecha de creación, autor, solución, tópico).
- **Perfil**: Información del perfil (id, nombre).

## Funcionalidades de la API

### Registrar Tópico
- **Endpoint**: POST /topicos
- **Cuerpo de Solicitud**: JSON con título, mensaje, autor y curso.
- **Validaciones**: Todos los campos son obligatorios, no se permiten tópicos duplicados.

### Listar Tópicos
- **Endpoint**: GET /topicos
- **Respuesta**: JSON con lista de tópicos (título, mensaje, fecha de creación, estado, autor, curso).

### Detalle de Tópico
- **Endpoint**: GET /topicos/{id}
- **Respuesta**: JSON con detalles del tópico (título, mensaje, fecha de creación, estado, autor, curso).

### Actualizar Tópico
- **Endpoint**: PUT /topicos/{id}
- **Cuerpo de Solicitud**: JSON con los datos a actualizar.
- **Validaciones**: Verificar la existencia del tópico por ID.

### Eliminar Tópico
- **Endpoint**: DELETE /topicos/{id}
- **Validaciones**: Verificar la existencia del tópico por ID.

## Autenticación

A partir de ahora, solo los usuarios autenticados pueden interactuar con la API.

### Implementación de Autenticación

#### Dependencia
Agregar la dependencia de Spring Security en el archivo `pom.xml`.

#### Configuración de Seguridad

Definir la clase `SecurityConfigurations` con anotaciones `@Configuration` y `@EnableWebSecurity`.
Utilizar la clase `HttpSecurity` para configurar el acceso a través de solicitudes HTTP.

#### Token JWT

Agregar la biblioteca JWT de Auth0 en el archivo `pom.xml`.

Crear una clase DTO `UsernamePasswordAuthenticationToken` para recibir el nombre de usuario y contraseña.

Generar y Validar Token:

Implementar la clase `TokenService` para generar y validar tokens.

El método `generarToken()` utiliza la biblioteca JWT para crear un token con el algoritmo HMAC256 y una contraseña secreta.

Configurar la fecha de expiración del token.

Inyectar esta clase en el controlador de autenticación para obtener el token retornado en la respuesta de la solicitud de inicio de sesión.

Control de Acceso:

Configurar la API para que solo los usuarios con un token válido puedan gestionar registros de tópicos.

Crear una nueva solicitud con una URL y un archivo JSON que contenga el nombre de usuario y contraseña para la generación del token.

Almacenar y enviar el token junto con las próximas solicitudes.

Mapear las URLs y validar los tokens en el controlador.

Crear un filtro o interceptor en el proyecto para validar el token en cada solicitud.

## Tecnologías y Librerías Utilizadas

- **Spring Boot**: Framework principal para el desarrollo de la API.
- **Spring Data JPA**: Para la persistencia de datos.
- **MySQL**: Base de datos relacional.
- **Spring Security**: Para la seguridad y autenticación.
- **JWT (JSON Web Token)**: Para la autenticación basada en tokens.
- **Lombok**: Para reducir el código boilerplate.
- **Jakarta Persistence API (JPA)**: Para la gestión de las entidades.

## Ejemplo de Uso

### Paso 1: Registrar un Usuario

1. **Endpoint**: `POST /usuarios`
2. **Cuerpo de Solicitud**:
   ```
   {
       "nombre": "Juan Perez",
       "correoElectronico": "juan.perez@example.com",
       "contrasena": "password",
       "perfiles": [{"id": 1, "nombre": "ROLE_USER"}]
   }
   ```

### Paso 2: Autenticarse y Obtener el Token JWT

1. **Endpoint**: `POST /auth/login`
2. **Cuerpo de Solicitud**:
   ```
   {
       "nombreUsuario": "juan.perez@example.com",
       "contrasena": "password"
   }
   ```

3. **Respuesta**:
   ```
   {
       "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
       "tipo": "Bearer"
   }
   ```

### Paso 3: Usar el Token para Acceder a Endpoints Protegidos

#### Registrar un Tópico

1. **Endpoint**: `POST /topicos`
2. **Encabezado (Headers)**:
   - `Authorization`: `Bearer <tu_token_jwt>`
3. **Cuerpo de Solicitud**:
   ```
   {
       "titulo": "Primer Tópico",
       "mensaje": "Este es el mensaje del primer tópico",
       "autor": {"id": 1},
       "curso": {"id": 1}
   }
   ```

#### Listar Tópicos

1. **Endpoint**: `GET /topicos`
2. **Encabezado (Headers)**:
   - `Authorization`: `Bearer <tu_token_jwt>`

#### Ver Detalles de un Tópico

1. **Endpoint**: `GET /topicos/{id}`
2. **Encabezado (Headers)**:
   - `Authorization`: `Bearer <tu_token_jwt>`

#### Actualizar un Tópico

1. **Endpoint**: `PUT /topicos/{id}`
2. **Encabezado (Headers)**:
   - `Authorization`: `Bearer <tu_token_jwt>`
3. **Cuerpo de Solicitud**:
   ```
   {
       "titulo": "Tópico Actualizado",
       "mensaje": "Este es el mensaje actualizado del tópico"
   }
   ```

#### Eliminar un Tópico

1. **Endpoint**: `DELETE /topicos/{id}`
2. **Encabezado (Headers)**:
   - `Authorization`: `Bearer <tu_token_jwt>`

## Configuración del Proyecto

### Paso 1: Clonar el Repositorio

Clona el repositorio del proyecto a tu máquina local:
```git clone https://github.com/iMawe/forohub.git```


### Paso 2: Configurar la Base de Datos

Configura tu base de datos MySQL y actualiza las credenciales en el archivo `application.properties`:

```
spring.datasource.url=jdbc:mysql://localhost:3306/forohub
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
```

### Paso 3: Ejecutar la Aplicación

Navega al directorio del proyecto y ejecuta la aplicación:

```mvn spring-boot```


### Paso 4: Probar la API con Postman

Utiliza Postman para probar los endpoints como se describe en la sección de "Ejemplo de Uso".

## Contribuciones

Si deseas contribuir al proyecto, por favor, sigue los pasos a continuación:

1. Realiza un fork del repositorio.
2. Crea una rama (`git checkout -b feature/nueva-funcionalidad`).
3. Realiza tus cambios (`git commit -am 'Agrega nueva funcionalidad'`).
4. Empuja tus cambios a la rama (`git push origin feature/nueva-funcionalidad`).
5. Abre un Pull Request.


