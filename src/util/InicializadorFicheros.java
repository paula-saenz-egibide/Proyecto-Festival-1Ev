package util;

import java.io.File;
import java.io.IOException;

public class InicializadorFicheros {

    private static final String CARPETA_DATA = "data";

    private static final String[] FICHEROS = {
            "artistas.dat",
            "escenarios.dat",
            "actuaciones.dat",
            "espectadores.dat",
            "entradas.dat"
    };

    public static void inicializar() {

        // Crear la carpeta data si no existe
        File carpeta = new File(CARPETA_DATA);

        if (!carpeta.exists()) {
            if (carpeta.mkdir()) {
                System.out.println("Carpeta data creada.");
            } else {
                System.out.println("No se ha podido crear la carpeta data.");
            }
        }

        // Crear los ficheros si no existen
        for (String nombreFichero : FICHEROS) {

            File fichero = new File(CARPETA_DATA, nombreFichero);

            if (!fichero.exists()) {
                try {
                    if (fichero.createNewFile()) {
                        System.out.println("Fichero creado: " + nombreFichero);
                    }
                } catch (IOException e) {
                    System.out.println(
                            "Error al crear el fichero " + nombreFichero
                    );
                }
            }
        }
    }
}