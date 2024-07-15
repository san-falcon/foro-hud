<h1># FORO HUD</h1>
Se creo una API Rest con spring boot para crear topicos de dudas, recomendaciones y mas.
Tambien se integro Spring security con JWT para que la API Rest sea privada y no de dominio publico.

## Funcionamiento de la Api Rest


### Dato: Por defecto no tendras un usuario asi que primero deberas registrarte directamente con comandos SQL y escriptar tu contraseña con Bcrypt.
Aca abajo te dejo el sql completo con la contraseña encryptada.
#### La contraseña es = user123456
#### INSERT INTO usuarios (nombre, correo, password)
#### VALUES ('Usuario de prueba', 'user@gmail.com', '$2y$10$tc.hH/Fmpe183iP7jbmK6OU8n87D3jeXSB8xpmQYTdq8eiMdJsFf2');



## Una vez que tengamos nuestro usuario debemos autenticarnos para poder tener nuestro token.
En el JSON del body debemos poner los datos del correo y password que se creo.
El endpoint es http://localhost:8080/autenticacion

![image](https://github.com/user-attachments/assets/6ed8bc44-28e9-430e-9374-91707413d7df)

### Con el token podremos consumir los EndPoints de detalles del usuario y de los TOPICOS

## 1) Crear topico
![image](https://github.com/user-attachments/assets/775c142b-6bf2-4674-af80-3b99034f7590)

## 2) Detalle del topico
Datos Extra: EndPoint - http://localhost:8080/topicos/{idDelTopicoABuscar}
![image](https://github.com/user-attachments/assets/2018c71a-e833-433c-9a96-3ac434f30427)

## 3) Listamos todos los topicos si deseas puedes agregar parametros
### Parametros de configuracion
### page = La pagina a mostrar, size = cuantos registros a mostrar y entre otros
### Por defecto se ordema por la fecha de manera ASC y solo te muestra 10 registros
![image](https://github.com/user-attachments/assets/363be7ce-0508-496d-ac39-fee5c49853a5)


## 4) Puedes actualizar directamente el estado del topico como ATENDIDO
![image](https://github.com/user-attachments/assets/81196fc0-3e34-4885-932a-1b658cee030b)

## 5) Actualiza los datos de los topicos
Datos a actualizar (Titulo, Mensaje, Autor y Curso)
DATO: Por si te equivocaste o quieres corregir tu topico
![image](https://github.com/user-attachments/assets/5efe7c8a-6e67-4368-9b29-a6b440594231)

## 6) Eliminacion permanetemento de un Topico
![image](https://github.com/user-attachments/assets/538de50e-eac7-4d4f-bd4b-66100ab2bf31)

# EXTRA

## Los EndPoint cuentan con validaciones y si no lo pasas te devolvera un JSON con los detalles

### Este es un ejemplo cuando no envias los datos correctos
![image](https://github.com/user-attachments/assets/768be27e-ab3e-4a0e-b9ea-97fca5c6b568)

### Cuando no encuentra el ID del topico; La api cuenta con mas validaciones.
![image](https://github.com/user-attachments/assets/73a83adf-a468-466f-a2d3-e53d0164ae62)

## ✔️ Tecnologias utilizadas
- `Java 17`
- `Spring Boot`
- `Spring Security`
- `JWT`
- `Lombok`
- `JPA con Hibernate`
- `FlyWay - Migraciones`
- `Validation`
- `PostgreSQL`










