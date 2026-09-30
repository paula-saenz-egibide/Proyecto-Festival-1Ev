package service;

import dao.ArtistaDAO;
import model.Artista;
import xml.XStreamXML;

import java.util.List;

public class ArtistaService {

    private ArtistaDAO artistaDAO;
    private XStreamXML xStreamXML;


    public ArtistaService() {
        artistaDAO = new ArtistaDAO();
        xStreamXML = new XStreamXML();

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

        if (artista == null) {
            return false;
        }

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

        List<Artista> artistas = listar();

        xStreamXML.exportarArtistas(artistas);
    }
}