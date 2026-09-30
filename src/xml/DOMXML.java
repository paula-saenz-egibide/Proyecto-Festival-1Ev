package xml;

import model.Espectador;
import model.Escenario;
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

    public void exportarEscenarios(List<Escenario> escenarios) {

        try {

            // 1. Crear el documento XML
            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();

            DocumentBuilder builder =
                    factory.newDocumentBuilder();

            Document document = builder.newDocument();


            // 2. Crear el elemento raíz
            Element raiz = document.createElement("escenarios");

            document.appendChild(raiz);


            // 3. Recorrer la lista de escenarios
            for (Escenario escenario : escenarios) {

                Element elementoEscenario =
                        document.createElement("escenario");

                raiz.appendChild(elementoEscenario);


                // ID
                Element id = document.createElement("id");

                id.setTextContent(
                        String.valueOf(escenario.getId())
                );

                elementoEscenario.appendChild(id);


                // Nombre
                Element nombre =
                        document.createElement("nombre");

                nombre.setTextContent(
                        escenario.getNombre()
                );

                elementoEscenario.appendChild(nombre);


                // Ubicación
                Element ubicacion =
                        document.createElement("ubicacion");

                ubicacion.setTextContent(
                        escenario.getUbicacion()
                );

                elementoEscenario.appendChild(ubicacion);


                // Capacidad
                Element capacidad =
                        document.createElement("capacidad");

                capacidad.setTextContent(
                        String.valueOf(escenario.getCapacidad())
                );

                elementoEscenario.appendChild(capacidad);
            }


            // 4. Crear el Transformer
            TransformerFactory transformerFactory =
                    TransformerFactory.newInstance();

            Transformer transformer =
                    transformerFactory.newTransformer();

            transformer.setOutputProperty(
                    OutputKeys.INDENT,
                    "yes"
            );


            // 5. Indicar dónde guardar el XML
            DOMSource source =
                    new DOMSource(document);

            StreamResult result =
                    new StreamResult(
                            new File("data/escenarios.xml")
                    );


            // 6. Generar el fichero
            transformer.transform(source, result);

            System.out.println(
                    "XML de escenarios creado correctamente."
            );

        } catch (Exception e) {

            System.out.println(
                    "Error al crear el XML de escenarios."
            );

            e.printStackTrace();
        }
    }
}