/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.util.ArrayList;
import java.util.List;
import main.Pregunta;

/**
 *
 * @author admin
 */
public class Utils {
    
    // La lista de preguntas que será gestionada por el singleton
    private List<Pregunta> listaPreguntas = new ArrayList<>();

    // Instancia estática de la clase Utils
    private static Utils instance;

    // Constructor privado para evitar la creación directa de instancias
    private Utils() {
        // Puedes inicializar la lista de preguntas aquí si es necesario
    }

    // Método estático para obtener la instancia del Singleton
    public static Utils getInstance() {
        if (instance == null) {
            instance = new Utils();
        }
        return instance;
    }
    
    
    public static String formatearNombreArchivo(String nombreArchivo) {
        // Eliminar la extensión .csv
        String nombreSinExtension = nombreArchivo.replace(".csv", "");

        // Reemplazar guiones bajos por espacios
        String nombreFormateado = nombreSinExtension.replace("_", " ");

        // Capitalizar las primeras letras de cada palabra
        String[] palabras = nombreFormateado.split(" ");
        StringBuilder nombreFinal = new StringBuilder();
        for (String palabra : palabras) {
            if (!palabra.isEmpty()) {
                nombreFinal.append(palabra.substring(0, 1).toUpperCase())
                           .append(palabra.substring(1).toLowerCase())
                           .append(" ");
            }
        }

        return nombreFinal.toString().trim();
    }

   
}
