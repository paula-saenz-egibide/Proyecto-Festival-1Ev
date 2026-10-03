# Festival App

Aplicación Java para gestionar la información de un festival: artistas,
escenarios, actuaciones, espectadores y entradas. Es un proyecto de la
asignatura de Acceso a Datos, organizado en modelos, DAO, servicios y utilidades.

## Funcionalidades

- Gestión de registros: altas, bajas, modificaciones, listados y búsquedas adicionales.
- Validaciones de relaciones entre artistas, escenarios, actuaciones y
  espectadores; control de solapamientos y capacidad de entradas.
- Control de errores y validación de los datos introducidos por el usuario:
  comprueba formatos y valores como números, fechas, horas y correos, y avisa
  si hay identificadores duplicados, referencias inexistentes o conflictos.
- Persistencia de objetos serializados en ficheros `.dat`.
- Exportación XML con XStream y DOM, y generación y lectura de JSON con Gson.
- Interfaz web para gestionar el festival, filtrar artistas por género y país,
  buscar actuaciones por fecha y generar o descargar archivos.

## Tecnologías

- Java 17 o superior.
- Maven.
- Gson y XStream (dependencias declaradas en `pom.xml`).
- IntelliJ IDEA recomendado para abrir y ejecutar el proyecto.

## Manual de usuario

Consulta el **[Manual de usuario y guía del proyecto](docs/MANUAL_USUARIO.md)**
para obtener las instrucciones de clonación, configuración y ejecución de la
consola y la web, además de información sobre el uso, los ficheros de datos y
las funcionalidades adicionales del proyecto.