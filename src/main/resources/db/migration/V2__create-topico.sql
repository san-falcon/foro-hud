create table topicos(
    id bigserial not null,
    titulo varchar(200) not null,
    mensaje text not null,
    autor varchar(200) not null,
    curso varchar(350) not null,
    fecha timestamp not null,
    estado varchar(200) not null,
    primary key(id)
)