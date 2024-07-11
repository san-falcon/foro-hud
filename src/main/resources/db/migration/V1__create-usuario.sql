create table usuarios(
    id bigserial not null,
    nombre varchar(200) not null,
    correo varchar(200) not null,
    password varchar(350) not null,
    primary key(id)
)