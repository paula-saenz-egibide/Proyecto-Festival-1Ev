package service;

import dao.EspectadorDAO;
import model.Espectador;

import java.util.List;

public class EspectadorService {

    private EspectadorDAO espectadorDAO;

    public EspectadorService() {
        espectadorDAO = new EspectadorDAO();
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

        if (espectador == null) {
            return false;
        }

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
}