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
import utils.Utils;
import utils.UtilsForImages;
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

  public static void actualizaVistaGeneratorView(String name) {
    Utils.loader();
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
                        preguntaView.getPreguntaLabel().setText(Utils.formatearTexto(pregunta.getPregunta(), 50));
                        preguntaView.getCorrectaLabel().setText(Utils.formatearTexto(pregunta.getRespuestaCorrecta(), 30));
                        preguntaView.getIncorrectaLabel1().setText(Utils.formatearTexto(pregunta.getRespuestasIncorrectas()[0], 30));
                        preguntaView.getIncorrectaLabel2().setText(Utils.formatearTexto(pregunta.getRespuestasIncorrectas()[1], 30));
                        preguntaView.getIncorrectaLabel3().setText(Utils.formatearTexto(pregunta.getRespuestasIncorrectas()[2], 30));
                        
                        // Agregar el PreguntaView al contenedor
                        generatorView.getPreguntasContainer().add(preguntaView);
                    }
                }
                
                UtilsForImages.setImageOnComponentAndSetResizeEvent(generatorView, generatorView.getDesplegableLabel(), "Desplegable_On.png");
                generatorView.getModalContainer().setVisible(false);
                generatorView.getModalContainer().setMinimumSize(new java.awt.Dimension(393, 54));
                
                
                
                setCrearLabel(generatorView.getCrearLabel());
                generatorView.setCheckCrearLabel(true);
                
                generatorView.getPreguntasContainer().revalidate();
                generatorView.getPreguntasContainer().repaint();
                
                Utils.finishLoading();
            });
        }
    } else {
        System.out.println("Simulador no encontrado con el nombre: " + name);
    }
}
  
    public static void setCrearLabel(JLabel crearLabel) {
       UtilsForImages.setImageOnComponentAndSetResizeEvent(generatorView, crearLabel, "Cilindrico_On.png");
       crearLabel.revalidate();
       crearLabel.repaint();
    }
}