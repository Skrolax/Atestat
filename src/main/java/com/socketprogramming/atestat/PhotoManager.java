package com.socketprogramming.atestat;

import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class PhotoManager {

    private static byte[] imageByte;
    public static byte[] DEFAULT_IMAGE;

    public static byte[] fileToByte(File file) throws IOException {
        if(file == null){
            return null;
        }
        return Files.readAllBytes(file.toPath());
    }

    public static Image getImage(byte[] photoByte){
        if (photoByte == null || photoByte.length == 0) {
            return new Image(Objects.requireNonNull(PhotoManager.class.getResourceAsStream("/photos/default-avatar-icon-of-social-media-user-vector.jpg")));
        }
        return new Image(new ByteArrayInputStream(photoByte));
    }

}
