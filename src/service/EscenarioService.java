package service;

import dao.EscenarioDAO;
import json.GsonJSON;
import model.Escenario;
import xml.DOMXML;
import java.util.List;
import java.util.Scanner;

public class EscenarioService {
    private EscenarioDAO escenarioDAO;
    private DOMXML domXML;
    private GsonJSON gsonJSON;

    public EscenarioService() {
        escenarioDAO = new EscenarioDAO();
        domXML = new DOMXML();
        gsonJSON = new GsonJSON();
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
        if (escenario == null) return false;
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
        domXML.exportarEscenarios(listar());
    }

    public void generarJSON() {
        gsonJSON.exportarEscenarios(listar());
    }

    public void leerJSON() {
        List<Escenario> escenarios = gsonJSON.leerEscenarios();

        if (escenarios == null) {
            System.out.print("El archivo no existe. ¿Quieres generarlo? (s/n): ");
            String respuesta = new Scanner(System.in).nextLine();

            if (respuesta.equalsIgnoreCase("s")) {
                gsonJSON.exportarEscenarios(listar());
                escenarios = gsonJSON.leerEscenarios();
            } else {
                return;
            }
        }

        System.out.println();
        System.out.println("===== CONTENIDO DE escenarios.json =====");

        if (escenarios.isEmpty()) System.out.println("El archivo está vacío.");
        else for (Escenario escenario : escenarios) System.out.println(escenario);
    }
}