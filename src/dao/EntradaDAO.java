package dao;

import model.Entrada;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EntradaDAO {

    private static final String FICHERO = "data/entradas.dat";

    public void guardar(Entrada entrada) {

        List<Entrada> entradas = listar();

        entradas.add(entrada);

        try (FileOutputStream fos = new FileOutputStream(FICHERO);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            for (Entrada e : entradas) {
                oos.writeObject(e);
            }

            System.out.println("Entrada guardada correctamente.");

        } catch (IOException e) {
            System.out.println("Error al guardar la entrada.");
            e.printStackTrace();
        }
    }

    public List<Entrada> listar() {

        List<Entrada> entradas = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(FICHERO);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            while (true) {
                Entrada entrada = (Entrada) ois.readObject();
                entradas.add(entrada);
            }

        } catch (EOFException e) {
            // Fin del fichero
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al leer las entradas.");
            e.printStackTrace();
        }

        return entradas;
    }

    public Entrada buscarPorId(int id) {

        List<Entrada> entradas = listar();

        for (Entrada entrada : entradas) {

            if (entrada.getId() == id) {
                return entrada;
            }
        }

        return null;
    }

    public boolean modificar(Entrada entradaModificada) {

        List<Entrada> entradas = listar();

        boolean encontrado = false;

        for (int i = 0; i < entradas.size(); i++) {

            if (entradas.get(i).getId() == entradaModificada.getId()) {

                entradas.set(i, entradaModificada);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            return false;
        }

        try (FileOutputStream fos = new FileOutputStream(FICHERO);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            for (Entrada entrada : entradas) {
                oos.writeObject(entrada);
            }

            return true;

        } catch (IOException e) {
            System.out.println("Error al modificar la entrada.");
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {

        List<Entrada> entradas = listar();

        boolean encontrado = false;

        for (int i = 0; i < entradas.size(); i++) {

            if (entradas.get(i).getId() == id) {

                entradas.remove(i);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            return false;
        }

        try (FileOutputStream fos = new FileOutputStream(FICHERO);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            for (Entrada entrada : entradas) {
                oos.writeObject(entrada);
            }

            return true;

        } catch (IOException e) {
            System.out.println("Error al eliminar la entrada.");
            e.printStackTrace();
            return false;
        }
    }
}