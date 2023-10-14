package org.example;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * cargarClientes es la clase que realiza la combinación de correspondencia y genera los archivos en salida.
 */
public class CombinarCorrespondencia {

    /**
     * Carga los datos de los clientes desde el archivo ListaClientes.csv y los almacena en un mapa.
     * Si falta el archivo, se lanza una excepción.
     */
    public static Map<String, Cliente> cargarClientes() throws ArchivosFaltantesException {
        Map<String, Cliente> clientes = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/ListaClientes.csv"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] datos = line.split(";");

                String codigo = "DATOS en csv Insuficientes";
                if (datos.length >= 5) {
                    codigo = datos[0];
                    String nombreEmpresa = datos[1];
                    String localidad = datos[2];
                    String correo = datos[3];
                    String nombreResponsable = datos[4];
                    clientes.put(codigo, new Cliente(codigo, nombreEmpresa, localidad, correo, nombreResponsable));
                } else {
                    clientes.put(codigo, new Cliente("ERROR", "ERROR", "ERROR", "ERROR", "ERROR"));
                }
            }
            br.close();
        } catch (IOException e) {
            throw new ArchivosFaltantesException("Falta el archivo ListaClientes.csv en la carpeta resources");
        }
        return clientes;
    }
    /**
     * Lee y carga el contenido de la plantilla desde el archivo template.txt.
     * Si falta el archivo, se lanza una excepción.
     */
    public static String cargarPlantilla() throws ArchivosFaltantesException {
        StringBuilder contenido = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/template.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                contenido.append(line).append("\n");
            }
            br.close();
        } catch (IOException e) {
            throw new ArchivosFaltantesException("Falta el archivo template.txt en la carpeta resources");
        }
        return contenido.toString();
    }
    /**
     * Combina la correspondencia para cada cliente y genera archivos de salida en la carpeta "salida".
     */
    public static void combinarCorrespondencia(Map<String, Cliente> clientes, String plantilla) {
        for (Map.Entry<String, Cliente> entry : clientes.entrySet()) {
            String codigo = entry.getKey();
            Cliente cliente = entry.getValue();

            String contenido = plantilla
                    .replace("%%1%%", codigo)
                    .replace("%%2%%", cliente.getNombreEmpresa())
                    .replace("%%3%%", cliente.getLocalidad())
                    .replace("%%4%%", cliente.getCorreo())
                    .replace("%%5%%", cliente.getNombreResponsable());

            String nombreArchivo = "src/main/resources/salida/template-" + codigo + ".txt";

            try (FileWriter writer = new FileWriter(nombreArchivo)) {
                writer.write(contenido);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Este método vacía la carpeta de salida de archivos.
     */
    public static void vaciarCarpetaSalida() {
        File carpetaSalida = new File("src/main/resources/salida");
        if (carpetaSalida.exists()) {
            File[] archivos = carpetaSalida.listFiles();
            for (File archivo : archivos) {
                archivo.delete();
            }
        }
    }
    /**
     * Esta es la excepción que utilizamos para indicar que faltan los archivos necesarios.
     */
    static class ArchivosFaltantesException extends Exception {
        public ArchivosFaltantesException(String mensaje) {
            super(mensaje);
        }
    }
}
