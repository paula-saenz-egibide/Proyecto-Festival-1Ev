package dao;

import model.Escenario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EscenarioDAO {

    private static final String FICHERO = "data/escenarios.dat";

    public void guardar(Escenario escenario) {

        List<Escenario> escenarios = listar();

        escenarios.add(escenario);

        try (FileOutputStream fos = new FileOutputStream(FICHERO);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            for (Escenario e : escenarios) {
                oos.writeObject(e);
            }

            System.out.println("Escenario guardado correctamente.");

        } catch (IOException e) {
            System.out.println("Error al guardar el escenario.");
            e.printStackTrace();
        }
    }

    public List<Escenario> listar() {

        List<Escenario> escenarios = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(FICHERO);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            while (true) {
                Escenario escenario = (Escenario) ois.readObject();
                escenarios.add(escenario);
            }

        } catch (EOFException e) {
            // Fin del fichero
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al leer los escenarios.");
            e.printStackTrace();
        }

        return escenarios;
    }

    public Escenario buscarPorId(int id) {

        List<Escenario> escenarios = listar();

        for (Escenario escenario : escenarios) {

            if (escenario.getId() == id) {
                return escenario;
            }
        }

        return null;
    }

    public boolean modificar(Escenario escenarioModificado) {

        List<Escenario> escenarios = listar();

        boolean encontrado = false;

        for (int i = 0; i < escenarios.size(); i++) {

            if (escenarios.get(i).getId() == escenarioModificado.getId()) {

                escenarios.set(i, escenarioModificado);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            return false;
        }

        try (FileOutputStream fos = new FileOutputStream(FICHERO);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            for (Escenario escenario : escenarios) {
                oos.writeObject(escenario);
            }

            return true;

        } catch (IOException e) {
            System.out.println("Error al modificar el escenario.");
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {

        List<Escenario> escenarios = listar();

        boolean encontrado = false;

        for (int i = 0; i < escenarios.size(); i++) {

            if (escenarios.get(i).getId() == id) {

                escenarios.remove(i);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            return false;
        }

        try (FileOutputStream fos = new FileOutputStream(FICHERO);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            for (Escenario escenario : escenarios) {
                oos.writeObject(escenario);
            }

            return true;

        } catch (IOException e) {
            System.out.println("Error al eliminar el escenario.");
            e.printStackTrace();
            return false;
        }
    }
}