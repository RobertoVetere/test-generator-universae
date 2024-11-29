/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import models.Pregunta;

/**
 *
 * @author admin
 */
public class Utils {
    
    private static JFrame mainFrame;
    private static AtomicBoolean isLoaded = new AtomicBoolean(false);

    // Establece el JFrame principal
    public static void setMainFrame(JFrame frame) {
        mainFrame = frame;
    }

    // Obtiene el JFrame principal
    public static JFrame getMainFrame() {
        if (mainFrame == null) {
            throw new IllegalStateException("El JFrame principal no ha sido configurado.");
        }
        return mainFrame;
    }

    
    
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
    
    public static void loader() {
        if (mainFrame == null) {
            throw new IllegalStateException("El JFrame principal no ha sido configurado.");
        }

        // Crear un panel overlay para el loader
        JPanel loaderPanel = new JPanel();
        loaderPanel.setBounds(0, 0, mainFrame.getWidth(), mainFrame.getHeight()); // Cubrir toda la ventana
        loaderPanel.setBackground(new Color(0, 0, 0, 150)); // Fondo semitransparente
        loaderPanel.setLayout(new GridBagLayout()); // Para centrar el contenido

        JLabel loaderLabel = new JLabel("Cargando...");
        loaderLabel.setForeground(Color.WHITE);
        loaderLabel.setFont(new Font("Arial", Font.BOLD, 20));
        loaderPanel.add(loaderLabel);

        // Añadir el loader al frame principal
        SwingUtilities.invokeLater(() -> {
            mainFrame.getLayeredPane().add(loaderPanel, JLayeredPane.MODAL_LAYER);
            mainFrame.getLayeredPane().revalidate();
            mainFrame.getLayeredPane().repaint();
        });

        // Simulamos que el contenido se carga en segundo plano
        new Timer(1000, e -> {
            if (isLoaded.get()) {
                SwingUtilities.invokeLater(() -> {
                    // Cuando la variable booleana indique que la vista está cargada, eliminamos el loader
                    mainFrame.getLayeredPane().remove(loaderPanel);
                    mainFrame.getLayeredPane().revalidate();
                    mainFrame.getLayeredPane().repaint();
                });
            }
        }).start();
    }
    
    public static void finishLoading() {
        isLoaded.set(true); 
    }
    
     public static void playClickSound() {
    try {
        // Carga el archivo de sonido desde la raíz de resources
        InputStream audioSrc = Utils.class.getResourceAsStream("/resources/TouchScreenFfx.wav");
        if (audioSrc == null) {
            throw new IOException("Archivo de sonido no encontrado.");
        }

        // Convertir el InputStream en AudioInputStream
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(audioSrc);
        
        // Obtener el clip y cargarlo con el archivo de sonido
        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        
        // Reproducir el sonido
        clip.start();
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
        ex.printStackTrace();
    }
}
}
