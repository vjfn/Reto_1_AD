package org.example;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CombinarCorrespondencia {
    public static void main(String[] args) {
        Map<String, Cliente> clientes = cargarClientes();
        String plantilla = cargarPlantilla();

        vaciarCarpetaSalida();

        combinarCorrespondencia(clientes, plantilla);

        System.out.println("Combinación de correspondencia completada.");
    }

    public static Map<String, Cliente> cargarClientes() {
        Map<String, Cliente> clientes = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/ListaClientes.csv"))) {
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
                    clientes.put(codigo, new Cliente(codigo,nombreEmpresa, localidad, correo, nombreResponsable));
                } else {
                    clientes.put(codigo, new Cliente("ERROR", "ERROR", "ERROR", "ERROR","ERROR"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return clientes;
    }

    public static String cargarPlantilla() {
        StringBuilder contenido = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/template.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                contenido.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contenido.toString();
    }

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

    public static void vaciarCarpetaSalida() {
        File carpetaSalida = new File("src/main/resources/salida");
        if (carpetaSalida.exists()) {
            File[] archivos = carpetaSalida.listFiles();
            for (File archivo : archivos) {
                archivo.delete();
            }
        }
    }
}
