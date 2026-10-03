package web;

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
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

public class GestorWeb {
    private static final Pattern FORMATO_EMAIL =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final String ERROR_EMAIL =
            "Error: introduce un email válido, por ejemplo usuario@gmail.com.";

    private ArtistaService artistaService = new ArtistaService();
    private ActuacionService actuacionService = new ActuacionService();
    private EscenarioService escenarioService = new EscenarioService();
    private EspectadorService espectadorService = new EspectadorService();
    private EntradaService entradaService = new EntradaService();

    public ResultadoOperacion guardarArtistaDetallado(Artista artista) {
        if (artista == null) return ResultadoOperacion.error("El artista no puede ser null.");
        if (artistaService.buscarPorId(artista.getId()) != null) {
            return ResultadoOperacion.error("Ya existe un artista con ese ID.");
        }
        return resultado(artistaService.guardar(artista), "No se pudo guardar el artista.");
    }

    public ResultadoOperacion modificarArtistaDetallado(Artista artista) {
        if (artista == null) return ResultadoOperacion.error("El artista no puede ser null.");
        if (artistaService.buscarPorId(artista.getId()) == null) {
            return ResultadoOperacion.error("No existe un artista con ese ID.");
        }
        return resultado(artistaService.modificar(artista), "No se pudo modificar el artista.");
    }

    public ResultadoOperacion eliminarArtistaDetallado(int id) {
        if (artistaService.buscarPorId(id) == null) {
            return ResultadoOperacion.error("No existe un artista con ese ID.");
        }
        return resultado(artistaService.eliminar(id), "No se pudo eliminar el artista.");
    }

    public ResultadoOperacion guardarEscenarioDetallado(Escenario escenario) {
        if (escenario == null) return ResultadoOperacion.error("El escenario no puede ser null.");
        if (escenarioService.buscarPorId(escenario.getId()) != null) {
            return ResultadoOperacion.error("Ya existe un escenario con ese ID.");
        }
        return resultado(escenarioService.guardar(escenario), "No se pudo guardar el escenario.");
    }

    public ResultadoOperacion modificarEscenarioDetallado(Escenario escenario) {
        if (escenario == null) return ResultadoOperacion.error("El escenario no puede ser null.");
        if (escenarioService.buscarPorId(escenario.getId()) == null) {
            return ResultadoOperacion.error("No existe un escenario con ese ID.");
        }
        return resultado(escenarioService.modificar(escenario), "No se pudo modificar el escenario.");
    }

    public ResultadoOperacion eliminarEscenarioDetallado(int id) {
        if (escenarioService.buscarPorId(id) == null) {
            return ResultadoOperacion.error("No existe un escenario con ese ID.");
        }
        return resultado(escenarioService.eliminar(id), "No se pudo eliminar el escenario.");
    }

    public ResultadoOperacion guardarActuacionDetallado(Actuacion actuacion) {
        if (actuacion == null) return ResultadoOperacion.error("La actuación no puede ser null.");
        if (actuacionService.buscarPorId(actuacion.getId()) != null) {
            return ResultadoOperacion.error("Ya existe una actuación con ese ID.");
        }
        if (artistaService.buscarPorId(actuacion.getIdArtista()) == null) {
            return ResultadoOperacion.error("El artista indicado no existe.");
        }
        if (escenarioService.buscarPorId(actuacion.getIdEscenario()) == null) {
            return ResultadoOperacion.error("El escenario indicado no existe.");
        }
        if (actuacionService.hayConflictoHorario(actuacion)) {
            return ResultadoOperacion.error("La actuación se solapa con otra del mismo escenario.");
        }
        return resultado(actuacionService.guardar(actuacion), "No se pudo guardar la actuación.");
    }

    public ResultadoOperacion modificarActuacionDetallado(Actuacion actuacion) {
        if (actuacion == null) return ResultadoOperacion.error("La actuación no puede ser null.");
        if (actuacionService.hayConflictoHorario(actuacion)) {
            return ResultadoOperacion.error("La actuación se solapa con otra del mismo escenario.");
        }
        if (actuacionService.buscarPorId(actuacion.getId()) == null) {
            return ResultadoOperacion.error("No existe una actuación con ese ID.");
        }
        if (artistaService.buscarPorId(actuacion.getIdArtista()) == null) {
            return ResultadoOperacion.error("El artista indicado no existe.");
        }
        if (escenarioService.buscarPorId(actuacion.getIdEscenario()) == null) {
            return ResultadoOperacion.error("El escenario indicado no existe.");
        }
        return resultado(actuacionService.modificar(actuacion), "No se pudo modificar la actuación.");
    }

    public ResultadoOperacion eliminarActuacionDetallado(int id) {
        if (actuacionService.buscarPorId(id) == null) {
            return ResultadoOperacion.error("No existe una actuación con ese ID.");
        }
        return resultado(actuacionService.eliminar(id), "No se pudo eliminar la actuación.");
    }

    public ResultadoOperacion guardarEspectadorDetallado(Espectador espectador) {
        if (espectador == null) return ResultadoOperacion.error("El espectador no puede ser null.");
        if (!emailValido(espectador.getEmail())) {
            return ResultadoOperacion.error(ERROR_EMAIL);
        }
        if (espectadorService.buscarPorId(espectador.getId()) != null) {
            return ResultadoOperacion.error("Ya existe un espectador con ese ID.");
        }
        return resultado(espectadorService.guardar(espectador), "No se pudo guardar el espectador.");
    }

    public ResultadoOperacion modificarEspectadorDetallado(Espectador espectador) {
        if (espectador == null) return ResultadoOperacion.error("El espectador no puede ser null.");
        if (!emailValido(espectador.getEmail())) {
            return ResultadoOperacion.error(ERROR_EMAIL);
        }
        if (espectadorService.buscarPorId(espectador.getId()) == null) {
            return ResultadoOperacion.error("No existe un espectador con ese ID.");
        }
        return resultado(espectadorService.modificar(espectador), "No se pudo modificar el espectador.");
    }

    private boolean emailValido(String email) {
        return email != null && FORMATO_EMAIL.matcher(email).matches();
    }

    public ResultadoOperacion eliminarEspectadorDetallado(int id) {
        if (espectadorService.buscarPorId(id) == null) {
            return ResultadoOperacion.error("No existe un espectador con ese ID.");
        }
        return resultado(espectadorService.eliminar(id), "No se pudo eliminar el espectador.");
    }

    public ResultadoOperacion guardarEntradaDetallado(Entrada entrada) {
        if (entrada == null) return ResultadoOperacion.error("La entrada no puede ser null.");
        if (entradaService.buscarPorId(entrada.getId()) != null) {
            return ResultadoOperacion.error("Ya existe una entrada con ese ID.");
        }
        Actuacion actuacion = actuacionService.buscarPorId(entrada.getIdActuacion());
        if (actuacion == null) {
            return ResultadoOperacion.error("No existe la actuación indicada.");
        }
        if (espectadorService.buscarPorId(entrada.getIdEspectador()) == null) {
            return ResultadoOperacion.error("No existe el espectador indicado.");
        }
        if (escenarioService.buscarPorId(actuacion.getIdEscenario()) == null) {
            return ResultadoOperacion.error("No se encontró el escenario de la actuación.");
        }
        if (!entradaService.hayCapacidadDisponible(entrada.getIdActuacion())) {
            return ResultadoOperacion.error("No hay capacidad disponible para la actuación.");
        }
        return resultado(guardarEntrada(entrada), "No se pudo guardar la entrada.");
    }

    public ResultadoOperacion modificarEntradaDetallado(Entrada entrada) {
        if (entrada == null) return ResultadoOperacion.error("La entrada no puede ser null.");
        if (entradaService.buscarPorId(entrada.getId()) == null) {
            return ResultadoOperacion.error("No existe una entrada con ese ID.");
        }
        return resultado(entradaService.modificar(entrada), "No se pudo modificar la entrada.");
    }

    public ResultadoOperacion eliminarEntradaDetallado(int id) {
        if (entradaService.buscarPorId(id) == null) {
            return ResultadoOperacion.error("No existe una entrada con ese ID.");
        }
        return resultado(entradaService.eliminar(id), "No se pudo eliminar la entrada.");
    }

    private ResultadoOperacion resultado(boolean exito, String mensajeError) {
        return exito
                ? ResultadoOperacion.exito()
                : ResultadoOperacion.error(mensajeError);
    }

    public static class ResultadoOperacion {
        private final boolean exito;
        private final String mensaje;

        private ResultadoOperacion(boolean exito, String mensaje) {
            this.exito = exito;
            this.mensaje = mensaje;
        }

        public boolean esExito() {
            return exito;
        }

        public static ResultadoOperacion exito() {
            return new ResultadoOperacion(true, "Operación realizada correctamente.");
        }

        private static ResultadoOperacion error(String mensaje) {
            System.out.println(mensaje);
            return new ResultadoOperacion(false, mensaje);
        }
    }

    public List<Artista> listarArtistas() { return artistaService.listar(); }
    public Artista buscarArtista(int id) { return artistaService.buscarPorId(id); }
    public boolean guardarArtista(Artista artista) { return artistaService.guardar(artista); }
    public boolean modificarArtista(Artista artista) { return artistaService.modificar(artista); }
    public boolean eliminarArtista(int id) { return artistaService.eliminar(id); }

    public List<Escenario> listarEscenarios() { return escenarioService.listar(); }
    public Escenario buscarEscenario(int id) { return escenarioService.buscarPorId(id); }
    public boolean guardarEscenario(Escenario escenario) { return escenarioService.guardar(escenario); }
    public boolean modificarEscenario(Escenario escenario) { return escenarioService.modificar(escenario); }
    public boolean eliminarEscenario(int id) { return escenarioService.eliminar(id); }

    public List<Actuacion> listarActuaciones() { return actuacionService.listar(); }
    public Actuacion buscarActuacion(int id) { return actuacionService.buscarPorId(id); }
    public boolean guardarActuacion(Actuacion actuacion) {
        if (actuacionService.hayConflictoHorario(actuacion)) return false;
        return actuacionService.guardar(actuacion);
    }
    public boolean modificarActuacion(Actuacion actuacion) {
        if (actuacionService.hayConflictoHorario(actuacion)) return false;
        return actuacionService.modificar(actuacion);
    }
    public boolean eliminarActuacion(int id) { return actuacionService.eliminar(id); }
    public List<Actuacion> buscarActuacionesPorFecha(LocalDate fecha) { return actuacionService.buscarPorFecha(fecha); }

    public List<Espectador> listarEspectadores() { return espectadorService.listar(); }
    public Espectador buscarEspectador(int id) { return espectadorService.buscarPorId(id); }
    public boolean guardarEspectador(Espectador espectador) { return espectadorService.guardar(espectador); }
    public boolean modificarEspectador(Espectador espectador) { return espectadorService.modificar(espectador); }
    public boolean eliminarEspectador(int id) { return espectadorService.eliminar(id); }

    public List<Entrada> listarEntradas() { return entradaService.listar(); }
    public Entrada buscarEntrada(int id) { return entradaService.buscarPorId(id); }
    public boolean guardarEntrada(Entrada entrada) {
        if (!entradaService.hayCapacidadDisponible(entrada.getIdActuacion())) return false;
        return entradaService.guardar(entrada);
    }
    public boolean modificarEntrada(Entrada entrada) { return entradaService.modificar(entrada); }
    public boolean eliminarEntrada(int id) { return entradaService.eliminar(id); }

    public void generarArtistasJSON() { artistaService.generarJSON(); }
    public void generarEscenariosJSON() { escenarioService.generarJSON(); }
    public void generarActuacionesJSON() { actuacionService.generarJSON(); }
    public void generarEspectadoresJSON() { espectadorService.generarJSON(); }
    public void generarEntradasJSON() { entradaService.generarJSON(); }

    public void generarArtistasXML() { artistaService.exportarXML(); }
    public void generarEscenariosXML() { escenarioService.exportarXML(); }
    public void generarActuacionesXML() { actuacionService.exportarXML(); }
    public void generarEspectadoresXML() { espectadorService.exportarXML(); }
    public void generarEntradasXML() { entradaService.exportarXML(); }
}