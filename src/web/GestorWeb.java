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

public class GestorWeb {
    private ArtistaService artistaService = new ArtistaService();
    private ActuacionService actuacionService = new ActuacionService();
    private EscenarioService escenarioService = new EscenarioService();
    private EspectadorService espectadorService = new EspectadorService();
    private EntradaService entradaService = new EntradaService();

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