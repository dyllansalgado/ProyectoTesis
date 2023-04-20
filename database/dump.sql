INSERT INTO rol(id_rol,tipo_rol)
VALUES(1,'Jefe de Proyecto'),
(2,'Usuario'),
(3,'Administrador');

INSERT INTO usuario(id_usuario,nombre_usuario,apellido_usuario,contrasena_usuario,correo_usuario,id_rol,token_usuario)
VALUES(1,'hola','salgado perez','123','juan_a@gmail.com',1,1),
(2,'admin','admin','contrasenaAdmin','admin@admin.com',3,1);

INSERT INTO proyecto(id_proyecto,nombre_proyecto,fecha_inicio_proyecto,estado_proyecto,objetivo_proyecto,contrasena,creadorProyecto, correoCreador)
VALUES(1,'Toma de requisitos para empresa Home Canter','2022/09/20',false,'Se solicita que se reunan para comnezar el proyecto','contrasena_123','hola','juan_a@gmail.com');

INSERT INTO reunion(id_reunion,fecha_reunion,hora_reunion,lugar_reunion,id_proyecto)
VALUES(1,'2022/09/20','22:00','zoom',1);

INSERT INTO glosario(id_glosario,nombre_glosario,descripcion_glosario,id_reunion)
VALUES(1,'Glosario para toma de requisitos Home Canter','Este glosario muestra algunos terminos que se deberian saber antes de comenzar las reuniones',1);

INSERT INTO glosario(id_glosario,nombre_glosario,descripcion_glosario,id_reunion)
VALUES(2,'Soy un segundo glosario para reunion 1','a',1);

INSERT INTO termino(id_termino,nombre_termino,descripcion_termino,id_glosario,correoCreador)
VALUES(1,'Electro-Domesticos','Esta descripcion es god',1,'juan_a@gmail.com');

INSERT INTO tema(id_tema,nombre_tema,descripcion_tema,id_reunion)
VALUES(1,'Revision preguntas x','soy descripcion',1);

INSERT INTO tema(id_tema,nombre_tema,descripcion_tema,id_reunion)
VALUES(2,'tema nuevo id_reunion 1','soy descripcion',1);

INSERT INTO pregunta(id_pregunta,pregunta,estado,id_tema,creador,correoCreador)
VALUES(1,'¿Hola?',false,1,'hola','juan_a@gmail.com');

INSERT INTO respuesta(id_respuesta,respuesta,id_pregunta)
VALUES(1,'Soy una respuesta para pregunta 1',1);

INSERT INTO voto(id_voto,tipo_voto,id_pregunta,id_usuario)
VALUES(1,true,1,1);

INSERT INTO usuario_pregunta(id_usuario_pregunta,id_usuario,id_pregunta)
VALUES(1,1,1);

INSERT INTO usuario_proyecto(id_usuario_proyecto,id_usuario,id_proyecto)
VALUES(1,1,1);

INSERT INTO tipo_requisito(id_tipo_requisito,nombre_tipo_requisito,descripcion_tipo_requisito)
VALUES(1,'Requerimiento funcional','Describe acciones específicas. A menudo se dividen en casos de uso, el cual definen lo que el sistema debe hacer');

INSERT INTO tipo_requisito(id_tipo_requisito,nombre_tipo_requisito,descripcion_tipo_requisito)
VALUES(2,'Requerimiento no funcional de rendimiento','Corresponden a los requisitos que tienen relación con el tiempo de respuesta y rendimiento del sistema ');

INSERT INTO tipo_requisito(id_tipo_requisito,nombre_tipo_requisito,descripcion_tipo_requisito)
VALUES(3,'Requerimiento no funcional de seguridad','Especifican las medidas que el sistema debe tener para otorgar seguridad y protección de los datos');

INSERT INTO tipo_requisito(id_tipo_requisito,nombre_tipo_requisito,descripcion_tipo_requisito)
VALUES(4,'Requerimiento no funcional de calidad','Estos requisitos deben especificar como se analizará el nivel de calidad que debe cumplir el sistema');

INSERT INTO preguntas_recomendadas(id_pregunta_recomendada,pregunta_recomendada)
VALUES(1,'¿Cuál es el proceso básico de la empresa?');
INSERT INTO preguntas_recomendadas(id_pregunta_recomendada,pregunta_recomendada)
VALUES(2,'¿Qué datos utiliza o produce este proceso?');
INSERT INTO preguntas_recomendadas(id_pregunta_recomendada,pregunta_recomendada)
VALUES(3,'¿Cuáles son los límites impuestos por el tiempo y la carga de trabajo?');
INSERT INTO preguntas_recomendadas(id_pregunta_recomendada,pregunta_recomendada)
VALUES(4,'¿Qué controles de desempeño utiliza?');
INSERT INTO preguntas_recomendadas(id_pregunta_recomendada,pregunta_recomendada)
VALUES(5,'¿Cuál es la finalidad de la actividad dentro de la empresa?');
INSERT INTO preguntas_recomendadas(id_pregunta_recomendada,pregunta_recomendada)
VALUES(6,'¿Qué pasos se siguen para realizarla?');
INSERT INTO preguntas_recomendadas(id_pregunta_recomendada,pregunta_recomendada)
VALUES(7,'¿Dónde se realizan estos pasos?');
INSERT INTO preguntas_recomendadas(id_pregunta_recomendada,pregunta_recomendada)
VALUES(8,'¿Quiénes los realizan?');
INSERT INTO preguntas_recomendadas(id_pregunta_recomendada,pregunta_recomendada)
VALUES(9,'¿Cuánto tiempo tardan en efectuarlos?');
INSERT INTO preguntas_recomendadas(id_pregunta_recomendada,pregunta_recomendada)
VALUES(10,'¿Con cuánta frecuencia lo hacen?');
INSERT INTO preguntas_recomendadas(id_pregunta_recomendada,pregunta_recomendada)
VALUES(11,'¿Quiénes emplean la información resultante?');
INSERT INTO preguntas_recomendadas(id_pregunta_recomendada,pregunta_recomendada)
VALUES(12,'¿Cuáles son las personas claves en el sistema? ¿Por qué son importantes?');
INSERT INTO preguntas_recomendadas(id_pregunta_recomendada,pregunta_recomendada)
VALUES(13,'¿Existen obstáculos o influencias de tipo político que afectan la eficiencia del sistema?');
INSERT INTO preguntas_recomendadas(id_pregunta_recomendada,pregunta_recomendada)
VALUES(14,'¿Qué criterios se emplean para medir y evaluar el desempeño?');
INSERT INTO preguntas_recomendadas(id_pregunta_recomendada,pregunta_recomendada)
VALUES(15,'¿Qué áreas necesitan un control específico?');
INSERT INTO preguntas_recomendadas(id_pregunta_recomendada,pregunta_recomendada)
VALUES(16,'¿Existen métodos para evadir el sistema?, ¿Por qué se presentan?');

INSERT INTO comentario(id_comentario,comentario,nombre_creador_comentario,correo_creador_comentario,id_pregunta)
VALUES(1,'soy un comentario para pregunta','Hola','juan_a@gmail.com',1);