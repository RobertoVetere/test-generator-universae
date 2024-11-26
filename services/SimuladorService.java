package services;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import main.Pregunta;
import main.SimuladorTipo;
import utils.Utils;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author admin
 */
public class SimuladorService {
    
    public List<SimuladorTipo> simuladoresList = new ArrayList<>();
    
    List<File> archivosCSV = new ArrayList<>();

    
    public void cargarSimuladores(){
        cargarArchivosCSV("build/classes/repositories/");
        crearListaDeSimuladores();
        
        imprimirArchivosCSV();
    }
    
    
 private void cargarArchivosCSV(String directorio) {
        // Crear el objeto File que representa el directorio
        File dir = new File(directorio);
        
        System.out.println("Buscando archivos CSV en el directorio: " + dir.getAbsolutePath());

        
        // Verificar que el directorio existe y es un directorio
        if (dir.exists() && dir.isDirectory()) {
            // Obtener todos los archivos en el directorio que terminan en .csv
            File[] archivos = dir.listFiles((file, name) -> name.toLowerCase().endsWith(".csv"));
            
            // Si hay archivos CSV, agregarlos a la lista
            if (archivos != null) {
                for (File archivo : archivos) {
                    archivosCSV.add(archivo); // Agregar el archivo a la lista privada
                }
            }
        }
    }
    
 
 private void imprimirArchivosCSV() {
        if (archivosCSV.isEmpty()) {
            System.out.println("No se encontraron archivos CSV en el directorio.");
        } else {
            System.out.println("Archivos CSV cargados:");
            for (File archivo : archivosCSV) {
                System.out.println(archivo.getName()); // Imprimir el nombre del archivo
            }
        }
    }

    private void crearListaDeSimuladores() {
        for (File archivo : archivosCSV) {
            try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = reader.readLine()) != null) {
                    String[] partes = linea.split(";");

                    if (partes.length == 5) {
                        String preguntaTexto = partes[0];
                        String respuestaCorrecta = partes[1];
                        String[] respuestasIncorrectas = new String[]{partes[2], partes[3], partes[4]};

                        Pregunta pregunta = new Pregunta(preguntaTexto, respuestaCorrecta, respuestasIncorrectas);

                        // Crear el simulador y asignar el nombre del archivo como simuladorRuta
                        SimuladorTipo simulador = new SimuladorTipo();
                        simulador.setsimuladorRuta(Utils.formatearNombreArchivo(archivo.getName())); // Asignar el nombre formateado
                        simulador.agregarPregunta(pregunta);

                        simuladoresList.add(simulador);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}
