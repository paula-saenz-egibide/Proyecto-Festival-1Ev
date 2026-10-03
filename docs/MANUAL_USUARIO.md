# Manual de usuario y guía del proyecto

## 1. ¿Qué es Festival App?

Festival App es una aplicación Java para gestionar los datos de un festival.
Permite mantener artistas, escenarios, actuaciones, espectadores y entradas.
Los datos principales se guardan en ficheros binarios `.dat`; además, la
aplicación puede exportar y leer JSON, exportar XML y ofrecer una interfaz web.

El proyecto dispone de dos formas de uso:

- **Consola:** menú interactivo iniciado desde `Main`.
- **Web:** servidor HTTP iniciado desde `web.Servidor`; se utiliza desde el
  navegador.

Ambas formas trabajan con los ficheros de la carpeta `data` del proyecto.

## 2. Clonar, abrir y ejecutar el proyecto

### Requisitos

- IntelliJ IDEA.
- JDK 17 o superior.
- Git, para clonar el repositorio.

### Obtener el código y abrirlo en IntelliJ

En PowerShell o en una terminal, sitúate en la carpeta donde quieras guardar el
proyecto y ejecuta:

```powershell
git clone https://github.com/paula-saenz-egibide/Proyecto-Festival-1Ev.git
```

En IntelliJ IDEA, selecciona **File > Open**, elige la carpeta
`Proyecto-Festival-1Ev` que se acaba de clonar y acepta abrir/importar el
proyecto Maven. Espera a que termine la sincronización y la descarga de las
dependencias declaradas en `pom.xml`. Comprueba que IntelliJ utiliza un JDK 17
o superior.

Configura el **Working directory** de las configuraciones de ejecución como la
raíz del proyecto, es decir, la carpeta que contiene `pom.xml`. La aplicación
usa rutas relativas como `data/artistas.dat` y `web/index.html`; si se ejecuta
desde otra carpeta, puede no encontrar esos recursos.

### Ejecutar la aplicación de consola

1. En el panel del proyecto, abre `src/Main.java`.
2. Pulsa el triángulo verde junto a `main` o haz clic derecho en la clase y
   selecciona **Run 'Main.main()'**.
3. La consola crea `data/` y los cinco ficheros `.dat` si faltan, y muestra el
   menú del festival.
4. Escribe el número de la opción que quieras y pulsa Intro. Usa `0` para
   volver al menú anterior o salir.

### Ejecutar la aplicación web

1. En el panel del proyecto, abre `src/web/Servidor.java`.
2. Pulsa el triángulo verde junto a `main` o haz clic derecho en la clase y
   selecciona **Run 'Servidor.main()'**.
3. Espera el mensaje `Servidor iniciado` y abre en el navegador la dirección
   que aparece en la consola, normalmente `http://localhost:8080`. Si el puerto
   está ocupado, el servidor prueba el siguiente.
4. Para detenerlo, usa el botón rojo **Stop** de la ventana Run de IntelliJ.

El servidor crea `data/` si no existe. Los datos se escribirán en los `.dat`
cuando se creen registros. No borres `data/` si quieres conservar la
información guardada.

## 3. Datos y relaciones

| Clase | Información que almacena |
|---|---|
| `Artista` | ID, nombre, género y país |
| `Escenario` | ID, nombre, ubicación y capacidad |
| `Actuacion` | ID, ID del artista, ID del escenario, fecha, hora y duración |
| `Espectador` | ID, nombre, correo electrónico y edad |
| `Entrada` | ID, ID de la actuación, ID del espectador, precio, tipo y estado activa/inactiva |

Las relaciones se guardan mediante identificadores:

```text
Artista 1 ─── N Actuaciones N ─── 1 Escenario
Actuación 1 ─── N Entradas N ─── 1 Espectador
```

Por ejemplo, una actuación no almacena una copia completa del artista: guarda
`idArtista` y `idEscenario`. Una entrada guarda `idActuacion` e
`idEspectador`. Así se pueden relacionar los registros consultando sus
identificadores.

## 4. Uso desde la consola

En el menú principal se elige una sección: artistas, escenarios, actuaciones,
espectadores o entradas. Dentro de cada sección se muestran las operaciones
disponibles. Se vuelve al menú anterior con la opción `0`.

Las operaciones comunes son **alta, baja, listado, búsqueda por ID y
modificación**. También hay búsquedas adicionales:

- **Artistas:** búsqueda por género y por país.
- **Actuaciones:** búsqueda por fecha.
- **Escenarios, espectadores y entradas:** búsqueda por ID.

Las opciones de exportación y lectura de JSON/XML aparecen en los submenús de
las entidades.

### Reglas y validaciones de entrada

La aplicación comprueba, entre otras cosas, que se introduzcan valores con el
formato esperado, que no se repitan ID y que ciertos campos no queden
vacíos. También valida el formato del correo, la fecha (`AAAA-MM-DD`), la hora
(`HH:MM`) y algunos rangos numéricos.

Al crear actuaciones se comprueba que existan el artista y el escenario. Se
rechazan solapamientos horarios en un mismo escenario y fecha. Al crear
entradas se comprueba que existan la actuación y el espectador y que haya
capacidad disponible en el escenario.

## 5. Uso desde la web

### Pantallas

- **Inicio:** muestra contadores de artistas, escenarios y entradas.
- **Artistas, escenarios, actuaciones, espectadores y entradas:** permiten
  listar registros, buscarlos por ID y abrir formularios para crear,
  modificar o eliminar.
- **Artistas:** además de la búsqueda por ID, se pueden filtrar por género y
  país con los selectores. Las opciones disponibles se obtienen de los artistas
  registrados y ambos filtros se pueden combinar. Al seleccionar “Todos” se
  quita ese criterio.
- **Entradas:** el campo `activa` de los formularios se edita con una casilla
  de verificación. Marcada significa que la entrada está activa; desmarcada,
  que no está activa.
- **Espectadores:** el campo de correo comprueba el formato antes de guardar
  o modificar. Debe seguir una forma como `usuario@gmail.com`; si el formato
  no es válido, aparece un error y no se envía la operación.
- **Archivos:** permite generar y descargar JSON y XML de las cinco entidades.
  Primero se genera el archivo y después se puede descargar.

La búsqueda por ID funciona en las cinco secciones. Los filtros por género y
país están disponibles en artistas. La búsqueda adicional de actuaciones por
fecha también está disponible en la web: selecciona una fecha para mostrar sus
actuaciones o deja el campo vacío para volver a verlas todas.

Los errores de validación y los errores que devuelve el servidor se muestran
en la web. Si el servidor no responde, se muestra un mensaje de conexión.

## 6. Ficheros, formatos y acceso a datos

Los ficheros se crean o se guardan en `data/`, relativa al directorio de
trabajo del programa.

| Formato | Ejemplos | Uso |
|---|---|
| Binario serializado | `artistas.dat`, `actuaciones.dat` | Almacenamiento principal utilizado por los DAO |
| JSON | `artistas.json`, `entradas.json` | Exportar datos y leerlos para mostrarlos en consola |
| XML | `artistas.xml`, `escenarios.xml` | Exportar los datos; no se importan actualmente desde XML |

Los cinco modelos implementan `Serializable`. Cada DAO utiliza
`ObjectInputStream` para leer objetos y `ObjectOutputStream` para escribirlos.
La lectura recorre los objetos en secuencia hasta llegar al final del fichero.
Para buscar por ID, el DAO obtiene la lista y la recorre. Para modificar o
eliminar un elemento, se lee la lista, se cambia en memoria y se vuelve a
escribir el fichero.

El acceso actual a los `.dat` es **secuencial**, no aleatorio. Es una decisión
adecuada para el tamaño y las operaciones de este proyecto:

- `ObjectOutputStream` guarda objetos serializados, y su tamaño depende de sus
  campos y valores. No existe una posición fija calculable para cada registro
  como la habría con registros de longitud fija.
- Las búsquedas por ID y los listados necesitan examinar los registros; con
  colecciones pequeñas, recorrerlos desde el principio es sencillo y suficiente.
- Para modificar o eliminar, los DAO ya cargan la colección, cambian la lista
  y vuelven a escribirla. El acceso aleatorio no evitaría esa reescritura en el
  diseño actual.
- Usar `RandomAccessFile` directamente no permitiría saltar a un objeto
  serializado conociendo solo su ID. Haría falta añadir un índice de posiciones
  o diseñar otro formato (por ejemplo, registros de longitud fija con offsets),
  además de mantener ese índice coherente en altas, bajas y modificaciones.
  Esa complejidad no aporta una ventaja necesaria para la escala prevista.


### JSON

La clase `GsonJSON` usa la biblioteca Gson para generar y leer JSON. Hay
operaciones para las cinco entidades. La lectura de JSON de los submenús
muestra su contenido en consola; **no importa esos datos a los `.dat`** ni los
fusiona con los registros actuales.

Como `Actuacion` contiene una `LocalDate`, `LocalDateAdapter` convierte las
fechas a texto al escribirlas y las reconstruye al leerlas.

### XML

El proyecto implementa dos formas de generar XML:

- **XStream** (`XStreamXML`): artistas, actuaciones y entradas.
- **DOM de Java** (`DOMXML`): escenarios y espectadores.

Esto permite cumplir tanto con la exportación mediante XStream como con una
implementación XML distinta. Actualmente XML sirve para exportar, no para
recuperar datos hacia los `.dat`.

## 7. Organización del código

```text
src/
  Main.java                 Menú de consola
  model/                    Clases de datos serializables
  dao/                      Lectura y escritura de los .dat
  service/                  Reglas de negocio y operaciones
  util/                     Validación de entrada e inicialización
  json/                     Gson y conversión de LocalDate
  xml/                      Exportación XStream y DOM
  web/                      Servidor HTTP y lógica para la interfaz web
web/
  index.html                Estructura de las pantallas
  css/style.css             Estilos
  js/app.js                 Interacción con la API
data/                       Datos generados durante la ejecución
```

Una operación típica sigue este recorrido:

```text
Main o navegador → Service / GestorWeb → DAO → fichero .dat
```

El modelo representa la información; el DAO se ocupa del fichero; el servicio
aplica reglas del festival; y la interfaz recoge datos y presenta resultados.
En web, `Servidor` recibe las peticiones HTTP y `GestorWeb` las conecta con
los servicios.

Los ficheros `.dat` contienen los datos de trabajo. No los borres mientras
quieras conservar los registros. JSON y XML son exportaciones que se pueden
volver a generar desde la aplicación.

## 8. Funcionalidades adicionales

Además de las operaciones básicas de alta, baja, modificación y listado, el
proyecto incluye ampliaciones relacionadas con los puntos extra del enunciado.

| Ampliación | Qué hace la aplicación |
|---|---|
| Más clases y ficheros | Gestiona cinco modelos serializables relacionados y cinco ficheros `.dat`, más que el mínimo de tres. |
| Búsquedas y reglas del festival | Busca artistas por género y país, actuaciones por fecha y registros por ID. Comprueba relaciones, solapamientos horarios y capacidad de entradas. |
| XML con dos métodos | Exporta XML con XStream y también con DOM de Java, una técnica distinta. |
| JSON de lectura y escritura | Gson exporta JSON de las cinco entidades y permite leer esos archivos y mostrar su contenido en consola. |
| Interfaz gráfica web | Un servidor HTTP ofrece una interfaz HTML/CSS/JavaScript para gestionar registros y generar/descargar archivos. Incluye búsqueda por ID, filtros de género/país para artistas y checkbox para el estado de las entradas. |
| Control de errores | Valida entradas en consola y muestra errores de validación o de operaciones web rechazadas. |
