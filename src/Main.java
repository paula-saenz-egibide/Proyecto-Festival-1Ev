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
    private static final ArtistaService artistaService = new ArtistaService();
    private static final EscenarioService escenarioService = new EscenarioService();
    private static final ActuacionService actuacionService = new ActuacionService();
    private static final EspectadorService espectadorService = new EspectadorService();
    private static final EntradaService entradaService = new EntradaService();

    public static void main(String[] args) {
        InicializadorFicheros.inicializar();
        boolean salir = false;
        while (!salir) {
            mostrarMenuPrincipal();
            int opcion = EntradaUtil.leerEntero("Elige una opción: ");
            switch (opcion) {
                case 1: menuArtistas(); break;
                case 2: menuEscenarios(); break;
                case 3: menuActuaciones(); break;
                case 4: menuEspectadores(); break;
                case 5: menuEntradas(); break;
                case 0:
                    salir = true;
                    System.out.println("Programa finalizado.");
                    break;
                default:
                    System.out.println("Opción no válida. Debes elegir una opción del menú.");
            }
        }
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n======================================");
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

    private static void menuArtistas() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n======================================");
            System.out.println("          GESTIÓN DE ARTISTAS");
            System.out.println("======================================");
            System.out.println("1. Alta");
            System.out.println("2. Baja");
            System.out.println("3. Listado");
            System.out.println("4. Buscar por ID");
            System.out.println("5. Buscar por género");
            System.out.println("6. Buscar por país");
            System.out.println("7. Modificar");
            System.out.println("8. Exportar XML");
            System.out.println("9. Generar JSON");
            System.out.println("10. Leer JSON");
            System.out.println("0. Volver");
            System.out.println("======================================");
            int opcion = EntradaUtil.leerEntero("Elige una opción: ");
            switch (opcion) {
                case 1: altaArtista(); break;
                case 2: bajaArtista(); break;
                case 3: listarArtistas(); break;
                case 4: buscarArtista(); break;
                case 5: buscarArtistasPorGenero(); break;
                case 6: buscarArtistasPorPais(); break;
                case 7: modificarArtista(); break;
                case 8: artistaService.exportarXML(); break;
                case 9: artistaService.generarJSON(); break;
                case 10: artistaService.leerJSON(); break;
                case 0: volver = true; break;
                default: System.out.println("Opción no válida.");
            }
        }
    }

    private static void altaArtista() {
        System.out.println("\n--- ALTA DE ARTISTA ---");
        int id = EntradaUtil.leerEntero("ID (0 para cancelar): ");
        if (id == 0) return;
        while (id < 0 || artistaService.buscarPorId(id) != null) {
            if (id < 0) System.out.println("El ID debe ser positivo.");
            else System.out.println("Ya existe un artista con ese ID.");
            id = EntradaUtil.leerEntero("Introduce otro ID (0 para cancelar): ");
            if (id == 0) return;
        }
        String nombre = EntradaUtil.leerTexto("Nombre: ");
        String genero = EntradaUtil.leerTexto("Género: ");
        String pais = EntradaUtil.leerTexto("País: ");
        Artista artista = new Artista(id, nombre, genero, pais);
        if (artistaService.guardar(artista)) System.out.println("Artista añadido correctamente.");
    }

    private static void bajaArtista() {
        System.out.println("\n--- BAJA DE ARTISTA ---");
        while (true) {
            int id = EntradaUtil.leerEntero("ID del artista (0 para cancelar): ");
            if (id == 0) return;
            Artista artista = artistaService.buscarPorId(id);
            if (artista == null) {
                System.out.println("No existe un artista con ese ID.");
            } else {
                System.out.println("Artista encontrado: " + artista);
                if (artistaService.eliminar(id)) System.out.println("Artista eliminado correctamente.");
                return;
            }
        }
    }

    private static void listarArtistas() {
        System.out.println("\n--- LISTADO DE ARTISTAS ---");
        List<Artista> artistas = artistaService.listar();
        if (artistas.isEmpty()) {
            System.out.println("No hay artistas registrados.");
            return;
        }
        for (Artista artista : artistas) System.out.println(artista);
    }

    private static void buscarArtista() {
        System.out.println("\n--- BUSCAR ARTISTA ---");
        while (true) {
            int id = EntradaUtil.leerEntero("ID del artista (0 para cancelar): ");
            if (id == 0) return;
            Artista artista = artistaService.buscarPorId(id);
            if (artista == null) System.out.println("No existe un artista con ese ID.");
            else {
                System.out.println("Artista encontrado:");
                System.out.println(artista);
                return;
            }
        }
    }

    private static void buscarArtistasPorGenero() {
        System.out.println("\n--- BUSCAR ARTISTAS POR GÉNERO ---");
        String genero = EntradaUtil.leerTexto("Género (0 para cancelar): ");
        if (genero.equals("0")) return;
        List<Artista> artistas = artistaService.buscarPorGenero(genero);
        if (artistas.isEmpty()) System.out.println("No hay artistas de ese género.");
        else for (Artista artista : artistas) System.out.println(artista);
    }

    private static void buscarArtistasPorPais() {
        System.out.println("\n--- BUSCAR ARTISTAS POR PAÍS ---");
        String pais = EntradaUtil.leerTexto("País (0 para cancelar): ");
        if (pais.equals("0")) return;
        List<Artista> artistas = artistaService.buscarPorPais(pais);
        if (artistas.isEmpty()) System.out.println("No hay artistas de ese país.");
        else for (Artista artista : artistas) System.out.println(artista);
    }

    private static void modificarArtista() {
        System.out.println("\n--- MODIFICAR ARTISTA ---");
        while (true) {
            int id = EntradaUtil.leerEntero("ID del artista (0 para cancelar): ");
            if (id == 0) return;
            Artista artista = artistaService.buscarPorId(id);
            if (artista == null) {
                System.out.println("No existe un artista con ese ID.");
                continue;
            }
            System.out.println("Artista actual: " + artista);
            String nombre = EntradaUtil.leerTexto("Nuevo nombre: ");
            String genero = EntradaUtil.leerTexto("Nuevo género: ");
            String pais = EntradaUtil.leerTexto("Nuevo país: ");
            Artista modificado = new Artista(id, nombre, genero, pais);
            if (artistaService.modificar(modificado)) System.out.println("Artista modificado correctamente.");
            return;
        }
    }

    private static void menuEscenarios() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n======================================");
            System.out.println("         GESTIÓN DE ESCENARIOS");
            System.out.println("======================================");
            System.out.println("1. Alta");
            System.out.println("2. Baja");
            System.out.println("3. Listado");
            System.out.println("4. Buscar por ID");
            System.out.println("5. Modificar");
            System.out.println("6. Exportar XML");
            System.out.println("7. Generar JSON");
            System.out.println("8. Leer JSON");
            System.out.println("0. Volver");
            System.out.println("======================================");
            int opcion = EntradaUtil.leerEntero("Elige una opción: ");
            switch (opcion) {
                case 1: altaEscenario(); break;
                case 2: bajaEscenario(); break;
                case 3: listarEscenarios(); break;
                case 4: buscarEscenario(); break;
                case 5: modificarEscenario(); break;
                case 6: escenarioService.exportarXML(); break;
                case 7: escenarioService.generarJSON(); break;
                case 8: escenarioService.leerJSON(); break;
                case 0: volver = true; break;
                default: System.out.println("Opción no válida.");
            }
        }
    }

    private static void altaEscenario() {
        System.out.println("\n--- ALTA DE ESCENARIO ---");
        int id = EntradaUtil.leerEntero("ID (0 para cancelar): ");
        if (id == 0) return;
        while (id < 0 || escenarioService.buscarPorId(id) != null) {
            if (id < 0) System.out.println("El ID debe ser positivo.");
            else System.out.println("Ya existe un escenario con ese ID.");
            id = EntradaUtil.leerEntero("Introduce otro ID (0 para cancelar): ");
            if (id == 0) return;
        }
        String nombre = EntradaUtil.leerTexto("Nombre: ");
        String ubicacion = EntradaUtil.leerTexto("Ubicación: ");
        int capacidad = EntradaUtil.leerEnteroPositivo("Capacidad: ");
        Escenario escenario = new Escenario(id, nombre, ubicacion, capacidad);
        if (escenarioService.guardar(escenario)) System.out.println("Escenario añadido correctamente.");
    }

    private static void bajaEscenario() {
        System.out.println("\n--- BAJA DE ESCENARIO ---");
        while (true) {
            int id = EntradaUtil.leerEntero("ID del escenario (0 para cancelar): ");
            if (id == 0) return;
            Escenario escenario = escenarioService.buscarPorId(id);
            if (escenario == null) System.out.println("No existe un escenario con ese ID.");
            else {
                System.out.println("Escenario encontrado: " + escenario);
                if (escenarioService.eliminar(id)) System.out.println("Escenario eliminado correctamente.");
                return;
            }
        }
    }

    private static void listarEscenarios() {
        System.out.println("\n--- LISTADO DE ESCENARIOS ---");
        List<Escenario> escenarios = escenarioService.listar();
        if (escenarios.isEmpty()) {
            System.out.println("No hay escenarios registrados.");
            return;
        }
        for (Escenario escenario : escenarios) System.out.println(escenario);
    }

    private static void buscarEscenario() {
        System.out.println("\n--- BUSCAR ESCENARIO ---");
        while (true) {
            int id = EntradaUtil.leerEntero("ID del escenario (0 para cancelar): ");
            if (id == 0) return;
            Escenario escenario = escenarioService.buscarPorId(id);
            if (escenario == null) System.out.println("No existe un escenario con ese ID.");
            else {
                System.out.println("Escenario encontrado:");
                System.out.println(escenario);
                return;
            }
        }
    }

    private static void modificarEscenario() {
        System.out.println("\n--- MODIFICAR ESCENARIO ---");
        while (true) {
            int id = EntradaUtil.leerEntero("ID del escenario (0 para cancelar): ");
            if (id == 0) return;
            Escenario escenario = escenarioService.buscarPorId(id);
            if (escenario == null) {
                System.out.println("No existe un escenario con ese ID.");
                continue;
            }
            System.out.println("Escenario actual: " + escenario);
            String nombre = EntradaUtil.leerTexto("Nuevo nombre: ");
            String ubicacion = EntradaUtil.leerTexto("Nueva ubicación: ");
            int capacidad = EntradaUtil.leerEnteroPositivo("Nueva capacidad: ");
            Escenario modificado = new Escenario(id, nombre, ubicacion, capacidad);
            if (escenarioService.modificar(modificado)) System.out.println("Escenario modificado correctamente.");
            return;
        }
    }

    private static void menuActuaciones() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n======================================");
            System.out.println("         GESTIÓN DE ACTUACIONES");
            System.out.println("======================================");
            System.out.println("1. Alta");
            System.out.println("2. Baja");
            System.out.println("3. Listado");
            System.out.println("4. Buscar por ID");
            System.out.println("5. Buscar por fecha");
            System.out.println("6. Modificar");
            System.out.println("7. Exportar XML");
            System.out.println("8. Generar JSON");
            System.out.println("9. Leer JSON");
            System.out.println("0. Volver");
            System.out.println("======================================");
            int opcion = EntradaUtil.leerEntero("Elige una opción: ");
            switch (opcion) {
                case 1: altaActuacion(); break;
                case 2: bajaActuacion(); break;
                case 3: listarActuaciones(); break;
                case 4: buscarActuacion(); break;
                case 5: buscarActuacionesPorFecha(); break;
                case 6: modificarActuacion(); break;
                case 7: actuacionService.exportarXML(); break;
                case 8: actuacionService.generarJSON(); break;
                case 9: actuacionService.leerJSON(); break;
                case 0: volver = true; break;
                default: System.out.println("Opción no válida.");
            }
        }
    }

    private static void altaActuacion() {
        System.out.println("\n--- ALTA DE ACTUACIÓN ---");
        int id = EntradaUtil.leerEntero("ID (0 para cancelar): ");
        if (id == 0) return;
        while (id < 0 || actuacionService.buscarPorId(id) != null) {
            if (id < 0) System.out.println("El ID debe ser positivo.");
            else System.out.println("Ya existe una actuación con ese ID.");
            id = EntradaUtil.leerEntero("Introduce otro ID (0 para cancelar): ");
            if (id == 0) return;
        }
        int idArtista = EntradaUtil.leerEntero("ID del artista (0 para cancelar): ");
        while (idArtista != 0 && artistaService.buscarPorId(idArtista) == null) {
            System.out.println("No existe ningún artista con ese ID.");
            idArtista = EntradaUtil.leerEntero("Introduce otro ID de artista (0 para cancelar): ");
        }
        if (idArtista == 0) return;
        int idEscenario = EntradaUtil.leerEntero("ID del escenario (0 para cancelar): ");
        while (idEscenario != 0 && escenarioService.buscarPorId(idEscenario) == null) {
            System.out.println("No existe ningún escenario con ese ID.");
            idEscenario = EntradaUtil.leerEntero("Introduce otro ID de escenario (0 para cancelar): ");
        }
        if (idEscenario == 0) return;
        LocalDate fecha = EntradaUtil.leerFecha("Fecha (AAAA-MM-DD): ");
        String hora = EntradaUtil.leerHora("Hora (HH:MM): ");
        int duracion = EntradaUtil.leerEnteroPositivo("Duración en minutos: ");
        Actuacion actuacion = new Actuacion(id, idArtista, idEscenario, fecha, hora, duracion);
        if (actuacionService.hayConflictoHorario(actuacion)) {
            System.out.println("No se puede añadir la actuación porque se solapa con otra del mismo escenario.");
            return;
        }
        if (actuacionService.guardar(actuacion)) System.out.println("Actuación añadida correctamente.");
    }

    private static void bajaActuacion() {
        System.out.println("\n--- BAJA DE ACTUACIÓN ---");
        while (true) {
            int id = EntradaUtil.leerEntero("ID de la actuación (0 para cancelar): ");
            if (id == 0) return;
            Actuacion actuacion = actuacionService.buscarPorId(id);
            if (actuacion == null) System.out.println("No existe una actuación con ese ID.");
            else {
                System.out.println("Actuación encontrada: " + actuacion);
                if (actuacionService.eliminar(id)) System.out.println("Actuación eliminada correctamente.");
                return;
            }
        }
    }

    private static void listarActuaciones() {
        System.out.println("\n--- LISTADO DE ACTUACIONES ---");
        List<Actuacion> actuaciones = actuacionService.listar();
        if (actuaciones.isEmpty()) {
            System.out.println("No hay actuaciones registradas.");
            return;
        }
        for (Actuacion actuacion : actuaciones) System.out.println(actuacion);
    }

    private static void buscarActuacion() {
        System.out.println("\n--- BUSCAR ACTUACIÓN ---");
        while (true) {
            int id = EntradaUtil.leerEntero("ID de la actuación (0 para cancelar): ");
            if (id == 0) return;
            Actuacion actuacion = actuacionService.buscarPorId(id);
            if (actuacion == null) System.out.println("No existe una actuación con ese ID.");
            else {
                System.out.println("Actuación encontrada:");
                System.out.println(actuacion);
                return;
            }
        }
    }

    private static void buscarActuacionesPorFecha() {
        System.out.println("\n--- BUSCAR ACTUACIONES POR FECHA ---");
        LocalDate fecha = EntradaUtil.leerFecha("Fecha (AAAA-MM-DD): ");
        List<Actuacion> actuaciones = actuacionService.buscarPorFecha(fecha);
        if (actuaciones.isEmpty()) System.out.println("No hay actuaciones para esa fecha.");
        else for (Actuacion actuacion : actuaciones) System.out.println(actuacion);
    }

    private static void modificarActuacion() {
        System.out.println("\n--- MODIFICAR ACTUACIÓN ---");
        while (true) {
            int id = EntradaUtil.leerEntero("ID de la actuación (0 para cancelar): ");
            if (id == 0) return;
            Actuacion actuacion = actuacionService.buscarPorId(id);
            if (actuacion == null) {
                System.out.println("No existe una actuación con ese ID.");
                continue;
            }
            System.out.println("Actuación actual: " + actuacion);
            int idArtista = EntradaUtil.leerEntero("Nuevo ID de artista (0 para cancelar): ");
            while (idArtista != 0 && artistaService.buscarPorId(idArtista) == null) {
                System.out.println("No existe ningún artista con ese ID.");
                idArtista = EntradaUtil.leerEntero("Introduce otro ID de artista (0 para cancelar): ");
            }
            if (idArtista == 0) return;
            int idEscenario = EntradaUtil.leerEntero("Nuevo ID de escenario (0 para cancelar): ");
            while (idEscenario != 0 && escenarioService.buscarPorId(idEscenario) == null) {
                System.out.println("No existe ningún escenario con ese ID.");
                idEscenario = EntradaUtil.leerEntero("Introduce otro ID de escenario (0 para cancelar): ");
            }
            if (idEscenario == 0) return;
            LocalDate fecha = EntradaUtil.leerFecha("Nueva fecha (AAAA-MM-DD): ");
            String hora = EntradaUtil.leerHora("Nueva hora (HH:MM): ");
            int duracion = EntradaUtil.leerEnteroPositivo("Nueva duración en minutos: ");
            Actuacion modificada = new Actuacion(id, idArtista, idEscenario, fecha, hora, duracion);
            if (actuacionService.hayConflictoHorario(modificada)) {
                System.out.println("No se puede modificar porque se solapa con otra actuación del mismo escenario.");
                return;
            }
            if (actuacionService.modificar(modificada)) System.out.println("Actuación modificada correctamente.");
            return;
        }
    }

    private static void menuEspectadores() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n======================================");
            System.out.println("        GESTIÓN DE ESPECTADORES");
            System.out.println("======================================");
            System.out.println("1. Alta");
            System.out.println("2. Baja");
            System.out.println("3. Listado");
            System.out.println("4. Buscar por ID");
            System.out.println("5. Modificar");
            System.out.println("6. Exportar XML");
            System.out.println("7. Generar JSON");
            System.out.println("8. Leer JSON");
            System.out.println("0. Volver");
            System.out.println("======================================");
            int opcion = EntradaUtil.leerEntero("Elige una opción: ");
            switch (opcion) {
                case 1: altaEspectador(); break;
                case 2: bajaEspectador(); break;
                case 3: listarEspectadores(); break;
                case 4: buscarEspectador(); break;
                case 5: modificarEspectador(); break;
                case 6: espectadorService.exportarXML(); break;
                case 7: espectadorService.generarJSON(); break;
                case 8: espectadorService.leerJSON(); break;
                case 0: volver = true; break;
                default: System.out.println("Opción no válida.");
            }
        }
    }

    private static void altaEspectador() {
        System.out.println("\n--- ALTA DE ESPECTADOR ---");
        int id = EntradaUtil.leerEntero("ID (0 para cancelar): ");
        if (id == 0) return;
        while (id < 0 || espectadorService.buscarPorId(id) != null) {
            if (id < 0) System.out.println("El ID debe ser positivo.");
            else System.out.println("Ya existe un espectador con ese ID.");
            id = EntradaUtil.leerEntero("Introduce otro ID (0 para cancelar): ");
            if (id == 0) return;
        }
        String nombre = EntradaUtil.leerTexto("Nombre: ");
        String email = EntradaUtil.leerEmail("Email: ");
        int edad = EntradaUtil.leerEdad("Edad: ");
        Espectador espectador = new Espectador(id, nombre, email, edad);
        if (espectadorService.guardar(espectador)) System.out.println("Espectador añadido correctamente.");
    }

    private static void bajaEspectador() {
        System.out.println("\n--- BAJA DE ESPECTADOR ---");
        while (true) {
            int id = EntradaUtil.leerEntero("ID del espectador (0 para cancelar): ");
            if (id == 0) return;
            Espectador espectador = espectadorService.buscarPorId(id);
            if (espectador == null) System.out.println("No existe un espectador con ese ID.");
            else {
                System.out.println("Espectador encontrado: " + espectador);
                if (espectadorService.eliminar(id)) System.out.println("Espectador eliminado correctamente.");
                return;
            }
        }
    }

    private static void listarEspectadores() {
        System.out.println("\n--- LISTADO DE ESPECTADORES ---");
        List<Espectador> espectadores = espectadorService.listar();
        if (espectadores.isEmpty()) {
            System.out.println("No hay espectadores registrados.");
            return;
        }
        for (Espectador espectador : espectadores) System.out.println(espectador);
    }

    private static void buscarEspectador() {
        System.out.println("\n--- BUSCAR ESPECTADOR ---");
        while (true) {
            int id = EntradaUtil.leerEntero("ID del espectador (0 para cancelar): ");
            if (id == 0) return;
            Espectador espectador = espectadorService.buscarPorId(id);
            if (espectador == null) System.out.println("No existe un espectador con ese ID.");
            else {
                System.out.println("Espectador encontrado:");
                System.out.println(espectador);
                return;
            }
        }
    }

    private static void modificarEspectador() {
        System.out.println("\n--- MODIFICAR ESPECTADOR ---");
        while (true) {
            int id = EntradaUtil.leerEntero("ID del espectador (0 para cancelar): ");
            if (id == 0) return;
            Espectador espectador = espectadorService.buscarPorId(id);
            if (espectador == null) {
                System.out.println("No existe un espectador con ese ID.");
                continue;
            }
            System.out.println("Espectador actual: " + espectador);
            String nombre = EntradaUtil.leerTexto("Nuevo nombre: ");
            String email = EntradaUtil.leerEmail("Nuevo email: ");
            int edad = EntradaUtil.leerEdad("Nueva edad: ");
            Espectador modificado = new Espectador(id, nombre, email, edad);
            if (espectadorService.modificar(modificado)) System.out.println("Espectador modificado correctamente.");
            return;
        }
    }

    private static void menuEntradas() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n======================================");
            System.out.println("          GESTIÓN DE ENTRADAS");
            System.out.println("======================================");
            System.out.println("1. Alta");
            System.out.println("2. Baja");
            System.out.println("3. Listado");
            System.out.println("4. Buscar por ID");
            System.out.println("5. Modificar");
            System.out.println("6. Exportar XML");
            System.out.println("7. Generar JSON");
            System.out.println("8. Leer JSON");
            System.out.println("0. Volver");
            System.out.println("======================================");
            int opcion = EntradaUtil.leerEntero("Elige una opción: ");
            switch (opcion) {
                case 1: altaEntrada(); break;
                case 2: bajaEntrada(); break;
                case 3: listarEntradas(); break;
                case 4: buscarEntrada(); break;
                case 5: modificarEntrada(); break;
                case 6: entradaService.exportarXML(); break;
                case 7: entradaService.generarJSON(); break;
                case 8: entradaService.leerJSON(); break;
                case 0: volver = true; break;
                default: System.out.println("Opción no válida.");
            }
        }
    }

    private static void altaEntrada() {
        System.out.println("\n--- ALTA DE ENTRADA ---");
        int id = EntradaUtil.leerEntero("ID (0 para cancelar): ");
        if (id == 0) return;
        while (id < 0 || entradaService.buscarPorId(id) != null) {
            if (id < 0) System.out.println("El ID debe ser positivo.");
            else System.out.println("Ya existe una entrada con ese ID.");
            id = EntradaUtil.leerEntero("Introduce otro ID (0 para cancelar): ");
            if (id == 0) return;
        }
        int idActuacion = EntradaUtil.leerEntero("ID de la actuación (0 para cancelar): ");
        while (idActuacion != 0 && actuacionService.buscarPorId(idActuacion) == null) {
            System.out.println("No existe ninguna actuación con ese ID.");
            idActuacion = EntradaUtil.leerEntero("Introduce otro ID de actuación (0 para cancelar): ");
        }
        if (idActuacion == 0) return;
        if (!entradaService.hayCapacidadDisponible(idActuacion)) {
            System.out.println("No hay plazas disponibles para esta actuación.");
            return;
        }
        int idEspectador = EntradaUtil.leerEntero("ID del espectador (0 para cancelar): ");
        while (idEspectador != 0 && espectadorService.buscarPorId(idEspectador) == null) {
            System.out.println("No existe ningún espectador con ese ID.");
            idEspectador = EntradaUtil.leerEntero("Introduce otro ID de espectador (0 para cancelar): ");
        }
        if (idEspectador == 0) return;
        double precio = EntradaUtil.leerDecimalNoNegativo("Precio: ");
        String tipo = EntradaUtil.leerTexto("Tipo de entrada: ");
        boolean activa = EntradaUtil.leerBooleano("¿La entrada está activa?");
        Entrada entrada = new Entrada(id, idActuacion, idEspectador, precio, tipo, activa);
        if (entradaService.guardar(entrada)) System.out.println("Entrada añadida correctamente.");
    }

    private static void bajaEntrada() {
        System.out.println("\n--- BAJA DE ENTRADA ---");
        while (true) {
            int id = EntradaUtil.leerEntero("ID de la entrada (0 para cancelar): ");
            if (id == 0) return;
            Entrada entrada = entradaService.buscarPorId(id);
            if (entrada == null) System.out.println("No existe una entrada con ese ID.");
            else {
                System.out.println("Entrada encontrada: " + entrada);
                if (entradaService.eliminar(id)) System.out.println("Entrada eliminada correctamente.");
                return;
            }
        }
    }

    private static void listarEntradas() {
        System.out.println("\n--- LISTADO DE ENTRADAS ---");
        List<Entrada> entradas = entradaService.listar();
        if (entradas.isEmpty()) {
            System.out.println("No hay entradas registradas.");
            return;
        }
        for (Entrada entrada : entradas) System.out.println(entrada);
    }

    private static void buscarEntrada() {
        System.out.println("\n--- BUSCAR ENTRADA ---");
        while (true) {
            int id = EntradaUtil.leerEntero("ID de la entrada (0 para cancelar): ");
            if (id == 0) return;
            Entrada entrada = entradaService.buscarPorId(id);
            if (entrada == null) System.out.println("No existe una entrada con ese ID.");
            else {
                System.out.println("Entrada encontrada:");
                System.out.println(entrada);
                return;
            }
        }
    }

    private static void modificarEntrada() {
        System.out.println("\n--- MODIFICAR ENTRADA ---");
        while (true) {
            int id = EntradaUtil.leerEntero("ID de la entrada (0 para cancelar): ");
            if (id == 0) return;
            Entrada entrada = entradaService.buscarPorId(id);
            if (entrada == null) {
                System.out.println("No existe una entrada con ese ID.");
                continue;
            }
            System.out.println("Entrada actual: " + entrada);
            int idActuacion = EntradaUtil.leerEntero("Nuevo ID de actuación (0 para cancelar): ");
            while (idActuacion != 0 && actuacionService.buscarPorId(idActuacion) == null) {
                System.out.println("No existe ninguna actuación con ese ID.");
                idActuacion = EntradaUtil.leerEntero("Introduce otro ID de actuación (0 para cancelar): ");
            }
            if (idActuacion == 0) return;
            int idEspectador = EntradaUtil.leerEntero("Nuevo ID de espectador (0 para cancelar): ");
            while (idEspectador != 0 && espectadorService.buscarPorId(idEspectador) == null) {
                System.out.println("No existe ningún espectador con ese ID.");
                idEspectador = EntradaUtil.leerEntero("Introduce otro ID de espectador (0 para cancelar): ");
            }
            if (idEspectador == 0) return;
            double precio = EntradaUtil.leerDecimalNoNegativo("Nuevo precio: ");
            String tipo = EntradaUtil.leerTexto("Nuevo tipo de entrada: ");
            boolean activa = EntradaUtil.leerBooleano("¿La entrada está activa?");
            if (activa && !entradaService.hayCapacidadDisponible(idActuacion, id)) {
                System.out.println("No hay plazas disponibles para esta actuación.");
                return;
            }
            Entrada modificada = new Entrada(id, idActuacion, idEspectador, precio, tipo, activa);
            if (entradaService.modificar(modificada)) System.out.println("Entrada modificada correctamente.");
            return;
        }
    }
}