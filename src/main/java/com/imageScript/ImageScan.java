package com.imageScript;

import org.apache.commons.collections4.map.HashedMap;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.imageio.ImageIO;

public class ImageScan {

    private Map<String, BufferedImage> images = new HashedMap<>();
    private String imagePath;

    public ImageScan(String imagePath) {
        this.imagePath = imagePath;
        loadImagePaths();
    }

    public void loadImagePaths() {

        File file = new File(imagePath);

        File[] imageFiles = file.listFiles(); // This gets all of the files inside
        for (File f : imageFiles) {
            try {
                images.put(f.getName(), ImageIO.read(f));
            } catch (IOException e) {
                System.out.println("File Not found");
            }
        }
    }

    public Map<String, BufferedImage> getImages() {
        return images;
    }
}
