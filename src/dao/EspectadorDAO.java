package dao;

import model.Espectador;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EspectadorDAO {

    private static final String FICHERO = "data/espectadores.dat";

    public void guardar(Espectador espectador) {

        List<Espectador> espectadores = listar();

        espectadores.add(espectador);

        try (FileOutputStream fos = new FileOutputStream(FICHERO);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            for (Espectador e : espectadores) {
                oos.writeObject(e);
            }

            System.out.println("Espectador guardado correctamente.");

        } catch (IOException e) {
            System.out.println("Error al guardar el espectador.");
            e.printStackTrace();
        }
    }

    public List<Espectador> listar() {

        List<Espectador> espectadores = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(FICHERO);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            while (true) {
                Espectador espectador = (Espectador) ois.readObject();
                espectadores.add(espectador);
            }

        } catch (EOFException e) {
            // Fin del fichero
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al leer los espectadores.");
            e.printStackTrace();
        }

        return espectadores;
    }

    public Espectador buscarPorId(int id) {

        List<Espectador> espectadores = listar();

        for (Espectador espectador : espectadores) {

            if (espectador.getId() == id) {
                return espectador;
            }
        }

        return null;
    }

    public boolean modificar(Espectador espectadorModificado) {

        List<Espectador> espectadores = listar();

        boolean encontrado = false;

        for (int i = 0; i < espectadores.size(); i++) {

            if (espectadores.get(i).getId() == espectadorModificado.getId()) {

                espectadores.set(i, espectadorModificado);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            return false;
        }

        try (FileOutputStream fos = new FileOutputStream(FICHERO);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            for (Espectador espectador : espectadores) {
                oos.writeObject(espectador);
            }

            return true;

        } catch (IOException e) {
            System.out.println("Error al modificar el espectador.");
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {

        List<Espectador> espectadores = listar();

        boolean encontrado = false;

        for (int i = 0; i < espectadores.size(); i++) {

            if (espectadores.get(i).getId() == id) {

                espectadores.remove(i);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            return false;
        }

        try (FileOutputStream fos = new FileOutputStream(FICHERO);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            for (Espectador espectador : espectadores) {
                oos.writeObject(espectador);
            }

            return true;

        } catch (IOException e) {
            System.out.println("Error al eliminar el espectador.");
            e.printStackTrace();
            return false;
        }
    }
}