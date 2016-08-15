package com.atoht.tank;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class Explode {
    private int x, y;
    private int steep = 0;
    private boolean live = true;
    TankWar tw;
    private static boolean init = false;
    private static Toolkit t = Toolkit.getDefaultToolkit();
    private static Image[] images = {
    		t.getImage(Explode.class.getClassLoader().getResource("explobe/e1.png")),
    		t.getImage(Explode.class.getClassLoader().getResource("explobe/e2.png")),
    		t.getImage(Explode.class.getClassLoader().getResource("explobe/e3.png")),
    		t.getImage(Explode.class.getClassLoader().getResource("explobe/e4.gif")),
    		t.getImage(Explode.class.getClassLoader().getResource("explobe/e5.gif")),
    		t.getImage(Explode.class.getClassLoader().getResource("explobe/e6.gif")),
    		t.getImage(Explode.class.getClassLoader().getResource("explobe/e7.gif")),
    		t.getImage(Explode.class.getClassLoader().getResource("explobe/e8.gif")),
    		t.getImage(Explode.class.getClassLoader().getResource("explobe/e9.gif")),
    		t.getImage(Explode.class.getClassLoader().getResource("explobe/e10.gif")),
    		t.getImage(Explode.class.getClassLoader().getResource("explobe/e11.gif")),
    		t.getImage(Explode.class.getClassLoader().getResource("explobe/e12.gif")),
    		t.getImage(Explode.class.getClassLoader().getResource("explobe/e13.gif")),
    		t.getImage(Explode.class.getClassLoader().getResource("explobe/e14.gif")),
    		t.getImage(Explode.class.getClassLoader().getResource("explobe/e15.png")),
    		t.getImage(Explode.class.getClassLoader().getResource("explobe/e16.png"))
    };
    
	public Explode(int x, int y, TankWar tw) {
		this.x = x;
		this.y = y;
		this.tw = tw;
	}
    
    public void draw(Graphics g) {
    	if(!init) {
    		for (int i = 0; i < images.length; i++) {
				g.drawImage(images[i], -100, -100, null);
			}
    		init = true;
    	}
    	if(!live) {
    		tw.explodes.remove(this);
    		return;
    	}
    	if(steep==images.length) {
    		live = false;
    		steep = 0;
    		return;
    	}
    	g.drawImage(images[steep], x, y, null);
    	steep++;
    }
    
}
