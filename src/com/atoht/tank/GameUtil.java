package com.atoht.tank;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class GameUtil {
     public static Image getImage(String part) {
    	 URL url = GameUtil.class.getClassLoader().getResource(part);
    	 BufferedImage img = null;
    	 try {
			img = ImageIO.read(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 return img;
     }
}
