package json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GsonJSON {
    private final Gson gson;

    public GsonJSON() {
        gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
    }

    public void exportarArtistas(List<Artista> artistas) {
        escribirJSON(artistas, "data/artistas.json");
    }

    public List<Artista> leerArtistas() {
        Artista[] artistas = leerJSON("data/artistas.json", Artista[].class);
        return artistas != null ? new ArrayList<>(Arrays.asList(artistas)) : null;
    }

    public void exportarEscenarios(List<Escenario> escenarios) {
        escribirJSON(escenarios, "data/escenarios.json");
    }

    public List<Escenario> leerEscenarios() {
        Escenario[] escenarios = leerJSON("data/escenarios.json", Escenario[].class);
        return escenarios != null ? new ArrayList<>(Arrays.asList(escenarios)) : null;
    }

    public void exportarActuaciones(List<Actuacion> actuaciones) {
        escribirJSON(actuaciones, "data/actuaciones.json");
    }

    public List<Actuacion> leerActuaciones() {
        Actuacion[] actuaciones = leerJSON("data/actuaciones.json", Actuacion[].class);
        return actuaciones != null ? new ArrayList<>(Arrays.asList(actuaciones)) : null;
    }

    public void exportarEspectadores(List<Espectador> espectadores) {
        escribirJSON(espectadores, "data/espectadores.json");
    }

    public List<Espectador> leerEspectadores() {
        Espectador[] espectadores = leerJSON("data/espectadores.json", Espectador[].class);
        return espectadores != null ? new ArrayList<>(Arrays.asList(espectadores)) : null;
    }

    public void exportarEntradas(List<Entrada> entradas) {
        escribirJSON(entradas, "data/entradas.json");
    }

    public List<Entrada> leerEntradas() {
        Entrada[] entradas = leerJSON("data/entradas.json", Entrada[].class);
        return entradas != null ? new ArrayList<>(Arrays.asList(entradas)) : null;
    }

    private void escribirJSON(Object objeto, String fichero) {
        try {
            File archivo = new File(fichero);
            File carpeta = archivo.getParentFile();
            if (carpeta != null && !carpeta.exists()) carpeta.mkdirs();

            try (FileWriter writer = new FileWriter(archivo)) {
                gson.toJson(objeto, writer);
                System.out.println("JSON creado correctamente: " + fichero);
            }
        } catch (IOException e) {
            System.out.println("Error al crear el JSON: " + fichero);
            e.printStackTrace();
        }
    }

    private <T> T leerJSON(String fichero, Class<T> clase) {
        File archivo = new File(fichero);

        if (!archivo.exists()) {
            System.out.println("El archivo no existe: " + fichero);
            return null;
        }

        try (FileReader reader = new FileReader(archivo)) {
            T objeto = gson.fromJson(reader, clase);
            System.out.println("JSON leído correctamente: " + fichero);
            return objeto;
        } catch (IOException e) {
            System.out.println("Error al leer el JSON: " + fichero);
            e.printStackTrace();
            return null;
        }
    }
}