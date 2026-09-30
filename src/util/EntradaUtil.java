
package util;

import java.util.Scanner;

public class EntradaUtil {

    private static final Scanner scanner = new Scanner(System.in);


    // LEER TEXTO
    public static String leerTexto(String mensaje) {

        while (true) {

            System.out.print(mensaje);

            String texto = scanner.nextLine().trim();

            if (!texto.isEmpty()) {
                return texto;
            }

            System.out.println("El campo no puede estar vacío.");
        }
    }


    // LEER ENTERO
    public static int leerEntero(String mensaje) {

        while (true) {

            System.out.print(mensaje);

            String texto = scanner.nextLine().trim();

            try {

                return Integer.parseInt(texto);

            } catch (NumberFormatException e) {

                System.out.println(
                        "Error: debes introducir un número entero."
                );
            }
        }
    }


    // LEER ENTERO POSITIVO
    public static int leerEnteroPositivo(String mensaje) {

        while (true) {

            int numero = leerEntero(mensaje);

            if (numero > 0) {
                return numero;
            }

            System.out.println(
                    "El número debe ser mayor que 0."
            );
        }
    }


    // LEER DECIMAL
    public static double leerDecimal(String mensaje) {

        while (true) {

            System.out.print(mensaje);

            String texto = scanner.nextLine().trim();

            try {

                return Double.parseDouble(texto.replace(",", "."));

            } catch (NumberFormatException e) {

                System.out.println(
                        "Error: debes introducir un número válido."
                );
            }
        }
    }


    // LEER DECIMAL NO NEGATIVO
    public static double leerDecimalNoNegativo(String mensaje) {

        while (true) {

            double numero = leerDecimal(mensaje);

            if (numero >= 0) {
                return numero;
            }

            System.out.println(
                    "El número no puede ser negativo."
            );
        }
    }


    // LEER EMAIL
    public static String leerEmail(String mensaje) {

        while (true) {

            String email = leerTexto(mensaje);

            if (email.matches(
                    "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
            )) {

                return email;
            }

            System.out.println(
                    "Error: introduce un email válido."
            );
        }
    }


    // LEER EDAD
    public static int leerEdad(String mensaje) {

        while (true) {

            int edad = leerEntero(mensaje);

            if (edad >= 0 && edad <= 120) {
                return edad;
            }

            System.out.println(
                    "La edad debe estar entre 0 y 120."
            );
        }
    }


    // LEER FECHA
    public static java.time.LocalDate leerFecha(String mensaje) {

        while (true) {

            System.out.print(mensaje);

            String texto = scanner.nextLine().trim();

            try {

                return java.time.LocalDate.parse(texto);

            } catch (java.time.format.DateTimeParseException e) {

                System.out.println(
                        "Error: introduce la fecha con formato AAAA-MM-DD."
                );
            }
        }
    }


    // LEER HORA
    public static String leerHora(String mensaje) {

        while (true) {

            System.out.print(mensaje);

            String texto = scanner.nextLine().trim();

            try {

                java.time.LocalTime.parse(texto);

                return texto;

            } catch (java.time.format.DateTimeParseException e) {

                System.out.println(
                        "Error: introduce la hora con formato HH:MM."
                );
            }
        }
    }


    // LEER BOOLEANO
    public static boolean leerBooleano(String mensaje) {

        while (true) {

            System.out.print(mensaje + " (s/n): ");

            String respuesta = scanner.nextLine().trim().toLowerCase();

            if (respuesta.equals("s")) {
                return true;
            }

            if (respuesta.equals("n")) {
                return false;
            }

            System.out.println(
                    "Error: debes introducir s o n."
            );
        }
    }
}

