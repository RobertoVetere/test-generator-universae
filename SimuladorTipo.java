/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class SimuladorTipo {

    private String name;
    private List<Pregunta> listaPreguntas = new ArrayList<>();

    // Constructor vacío
    public SimuladorTipo() {
    }

    // Constructor con parámetros
    public SimuladorTipo(String name) {
        this.name = name;
    }

    // Getters y setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Pregunta> getListaPreguntas() {
        return listaPreguntas;
    }

    public void setListaPreguntas(List<Pregunta> listaPreguntas) {
        this.listaPreguntas = listaPreguntas;
    }

    // Método para agregar una pregunta a la lista
    public void agregarPregunta(Pregunta pregunta) {
        listaPreguntas.add(pregunta);
    }

    // Método para mostrar las preguntas
    public void mostrarPreguntas() {
        for (Pregunta pregunta : listaPreguntas) {
            System.out.println("Pregunta: " + pregunta.getPregunta());
        }
    }
}