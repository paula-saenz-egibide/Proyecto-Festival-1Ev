package web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import json.LocalDateAdapter;
import model.Artista;
import model.Actuacion;
import model.Escenario;
import model.Espectador;
import model.Entrada;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public class Servidor {

    private static final GestorWeb gestorWeb = new GestorWeb();

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

    public static void main(String[] args) throws IOException {

        HttpServer servidor = null;
        int puerto = 8080;

        while (servidor == null) {
            try {
                servidor = HttpServer.create(new InetSocketAddress(puerto), 0);
            } catch (java.net.BindException e) {
                System.out.println("El puerto " + puerto + " está ocupado.");
                puerto++;
            }
        }

        servidor.createContext("/", Servidor::archivosWeb);
        servidor.createContext("/api/artistas", Servidor::artistas);
        servidor.createContext("/api/escenarios", Servidor::escenarios);
        servidor.createContext("/api/actuaciones", Servidor::actuaciones);
        servidor.createContext("/api/espectadores", Servidor::espectadores);
        servidor.createContext("/api/entradas", Servidor::entradas);
        servidor.createContext("/api/archivos", Servidor::archivos);
        servidor.createContext("/api/esquema", Servidor::esquema);

        servidor.start();

        System.out.println("Servidor iniciado.");
        System.out.println("Abre http://localhost:" + puerto);
    }

    private static void archivosWeb(HttpExchange exchange) throws IOException {

        if (!exchange.getRequestMethod().equals("GET")) {
            responder(exchange, "Método no permitido", 405);
            return;
        }

        String ruta = exchange.getRequestURI().getPath();

        if (ruta.equals("/")) {
            ruta = "/index.html";
        }

        File archivo = new File("web" + ruta);

        if (!archivo.exists() || archivo.isDirectory()) {
            responder(exchange, "Archivo no encontrado", 404);
            return;
        }

        byte[] datos = Files.readAllBytes(archivo.toPath());

        exchange.getResponseHeaders().set(
                "Content-Type",
                obtenerTipoContenido(archivo.getName())
        );

        exchange.sendResponseHeaders(200, datos.length);

        try (OutputStream salida = exchange.getResponseBody()) {
            salida.write(datos);
        }
    }

    private static String obtenerTipoContenido(String nombre) {

        if (nombre.endsWith(".html")) {
            return "text/html; charset=UTF-8";
        }

        if (nombre.endsWith(".css")) {
            return "text/css; charset=UTF-8";
        }

        if (nombre.endsWith(".js")) {
            return "application/javascript; charset=UTF-8";
        }

        return "application/octet-stream";
    }

    private static void artistas(HttpExchange exchange) throws IOException {

        String metodo = exchange.getRequestMethod();

        Integer id = obtenerId(
                exchange.getRequestURI().getPath(),
                "/api/artistas"
        );

        try {

            if (metodo.equals("GET")) {

                if (id == null) {
                    responder(
                            exchange,
                            gson.toJson(gestorWeb.listarArtistas()),
                            200
                    );
                } else {
                    responder(
                            exchange,
                            gson.toJson(gestorWeb.buscarArtista(id)),
                            200
                    );
                }

            } else if (metodo.equals("POST")) {

                Artista artista = gson.fromJson(
                        leerBody(exchange),
                        Artista.class
                );

                responderOperacion(exchange, gestorWeb.guardarArtistaDetallado(artista));

            } else if (metodo.equals("PUT")) {

                Artista artista = gson.fromJson(
                        leerBody(exchange),
                        Artista.class
                );

                responderOperacion(exchange, gestorWeb.modificarArtistaDetallado(artista));

            } else if (metodo.equals("DELETE") && id != null) {

                responderOperacion(exchange, gestorWeb.eliminarArtistaDetallado(id));

            } else {

                responder(exchange, "Método no permitido", 405);
            }

        } catch (Exception e) {

            e.printStackTrace();

            responderError(exchange, e, 500);
        }
    }

    private static void escenarios(HttpExchange exchange) throws IOException {

        String metodo = exchange.getRequestMethod();

        Integer id = obtenerId(
                exchange.getRequestURI().getPath(),
                "/api/escenarios"
        );

        try {

            if (metodo.equals("GET")) {

                if (id == null) {
                    responder(
                            exchange,
                            gson.toJson(gestorWeb.listarEscenarios()),
                            200
                    );
                } else {
                    responder(
                            exchange,
                            gson.toJson(gestorWeb.buscarEscenario(id)),
                            200
                    );
                }

            } else if (metodo.equals("POST")) {

                Escenario escenario = gson.fromJson(
                        leerBody(exchange),
                        Escenario.class
                );

                responderOperacion(exchange, gestorWeb.guardarEscenarioDetallado(escenario));

            } else if (metodo.equals("PUT")) {

                Escenario escenario = gson.fromJson(
                        leerBody(exchange),
                        Escenario.class
                );

                responderOperacion(exchange, gestorWeb.modificarEscenarioDetallado(escenario));

            } else if (metodo.equals("DELETE") && id != null) {

                responderOperacion(exchange, gestorWeb.eliminarEscenarioDetallado(id));

            } else {

                responder(exchange, "Método no permitido", 405);
            }

        } catch (Exception e) {

            e.printStackTrace();

            responderError(exchange, e, 500);
        }
    }

    private static void actuaciones(HttpExchange exchange) throws IOException {

        String metodo = exchange.getRequestMethod();

        Integer id = obtenerId(
                exchange.getRequestURI().getPath(),
                "/api/actuaciones"
        );

        try {

            if (metodo.equals("GET")) {

                if (id == null) {
                    responder(
                            exchange,
                            gson.toJson(gestorWeb.listarActuaciones()),
                            200
                    );
                } else {
                    responder(
                            exchange,
                            gson.toJson(gestorWeb.buscarActuacion(id)),
                            200
                    );
                }

            } else if (metodo.equals("POST")) {

                Actuacion actuacion = gson.fromJson(
                        leerBody(exchange),
                        Actuacion.class
                );

                responderOperacion(exchange, gestorWeb.guardarActuacionDetallado(actuacion));

            } else if (metodo.equals("PUT")) {

                Actuacion actuacion = gson.fromJson(
                        leerBody(exchange),
                        Actuacion.class
                );

                responderOperacion(exchange, gestorWeb.modificarActuacionDetallado(actuacion));

            } else if (metodo.equals("DELETE") && id != null) {

                responderOperacion(exchange, gestorWeb.eliminarActuacionDetallado(id));

            } else {

                responder(exchange, "Método no permitido", 405);
            }

        } catch (Exception e) {

            e.printStackTrace();

            responderError(exchange, e, 500);
        }
    }

    private static void espectadores(HttpExchange exchange) throws IOException {

        String metodo = exchange.getRequestMethod();

        Integer id = obtenerId(
                exchange.getRequestURI().getPath(),
                "/api/espectadores"
        );

        try {

            if (metodo.equals("GET")) {

                if (id == null) {
                    responder(
                            exchange,
                            gson.toJson(gestorWeb.listarEspectadores()),
                            200
                    );
                } else {
                    responder(
                            exchange,
                            gson.toJson(gestorWeb.buscarEspectador(id)),
                            200
                    );
                }

            } else if (metodo.equals("POST")) {

                Espectador espectador = gson.fromJson(
                        leerBody(exchange),
                        Espectador.class
                );

                responderOperacion(exchange, gestorWeb.guardarEspectadorDetallado(espectador));

            } else if (metodo.equals("PUT")) {

                Espectador espectador = gson.fromJson(
                        leerBody(exchange),
                        Espectador.class
                );

                responderOperacion(exchange, gestorWeb.modificarEspectadorDetallado(espectador));

            } else if (metodo.equals("DELETE") && id != null) {

                responderOperacion(exchange, gestorWeb.eliminarEspectadorDetallado(id));

            } else {

                responder(exchange, "Método no permitido", 405);
            }

        } catch (Exception e) {

            e.printStackTrace();

            responderError(exchange, e, 500);
        }
    }

    private static void entradas(HttpExchange exchange) throws IOException {

        String metodo = exchange.getRequestMethod();

        Integer id = obtenerId(
                exchange.getRequestURI().getPath(),
                "/api/entradas"
        );

        try {

            if (metodo.equals("GET")) {

                if (id == null) {
                    responder(
                            exchange,
                            gson.toJson(gestorWeb.listarEntradas()),
                            200
                    );
                } else {
                    responder(
                            exchange,
                            gson.toJson(gestorWeb.buscarEntrada(id)),
                            200
                    );
                }

            } else if (metodo.equals("POST")) {

                Entrada entrada = gson.fromJson(
                        leerBody(exchange),
                        Entrada.class
                );

                responderOperacion(exchange, gestorWeb.guardarEntradaDetallado(entrada));

            } else if (metodo.equals("PUT")) {

                Entrada entrada = gson.fromJson(
                        leerBody(exchange),
                        Entrada.class
                );

                responderOperacion(exchange, gestorWeb.modificarEntradaDetallado(entrada));

            } else if (metodo.equals("DELETE") && id != null) {

                responderOperacion(exchange, gestorWeb.eliminarEntradaDetallado(id));

            } else {

                responder(exchange, "Método no permitido", 405);
            }

        } catch (Exception e) {

            e.printStackTrace();

            responderError(exchange, e, 500);
        }
    }

    private static void archivos(HttpExchange exchange) throws IOException {

        String[] partes = exchange.getRequestURI().getPath().split("/");

        if (partes.length < 5) {
            responder(exchange, "Ruta incorrecta", 400);
            return;
        }

        String tipo = partes[3];
        String formato = partes[4];
        String accion = partes.length > 5 ? partes[5] : "";

        if (accion.equals("generar")) {

            try {

                if (formato.equals("json")) {
                    generarJSON(tipo);
                } else if (formato.equals("xml")) {
                    generarXML(tipo);
                } else {
                    responder(exchange, "Formato no válido", 400);
                    return;
                }

                responder(exchange, "true", 200);

            } catch (Exception e) {

                e.printStackTrace();

                responderError(exchange, e, 500);
            }

            return;
        }

        if (accion.equals("descargar")) {
            descargarArchivo(exchange, tipo, formato);
            return;
        }

        responder(exchange, "Ruta incorrecta", 400);
    }

    private static void generarJSON(String tipo) {

        if (tipo.equals("artistas")) {
            gestorWeb.generarArtistasJSON();
        } else if (tipo.equals("escenarios")) {
            gestorWeb.generarEscenariosJSON();
        } else if (tipo.equals("actuaciones")) {
            gestorWeb.generarActuacionesJSON();
        } else if (tipo.equals("espectadores")) {
            gestorWeb.generarEspectadoresJSON();
        } else if (tipo.equals("entradas")) {
            gestorWeb.generarEntradasJSON();
        } else {
            throw new IllegalArgumentException("Tipo no válido");
        }
    }

    private static void generarXML(String tipo) {

        if (tipo.equals("artistas")) {
            gestorWeb.generarArtistasXML();
        } else if (tipo.equals("escenarios")) {
            gestorWeb.generarEscenariosXML();
        } else if (tipo.equals("actuaciones")) {
            gestorWeb.generarActuacionesXML();
        } else if (tipo.equals("espectadores")) {
            gestorWeb.generarEspectadoresXML();
        } else if (tipo.equals("entradas")) {
            gestorWeb.generarEntradasXML();
        } else {
            throw new IllegalArgumentException("Tipo no válido");
        }
    }

    private static void descargarArchivo(
            HttpExchange exchange,
            String tipo,
            String formato
    ) throws IOException {

        if (!formato.equals("json") && !formato.equals("xml")) {
            responder(exchange, "Formato no válido", 400);
            return;
        }

        File archivo = new File(
                "data/" + tipo + "." + formato
        );

        if (!archivo.exists()) {

            responder(
                    exchange,
                    "El archivo no existe. Genera primero el archivo.",
                    404
            );

            return;
        }

        byte[] datos = Files.readAllBytes(archivo.toPath());

        exchange.getResponseHeaders().set(
                "Content-Type",
                "application/octet-stream"
        );

        exchange.getResponseHeaders().set(
                "Content-Disposition",
                "attachment; filename=\"" + archivo.getName() + "\""
        );

        exchange.sendResponseHeaders(200, datos.length);

        try (OutputStream salida = exchange.getResponseBody()) {
            salida.write(datos);
        }
    }

    private static void esquema(HttpExchange exchange) throws IOException {

        String[] partes = exchange.getRequestURI()
                .getPath()
                .split("/");

        if (partes.length < 4) {

            responder(
                    exchange,
                    "Tipo no indicado",
                    400
            );

            return;
        }

        String tipo = partes[3];

        Class<?> clase;

        if (tipo.equals("artistas")) {
            clase = Artista.class;
        } else if (tipo.equals("escenarios")) {
            clase = Escenario.class;
        } else if (tipo.equals("actuaciones")) {
            clase = Actuacion.class;
        } else if (tipo.equals("espectadores")) {
            clase = Espectador.class;
        } else if (tipo.equals("entradas")) {
            clase = Entrada.class;
        } else {

            responder(
                    exchange,
                    "Tipo no válido",
                    400
            );

            return;
        }

        Map<String, String> campos = new LinkedHashMap<>();

        for (Field campo : clase.getDeclaredFields()) {

            if (!campo.getName().equals("serialVersionUID")) {

                campos.put(
                        campo.getName(),
                        campo.getType().getSimpleName()
                );
            }
        }

        responder(
                exchange,
                gson.toJson(campos),
                200
        );
    }

    private static String leerBody(
            HttpExchange exchange
    ) throws IOException {

        try (InputStream entrada = exchange.getRequestBody()) {

            return new String(
                    entrada.readAllBytes(),
                    StandardCharsets.UTF_8
            );
        }
    }

    private static Integer obtenerId(
            String ruta,
            String base
    ) {

        if (!ruta.equals(base) && ruta.startsWith(base + "/")) {

            try {

                return Integer.parseInt(
                        ruta.substring(
                                (base + "/").length()
                        )
                );

            } catch (NumberFormatException e) {

                return null;
            }
        }

        return null;
    }

    private static void responderOperacion(
            HttpExchange exchange,
            GestorWeb.ResultadoOperacion resultado
    ) throws IOException {
        responder(
                exchange,
                gson.toJson(resultado),
                resultado.esExito() ? 200 : 400
        );
    }

    private static void responderError(
            HttpExchange exchange,
            Exception error,
            int codigo
    ) throws IOException {
        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("exito", false);
        respuesta.put(
                "mensaje",
                error.getMessage() == null
                        ? error.getClass().getSimpleName()
                        : error.getMessage()
        );
        responder(exchange, gson.toJson(respuesta), codigo);
    }

    private static void responder(
            HttpExchange exchange,
            String respuesta,
            int codigo
    ) throws IOException {

        byte[] datos = respuesta.getBytes(
                StandardCharsets.UTF_8
        );

        exchange.getResponseHeaders().set(
                "Content-Type",
                "application/json; charset=UTF-8"
        );

        exchange.getResponseHeaders().set(
                "Access-Control-Allow-Origin",
                "*"
        );

        exchange.sendResponseHeaders(
                codigo,
                datos.length
        );

        try (OutputStream salida = exchange.getResponseBody()) {
            salida.write(datos);
        }
    }
}