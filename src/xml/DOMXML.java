package xml;

import model.Espectador;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.util.List;

public class DOMXML {

    public void exportarEspectadores(List<Espectador> espectadores) {

        try {

            // Creamos el documento XML
            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();

            DocumentBuilder builder =
                    factory.newDocumentBuilder();

            Document document = builder.newDocument();

            // Elemento raíz
            Element raiz = document.createElement("espectadores");

            document.appendChild(raiz);

            // Añadimos cada espectador
            for (Espectador espectador : espectadores) {

                Element elementoEspectador =
                        document.createElement("espectador");

                raiz.appendChild(elementoEspectador);

                // ID
                Element id = document.createElement("id");
                id.setTextContent(
                        String.valueOf(espectador.getId())
                );

                elementoEspectador.appendChild(id);

                // Nombre
                Element nombre =
                        document.createElement("nombre");

                nombre.setTextContent(
                        espectador.getNombre()
                );

                elementoEspectador.appendChild(nombre);

                // Email
                Element email =
                        document.createElement("email");

                email.setTextContent(
                        espectador.getEmail()
                );

                elementoEspectador.appendChild(email);

                // Edad
                Element edad =
                        document.createElement("edad");

                edad.setTextContent(
                        String.valueOf(espectador.getEdad())
                );

                elementoEspectador.appendChild(edad);
            }

            // Transformamos el Document en fichero XML
            TransformerFactory transformerFactory =
                    TransformerFactory.newInstance();

            Transformer transformer =
                    transformerFactory.newTransformer();

            transformer.setOutputProperty(
                    OutputKeys.INDENT,
                    "yes"
            );

            DOMSource source =
                    new DOMSource(document);

            StreamResult result =
                    new StreamResult(
                            new File("data/espectadores.xml")
                    );

            transformer.transform(source, result);

            System.out.println(
                    "XML de espectadores creado correctamente."
            );

        } catch (Exception e) {

            System.out.println(
                    "Error al crear el XML de espectadores."
            );

            e.printStackTrace();
        }
    }
}