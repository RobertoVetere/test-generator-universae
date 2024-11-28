/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import models.SimuladorTipo;
import views.GeneratorView;
import views.PreguntaView;

/**
 *
 * @author admin
 */
public class ComportamientoService {
    
    private static GeneratorView generatorView;
    private PreguntaView pregunta;

    public static void setGeneratorView(GeneratorView view) {
        generatorView = view;
    }

   public static void actualizaVistaPreguntas(String name) {
        SimuladorTipo simulador = SimuladorService.getInstance().obtenerSimuladorPorNombre(name); // Buscar el simulador por nombre

        if (simulador != null) {
            // Si encontramos el simulador, actualizamos la vista
            if (generatorView != null) {
                SwingUtilities.invokeLater(() -> {
                    // Limpiar el contenedor de scroll (si es necesario)
                    generatorView.getPreguntasContainer().removeAll();
                    
                    

                    // Crear un JLabel con el nombre del simulador y agregarlo al contenedor
                    JLabel simuladorNombreLabel = new JLabel();
                    simuladorNombreLabel.setText(simulador.getName());
                    generatorView.getPreguntasContainer().add(simuladorNombreLabel);
                    System.out.println("Simulador en comportamiento: " + simulador.getName());

                    // Realizamos la revalidación y repaint para actualizar la vista
                    generatorView.getPreguntasContainer().revalidate();
                    generatorView.getPreguntasContainer().repaint();
                });
            }
        } else {
            System.out.println("Simulador no encontrado con el nombre: " + name);
        }
    }
}