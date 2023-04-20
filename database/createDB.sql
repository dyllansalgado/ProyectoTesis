DROP TABLE IF EXISTS rol CASCADE;
DROP TABLE IF EXISTS usuario CASCADE;
DROP TABLE IF EXISTS proyecto CASCADE;
DROP TABLE IF EXISTS usuario_proyecto CASCADE;
DROP TABLE IF EXISTS glosario CASCADE;
DROP TABLE IF EXISTS termino CASCADE;
DROP TABLE IF EXISTS reunion CASCADE;
DROP TABLE IF EXISTS tema CASCADE;
DROP TABLE IF EXISTS pregunta CASCADE;
DROP TABLE IF EXISTS respuesta CASCADE;
DROP TABLE IF EXISTS voto CASCADE;
DROP TABLE IF EXISTS usuario_pregunta CASCADE;
DROP TABLE IF EXISTS voto CASCADE;
DROP TABLE IF EXISTS tipo_requisito CASCADE;
DROP TABLE IF EXISTS resultado CASCADE;
DROP TABLE IF EXISTS requisito CASCADE;
DROP TABLE IF EXISTS preguntas_recomendadas CASCADE;
DROP TABLE IF EXISTS comentario CASCADE;

CREATE TABLE rol(
    id_rol serial,
    tipo_rol varchar(64),
    PRIMARY KEY (id_rol),
    deleted boolean NOT NULL DEFAULT false
); 

CREATE TABLE usuario(
    id_usuario serial,
    nombre_usuario varchar(128),
    apellido_usuario varchar(128),
    contrasena_usuario varchar(64),
    correo_usuario varchar(128),
    id_rol integer NOT NULL,
    token_usuario integer NOT NULL,
    PRIMARY KEY (id_usuario),
    deleted boolean NOT NULL DEFAULT false,
    FOREIGN KEY (id_rol) REFERENCES rol(id_rol)
);


CREATE TABLE proyecto(
    
    id_proyecto serial,
    nombre_proyecto varchar(128),
    fecha_inicio_proyecto date,
    estado_proyecto boolean,
    objetivo_proyecto text,
    contrasena varchar(64),
    creadorProyecto varchar(128),
    correoCreador varchar(128),
    PRIMARY KEY (id_proyecto),
    deleted boolean NOT NULL DEFAULT false
    
);

CREATE TABLE usuario_proyecto(

    id_usuario_proyecto serial,
    id_usuario integer NOT NULL,
    id_proyecto integer NOT NULL,
    PRIMARY KEY (id_usuario_proyecto),
    deleted boolean NOT NULL DEFAULT false,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
    FOREIGN KEY (id_proyecto) REFERENCES proyecto(id_proyecto)
    
);

CREATE TABLE reunion(
    
    id_reunion serial,
    fecha_reunion date,
    lugar_reunion varchar(256),
    hora_reunion varchar(256),
    id_proyecto integer NOT NULL,
    PRIMARY KEY (id_reunion),
    deleted boolean NOT NULL DEFAULT false,
    FOREIGN KEY (id_proyecto) REFERENCES proyecto(id_proyecto)
);

CREATE TABLE glosario(
    id_glosario serial,
    nombre_glosario varchar(256),
    descripcion_glosario text,
    id_reunion integer NOT NULL,
    PRIMARY KEY (id_glosario),
    deleted boolean NOT NULL DEFAULT false,
    FOREIGN KEY (id_reunion) REFERENCES reunion(id_reunion)
);

CREATE TABLE termino(
    
    id_termino serial,
    nombre_termino varchar(128),
    descripcion_termino text,
    id_glosario integer NOT NULL,
    correoCreador varchar(80),
    PRIMARY KEY (id_termino),
    deleted boolean NOT NULL DEFAULT false,
    FOREIGN KEY (id_glosario) REFERENCES glosario(id_glosario)
);
CREATE TABLE tema(
    
    id_tema serial,
    nombre_tema varchar(128),
    descripcion_tema text,
    id_reunion integer NOT NULL,
    PRIMARY KEY (id_tema),
    deleted boolean NOT NULL DEFAULT false,
    FOREIGN KEY (id_reunion) REFERENCES reunion(id_reunion)
);

CREATE TABLE pregunta(
    
    id_pregunta serial,
    pregunta text,
    estado boolean,
    creador varchar(80),
    correoCreador varchar(80),
    id_tema integer NOT NULL,
    PRIMARY KEY (id_pregunta),
    deleted boolean NOT NULL DEFAULT false,
    FOREIGN KEY (id_tema) REFERENCES tema(id_tema)
);

CREATE TABLE respuesta(
    id_respuesta serial,
    respuesta text,
    id_pregunta integer NOT NULL,
    PRIMARY KEY (id_respuesta),
    deleted boolean NOT NULL DEFAULT false,
    FOREIGN KEY (id_pregunta) REFERENCES pregunta(id_pregunta)
);


CREATE TABLE voto(

    id_voto serial,
    tipo_voto boolean,
    id_pregunta integer NOT NULL,
    id_usuario integer NOT null,
    PRIMARY KEY (id_voto),
    deleted boolean NOT NULL DEFAULT false,
    FOREIGN KEY (id_pregunta) REFERENCES pregunta(id_pregunta),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)

);

CREATE TABLE usuario_pregunta(

    id_usuario_pregunta serial,
    id_usuario integer NOT NULL,
    id_pregunta integer NOT NULL,
    PRIMARY KEY (id_usuario_pregunta),
    deleted boolean NOT NULL DEFAULT false,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
    FOREIGN KEY (id_pregunta) REFERENCES pregunta(id_pregunta)

);

CREATE TABLE tipo_requisito(

    id_tipo_requisito serial,
    nombre_tipo_requisito varchar(128),
    descripcion_tipo_requisito text,
    PRIMARY KEY (id_tipo_requisito),
    deleted boolean NOT NULL DEFAULT false

);

CREATE TABLE resultado(

    id_resultado serial,
    respuesta varchar(500),
    pregunta varchar(258),
    estado boolean,
    id_tema serial,
    id_pregunta serial,
    id_respuesta serial,
    PRIMARY KEY (id_resultado),
    deleted boolean NOT NULL DEFAULT false,
    FOREIGN KEY (id_tema) REFERENCES tema(id_tema),
    FOREIGN KEY (id_pregunta) REFERENCES pregunta(id_pregunta),
    FOREIGN KEY (id_respuesta) REFERENCES respuesta(id_respuesta)

);

CREATE TABLE requisito(

    id_requisito serial,
    nombre_requisito varchar(256),
    estado_requisito boolean,
    descripcion_requisito text,
    prioridad integer,
    id_pregunta serial,
    creador_requisito varchar(80),
    correo_creador varchar(80),
    id_tipo_requisito serial,
    PRIMARY KEY (id_requisito),
    deleted boolean NOT NULL DEFAULT false,
    FOREIGN KEY (id_pregunta) REFERENCES pregunta(id_pregunta),
    FOREIGN KEY (id_tipo_requisito) REFERENCES tipo_requisito(id_tipo_requisito)

);


CREATE TABLE preguntas_recomendadas(

    id_pregunta_recomendada serial,
    pregunta_recomendada varchar(256),
    PRIMARY KEY (id_pregunta_recomendada),
    deleted boolean NOT NULL DEFAULT false

);

CREATE TABLE comentario(

    id_comentario serial,
    comentario text,
    nombre_creador_comentario varchar(80),
    correo_creador_comentario varchar(80),
    id_pregunta serial,
    PRIMARY KEY (id_comentario),
    deleted boolean NOT NULL DEFAULT false,
    FOREIGN KEY (id_pregunta) REFERENCES pregunta(id_pregunta)

);