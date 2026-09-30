import model.Artista;
import service.ArtistaService;
import util.InicializadorFicheros;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        // 1. Inicializamos la carpeta y los ficheros
        InicializadorFicheros.inicializar();

        // 2. Creamos el Service
        ArtistaService artistaService = new ArtistaService();

        // 3. Creamos algunos artistas
        Artista artista1 = new Artista(
                1,
                "Amaia",
                "Pop",
                "España"
        );

        Artista artista2 = new Artista(
                2,
                "Dua Lipa",
                "Pop",
                "Reino Unido"
        );

        // 4. Guardamos los artistas
        artistaService.guardar(artista1);
        artistaService.guardar(artista2);

        // 5. Mostramos todos los artistas
        System.out.println("\n--- LISTA DE ARTISTAS ---");

        List<Artista> artistas = artistaService.listar();

        for (Artista artista : artistas) {
            System.out.println(artista);
        }

        // 6. Buscamos un artista por ID
        System.out.println("\n--- BUSCAR ARTISTA ---");

        Artista artistaEncontrado = artistaService.buscarPorId(1);

        if (artistaEncontrado != null) {
            System.out.println(artistaEncontrado);
        } else {
            System.out.println("No se ha encontrado el artista.");
        }

        // 7. Modificamos un artista
        System.out.println("\n--- MODIFICAR ARTISTA ---");

        Artista artistaModificado = new Artista(
                1,
                "Amaia",
                "Pop / Rock",
                "España"
        );

        if (artistaService.modificar(artistaModificado)) {
            System.out.println("Artista modificado correctamente.");
        }

        // 8. Mostramos la lista después de modificar
        System.out.println("\n--- LISTA DESPUÉS DE MODIFICAR ---");

        for (Artista artista : artistaService.listar()) {
            System.out.println(artista);
        }

        // 9. Eliminamos un artista
        System.out.println("\n--- ELIMINAR ARTISTA ---");

        if (artistaService.eliminar(2)) {
            System.out.println("Artista eliminado correctamente.");
        }

        // 10. Mostramos la lista final
        System.out.println("\n--- LISTA FINAL ---");

        for (Artista artista : artistaService.listar()) {
            System.out.println(artista);
        }
    }
}