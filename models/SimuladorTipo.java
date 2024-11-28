package models;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import models.Pregunta;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class SimuladorTipo {

    private String name;
    private String simuladorRuta; // Se mantiene el nombre original para el ID del archivo
    private List<Pregunta> listaPreguntas = new ArrayList<>();

    // Constructor vacío
    public SimuladorTipo() {
    }

    // Constructor con parámetros
    public SimuladorTipo(String name, String simuladorRuta) {
        this.name = name;
        this.simuladorRuta = simuladorRuta;
    }

    // Getters y setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getsimuladorRuta() {
        return simuladorRuta;
    }

    public void setsimuladorRuta(String simuladorRuta) {
        this.simuladorRuta = simuladorRuta; // Asignamos a simuladorRuta en lugar de name
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