/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import java.util.List;
import models.SimuladorTipo;

/**
 *
 * @author admin
 */
public interface ISimuladorService {
    void cargarSimuladores();
    List<SimuladorTipo> getSimuladoresList();
    SimuladorTipo obtenerSimuladorPorNombre(String name);
    void actualizarCSV(String name);
    int tieneXPreguntas(String name);
    SimuladorTipo getSimuladorSeleccionado();
    void setSimuladorSeleccionado(SimuladorTipo simuladorSeleccionado);
}
