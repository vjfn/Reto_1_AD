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
        // Crea un mapa para almacenar los datos de los clientes (clave: código de cliente, valor: objeto Cliente).
        Map<String, Cliente> clientes = new HashMap<>();
        try {
            // Abre y lee el archivo ListaClientes.csv en la carpeta resources.
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/ListaClientes.csv"));
            String line;
            // Lee cada línea del archivo CSV mientras que no este vacia
            while ((line = br.readLine()) != null) {
                // Divide la línea en partes utilizando el punto y coma como separador.
                String[] datos = line.split(";");
                // Valor predeterminado de codigo si no hay suficientes datos, en este caso espera 5 elementos.
                String codigo = "DATOS en csv Insuficientes";
                // Comprueba si la línea contiene al menos 5 elementos.
                if (datos.length >= 5) {
                    // Si hay suficientes datos, asigna los valores a las variables correspondientes.
                    codigo = datos[0];
                    String nombreEmpresa = datos[1];
                    String localidad = datos[2];
                    String correo = datos[3];
                    String nombreResponsable = datos[4];
                    // Agrega un nuevo objeto Cliente al mapa utilizando el código de cliente como clave.
                    clientes.put(codigo, new Cliente(codigo, nombreEmpresa, localidad, correo, nombreResponsable));
                } else {
                    // Si no hay suficientes datos, agrega un objeto Cliente con valores de "ERROR" al mapa.
                    clientes.put(codigo, new Cliente("ERROR", "ERROR", "ERROR", "ERROR", "ERROR"));
                }
            }
            // Cierra el archivo después de leerlo.
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
            // Si se produce una excepción IOException, lanza una excepción ArchivosFaltantesException
            // indicando que falta el archivo ListaClientes.csv en la carpeta resources.
            throw new ArchivosFaltantesException("Falta el archivo template.txt en la carpeta resources");
        }
        // Devuelve el mapa que contiene los datos de los clientes.
        return contenido.toString();
    }
    /**
     * Combina la template para cada cliente y genera archivos de salida en la carpeta "salida".
     */
    public static void combinarCorrespondencia(Map<String, Cliente> clientes, String plantilla) {
        // Recorre el mapa de clientes, donde la clave es el código de cliente y el valor es un objeto Cliente.
        for (Map.Entry<String, Cliente> entry : clientes.entrySet()) {
            String codigo = entry.getKey(); // Obtiene el código de cliente.
            Cliente cliente = entry.getValue(); // Obtiene el objeto Cliente.

            // Reemplaza los marcadores en la plantilla con los datos del cliente.
            String contenido = plantilla
                    .replace("%%1%%", codigo)
                    .replace("%%2%%", cliente.getNombreEmpresa())
                    .replace("%%3%%", cliente.getLocalidad())
                    .replace("%%4%%", cliente.getCorreo())
                    .replace("%%5%%", cliente.getNombreResponsable());
            // Ponemos el nombre del archivo de salida personalizado para cada cliente.
            String nombreArchivo = "src/main/resources/salida/template-" + codigo + ".txt";
            // Crea un FileWriter para escribir el contenido en el archivo de salida y su contenido.
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
