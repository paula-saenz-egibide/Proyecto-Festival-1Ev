package dao;

import model.Actuacion;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ActuacionDAO {

    private static final String FICHERO = "data/actuaciones.dat";

    public void guardar(Actuacion actuacion) {

        List<Actuacion> actuaciones = listar();

        actuaciones.add(actuacion);

        try (FileOutputStream fos = new FileOutputStream(FICHERO);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            for (Actuacion a : actuaciones) {
                oos.writeObject(a);
            }

            System.out.println("Actuación guardada correctamente.");

        } catch (IOException e) {
            System.out.println("Error al guardar la actuación.");
            e.printStackTrace();
        }
    }

    public List<Actuacion> listar() {

        List<Actuacion> actuaciones = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(FICHERO);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            while (true) {
                Actuacion actuacion = (Actuacion) ois.readObject();
                actuaciones.add(actuacion);
            }

        } catch (EOFException e) {
            // Fin del fichero
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al leer las actuaciones.");
            e.printStackTrace();
        }

        return actuaciones;
    }

    public Actuacion buscarPorId(int id) {

        List<Actuacion> actuaciones = listar();

        for (Actuacion actuacion : actuaciones) {

            if (actuacion.getId() == id) {
                return actuacion;
            }
        }

        return null;
    }

    public boolean modificar(Actuacion actuacionModificada) {

        List<Actuacion> actuaciones = listar();

        boolean encontrado = false;

        for (int i = 0; i < actuaciones.size(); i++) {

            if (actuaciones.get(i).getId() == actuacionModificada.getId()) {

                actuaciones.set(i, actuacionModificada);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            return false;
        }

        try (FileOutputStream fos = new FileOutputStream(FICHERO);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            for (Actuacion actuacion : actuaciones) {
                oos.writeObject(actuacion);
            }

            return true;

        } catch (IOException e) {
            System.out.println("Error al modificar la actuación.");
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {

        List<Actuacion> actuaciones = listar();

        boolean encontrado = false;

        for (int i = 0; i < actuaciones.size(); i++) {

            if (actuaciones.get(i).getId() == id) {

                actuaciones.remove(i);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            return false;
        }

        try (FileOutputStream fos = new FileOutputStream(FICHERO);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            for (Actuacion actuacion : actuaciones) {
                oos.writeObject(actuacion);
            }

            return true;

        } catch (IOException e) {
            System.out.println("Error al eliminar la actuación.");
            e.printStackTrace();
            return false;
        }
    }
}