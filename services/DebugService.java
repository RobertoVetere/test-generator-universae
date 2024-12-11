package services;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class DebugService {

    // Mensajes de error, aviso, confirmación y registro
    public static final String ERROR_NO_ENCONTRADO = "No se encontró el archivo de preguntas";
    public static final String ERROR_ESTRUCTURA = "Error en la estructura del archivo de preguntas";
    public static final String ERROR_PREGUNTAS_VACIAS = "Algunas preguntas están vacías";
    public static final String ERROR_CREAR_ARCHIVO = "No se pudo crear el archivo de preguntas";

    public static final String AVISO_NO_PREGUNTAS = "Este simulador no tiene preguntas";
    public static final String AVISO_NO_COMPRESION = "Las preguntas se guardaron, pero no se pudo comprimir el simulador";

    public static final String CONFIRMACION_PREGUNTAS_CARGADAS = "Las preguntas han sido cargadas con éxito";
    public static final String CONFIRMACION_PREGUNTAS_GUARDADAS = "Las preguntas han sido guardadas (X en total)";
    public static final String CONFIRMACION_EXPORTACION_SIMULADOR = "Las preguntas se guardaron y se exportó el simulador en: X/X.zip";

    public static final String REGISTRO_PREGUNTA_ANADIDA = "Pregunta añadida (actualmente X)";
    public static final String REGISTRO_PREGUNTA_ELIMINADA = "Pregunta eliminada (quedan X)";

    // Método para validar errores
    public static boolean validarArchivoPreguntasNoEncontrado(boolean archivoExistente) {
        if (!archivoExistente) {
            mostrarError(ERROR_NO_ENCONTRADO);
            return true;
        }
        return false;
    }

    public static boolean validarEstructuraArchivo(boolean estructuraCorrecta) {
        if (!estructuraCorrecta) {
            mostrarError(ERROR_ESTRUCTURA);
            return true;
        }
        return false;
    }

    public static boolean validarPreguntasVacias(boolean preguntasVacias) {
        if (preguntasVacias) {
            mostrarError(ERROR_PREGUNTAS_VACIAS);
            return true;
        }
        return false;
    }

    public static boolean validarCreacionArchivo(boolean archivoCreado) {
        if (!archivoCreado) {
            mostrarError(ERROR_CREAR_ARCHIVO);
            return true;
        }
        return false;
    }

    // Método para validar avisos
    public static boolean validarSimuladorSinPreguntas(boolean simuladorTienePreguntas) {
        if (!simuladorTienePreguntas) {
            mostrarAviso(AVISO_NO_PREGUNTAS);
            return true;
        }
        return false;
    }

    public static boolean validarCompresionSimulador(boolean compresionExitosa) {
        if (!compresionExitosa) {
            mostrarAviso(AVISO_NO_COMPRESION);
            return true;
        }
        return false;
    }

    // Método para mostrar confirmaciones
    public static boolean validarPreguntasCargadas(boolean preguntasCargadas) {
        if (preguntasCargadas) {
            mostrarConfirmacion(CONFIRMACION_PREGUNTAS_CARGADAS);
            return true;
        }
        return false;
    }

    public static boolean validarPreguntasGuardadas(boolean preguntasGuardadas, int numPreguntas) {
        if (preguntasGuardadas) {
            mostrarConfirmacion(CONFIRMACION_PREGUNTAS_GUARDADAS.replace("X", String.valueOf(numPreguntas)));
            return true;
        }
        return false;
    }

    public static boolean validarExportacionSimulador(boolean exportacionExitosa, String path) {
        if (exportacionExitosa) {
            mostrarConfirmacion(CONFIRMACION_EXPORTACION_SIMULADOR.replace("X/X.zip", path));
            return true;
        }
        return false;
    }

    // Método para mostrar registros
    public static void mostrarRegistroPreguntaAnadida(int numPreguntas) {
        mostrarMensaje(REGISTRO_PREGUNTA_ANADIDA.replace("X", String.valueOf(numPreguntas)), "#F7F7F7", 1000);
    }

    public static void mostrarRegistroPreguntaEliminada(int numPreguntas) {
        mostrarMensaje(REGISTRO_PREGUNTA_ELIMINADA.replace("X", String.valueOf(numPreguntas)), "#F7F7F7", 1000);
    }

    // Método general para mostrar el mensaje con el color y duración especificados
    private static void mostrarMensaje(String mensaje, String colorHex, int tiempo) {
        JLabel label = new JLabel(mensaje);
        label.setForeground(Color.decode(colorHex));
        label.setFont(new Font("Raleway", Font.PLAIN, 14));

        // Crear un JOptionPane para mostrar el mensaje
        JOptionPane optionPane = new JOptionPane(label, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog("Mensaje");
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setModal(false);

        // Mostrar el mensaje
        dialog.setVisible(true);

        // Usar un Timer para cerrar el mensaje después del tiempo especificado
        new Timer(1, e -> dialog.dispose()).setRepeats(false);
        new Timer(1, e -> new Timer(1, e1 -> dialog.dispose()).start()).setInitialDelay(tiempo); // Tiempo en milisegundos
    }

    // Métodos para mostrar errores
    private static void mostrarError(String mensaje) {
        mostrarMensaje(mensaje, "#EB4151", 5000);
    }

    // Métodos para mostrar avisos
    private static void mostrarAviso(String mensaje) {
        mostrarMensaje(mensaje, "#FF9C00", 5000);
    }

    // Métodos para mostrar confirmaciones
    private static void mostrarConfirmacion(String mensaje) {
        mostrarMensaje(mensaje, "#86D295", 5000);
    }
}
