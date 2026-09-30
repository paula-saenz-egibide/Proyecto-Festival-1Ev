import model.Artista;
import model.Actuacion;
import model.Escenario;
import model.Espectador;
import model.Entrada;

import service.ArtistaService;
import service.ActuacionService;
import service.EscenarioService;
import service.EspectadorService;
import service.EntradaService;

import util.EntradaUtil;
import util.InicializadorFicheros;

import java.time.LocalDate;
import java.util.List;

public class Main {

    // SERVICES
    private static final ArtistaService artistaService = new ArtistaService();

    private static final EscenarioService escenarioService = new EscenarioService();

    private static final ActuacionService actuacionService = new ActuacionService();

    private static final EspectadorService espectadorService = new EspectadorService();

    private static final EntradaService entradaService = new EntradaService();

    // MAIN
    public static void main(String[] args) {

        InicializadorFicheros.inicializar();

        boolean salir = false;

        while (!salir) {

            mostrarMenuPrincipal();

            int opcion = EntradaUtil.leerEntero("Elige una opción: ");

            switch (opcion) {

                case 1:
                    menuArtistas();
                    break;

                case 2:
                    menuEscenarios();
                    break;

                case 3:
                    menuActuaciones();
                    break;

                case 4:
                    menuEspectadores();
                    break;

                case 5:
                    menuEntradas();
                    break;

                case 0:
                    salir = true;
                    System.out.println("Programa finalizado.");
                    break;

                default:
                    System.out.println("Opción no válida. Debes elegir una opción del menú.");
            }
        }
    }


    // MENÚ PRINCIPAL
    private static void mostrarMenuPrincipal() {

        System.out.println();
        System.out.println("======================================");
        System.out.println("       GESTIÓN DEL FESTIVAL");
        System.out.println("======================================");
        System.out.println("1. Gestionar artistas");
        System.out.println("2. Gestionar escenarios");
        System.out.println("3. Gestionar actuaciones");
        System.out.println("4. Gestionar espectadores");
        System.out.println("5. Gestionar entradas");
        System.out.println("0. Salir");
        System.out.println("======================================");
    }


    // MENÚ ARTISTAS
    private static void menuArtistas() {

        boolean volver = false;

        while (!volver) {

            System.out.println();
            System.out.println("======================================");
            System.out.println("          GESTIÓN DE ARTISTAS");
            System.out.println("======================================");
            System.out.println("1. Alta");
            System.out.println("2. Baja");
            System.out.println("3. Listado");
            System.out.println("4. Buscar por ID");
            System.out.println("5. Modificar");
            System.out.println("6. Exportar XML");
            System.out.println("0. Volver");
            System.out.println("======================================");

            int opcion = EntradaUtil.leerEntero("Elige una opción: ");

            switch (opcion) {

                case 1:
                    altaArtista();
                    break;

                case 2:
                    bajaArtista();
                    break;

                case 3:
                    listarArtistas();
                    break;

                case 4:
                    buscarArtista();
                    break;

                case 5:
                    modificarArtista();
                    break;

                case 6:
                    artistaService.exportarXML();
                    break;

                case 0:
                    volver = true;
                    break;

                default:
                    System.out.println(
                            "Opción no válida."
                    );
            }
        }
    }

    // ARTISTAS - ALTA
    private static void altaArtista() {

        System.out.println();
        System.out.println("--- ALTA DE ARTISTA ---");

        int id = EntradaUtil.leerEnteroPositivo("ID: ");

        while (artistaService.buscarPorId(id) != null) {

            System.out.println("Ya existe un artista con ese ID.");

            id = EntradaUtil.leerEnteroPositivo("Introduce otro ID: ");
        }

        String nombre = EntradaUtil.leerTexto("Nombre: ");

        String genero = EntradaUtil.leerTexto("Género: ");

        String pais = EntradaUtil.leerTexto("País: ");

        Artista artista = new Artista(id, nombre, genero, pais);

        if (artistaService.guardar(artista)) {

            System.out.println("Artista añadido correctamente.");
        }
    }

    // ARTISTAS - BAJA
    private static void bajaArtista() {

        System.out.println();
        System.out.println("--- BAJA DE ARTISTA ---");

        while (true) {

            int id = EntradaUtil.leerEntero("ID del artista (0 para cancelar): ");

            if (id == 0) {
                return;
            }

            Artista artista =
                    artistaService.buscarPorId(id);

            if (artista == null) {

                System.out.println("No existe un artista con ese ID.");

            } else {

                System.out.println("Artista encontrado: " + artista);

                if (artistaService.eliminar(id)) {

                    System.out.println("Artista eliminado correctamente.");
                }

                return;
            }
        }
    }

    // ARTISTAS - LISTAR
    private static void listarArtistas() {

        System.out.println();
        System.out.println("--- LISTADO DE ARTISTAS ---");

        List<Artista> artistas =
                artistaService.listar();

        if (artistas.isEmpty()) {

            System.out.println("No hay artistas registrados.");

            return;
        }
        for (Artista artista : artistas) {
            System.out.println(artista);
        }
    }

    // ARTISTAS - BUSCAR

    private static void buscarArtista() {

        System.out.println();
        System.out.println("--- BUSCAR ARTISTA ---");

        while (true) {

            int id = EntradaUtil.leerEntero("ID del artista (0 para cancelar): ");

            if (id == 0) {
                return;
            }

            Artista artista = artistaService.buscarPorId(id);

            if (artista == null) {

                System.out.println("No existe un artista con ese ID.");

            } else {

                System.out.println("Artista encontrado:");

                System.out.println(artista);
                return;
            }
        }
    }

    // ARTISTAS - MODIFICAR

    private static void modificarArtista() {

        System.out.println();
        System.out.println("--- MODIFICAR ARTISTA ---");

        while (true) {

            int id = EntradaUtil.leerEntero("ID del artista (0 para cancelar): ");

            if (id == 0) {
                return;
            }

            Artista artista =
                    artistaService.buscarPorId(id);

            if (artista == null) {

                System.out.println("No existe un artista con ese ID.");

                continue;
            }

            System.out.println("Artista actual: " + artista);

            String nombre = EntradaUtil.leerTexto("Nuevo nombre: ");

            String genero = EntradaUtil.leerTexto("Nuevo género: ");

            String pais = EntradaUtil.leerTexto("Nuevo país: ");

            Artista artistaModificado =
                    new Artista(id, nombre, genero, pais);

            if (artistaService.modificar(
                    artistaModificado
            )) {

                System.out.println("Artista modificado correctamente.");
            }
            return;
        }
    }

    // MENÚ ESCENARIOS

    private static void menuEscenarios() {

        boolean volver = false;

        while (!volver) {

            System.out.println();
            System.out.println("======================================");
            System.out.println("         GESTIÓN DE ESCENARIOS");
            System.out.println("======================================");
            System.out.println("1. Alta");
            System.out.println("2. Baja");
            System.out.println("3. Listado");
            System.out.println("4. Buscar por ID");
            System.out.println("5. Modificar");
            System.out.println("6. Exportar XML");
            System.out.println("0. Volver");
            System.out.println("======================================");

            int opcion = EntradaUtil.leerEntero(
                    "Elige una opción: "
            );

            switch (opcion) {

                case 1:
                    altaEscenario();
                    break;

                case 2:
                    bajaEscenario();
                    break;

                case 3:
                    listarEscenarios();
                    break;

                case 4:
                    buscarEscenario();
                    break;

                case 5:
                    modificarEscenario();
                    break;

                case 6:
                    escenarioService.exportarXML();
                    break;

                case 0:
                    volver = true;
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        }
    }


    private static void altaEscenario() {

        System.out.println();
        System.out.println("--- ALTA DE ESCENARIO ---");

        int id = EntradaUtil.leerEnteroPositivo("ID: ");

        while (escenarioService.buscarPorId(id) != null) {

            System.out.println("Ya existe un escenario con ese ID.");

            id = EntradaUtil.leerEnteroPositivo("Introduce otro ID: ");
        }

        String nombre = EntradaUtil.leerTexto("Nombre: ");

        String ubicacion = EntradaUtil.leerTexto("Ubicación: ");

        int capacidad = EntradaUtil.leerEnteroPositivo("Capacidad: ");

        Escenario escenario = new Escenario(id, nombre, ubicacion, capacidad);

        if (escenarioService.guardar(escenario)) {

            System.out.println("Escenario añadido correctamente.");
        }
    }


    private static void bajaEscenario() {

        System.out.println();
        System.out.println("--- BAJA DE ESCENARIO ---");

        while (true) {

            int id = EntradaUtil.leerEntero(
                    "ID del escenario (0 para cancelar): "
            );

            if (id == 0) {
                return;
            }

            Escenario escenario = escenarioService.buscarPorId(id);

            if (escenario == null) {

                System.out.println("No existe un escenario con ese ID.");

            } else {

                System.out.println(escenario);

                if (escenarioService.eliminar(id)) {

                    System.out.println("Escenario eliminado correctamente.");
                }

                return;
            }
        }
    }


    private static void listarEscenarios() {

        System.out.println();
        System.out.println("--- LISTADO DE ESCENARIOS ---");

        List<Escenario> escenarios =
                escenarioService.listar();

        if (escenarios.isEmpty()) {

            System.out.println("No hay escenarios registrados.");

            return;
        }

        for (Escenario escenario : escenarios) {
            System.out.println(escenario);
        }
    }


    private static void buscarEscenario() {

        System.out.println();
        System.out.println("--- BUSCAR ESCENARIO ---");

        while (true) {

            int id = EntradaUtil.leerEntero(
                    "ID del escenario (0 para cancelar): "
            );

            if (id == 0) {
                return;
            }

            Escenario escenario =
                    escenarioService.buscarPorId(id);

            if (escenario == null) {

                System.out.println("No existe un escenario con ese ID.");

            } else {

                System.out.println("Escenario encontrado:");

                System.out.println(escenario);

                return;
            }
        }
    }


    private static void modificarEscenario() {

        System.out.println();
        System.out.println("--- MODIFICAR ESCENARIO ---");

        while (true) {

            int id = EntradaUtil.leerEntero("ID del escenario (0 para cancelar): ");

            if (id == 0) {
                return;
            }

            Escenario escenario = escenarioService.buscarPorId(id);

            if (escenario == null) {

                System.out.println("No existe un escenario con ese ID.");

                continue;
            }

            System.out.println("Escenario actual: " + escenario);

            String nombre = EntradaUtil.leerTexto("Nuevo nombre: ");

            String ubicacion = EntradaUtil.leerTexto("Nueva ubicación: ");

            int capacidad = EntradaUtil.leerEnteroPositivo(
                    "Nueva capacidad: ");

            Escenario escenarioModificado = new Escenario(id, nombre, ubicacion, capacidad);

            if (escenarioService.modificar(escenarioModificado)) {

                System.out.println("Escenario modificado correctamente.");
            }

            return;
        }
    }

    // MENÚ ACTUACIONES
    private static void menuActuaciones() {

        boolean volver = false;

        while (!volver) {

            System.out.println();
            System.out.println("======================================");
            System.out.println("         GESTIÓN DE ACTUACIONES");
            System.out.println("======================================");
            System.out.println("1. Alta");
            System.out.println("2. Baja");
            System.out.println("3. Listado");
            System.out.println("4. Buscar por ID");
            System.out.println("5. Modificar");
            System.out.println("6. Exportar XML");
            System.out.println("0. Volver");
            System.out.println("======================================");

            int opcion = EntradaUtil.leerEntero("Elige una opción: ");

            switch (opcion) {

                case 1:
                    altaActuacion();
                    break;

                case 2:
                    bajaActuacion();
                    break;

                case 3:
                    listarActuaciones();
                    break;

                case 4:
                    buscarActuacion();
                    break;

                case 5:
                    modificarActuacion();
                    break;

                case 6:
                    actuacionService.exportarXML();
                    break;

                case 0:
                    volver = true;
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        }
    }


    private static void altaActuacion() {

        System.out.println();
        System.out.println("--- ALTA DE ACTUACIÓN ---");

        int id = EntradaUtil.leerEnteroPositivo("ID: ");

        while (actuacionService.buscarPorId(id) != null) {

            System.out.println("Ya existe una actuación con ese ID.");

            id = EntradaUtil.leerEnteroPositivo("Introduce otro ID: ");
        }

        int idArtista = EntradaUtil.leerEnteroPositivo("ID del artista: ");

        while (artistaService.buscarPorId(idArtista) == null) {

            System.out.println("No existe ningún artista con ese ID.");

            idArtista = EntradaUtil.leerEnteroPositivo("Introduce otro ID de artista: ");
        }

        int idEscenario =
                EntradaUtil.leerEnteroPositivo("ID del escenario: ");

        while (escenarioService.buscarPorId(idEscenario) == null) {

            System.out.println("No existe ningún escenario con ese ID.");

            idEscenario =
                    EntradaUtil.leerEnteroPositivo("Introduce otro ID de escenario: ");
        }

        LocalDate fecha = EntradaUtil.leerFecha("Fecha (AAAA-MM-DD): ");

        String hora = EntradaUtil.leerHora("Hora (HH:MM): ");

        int duracion = EntradaUtil.leerEnteroPositivo("Duración en minutos: ");

        Actuacion actuacion = new Actuacion(id, idArtista, idEscenario, fecha, hora, duracion);

        if (actuacionService.guardar(actuacion)) {

            System.out.println("Actuación añadida correctamente.");
        }
    }


    private static void bajaActuacion() {

        System.out.println();
        System.out.println("--- BAJA DE ACTUACIÓN ---");

        while (true) {

            int id = EntradaUtil.leerEntero("ID de la actuación (0 para cancelar): ");

            if (id == 0) {
                return;
            }

            Actuacion actuacion = actuacionService.buscarPorId(id);

            if (actuacion == null) {

                System.out.println("No existe una actuación con ese ID.");

            } else {

                System.out.println(actuacion);

                if (actuacionService.eliminar(id)) {

                    System.out.println("Actuación eliminada correctamente.");
                }
                return;
            }
        }
    }


    private static void listarActuaciones() {

        System.out.println();
        System.out.println("--- LISTADO DE ACTUACIONES ---");

        List<Actuacion> actuaciones =
                actuacionService.listar();

        if (actuaciones.isEmpty()) {

            System.out.println("No hay actuaciones registradas.");

            return;
        }

        for (Actuacion actuacion : actuaciones) {
            System.out.println(actuacion);
        }
    }


    private static void buscarActuacion() {

        System.out.println();
        System.out.println("--- BUSCAR ACTUACIÓN ---");

        while (true) {

            int id = EntradaUtil.leerEntero("ID de la actuación (0 para cancelar): ");

            if (id == 0) {
                return;
            }

            Actuacion actuacion = actuacionService.buscarPorId(id);

            if (actuacion == null) {

                System.out.println("No existe una actuación con ese ID.");

            } else {

                System.out.println("Actuación encontrada:");

                System.out.println(actuacion);

                return;
            }
        }
    }


    private static void modificarActuacion() {

        System.out.println();
        System.out.println("--- MODIFICAR ACTUACIÓN ---");

        while (true) {

            int id = EntradaUtil.leerEntero("ID de la actuación (0 para cancelar): ");

            if (id == 0) {
                return;
            }

            Actuacion actuacion = actuacionService.buscarPorId(id);

            if (actuacion == null) {

                System.out.println("No existe una actuación con ese ID.");

                continue;
            }

            System.out.println("Actuación actual: " + actuacion);

            int idArtista =
                    EntradaUtil.leerEnteroPositivo("Nuevo ID de artista: ");

            while (artistaService.buscarPorId(idArtista) == null) {

                System.out.println("No existe ningún artista con ese ID.");

                idArtista = EntradaUtil.leerEnteroPositivo("Introduce otro ID: ");
            }

            int idEscenario =
                    EntradaUtil.leerEnteroPositivo("Nuevo ID de escenario: ");

            while (escenarioService.buscarPorId(idEscenario) == null) {

                System.out.println("No existe ningún escenario con ese ID.");

                idEscenario = EntradaUtil.leerEnteroPositivo("Introduce otro ID: ");
            }

            LocalDate fecha = EntradaUtil.leerFecha("Nueva fecha (AAAA-MM-DD): ");

            String hora = EntradaUtil.leerHora("Nueva hora (HH:MM): ");

            int duracion = EntradaUtil.leerEnteroPositivo("Nueva duración en minutos: ");

            Actuacion modificada = new Actuacion(id, idArtista, idEscenario, fecha, hora, duracion);

            if (actuacionService.modificar(modificada)) {

                System.out.println("Actuación modificada correctamente.");
            }

            return;
        }
    }

    // MENÚ ESPECTADORES

    private static void menuEspectadores() {

        boolean volver = false;

        while (!volver) {

            System.out.println();
            System.out.println("======================================");
            System.out.println("        GESTIÓN DE ESPECTADORES");
            System.out.println("======================================");
            System.out.println("1. Alta");
            System.out.println("2. Baja");
            System.out.println("3. Listado");
            System.out.println("4. Buscar por ID");
            System.out.println("5. Modificar");
            System.out.println("6. Exportar XML");
            System.out.println("0. Volver");
            System.out.println("======================================");

            int opcion = EntradaUtil.leerEntero("Elige una opción: ");

            switch (opcion) {

                case 1:
                    altaEspectador();
                    break;

                case 2:
                    bajaEspectador();
                    break;

                case 3:
                    listarEspectadores();
                    break;

                case 4:
                    buscarEspectador();
                    break;

                case 5:
                    modificarEspectador();
                    break;

                case 6:
                    espectadorService.exportarXML();
                    break;

                case 0:
                    volver = true;
                    break;

                default:
                    System.out.println(
                            "Opción no válida."
                    );
            }
        }
    }


    private static void altaEspectador() {

        System.out.println();
        System.out.println("--- ALTA DE ESPECTADOR ---");

        int id = EntradaUtil.leerEnteroPositivo("ID: ");

        while (espectadorService.buscarPorId(id) != null) {

            System.out.println("Ya existe un espectador con ese ID.");

            id = EntradaUtil.leerEnteroPositivo("Introduce otro ID: ");
        }

        String nombre = EntradaUtil.leerTexto(
                "Nombre: "
        );

        String email = EntradaUtil.leerEmail(
                "Email: "
        );

        int edad = EntradaUtil.leerEdad(
                "Edad: "
        );

        Espectador espectador =
                new Espectador(
                        id,
                        nombre,
                        email,
                        edad
                );

        if (espectadorService.guardar(espectador)) {

            System.out.println(
                    "Espectador añadido correctamente."
            );
        }
    }


    private static void bajaEspectador() {

        System.out.println();
        System.out.println("--- BAJA DE ESPECTADOR ---");

        while (true) {

            int id = EntradaUtil.leerEntero(
                    "ID del espectador (0 para cancelar): "
            );

            if (id == 0) {
                return;
            }

            Espectador espectador =
                    espectadorService.buscarPorId(id);

            if (espectador == null) {

                System.out.println(
                        "No existe un espectador con ese ID."
                );

            } else {

                System.out.println(espectador);

                if (espectadorService.eliminar(id)) {

                    System.out.println(
                            "Espectador eliminado correctamente."
                    );
                }

                return;
            }
        }
    }


    private static void listarEspectadores() {

        System.out.println();
        System.out.println("--- LISTADO DE ESPECTADORES ---");

        List<Espectador> espectadores =
                espectadorService.listar();

        if (espectadores.isEmpty()) {

            System.out.println(
                    "No hay espectadores registrados."
            );

            return;
        }

        for (Espectador espectador : espectadores) {
            System.out.println(espectador);
        }
    }


    private static void buscarEspectador() {

        System.out.println();
        System.out.println("--- BUSCAR ESPECTADOR ---");

        while (true) {

            int id = EntradaUtil.leerEntero(
                    "ID del espectador (0 para cancelar): "
            );

            if (id == 0) {
                return;
            }

            Espectador espectador =
                    espectadorService.buscarPorId(id);

            if (espectador == null) {

                System.out.println(
                        "No existe un espectador con ese ID."
                );

            } else {

                System.out.println(
                        "Espectador encontrado:"
                );

                System.out.println(espectador);

                return;
            }
        }
    }


    private static void modificarEspectador() {

        System.out.println();
        System.out.println("--- MODIFICAR ESPECTADOR ---");

        while (true) {

            int id = EntradaUtil.leerEntero(
                    "ID del espectador (0 para cancelar): "
            );

            if (id == 0) {
                return;
            }

            Espectador espectador =
                    espectadorService.buscarPorId(id);

            if (espectador == null) {

                System.out.println(
                        "No existe un espectador con ese ID."
                );

                continue;
            }

            System.out.println(
                    "Espectador actual: " + espectador
            );

            String nombre = EntradaUtil.leerTexto(
                    "Nuevo nombre: "
            );

            String email = EntradaUtil.leerEmail(
                    "Nuevo email: "
            );

            int edad = EntradaUtil.leerEdad(
                    "Nueva edad: "
            );

            Espectador modificado =
                    new Espectador(
                            id,
                            nombre,
                            email,
                            edad
                    );

            if (espectadorService.modificar(modificado)) {

                System.out.println(
                        "Espectador modificado correctamente."
                );
            }

            return;
        }
    }


    // =========================================================
    // MENÚ ENTRADAS
    // =========================================================

    private static void menuEntradas() {

        boolean volver = false;

        while (!volver) {

            System.out.println();
            System.out.println("======================================");
            System.out.println("          GESTIÓN DE ENTRADAS");
            System.out.println("======================================");
            System.out.println("1. Alta");
            System.out.println("2. Baja");
            System.out.println("3. Listado");
            System.out.println("4. Buscar por ID");
            System.out.println("5. Modificar");
            System.out.println("6. Exportar XML");
            System.out.println("0. Volver");
            System.out.println("======================================");

            int opcion = EntradaUtil.leerEntero(
                    "Elige una opción: "
            );

            switch (opcion) {

                case 1:
                    altaEntrada();
                    break;

                case 2:
                    bajaEntrada();
                    break;

                case 3:
                    listarEntradas();
                    break;

                case 4:
                    buscarEntrada();
                    break;

                case 5:
                    modificarEntrada();
                    break;

                case 6:
                    entradaService.exportarXML();
                    break;

                case 0:
                    volver = true;
                    break;

                default:
                    System.out.println(
                            "Opción no válida."
                    );
            }
        }
    }


    private static void altaEntrada() {

        System.out.println();
        System.out.println("--- ALTA DE ENTRADA ---");

        int id = EntradaUtil.leerEnteroPositivo(
                "ID: "
        );

        while (entradaService.buscarPorId(id) != null) {

            System.out.println(
                    "Ya existe una entrada con ese ID."
            );

            id = EntradaUtil.leerEnteroPositivo(
                    "Introduce otro ID: "
            );
        }

        int idActuacion =
                EntradaUtil.leerEnteroPositivo(
                        "ID de la actuación: "
                );

        while (actuacionService.buscarPorId(idActuacion) == null) {

            System.out.println(
                    "No existe ninguna actuación con ese ID."
            );

            idActuacion =
                    EntradaUtil.leerEnteroPositivo(
                            "Introduce otro ID de actuación: "
                    );
        }

        int idEspectador =
                EntradaUtil.leerEnteroPositivo(
                        "ID del espectador: "
                );

        while (espectadorService.buscarPorId(idEspectador) == null) {

            System.out.println(
                    "No existe ningún espectador con ese ID."
            );

            idEspectador =
                    EntradaUtil.leerEnteroPositivo(
                            "Introduce otro ID de espectador: "
                    );
        }

        double precio =
                EntradaUtil.leerDecimalNoNegativo(
                        "Precio: "
                );

        String tipo = EntradaUtil.leerTexto(
                "Tipo de entrada: "
        );

        boolean activa = EntradaUtil.leerBooleano(
                "¿La entrada está activa?"
        );

        Entrada entrada =
                new Entrada(
                        id,
                        idActuacion,
                        idEspectador,
                        precio,
                        tipo,
                        activa
                );

        if (entradaService.guardar(entrada)) {

            System.out.println(
                    "Entrada añadida correctamente."
            );
        }
    }


    private static void bajaEntrada() {

        System.out.println();
        System.out.println("--- BAJA DE ENTRADA ---");

        while (true) {

            int id = EntradaUtil.leerEntero(
                    "ID de la entrada (0 para cancelar): "
            );

            if (id == 0) {
                return;
            }

            Entrada entrada =
                    entradaService.buscarPorId(id);

            if (entrada == null) {

                System.out.println(
                        "No existe una entrada con ese ID."
                );

            } else {

                System.out.println(entrada);

                if (entradaService.eliminar(id)) {

                    System.out.println(
                            "Entrada eliminada correctamente."
                    );
                }

                return;
            }
        }
    }


    private static void listarEntradas() {

        System.out.println();
        System.out.println("--- LISTADO DE ENTRADAS ---");

        List<Entrada> entradas =
                entradaService.listar();

        if (entradas.isEmpty()) {

            System.out.println(
                    "No hay entradas registradas."
            );

            return;
        }

        for (Entrada entrada : entradas) {
            System.out.println(entrada);
        }
    }


    private static void buscarEntrada() {

        System.out.println();
        System.out.println("--- BUSCAR ENTRADA ---");

        while (true) {

            int id = EntradaUtil.leerEntero(
                    "ID de la entrada (0 para cancelar): "
            );

            if (id == 0) {
                return;
            }

            Entrada entrada =
                    entradaService.buscarPorId(id);

            if (entrada == null) {

                System.out.println(
                        "No existe una entrada con ese ID."
                );

            } else {

                System.out.println(
                        "Entrada encontrada:"
                );

                System.out.println(entrada);

                return;
            }
        }
    }


    private static void modificarEntrada() {

        System.out.println();
        System.out.println("--- MODIFICAR ENTRADA ---");

        while (true) {

            int id = EntradaUtil.leerEntero(
                    "ID de la entrada (0 para cancelar): "
            );

            if (id == 0) {
                return;
            }

            Entrada entrada =
                    entradaService.buscarPorId(id);

            if (entrada == null) {

                System.out.println(
                        "No existe una entrada con ese ID."
                );

                continue;
            }

            System.out.println(
                    "Entrada actual: " + entrada
            );

            int idActuacion =
                    EntradaUtil.leerEnteroPositivo(
                            "Nuevo ID de actuación: "
                    );

            while (actuacionService.buscarPorId(idActuacion) == null) {

                System.out.println(
                        "No existe ninguna actuación con ese ID."
                );

                idActuacion =
                        EntradaUtil.leerEnteroPositivo(
                                "Introduce otro ID: "
                        );
            }

            int idEspectador =
                    EntradaUtil.leerEnteroPositivo(
                            "Nuevo ID de espectador: "
                    );

            while (espectadorService.buscarPorId(idEspectador) == null) {

                System.out.println(
                        "No existe ningún espectador con ese ID."
                );

                idEspectador =
                        EntradaUtil.leerEnteroPositivo(
                                "Introduce otro ID: "
                        );
            }

            double precio =
                    EntradaUtil.leerDecimalNoNegativo(
                            "Nuevo precio: "
                    );

            String tipo = EntradaUtil.leerTexto(
                    "Nuevo tipo de entrada: "
            );

            boolean activa = EntradaUtil.leerBooleano(
                    "¿La entrada está activa?"
            );

            Entrada modificada =
                    new Entrada(
                            id,
                            idActuacion,
                            idEspectador,
                            precio,
                            tipo,
                            activa
                    );

            if (entradaService.modificar(modificada)) {

                System.out.println(
                        "Entrada modificada correctamente."
                );
            }

            return;
        }
    }
}