package service;

import dao.ArtistaDAO;
import json.GsonJSON;
import model.Artista;
import xml.XStreamXML;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArtistaService {
    private ArtistaDAO artistaDAO;
    private XStreamXML xStreamXML;
    private GsonJSON gsonJSON;

    public ArtistaService() {
        artistaDAO = new ArtistaDAO();
        xStreamXML = new XStreamXML();
        gsonJSON = new GsonJSON();
    }

    public boolean guardar(Artista artista) {
        if (artista == null) {
            System.out.println("El artista no puede ser null.");
            return false;
        }
        if (artistaDAO.buscarPorId(artista.getId()) != null) {
            System.out.println("Ya existe un artista con ese ID.");
            return false;
        }
        artistaDAO.guardar(artista);
        return true;
    }

    public List<Artista> listar() {
        return artistaDAO.listar();
    }

    public Artista buscarPorId(int id) {
        return artistaDAO.buscarPorId(id);
    }

    public boolean modificar(Artista artista) {
        if (artista == null) return false;
        if (artistaDAO.buscarPorId(artista.getId()) == null) {
            System.out.println("No existe un artista con ese ID.");
            return false;
        }
        return artistaDAO.modificar(artista);
    }

    public boolean eliminar(int id) {
        if (artistaDAO.buscarPorId(id) == null) {
            System.out.println("No existe un artista con ese ID.");
            return false;
        }
        return artistaDAO.eliminar(id);
    }

    public void exportarXML() {
        xStreamXML.exportarArtistas(listar());
    }

    public List<Artista> buscarPorGenero(String genero) {
        List<Artista> resultado = new ArrayList<>();
        for (Artista artista : listar()) {
            if (artista.getGenero().equalsIgnoreCase(genero)) resultado.add(artista);
        }
        return resultado;
    }

    public List<Artista> buscarPorPais(String pais) {
        List<Artista> resultado = new ArrayList<>();
        for (Artista artista : listar()) {
            if (artista.getPais().equalsIgnoreCase(pais)) resultado.add(artista);
        }
        return resultado;
    }

    public void generarJSON() {
        gsonJSON.exportarArtistas(listar());
    }

    public void leerJSON() {
        List<Artista> artistas = gsonJSON.leerArtistas();

        if (artistas == null) {
            System.out.print("El archivo no existe. ¿Quieres generarlo? (s/n): ");
            String respuesta = new Scanner(System.in).nextLine();

            if (respuesta.equalsIgnoreCase("s")) {
                gsonJSON.exportarArtistas(listar());
                artistas = gsonJSON.leerArtistas();
            } else {
                return;
            }
        }

        System.out.println();
        System.out.println("===== CONTENIDO DE artistas.json =====");

        if (artistas.isEmpty()) System.out.println("El archivo está vacío.");
        else for (Artista artista : artistas) System.out.println(artista);
    }
}