package services;

import interfaces.ISimuladorService;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.JFileChooser;
import models.Pregunta;
import models.SimuladorTipo;
import utils.Utils;
import static utils.Utils.mostrarMensaje;

public class SimuladorService implements ISimuladorService {

    private static SimuladorService instance;
    private SimuladorTipo simuladorSeleccionado;
    private final List<SimuladorTipo> simuladoresList = new ArrayList<>();

    // Constructor privado para evitar instanciación directa
    private SimuladorService() {
    }

    // Método estático para obtener la única instancia de la clase
    public static SimuladorService getInstance() {
        if (instance == null) {
            instance = new SimuladorService();
        }
        return instance;
    }

    public void cargarSimuladores() {
        // Cargar archivos CSV desde el directorio especificado
        List<File> archivosCSV = cargarArchivosCSV("build/classes/repositories/");
        // Crear simuladores con base en los archivos cargados
        crearListaDeSimuladores(archivosCSV);
        // Imprimir los archivos cargados
        imprimirArchivosCSV(archivosCSV);
    }

    private List<File> cargarArchivosCSV(String directorio) {
        List<File> archivosCSV = new ArrayList<>();
        File dir = new File(directorio);

        //System.out.println("Buscando archivos CSV en el directorio: " + dir.getAbsolutePath());

        if (dir.exists() && dir.isDirectory()) {
            File[] archivos = dir.listFiles((file, name) -> name.toLowerCase().endsWith(".csv"));
            if (archivos != null) {
                for (File archivo : archivos) {
                    archivosCSV.add(archivo);
                }
            }
        }
        return archivosCSV;
    }

    private void imprimirArchivosCSV(List<File> archivosCSV) {
        if (archivosCSV.isEmpty()) {
            System.out.println("Mensaje desde SimuladorService: No se encontraron archivos CSV en el directorio.");
        } else {
            System.out.println("Mensaje desde SimuladorService:  Archivos CSV cargados:");
            for (File archivo : archivosCSV) {
                System.out.println("Mensaje desde SimuladorService: CSV" + archivo.getName());
            }
        }
    }

   private void crearListaDeSimuladores(List<File> archivosCSV) {
    simuladoresList.clear(); // Limpiar lista para evitar duplicados si se llama varias veces
    for (File archivo : archivosCSV) {
        SimuladorTipo simulador = new SimuladorTipo(); // Crear un nuevo simulador para cada archivo
        simulador.setName(Utils.formatearNombreArchivo(archivo.getName()));
        simulador.setsimuladorRuta(archivo.getName()); // Asignar el nombre formateado

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(";");

                if (partes.length == 5) {
                    String preguntaTexto = partes[0];
                    String respuestaCorrecta = partes[1];
                    String[] respuestasIncorrectas = new String[]{partes[2], partes[3], partes[4]};

                    Pregunta pregunta = new Pregunta(preguntaTexto, respuestaCorrecta, respuestasIncorrectas);
                    simulador.agregarPregunta(pregunta); // Agregar pregunta al simulador actual
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Agregar el simulador a la lista sin verificar si tiene preguntas
        simuladoresList.add(simulador); // Ahora se agregan todos los simuladores, sin importar si tienen preguntas

        // Mensaje para indicar si el archivo estaba vacío o no
        if (simulador.getListaPreguntas().isEmpty()) {
            System.out.println("Mensaje desde SimuladorService:  El archivo " + archivo.getName() + " está vacío.");
        }
    }
}


    public List<SimuladorTipo> getSimuladoresList() {
        if (simuladoresList.isEmpty()) {
            System.out.println("Mensaje desde SimuladorService:  La lista de simuladores está vacía. ¿Llamaste a cargarSimuladores()?");
        }
        return simuladoresList;
    }
    
    public SimuladorTipo obtenerSimuladorPorNombre(String name) {
        for (SimuladorTipo simulador : simuladoresList) {
            if (simulador.getName().equals(name)) {
                return simulador;  // Si encuentra el simulador, lo retorna
            }
        }
        return null;  // Si no encuentra el simulador, retorna null
    }
    
     public void comprimirSimuladorCSV(String nombreSimulador, Component parentComponent) {
        // Reutilizamos el método obtenerSimuladorPorNombre para encontrar el simulador
        SimuladorTipo simulador = obtenerSimuladorPorNombre(nombreSimulador);
        if (simulador != null) {
            // Ruta del archivo CSV original
            File archivoCSV = new File("build/classes/repositories/" + simulador.getsimuladorRuta());
            if (archivoCSV.exists()) {
                // Pedir al usuario que elija la ubicación para guardar el archivo ZIP
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Guardar archivo ZIP");
                fileChooser.setSelectedFile(new File(simulador.getName() + ".zip"));
                fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos ZIP", "zip"));

                int resultado = fileChooser.showSaveDialog(null);
                if (resultado == JFileChooser.APPROVE_OPTION) {
                    // Obtener la ruta seleccionada por el usuario
                    File archivoZip = fileChooser.getSelectedFile();

                    // Comprimir el archivo CSV en un archivo ZIP
                    try (FileInputStream fis = new FileInputStream(archivoCSV);
                         FileOutputStream fos = new FileOutputStream(archivoZip);
                         ZipOutputStream zipOut = new ZipOutputStream(fos)) {

                        // Crear una entrada en el archivo ZIP para el archivo CSV
                        ZipEntry zipEntry = new ZipEntry(archivoCSV.getName());
                        zipOut.putNextEntry(zipEntry);

                        // Leer el archivo CSV y escribirlo en el archivo ZIP
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = fis.read(buffer)) > 0) {
                            zipOut.write(buffer, 0, length);
                        }

                        zipOut.closeEntry(); // Cerrar la entrada del archivo ZIP

                        // Usamos mostrarMensaje para mostrar el resultado
                        mostrarMensaje(parentComponent, "information", "Éxito", "El archivo se comprimió correctamente en: " + archivoZip.getAbsolutePath());

                    } catch (IOException e) {
                        e.printStackTrace();
                        // Usamos mostrarMensaje para mostrar el error
                        mostrarMensaje(parentComponent, "error", "Error", "Error al comprimir el archivo CSV: " + e.getMessage());
                    }
                }
            } else {
                // Usamos mostrarMensaje para mostrar el error si no se encuentra el archivo
                mostrarMensaje(parentComponent, "error", "Error", "El archivo CSV no existe: " + archivoCSV.getAbsolutePath());
            }
        } else {
            // Usamos mostrarMensaje para mostrar el error si no se encuentra el simulador
            mostrarMensaje(parentComponent, "error", "Error", "Simulador no encontrado: " + nombreSimulador);
        }
    }
    
    
    public void actualizarCSV(String name) {
        SimuladorTipo simulador = obtenerSimuladorPorNombre(name); // Buscar simulador por nombre
        if (simulador != null) {
            File archivoCSV = new File("build/classes/repositories/" + simulador.getsimuladorRuta()); // Ruta del archivo CSV
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoCSV))) {
                // Escribir los datos del simulador de nuevo en el archivo CSV
                for (Pregunta pregunta : simulador.getListaPreguntas()) {
                    // Formatear cada pregunta y respuesta en formato CSV
                    String linea = pregunta.getPregunta() + ";" 
                        + pregunta.getRespuestaCorrecta() + ";" 
                        + pregunta.getRespuestasIncorrectas()[0] + ";" 
                        + pregunta.getRespuestasIncorrectas()[1] + ";" 
                        + pregunta.getRespuestasIncorrectas()[2];
                    writer.write(linea);
                    writer.newLine(); // Nueva línea para cada pregunta
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error al actualizar el archivo CSV.");
            }
        } else {
            System.out.println("Simulador con el nombre '" + name + "' no encontrado.");
        }
    }
    
    public int tieneXPreguntas(String name) {
        SimuladorTipo simulador = obtenerSimuladorPorNombre(name); // Buscar simulador por nombre
        
            List<Pregunta> preguntas = simulador.getListaPreguntas();
            return preguntas.size();
    }
    
    public SimuladorTipo getSimuladorSeleccionado() {
        return simuladorSeleccionado;
    }

    public void setSimuladorSeleccionado(SimuladorTipo simuladorSeleccionado) {
        this.simuladorSeleccionado = simuladorSeleccionado;
    }
    
    
    

}
