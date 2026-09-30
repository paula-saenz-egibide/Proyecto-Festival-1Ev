package service;

import dao.ActuacionDAO;
import dao.ArtistaDAO;
import dao.EscenarioDAO;
import model.Actuacion;
import xml.XStreamXML;

import java.util.List;

public class ActuacionService {

    private ActuacionDAO actuacionDAO;
    private ArtistaDAO artistaDAO;
    private EscenarioDAO escenarioDAO;
    private XStreamXML xStreamXML;


    public ActuacionService() {
        actuacionDAO = new ActuacionDAO();
        artistaDAO = new ArtistaDAO();
        escenarioDAO = new EscenarioDAO();
        xStreamXML = new XStreamXML();

    }

    public boolean guardar(Actuacion actuacion) {

        if (actuacion == null) {
            System.out.println("La actuación no puede ser null.");
            return false;
        }

        if (actuacionDAO.buscarPorId(actuacion.getId()) != null) {
            System.out.println("Ya existe una actuación con ese ID.");
            return false;
        }

        if (artistaDAO.buscarPorId(actuacion.getIdArtista()) == null) {
            System.out.println("El artista indicado no existe.");
            return false;
        }

        if (escenarioDAO.buscarPorId(actuacion.getIdEscenario()) == null) {
            System.out.println("El escenario indicado no existe.");
            return false;
        }

        actuacionDAO.guardar(actuacion);
        return true;
    }

    public List<Actuacion> listar() {
        return actuacionDAO.listar();
    }

    public Actuacion buscarPorId(int id) {
        return actuacionDAO.buscarPorId(id);
    }

    public boolean modificar(Actuacion actuacion) {

        if (actuacion == null) {
            return false;
        }

        if (actuacionDAO.buscarPorId(actuacion.getId()) == null) {
            System.out.println("No existe una actuación con ese ID.");
            return false;
        }

        if (artistaDAO.buscarPorId(actuacion.getIdArtista()) == null) {
            System.out.println("El artista indicado no existe.");
            return false;
        }

        if (escenarioDAO.buscarPorId(actuacion.getIdEscenario()) == null) {
            System.out.println("El escenario indicado no existe.");
            return false;
        }

        return actuacionDAO.modificar(actuacion);
    }

    public boolean eliminar(int id) {

        if (actuacionDAO.buscarPorId(id) == null) {
            System.out.println("No existe una actuación con ese ID.");
            return false;
        }

        return actuacionDAO.eliminar(id);
    }

    public void exportarXML() {

        List<Actuacion> actuaciones = listar();

        xStreamXML.exportarActuaciones(actuaciones);
    }
}