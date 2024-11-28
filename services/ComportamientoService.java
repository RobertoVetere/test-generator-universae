/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import java.util.List;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import models.Pregunta;
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
    private int scrollVentanaAltura; 

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

                List<Pregunta> lista = simulador.getListaPreguntas();
                
                if (lista != null) {
                    // Calcular la altura total según el número de preguntas
                    int totalHeight = lista.size() * 300;
                    
                    // Establecer el tamaño preferido del contenedor de preguntas
                    generatorView.getPreguntasContainer().setPreferredSize(new java.awt.Dimension(
                        generatorView.getPreguntasContainer().getPreferredSize().width, // Mantener el ancho actual
                        totalHeight // Establecer la nueva altura
                    ));

                    System.out.println("Número de preguntas a pintar: " + lista.size());

                    for (Pregunta pregunta : lista) {
                        // Crear un nuevo PreguntaView para cada pregunta
                        PreguntaView preguntaView = new PreguntaView();
                        
                        // Establecer la pregunta, la respuesta correcta y las respuestas incorrectas
                        preguntaView.getPreguntaLabel().setText(pregunta.getPregunta());
                        preguntaView.getCorrectaLabel().setText(pregunta.getRespuestaCorrecta());
                        preguntaView.getIncorrectaLabel1().setText(pregunta.getRespuestasIncorrectas()[0]);
                        preguntaView.getIncorrectaLabel2().setText(pregunta.getRespuestasIncorrectas()[1]);
                        preguntaView.getIncorrectaLabel3().setText(pregunta.getRespuestasIncorrectas()[2]);
                        
                        // Agregar el PreguntaView al contenedor
                        generatorView.getPreguntasContainer().add(preguntaView);
                    }
                }

                // Crear un JLabel con el nombre del simulador y agregarlo al contenedor
                //JLabel simuladorNombreLabel = new JLabel();
                //simuladorNombreLabel.setText(simulador.getName());
                //generatorView.getPreguntasContainer().add(simuladorNombreLabel);
                //System.out.println("Simulador en comportamiento: " + simulador.getName());

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