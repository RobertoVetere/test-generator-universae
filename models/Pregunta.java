package models;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author admin
 */
public class Pregunta {
    private String pregunta;
    private String respuestaCorrecta;
    private String[] respuestasIncorrectas;

    public Pregunta(String pregunta, String respuestaCorrecta, String[] respuestasIncorrectas) {
        this.pregunta = pregunta;
        this.respuestaCorrecta = respuestaCorrecta;
        this.respuestasIncorrectas = respuestasIncorrectas;
    }

    public Pregunta() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public void setRespuestaCorrecta(String respuestaCorrecta) {
        this.respuestaCorrecta = respuestaCorrecta;
    }

    public String[] getRespuestasIncorrectas() {
        return respuestasIncorrectas;
    }

    public void setRespuestasIncorrectas(String[] respuestasIncorrectas) {
        this.respuestasIncorrectas = respuestasIncorrectas;
    }

    @Override
    public String toString() {
        return "Pregunta{" + "pregunta=" + pregunta + ", respuestaCorrecta=" + respuestaCorrecta + ", respuestasIncorrectas=" + respuestasIncorrectas + '}';
    }
    
    
}
