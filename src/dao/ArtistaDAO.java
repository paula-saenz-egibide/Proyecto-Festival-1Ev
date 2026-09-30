package dao;

import model.Artista;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArtistaDAO {

    private static final String FICHERO = "data/artistas.dat";

    public void guardar(Artista artista) {

        List<Artista> artistas = listar();

        artistas.add(artista);

        try (FileOutputStream fos = new FileOutputStream(FICHERO);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            for (Artista a : artistas) {
                oos.writeObject(a);
            }

            System.out.println("Artista guardado correctamente.");

        } catch (IOException e) {
            System.out.println("Error al guardar el artista.");
            e.printStackTrace();
        }
    }

    public List<Artista> listar() {

        List<Artista> artistas = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(FICHERO);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            while (true) {
                Artista artista = (Artista) ois.readObject();
                artistas.add(artista);
            }

        } catch (EOFException e) {
            // Fin del fichero
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al leer los artistas.");
            e.printStackTrace();
        }

        return artistas;
    }

    public Artista buscarPorId(int id) {

        List<Artista> artistas = listar();

        for (Artista artista : artistas) {

            if (artista.getId() == id) {
                return artista;
            }
        }

        return null;
    }

    public boolean modificar(Artista artistaModificado) {

        List<Artista> artistas = listar();

        boolean encontrado = false;

        for (int i = 0; i < artistas.size(); i++) {

            if (artistas.get(i).getId() == artistaModificado.getId()) {

                artistas.set(i, artistaModificado);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            return false;
        }

        try (FileOutputStream fos = new FileOutputStream(FICHERO);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            for (Artista artista : artistas) {
                oos.writeObject(artista);
            }

            return true;

        } catch (IOException e) {
            System.out.println("Error al modificar el artista.");
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {

        List<Artista> artistas = listar();

        boolean encontrado = false;

        for (int i = 0; i < artistas.size(); i++) {

            if (artistas.get(i).getId() == id) {

                artistas.remove(i);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            return false;
        }

        try (FileOutputStream fos = new FileOutputStream(FICHERO);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            for (Artista artista : artistas) {
                oos.writeObject(artista);
            }

            return true;

        } catch (IOException e) {
            System.out.println("Error al eliminar el artista.");
            e.printStackTrace();
            return false;
        }
    }
}