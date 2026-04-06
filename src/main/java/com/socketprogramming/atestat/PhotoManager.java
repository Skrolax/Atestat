package com.socketprogramming.atestat;

import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class PhotoManager {

    private static byte[] imageByte;
    public static byte[] DEFAULT_IMAGE;

    //Trebuie să găsesc ceva default image;
    //Trebuie ca DEFAULT_IMAGE sa fie pastrata local, pe server


    private static void fileToByte(File file) throws IOException {
        imageByte = Files.readAllBytes(file.toPath());
    }

  /*  public static byte[] getImage(){
        if(imageByte.length == 0){
            return DEFAULT_IMAGE;
        }
        return new Image(new ByteArrayInputStream(imageByte));
    }*/

}
