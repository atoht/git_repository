package com.atoht.tank;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Blood {
    private int x, y, w, h;
    private int[][] por = {{300,200},{300,170},{320,180},{350,190},
    		{320,200},{300,210},{280,220},{265,230}};
    private int step = 0;
    private boolean live = true;
    
    public boolean isLive() {
		return live;
	}


	public void setLive(boolean live) {
		this.live = live;
	}


	public Blood() {
		this.x = por[0][0];
		this.y = por[0][1];
		this.w = 10;
		this.h = 10;
	}


	public void draw(Graphics g) {
		if(!this.isLive()) return;
    	Color c = g.getColor();
    	g.setColor(Color.RED);
    	if(step==por.length) {
    		step = 0;
    	}
    	this.x = por[step][0];
    	this.y = por[step][1];
    	g.fillRect(x, y, w, h);
    	step++;
    }
	public Rectangle getRect() {
		return new Rectangle(x, y, w, h);
	}
}
