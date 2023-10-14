package org.example;

import java.util.Map;

import static org.example.CombinarCorrespondencia.*;

public class Main {
    public static void main(String[] args) {
        Map<String, Cliente> clientes = cargarClientes();
        String plantilla = cargarPlantilla();

        vaciarCarpetaSalida();

        combinarCorrespondencia(clientes, plantilla);

        System.out.println("Combinación de correspondencia completada.");
    }

    //Si hay un marcador inválido, el método combinarCorrespondencia no sería capaz de hacer el reemplazo
    //quedando esa parte de la template sin modificar

    //Si faltan datos en el csv, el archivo generado será template-DATOS en csv Insuficientes.txt

}