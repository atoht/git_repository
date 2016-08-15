package com.atoht.tank;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Missile {
	public static final int WIDTH = 38, HEIGHT = 36;
	private double SPEED=8, BMISPEED = 1.5;
    private int x, y;
    boolean up, down, left, right;
    Direction mh;
    private boolean live = true;
	private TankWar tw;
	private boolean good;
	private boolean bigMissile = false;
	public void setBigMissile(boolean bigMissile) {
		this.bigMissile = bigMissile;
	}

	private static Toolkit t = Toolkit.getDefaultToolkit();
	private static Image[] images = {
			t.getImage(Missile.class.getClassLoader().getResource("images/bullet1.png")),
			t.getImage(Missile.class.getClassLoader().getResource("images/bullet2.png")),
			t.getImage(Missile.class.getClassLoader().getResource("images/bullet3.png")),
	};

	public boolean isGood() {
		return good;
	}

	public boolean isLive() {
		return live;
	}

	public Missile(int x, int y, boolean good, Direction mh, TankWar tw) {
		this.x = x;
		this.y = y;
		this.mh =mh;
		this.tw = tw;
		this.good = good;
	}
    
    public void darw(Graphics g) {
    	if(!live) {
    		tw.mis.remove(this);
    		return;
    	}
    	if(isGood()&&bigMissile==false) {
    		g.drawImage(images[0], x, y, null);
    	}else if(isGood()&&bigMissile==true) {
    			g.drawImage(images[2], x, y, null);
    		
    	}else if(!isGood()) {
    		g.drawImage(images[1], x, y, null);
    	}
    	move();
    	
    }
    public Rectangle getRect() {
    	return new Rectangle(x, y, WIDTH, HEIGHT);
    }
    public boolean hitTank(Tank tk) {
    	if(this.live&&getRect().intersects(tk.getRect())&&tk.isLive()&&this.good!=tk.isGood()) {
    		if(tk.isGood()) {
    			tk.setLife(tk.getLife()-20);
    			if(tk.getLife()<=0) {
    				tk.setLive(false);
    			}
    		}else if(!tk.isGood()){
    			tk.setLive(false);
    		}
    		Explode e = new Explode(x-30, y-30, tw);
    		tw.explodes.add(e);
    		this.live = false;
    		return true;
    	}
    	return false;
    }
    public boolean hitTanks(List<Tank> tanks) {
    	for(int i=0; i<tanks.size(); i++) {
    		if(hitTank(tanks.get(i))) {
    			return true;
    		}
    	}
    	return false;
    }
    public boolean hitWall(Wall w) {
    	if(this.live && getRect().intersects(w.getRect())) {
    		this.live = false;
    		return true;
    	}
    	return false;
    }
	public void move() {
		switch(mh) {
		case U:
			if(!good) {y -= BMISPEED;}
			else{y -= SPEED;}
			break;
		case RU:
			if(!good) {
				y -= BMISPEED;
				x += BMISPEED;
			}else {
				y -= SPEED;
				x += SPEED;
			}
			break;
		case LU:
			if(!good) {
				y -= BMISPEED;
				x -= BMISPEED;
			}else {
				y -= SPEED;
				x -= SPEED;
			}
			break;
		case D:
			if(!good) {y += BMISPEED;}
			else {y += SPEED;}
			break;
		case RD:
			if(!good) {
				y += BMISPEED;
				x += BMISPEED;
			}else {
				y += SPEED;
				x += SPEED;
			}
			break;
		case LD:
			if(!good) {
				y += BMISPEED;
				x -= BMISPEED;
			}else {
				y += SPEED;
				x -= SPEED;
			}
			break;
		case R:
			if(!good) {x += BMISPEED;}
			else {x += SPEED;}
			break;
		case L:
			if(!good) {x -= BMISPEED;}
			else {x -= SPEED;}
			break;
		}
		if(x<0||y<0||x>TankWar.WIDTH||y>TankWar.HEIGHT) {
			live = false;
		}
	}

	public void setLive(boolean live) {
		this.live = live;
	}
}
