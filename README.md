# Software Colaborativo de Apoyo para Entrevistas en el proceso de Captura de requisitos

<center> <h2>Índice</h2> </center>

1. [Instrucciones de Instalación](#id1)

    1.1 [Java versión 11](#id1.1)
  
    1.2 [NodeJS](#id1.2)

    1.3 [PostgreSQL](#id1.3)

2. [Carga de datos a la Base de Datos](#id2)
3. [Compilación Backend y Frontend](#id3)

    3.1 [Backend](#id3.1)
  
    3.2 [Frontend](#id3.2)

4. [Solución de posibles errores](#id4)

<div id='id1' />


## Instrucciones de Instalación
Para que el software funcione de manera correcta, debe tener instaladas tres tecnologías que se muestran a continuación:

<div id='id1.1' />
1. Java versión 11


Para su instalación de forma correcta debe seguir los siguientes pasos: 
#### 1.1 Debe ingresar al siguiente link, el cual al abrirlo se verá de la siguiente manera:
https://www.oracle.com/cl/java/technologies/javase/jdk11-archive-downloads.html
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/java_01.PNG" width="600"></a></p>

#### 1.2 Luego debe bajar y seleccionar el siguiente archivo, si su sistema operativo corresponde a windows x64.
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/java_02.PNG" width="600"></a></p>

#### 1.3 Al seleccionar para descargar el link correspondiente al sistema operativo, debe aceptar los términos y condiciones de Oracle y seleccionar Download.
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/java_03.PNG" width="600"></a></p>

#### 1.4 Debe crear una cuenta en Oracle y la descarga de Java versión 11 comenzará.
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/java_05.PNG" width="600"></a></p>

#### 1.5 Una vez descargado el archivo, lo selecciona y debe colocar Sí.
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/java_06.PNG" width="600"></a></p>

#### 1.6 Selecciona Next.
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/java_07.PNG" width="600"></a></p>

#### 1.7 Nuevamente Next.
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/java_08.PNG" width="600"></a></p>

#### 1.8 Selecciona Close y la instalación de Java 11 se encuentra realizada con éxito.
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/java_09.PNG" width="600"></a></p>

<div id='id1.2' />
2. NodeJS, su instalación es necesaria para utilizar el comando npm en el frontend.

Para su instalación de forma correcta debe seguir los siguientes pasos: 
#### 2.1 Debe ingresar al siguiente link, descargar el instalador para el sistema operativo que este utilizando, en este caso, Windows 64-bit:
https://nodejs.org/es/download/
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/Node_01.PNG" width="600"></a></p>

#### 2.2 Al descargar el archivo, debe abrirlo y seguir los siguientes pasos. Seleccione Next:
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/Node_02.PNG" width="600"></a></p>

#### 2.3 Debe aceptar los términos y condiciones. Luego debe seleccionar Next:
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/Node_03.PNG" width="600"></a></p>

#### 2.4 Presione Next:
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/Node_04.PNG" width="600"></a></p>

#### 2.5 Nuavemente Next:
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/Node_05.PNG" width="600"></a></p>

#### 2.6 Debe seleccionar el recuadro que dice "Automatically install". Luego debe presionar Next:
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/Node_06.PNG" width="600"></a></p>

#### 2.7 Debe seleccionar install:
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/Node_07.PNG" width="600"></a></p>

#### 2.8 Comenzará la descarga e instalación, debe seleccionar Sí:
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/Node_08.PNG" width="600"></a></p>

#### 2.9 La descarga ha sido completada y debe seleccionar Finish:
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/Node_09.PNG" width="600"></a></p>

#### 2.10 Se abrirá la consola, debe seleccionar dos veces el Enter o el Continuar
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/Node_10.PNG" width="600"></a></p>

#### 2.11 Luego debe seleccionar Sí
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/Node_11.PNG" width="600"></a></p>

#### 2.12 Comenzará la descarga a traves de la consola. Debe esperar hasta que se termine la instalación
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/Node_12.PNG" width="600"></a></p>

#### 2.13 Una vez que la descarga ha sido completada, debe ingresar a mi PC e ingresar a las propiedades
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/Node_13.PNG" width="600"></a></p>

#### 2.14 Debe ingresar a "Opciones avanzada del sistema"
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/Node_14.PNG" width="600"></a></p>

#### 2.15 Ingresar a "Variables de entorno..."
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/Node_15.PNG" width="600"></a></p>

#### 2.16 Ingresar a "Nueva" en variables de usuario
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/Node_16.PNG" width="600"></a></p>

#### 2.17 Agregar una nueva variable de entorno con el nombre de NodeJS y la dirección donde se encuentra la carpeta de NodeJS. Selecciona Aceptar
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/Node_17.PNG" width="600"></a></p>

#### 2.18 Una vez seleccionado Aceptar, NodeJS se encontrará instalado de forma correcta en su sistema. Debe reiniciar el computador o PC y para que se actualicen de forma correcta las variables de entorno


<div id='id1.3' />
3. PostgreSQL

Para su instalación de forma correcta debe seguir los siguientes pasos: 
#### 3.1 Debe ingresar al siguiente link, descargar el instalador para el sistema operativo que este utilizando, en este caso, Windows 64-bit y la versión 14.6:
https://www.enterprisedb.com/downloads/postgres-postgresql-downloads
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/postgresql_01.PNG" width="600"></a></p>

#### 3.2 Seleccionar Sí
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/postgresql_02.PNG" width="600"></a></p>

#### 3.3 Seguir los siguientes pasos:
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/postgresql_03.PNG" width="600"></a></p>
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/postgresql_04.PNG" width="600"></a></p>
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/postgresql_05.PNG" width="600"></a></p>
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/postgresql_06.PNG" width="600"></a></p>

#### 3.4 Ingresar una clave que no se le olvide, en este caso se utiliza como contraseña: 123456789 y luego debe seleccionar siguiente
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/postgresql_07.PNG" width="600"></a></p>

#### 3.5 Acá debe indicar el puerto en el cuál estará corriendo la base de datos, debe ingresar en el puerto 5432, como indica la imagen
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/postgresql_08.PNG" width="600"></a></p>

#### 3.6 Seguir nuevamente los pasos:
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/postgresql_09.PNG" width="600"></a></p>
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/postgresql_10.PNG" width="600"></a></p>
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/postgresql_11.PNG" width="600"></a></p>
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/postgresql_12.PNG" width="600"></a></p>

#### 3.7 Seleccionar Terminar
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/postgresql_13.PNG" width="600"></a></p>

#### 3.8 Seleccionar Next
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/postgresql_14.PNG" width="600"></a></p>

#### 3.9 Instalación pack de lenguaje, debe seguir los pasos de las siguientes imágenes
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/postgresql_15.PNG" width="600"></a></p>
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/postgresql_16.PNG" width="600"></a></p>
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/postgresql_17.PNG" width="600"></a></p>
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/postgresql_18.PNG" width="600"></a></p>
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/postgresql_19.PNG" width="600"></a></p>
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/postgresql_20.PNG" width="600"></a></p>
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/postgresql_21.PNG" width="600"></a></p>

#### 3.10 Seleccionar Finish
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/postgresql_22.PNG" width="600"></a></p>

#### 3.11 Seleccionar Finish
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/postgresql_23.PNG" width="600"></a></p>

#### Con estos pasos ya tendría instalado el gestor de la base de datos PostgreSQL.

<div id='id2' />


## Carga de datos a la Base de Datos

1. Puede utilizar la herramienta de base de datos que usted desee: pgAdmin4 , Dbeaver, Adminer ,etc. En este caso se va a enseñar la creación y cargado de la base de datos en pgAdmin 4.

#### 1.1 Al tener descargado pgAdmin 4, debe crear la nueva base de datos de la siguiente manera:
Primero le solicitará la contraseña de pgAdmin que usted debe crear y luego la de PostgreSQL, que en este caso era 123456789
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/baseDeDatos_01.PNG" width="600"></a></p>


#### 1.2 Crear nueva base de datos, el nombre debe ser scaiec:
Debe ingresar el nombre de scaiec para su correcto funcionamiento
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/baseDeDatos_02.1.PNG" width="600"></a></p>
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/baseDeDatos_02.PNG" width="600"></a></p>


#### 1.3 Seleccion con click derecho la base de datos scaiec e ingresar a Query Tool:
Debe seleccionar Query Tool para crear el algoritmo que ingrese las tablas y los datos a la base de datos
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/baseDeDatos_03.PNG" width="600"></a></p>
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/baseDeDatos_04.PNG" width="600"></a></p>

#### 1.4 Luego debe ir a las carpetas :
Debe ir a las carpetas que se encuentran a continuación y copiar toda la información, donde debe ser pegada en el archivo de Query Tool
https://github.com/dyllansalgado/ProyectoTesis/blob/main/database/createDB
https://github.com/dyllansalgado/ProyectoTesis/blob/main/database/dump
Es decir, copia la información de createDB la pega en el archivo creado de Query Tool y luego ingresa a la carpeta dump y nuevamente copia toda la información y la debe pegar a continuación de los datos ya copiados que se encuentran en Query Tool, quedando algo similar a:
<p><a><img src="https://github.com/dyllansalgado/ProyectoTesis/blob/main/ImagenesREADME/baseDeDatos_05.PNG" width="600"></a></p>
Luego selecciona el botón de play o run y la base de datos ya se encontraría creada y cargada con datos

<div id='id3' />


## Compilación Backend y Frontend

Como ultimo paso, tendremos que compilar el backend y el frontend para que el software funcione. Para realizar esta accion usted debe realizar lo siguiente:


<div id='id3.1' />

### 3.1 Backend 

En la consola debe ingresar a la carpeta de Backend e ingresar el siguiente comando:

###  `/.gradlew bootrun` o en caso de no funcionar `gradle bootrun`

<div id='id3.2' />

### 3.2 Frontend 

En la consola debe ingresar a la carpeta de Frontend e ingresar el siguiente comando:

### `npm run start` o `npm start`


Cabe destacar que al momento de ingresar los comandos, no puede cerrar la consola, por ende debe abrir dos consolas para ingresar un comando en cada una. Al realizar esta acción, se debería abrir una ventana en su navegador con la URL de `localhost:3000`

<div id='id4' />


## Solución de posibles errores

Al momento de seguir el siguiente manual de instalación y uso, ha ocurrido solo un error. Al momento de ejecutar el comando `npm run start` o `npm start` en la consola dentro de la carpeta del Frontend aparece este error: `react-scripts: command not found error`.
La solución consiste en ingresar el siguiente comando `npm install react-scripts --save` en la consola dentro de la carpeta del Frontend, esperar su instalación y volver a ingresar el comando `npm run start` o `npm start`. Con esto ya se encuentra solucionado el error.

