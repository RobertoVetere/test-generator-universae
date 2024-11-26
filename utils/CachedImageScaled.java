/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;

public class CachedImageScaled {

    private final ImageIcon originalImage;
    private final Map<Float, ImageIcon> scaledCache = new HashMap<>();

    public CachedImageScaled(ImageIcon image) {
        this.originalImage = image;
    }

    public ImageIcon getScaledImage(float scale) {
        if (scaledCache.containsKey(scale)) {
            return scaledCache.get(scale);
        }

        int newWidth = (int) (originalImage.getIconWidth() * scale);
        int newHeight = (int) (originalImage.getIconHeight() * scale);
        Image scaledImage = originalImage.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        scaledCache.put(scale, scaledIcon);  // Guardar en caché
        return scaledIcon;
    }
}
