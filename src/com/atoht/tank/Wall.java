package com.atoht.tank;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Wall {
    private int x, y, w, h;
    TankWar tw;
	public Wall(int x, int y, int w, int h, TankWar tw) {
		super();
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.tw = tw;
	}
    
    public void draw(Graphics g) {
    	g.fill3DRect(x, y, w, h, false);
    }
    public Rectangle getRect() {
    	return new Rectangle(x, y, w, h);
    }
}
