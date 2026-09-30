package xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import model.Artista;
import model.Actuacion;
import model.Entrada;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class XStreamXML {

    public void exportarArtistas(List<Artista> artistas) {

        XStream xstream = new XStream(new StaxDriver());

        xstream.alias("artista", Artista.class);

        String xml = xstream.toXML(artistas);

        guardarFichero(xml, "data/artistas.xml");
    }

    public void exportarActuaciones(List<Actuacion> actuaciones) {

        XStream xstream = new XStream(new StaxDriver());

        xstream.alias("actuacion", Actuacion.class);

        String xml = xstream.toXML(actuaciones);

        guardarFichero(xml, "data/actuaciones.xml");
    }

    public void exportarEntradas(List<Entrada> entradas) {

        XStream xstream = new XStream(new StaxDriver());

        xstream.alias("entrada", Entrada.class);

        String xml = xstream.toXML(entradas);

        guardarFichero(xml, "data/entradas.xml");
    }

    private void guardarFichero(String xml, String fichero) {

        try (FileOutputStream fos = new FileOutputStream(fichero)) {

            fos.write(xml.getBytes(StandardCharsets.UTF_8));

            System.out.println(
                    "XML creado correctamente: " + fichero
            );

        } catch (IOException e) {

            System.out.println(
                    "Error al crear el fichero XML: " + fichero
            );

            e.printStackTrace();
        }
    }
}