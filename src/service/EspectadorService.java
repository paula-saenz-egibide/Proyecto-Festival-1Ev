package service;

import dao.EspectadorDAO;
import json.GsonJSON;
import model.Espectador;
import xml.DOMXML;
import java.util.List;
import java.util.Scanner;

public class EspectadorService {
    private EspectadorDAO espectadorDAO;
    private DOMXML domXML;
    private GsonJSON gsonJSON;

    public EspectadorService() {
        espectadorDAO = new EspectadorDAO();
        domXML = new DOMXML();
        gsonJSON = new GsonJSON();
    }

    public boolean guardar(Espectador espectador) {
        if (espectador == null) {
            System.out.println("El espectador no puede ser null.");
            return false;
        }
        if (espectadorDAO.buscarPorId(espectador.getId()) != null) {
            System.out.println("Ya existe un espectador con ese ID.");
            return false;
        }
        espectadorDAO.guardar(espectador);
        return true;
    }

    public List<Espectador> listar() {
        return espectadorDAO.listar();
    }

    public Espectador buscarPorId(int id) {
        return espectadorDAO.buscarPorId(id);
    }

    public boolean modificar(Espectador espectador) {
        if (espectador == null) return false;
        if (espectadorDAO.buscarPorId(espectador.getId()) == null) {
            System.out.println("No existe un espectador con ese ID.");
            return false;
        }
        return espectadorDAO.modificar(espectador);
    }

    public boolean eliminar(int id) {
        if (espectadorDAO.buscarPorId(id) == null) {
            System.out.println("No existe un espectador con ese ID.");
            return false;
        }
        return espectadorDAO.eliminar(id);
    }

    public void exportarXML() {
        domXML.exportarEspectadores(listar());
    }

    public void generarJSON() {
        gsonJSON.exportarEspectadores(listar());
    }

    public void leerJSON() {
        List<Espectador> espectadores = gsonJSON.leerEspectadores();

        if (espectadores == null) {
            System.out.print("El archivo no existe. ¿Quieres generarlo? (s/n): ");
            String respuesta = new Scanner(System.in).nextLine();

            if (respuesta.equalsIgnoreCase("s")) {
                gsonJSON.exportarEspectadores(listar());
                espectadores = gsonJSON.leerEspectadores();
            } else {
                return;
            }
        }

        System.out.println();
        System.out.println("===== CONTENIDO DE espectadores.json =====");

        if (espectadores.isEmpty()) System.out.println("El archivo está vacío.");
        else for (Espectador espectador : espectadores) System.out.println(espectador);
    }
}