package utils;

import java.awt.Container;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class UtilsForImages {

    // Configuración
    private static final float DEFAULT_SIZE_CHANGE_THRESHOLD = 0.01f;
    private static final String ROOT_IMAGES_PATH = "/resources/";
    private static final Map<JComponent, Timer> activeTimers = new HashMap<>();
    private static final Map<String, CachedImageScaled> imageCache = new HashMap<>();

    // Método para obtener la imagen de la caché o cargarla
    private static ImageIcon getImage(String name, float scale, JComponent component) throws FileNotFoundException {
        if (!imageCache.containsKey(name)) {
            ImageIcon image = new ImageIcon(UtilsForImages.class.getResource(ROOT_IMAGES_PATH + name));
            String imagePath = ROOT_IMAGES_PATH + name;
            System.out.println("Ruta de la imagen: " + UtilsForImages.class.getResource(imagePath));
            if (image.getImageLoadStatus() != java.awt.MediaTracker.COMPLETE) {
                throw new FileNotFoundException("Imagen no encontrada en la ruta: " + ROOT_IMAGES_PATH + name);
            }
            if (component.getWidth() > 0 && component.getHeight() > 0) {
                int newWidth = (int) (component.getWidth() * scale);
                int newHeight = (int) (component.getHeight() * scale);
                Image scaledImage = image.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                imageCache.put(name, new CachedImageScaled(scaledIcon));
            }

        }
        CachedImageScaled imgCache = imageCache.get(name);
        return imgCache != null ? imgCache.getScaledImage(scale) : null;
    }

    // Cargar imagen en componente con opciones de escala y eventos
    public static void setImageOnComponentAndSetResizeEvent(Container frame, JComponent component, String imageName) {
        setImageOnComponentAndSetResizeEvent(frame, component, imageName, 1.0f, DEFAULT_SIZE_CHANGE_THRESHOLD);
    }

    public static void setImageOnComponentAndSetResizeEvent(Container frame, JComponent component, String imageName, float scale) {
        setImageOnComponentAndSetResizeEvent(frame, component, imageName, scale, DEFAULT_SIZE_CHANGE_THRESHOLD);
    }

    public static void setImageOnComponentAndSetResizeEvent(Container frame, JComponent component, String imageName, float scale, float threshold) {
        applyImageWithResizeListener(component, imageName, scale, threshold);
    }

    private static void applyImageWithResizeListener(JComponent component, String root, float scale, float threshold) {
        setImageOnComponent(root, component, scale);
        component.addComponentListener(new ComponentAdapter() {
            private int lastWidth = -1;
            private int lastHeight = -1;

            @Override
            public void componentResized(ComponentEvent evt) {
                if (hasSignificantChange(component.getWidth(), component.getHeight(), lastWidth, lastHeight, threshold)) {
                    setImageOnComponent(root, component, scale);
                    lastWidth = component.getWidth();
                    lastHeight = component.getHeight();
                }
            }
        });
    }

    // Escalar y aplicar imagen en JLabel o JButton
    @SuppressWarnings("unchecked")
    public static void setImageOnComponent(String root, JComponent component, float scale) {

        try {
            ImageIcon image = getImage(root, scale, component);
            if (image != null) {

                if (component instanceof JLabel) {
                    ((JLabel) component).setIcon(image);
                } else if (component instanceof JButton) {
                    ((JButton) component).setIcon(image);
                } else {
                    System.out.println("El componente no es compatible con iconos.");
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        component.repaint();
    }

    private static boolean hasSignificantChange(int currentWidth, int currentHeight, int lastWidth, int lastHeight, float threshold) {
        if (lastWidth == -1 || lastHeight == -1) {
            return true;
        }
        float widthChange = Math.abs((currentWidth - lastWidth) / (float) lastWidth);
        float heightChange = Math.abs((currentHeight - lastHeight) / (float) lastHeight);
        return widthChange > threshold || heightChange > threshold;
    }

    // Escalado suave entre dos valores
//    public static void applySmoothScaling(Container frame, JComponent component, String imageName, float initialScale, float targetScale, int duration) {
//        try {
//            ImageIcon image = getImage(imageName);
//            if (activeTimers.containsKey(component) && activeTimers.get(component).isRunning()) {
//                activeTimers.get(component).stop();
//            }
//            Timer timer = createSmoothScalingTimer(component, image, initialScale, targetScale, duration);
//            activeTimers.put(component, timer);
//            timer.start();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//    private static Timer createSmoothScalingTimer(JComponent component, ImageIcon image, float initialScale, float targetScale, int duration) {
//        final long startTime = System.currentTimeMillis();
//        Timer timer = new Timer(10, null);
//        timer.addActionListener(e -> {
//            long elapsedTime = System.currentTimeMillis() - startTime;
//            float progress = Math.min(1.0f, (float) elapsedTime / duration);
//            float currentScale = initialScale + (targetScale - initialScale) * progress;
//            setImageOnComponent(image, component, currentScale);
//            if (progress >= 1.0f) {
//                timer.stop();
//            }
//        });
//        return timer;
//    }
    public static void applySmoothScaling(Container frame, JComponent component, String imageName, float initialScale, float targetScale, int duration) {
        // Almacenamos los valores de la animación en el componente usando un objeto Runnable
        Runnable animationTask = new Runnable() {
            private long startTime = System.currentTimeMillis();

            @Override
            public void run() {
                long elapsedTime = System.currentTimeMillis() - startTime;
                float progress = Math.min(1.0f, (float) elapsedTime / duration);

                // Interpolación de la escala actual en función del progreso
                float currentScale = initialScale + (targetScale - initialScale) * progress;

                // Escala la imagen y establece el icono en el componente
                setImageOnComponent(imageName, component, currentScale);

                // Redibuja el componente hasta que la animación termine
                if (progress < 1.0f) {
                    component.repaint(); // fuerza a Swing a llamar a paintComponent()
                    SwingUtilities.invokeLater(this); // Ejecuta de nuevo el Runnable
                }
            }
        };

        // Iniciamos la animación
        SwingUtilities.invokeLater(animationTask);
    }

    
}
