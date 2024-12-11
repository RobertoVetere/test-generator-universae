/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

/**
 *
 * @author admin
 */
public class ConfigService {
    
    // Instancia privada y estática con la palabra clave 'volatile'
    private static volatile ConfigService instance;
    private static int simulatorQuestionNumber = 10;

    // Constructor privado
    private ConfigService() {}

    // Método público y estático para obtener la instancia
    public static ConfigService getInstance() {
        if (instance == null) {  // Primer chequeo
            synchronized (ConfigService.class) {
                if (instance == null) {  // Segundo chequeo
                    instance = new ConfigService();
                }
            }   
        }
        return instance;
    }
    
    public int getSimulatorQuestionNumber(){
        return simulatorQuestionNumber;
    }
    
    public void setSimulatorQuestionNumber(int getSimulatorQuestionNumber){
        this.simulatorQuestionNumber = simulatorQuestionNumber;
    }
}