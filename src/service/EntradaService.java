package service;

import dao.ActuacionDAO;
import dao.EntradaDAO;
import dao.EspectadorDAO;
import model.Entrada;
import xml.XStreamXML;

import java.util.List;

public class EntradaService {

    private EntradaDAO entradaDAO;
    private ActuacionDAO actuacionDAO;
    private EspectadorDAO espectadorDAO;
    private XStreamXML xStreamXML;

    public EntradaService() {
        entradaDAO = new EntradaDAO();
        actuacionDAO = new ActuacionDAO();
        espectadorDAO = new EspectadorDAO();
        xStreamXML = new XStreamXML();

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
            System.out.println("La actuación indicada no existe.");
            return false;
        }

        if (espectadorDAO.buscarPorId(entrada.getIdEspectador()) == null) {
            System.out.println("El espectador indicado no existe.");
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

        if (entrada == null) {
            return false;
        }

        if (entradaDAO.buscarPorId(entrada.getId()) == null) {
            System.out.println("No existe una entrada con ese ID.");
            return false;
        }

        if (actuacionDAO.buscarPorId(entrada.getIdActuacion()) == null) {
            System.out.println("La actuación indicada no existe.");
            return false;
        }

        if (espectadorDAO.buscarPorId(entrada.getIdEspectador()) == null) {
            System.out.println("El espectador indicado no existe.");
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

        List<Entrada> entradas = listar();

        xStreamXML.exportarEntradas(entradas);
    }
}