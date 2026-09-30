package service;

import dao.ActuacionDAO;
import dao.ArtistaDAO;
import dao.EscenarioDAO;
import json.GsonJSON;
import model.Actuacion;
import xml.XStreamXML;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ActuacionService {
    private ActuacionDAO actuacionDAO;
    private ArtistaDAO artistaDAO;
    private EscenarioDAO escenarioDAO;
    private XStreamXML xStreamXML;
    private GsonJSON gsonJSON;

    public ActuacionService() {
        actuacionDAO = new ActuacionDAO();
        artistaDAO = new ArtistaDAO();
        escenarioDAO = new EscenarioDAO();
        xStreamXML = new XStreamXML();
        gsonJSON = new GsonJSON();
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

    public List<Actuacion> buscarPorFecha(LocalDate fecha) {
        List<Actuacion> resultado = new ArrayList<>();
        for (Actuacion actuacion : listar()) {
            if (actuacion.getFecha().equals(fecha)) {
                resultado.add(actuacion);
            }
        }
        return resultado;
    }

    public boolean hayConflictoHorario(Actuacion nueva) {
        LocalDate fecha = nueva.getFecha();
        int inicioNuevo = convertirHoraAMinutos(nueva.getHora());
        int finNuevo = inicioNuevo + nueva.getDuracion();
        for (Actuacion actuacion : listar()) {
            if (actuacion.getId() == nueva.getId()) continue;
            if (actuacion.getIdEscenario() != nueva.getIdEscenario()) continue;
            if (!actuacion.getFecha().equals(fecha)) continue;
            int inicioExistente = convertirHoraAMinutos(actuacion.getHora());
            int finExistente = inicioExistente + actuacion.getDuracion();
            if (inicioNuevo < finExistente && finNuevo > inicioExistente) {
                return true;
            }
        }
        return false;
    }

    private int convertirHoraAMinutos(String hora) {
        String[] partes = hora.split(":");
        int horas = Integer.parseInt(partes[0]);
        int minutos = Integer.parseInt(partes[1]);
        return horas * 60 + minutos;
    }

    public void generarJSON() {
        gsonJSON.exportarActuaciones(listar());
    }

    public void leerJSON() {
        List<Actuacion> actuaciones = gsonJSON.leerActuaciones();

        if (actuaciones == null) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("El archivo no existe. ¿Quieres generarlo? (s/n): ");
            String respuesta = scanner.nextLine();

            if (respuesta.equalsIgnoreCase("s")) {
                generarJSON();
                actuaciones = gsonJSON.leerActuaciones();
            } else {
                return;
            }
        }

        System.out.println();
        System.out.println("===== CONTENIDO DE actuaciones.json =====");

        if (actuaciones.isEmpty()) {
            System.out.println("El archivo está vacío.");
        } else {
            for (Actuacion actuacion : actuaciones) {
                System.out.println(actuacion);
            }
        }
    }
}