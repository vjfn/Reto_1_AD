package org.example;

import java.util.Map;

import static org.example.CombinarCorrespondencia.*;

/**
 *Este es el Main que llevará a cabo la combinación de correspondencia.
 *En el caso de quefalten archivos o de que uno esté vació nos lo indicará por pantalla
 *Si hay un marcador inválido, el método combinarCorrespondencia no sería capaz de hacer el reemplazo
 *quedando esa parte de la template sin modificar
 *Si faltan datos en el csv, el archivo generado será template-DATOS en csv Insuficientes.txt
 */
public class Main {
    public static void main(String[] args) {
        try {
            // Intenta cargar los datos de los clientes y la plantilla.
            Map<String, Cliente> clientes = cargarClientes();
            String plantilla = cargarPlantilla();

            if (clientes.isEmpty() || plantilla.isEmpty()) {
                // Si alguno de los archivos necesarios está vacío, muestra un mensaje de error para su correción manual.
                System.out.println("Uno de los archivos necesarios está vacío. Verifique que el contenido de ListaClientes.csv y template.txt es el correcto.");
            } else {
                // Si ambos archivos se cargaron correctamente y no están vacíos, procede con la combinación de correspondencia.
                vaciarCarpetaSalida(); //Primero vacía la carpeta de salida
                combinarCorrespondencia(clientes, plantilla); // combina la correspondencia y genera nuevos archivos
                System.out.println("Combinación de correspondencia completada.");
            }
        } catch (ArchivosFaltantesException e) {
            // Captura una excepción si falta alguno de los archivos necesarios.
            System.out.println(e.getMessage());
            System.out.println("Por favor, verifique los archivos faltantes antes de volver a ejecutar el programa.");
        }
    }
}