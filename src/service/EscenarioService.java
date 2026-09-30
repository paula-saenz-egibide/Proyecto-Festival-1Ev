package service;

import dao.EscenarioDAO;
import model.Escenario;
import xml.DOMXML;

import java.util.List;

public class EscenarioService {

    private EscenarioDAO escenarioDAO;
    private DOMXML domXML;


    public EscenarioService() {
        escenarioDAO = new EscenarioDAO();
        domXML = new DOMXML();

    }

    public boolean guardar(Escenario escenario) {

        if (escenario == null) {
            System.out.println("El escenario no puede ser null.");
            return false;
        }

        if (escenarioDAO.buscarPorId(escenario.getId()) != null) {
            System.out.println("Ya existe un escenario con ese ID.");
            return false;
        }

        escenarioDAO.guardar(escenario);
        return true;
    }

    public List<Escenario> listar() {
        return escenarioDAO.listar();
    }

    public Escenario buscarPorId(int id) {
        return escenarioDAO.buscarPorId(id);
    }

    public boolean modificar(Escenario escenario) {

        if (escenario == null) {
            return false;
        }

        if (escenarioDAO.buscarPorId(escenario.getId()) == null) {
            System.out.println("No existe un escenario con ese ID.");
            return false;
        }

        return escenarioDAO.modificar(escenario);
    }

    public boolean eliminar(int id) {

        if (escenarioDAO.buscarPorId(id) == null) {
            System.out.println("No existe un escenario con ese ID.");
            return false;
        }

        return escenarioDAO.eliminar(id);
    }

    public void exportarXML() {

        List<Escenario> escenarios = listar();

        domXML.exportarEscenarios(escenarios);
    }
}