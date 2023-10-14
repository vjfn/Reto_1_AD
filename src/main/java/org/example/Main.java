package org.example;

import java.util.Map;

import static org.example.CombinarCorrespondencia.*;


public class Main {
    public static void main(String[] args) {
        try {
            Map<String, Cliente> clientes = cargarClientes();
            String plantilla = cargarPlantilla();

            if (clientes.isEmpty() || plantilla.isEmpty()) {
                System.out.println("Uno de los archivos necesarios está vacío. Verifique que el contenido de ListaClientes.csv y template.txt es el correcto.");
            } else {
                vaciarCarpetaSalida();
                combinarCorrespondencia(clientes, plantilla);
                System.out.println("Combinación de correspondencia completada.");
            }
        } catch (ArchivosFaltantesException e) {
            System.out.println(e.getMessage());
            System.out.println("Por favor, verifique los archivos faltantes antes de volver a ejecutar el programa.");
        }
    }

    //En el caso de quefalten archivos o de que uno esté vació nos lo indicará por pantalla

    //Si hay un marcador inválido, el método combinarCorrespondencia no sería capaz de hacer el reemplazo
    //quedando esa parte de la template sin modificar

    //Si faltan datos en el csv, el archivo generado será template-DATOS en csv Insuficientes.txt

}