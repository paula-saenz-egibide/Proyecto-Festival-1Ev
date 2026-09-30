package service;

import dao.ActuacionDAO;
import dao.EntradaDAO;
import dao.EscenarioDAO;
import dao.EspectadorDAO;
import json.GsonJSON;
import model.Actuacion;
import model.Entrada;
import model.Escenario;
import xml.XStreamXML;
import java.util.List;
import java.util.Scanner;

public class EntradaService {
    private EntradaDAO entradaDAO;
    private ActuacionDAO actuacionDAO;
    private EspectadorDAO espectadorDAO;
    private XStreamXML xStreamXML;
    private EscenarioDAO escenarioDAO;
    private GsonJSON gsonJSON;

    public EntradaService() {
        entradaDAO = new EntradaDAO();
        actuacionDAO = new ActuacionDAO();
        espectadorDAO = new EspectadorDAO();
        xStreamXML = new XStreamXML();
        escenarioDAO = new EscenarioDAO();
        gsonJSON = new GsonJSON();
    }

    public boolean guardar(Entrada entrada) {
        if (entrada == null) {
            System.out.println("La entrada no puede ser null.");
            return false;
        }
        if (entradaDAO.buscarPorId(entrada.getId()) != null) {
            System.out.println("Ya existe una entrada con ese ID.");
            return false;
        }
        if (actuacionDAO.buscarPorId(entrada.getIdActuacion()) == null) {
            System.out.println("No existe la actuación indicada.");
            return false;
        }
        if (espectadorDAO.buscarPorId(entrada.getIdEspectador()) == null) {
            System.out.println("No existe el espectador indicado.");
            return false;
        }
        entradaDAO.guardar(entrada);
        return true;
    }

    public List<Entrada> listar() {
        return entradaDAO.listar();
    }

    public Entrada buscarPorId(int id) {
        return entradaDAO.buscarPorId(id);
    }

    public boolean modificar(Entrada entrada) {
        if (entrada == null) return false;
        if (entradaDAO.buscarPorId(entrada.getId()) == null) {
            System.out.println("No existe una entrada con ese ID.");
            return false;
        }
        return entradaDAO.modificar(entrada);
    }

    public boolean eliminar(int id) {
        if (entradaDAO.buscarPorId(id) == null) {
            System.out.println("No existe una entrada con ese ID.");
            return false;
        }
        return entradaDAO.eliminar(id);
    }

    public void exportarXML() {
        xStreamXML.exportarEntradas(listar());
    }

    public int contarEntradasActivas(int idActuacion) {
        int contador = 0;

        for (Entrada entrada : listar()) {
            if (entrada.getIdActuacion() == idActuacion && entrada.isActiva()) contador++;
        }

        return contador;
    }

    public boolean hayCapacidadDisponible(int idActuacion) {
        Actuacion actuacion = actuacionDAO.buscarPorId(idActuacion);

        if (actuacion == null) return false;

        Escenario escenario = escenarioDAO.buscarPorId(actuacion.getIdEscenario());

        if (escenario == null) return false;

        return contarEntradasActivas(idActuacion) < escenario.getCapacidad();
    }

    public boolean hayCapacidadDisponible(int idActuacion, int idEntradaExcluir) {
        Actuacion actuacion = actuacionDAO.buscarPorId(idActuacion);

        if (actuacion == null) return false;

        Escenario escenario = escenarioDAO.buscarPorId(actuacion.getIdEscenario());

        if (escenario == null) return false;

        int contador = 0;

        for (Entrada entrada : listar()) {
            if (entrada.getId() != idEntradaExcluir && entrada.getIdActuacion() == idActuacion && entrada.isActiva()) {
                contador++;
            }
        }

        return contador < escenario.getCapacidad();
    }

    public void generarJSON() {
        gsonJSON.exportarEntradas(listar());
    }

    public void leerJSON() {
        List<Entrada> entradas = gsonJSON.leerEntradas();

        if (entradas == null) {
            System.out.print("El archivo no existe. ¿Quieres generarlo? (s/n): ");
            String respuesta = new Scanner(System.in).nextLine();

            if (respuesta.equalsIgnoreCase("s")) {
                gsonJSON.exportarEntradas(listar());
                entradas = gsonJSON.leerEntradas();
            } else {
                return;
            }
        }

        System.out.println();
        System.out.println("===== CONTENIDO DE entradas.json =====");

        if (entradas.isEmpty()) System.out.println("El archivo está vacío.");
        else for (Entrada entrada : entradas) System.out.println(entrada);
    }
}