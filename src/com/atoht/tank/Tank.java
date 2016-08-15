package com.atoht.tank;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class Tank {
    private int x, y, oldx, oldy;
    private double SPEED = 2, BSPEED = 1;
    private boolean good;
    private boolean up, down, left, right, live = true;
    private int life = 100;
    private int NumberofLife = 2;
    
    public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
    private Direction h= Direction.STOP;
    private Direction hh= Direction.U;
    public static final int WIDTH = 31, HEIGHT = 31;
    TankWar tw;
    private static Random r = new Random();
    private int step = r.nextInt(150)+3;
    private static Image[] tkImgs = null;
    private static Map<String, Image> imgs = new HashMap<String, Image>();
    
    static {
    	tkImgs = new Image[] {
    			GameUtil.getImage("images/D.png"),
    			GameUtil.getImage("images/L.png"),
    			GameUtil.getImage("images/LD.png"),
    			GameUtil.getImage("images/LU.png"),
    			GameUtil.getImage("images/R.png"),
    			GameUtil.getImage("images/RD.png"),
    			GameUtil.getImage("images/RU.png"),
    			GameUtil.getImage("images/U.png"),
    	};
    	imgs.put("D", tkImgs[0]);
    	imgs.put("L", tkImgs[1]);
    	imgs.put("LD", tkImgs[2]);
    	imgs.put("LU", tkImgs[3]);
    	imgs.put("R", tkImgs[4]);
    	imgs.put("RD", tkImgs[5]);
    	imgs.put("RU", tkImgs[6]);
    	imgs.put("U", tkImgs[7]);
    }

	public Tank(int x, int y, boolean good) {
		this.x = x;
		this.y = y;
		this.oldx = x;
		this.oldy = y;
		this.good = good;
	}
	public Tank(int x, int y, boolean good, TankWar tw) {
		this(x,y,good);
		this.tw = tw;
	}
    public void draw(Graphics g) {
    	if(!live) {
    		tw.tanks.remove(this);
    		return;
    	}
		if(this.isGood())  new BloodTrough().draw(g);
		switch(hh) {
		case U:
			g.drawImage(imgs.get("U"), x, y, null);
			break;
		case RU:
			g.drawImage(imgs.get("RU"), x, y, null);
			break;
		case LU:
			g.drawImage(imgs.get("LU"), x, y, null);	
			break;
		case D:
			g.drawImage(imgs.get("D"), x, y, null);
			break;
		case RD:
			g.drawImage(imgs.get("RD"), x, y, null);
			break;
		case LD:
			g.drawImage(imgs.get("LD"), x, y, null);
			break;
		case L:
			g.drawImage(imgs.get("L"), x, y, null);
			break;
		case R:
			g.drawImage(imgs.get("R"), x, y, null);
			break;
		}
		move();
    } 
    
    public boolean isLive() {
		return live;
	}
	public void setLive(boolean live) {
		this.live = live;
	}
	public void move() {
		this.oldx  = x;
		this.oldy  = y;
		switch(h) {
		case U:
			if(!good) {y -= BSPEED;}
			else{y -= SPEED;}
			break;
		case RU:
			if(!good) {
				y -= BSPEED;
				x += BSPEED;
			}else {
				y -= SPEED;
				x += SPEED;
			}
			break;
		case LU:
			if(!good) {
				y -= BSPEED;
				x -= BSPEED;
			}else {
				y -= SPEED;
				x -= SPEED;
			}
			break;
		case D:
			if(!good) {y += BSPEED;}
			else {y += SPEED;}
			break;
		case RD:
			if(!good) {
				y += BSPEED;
				x += BSPEED;
			}else {
				y += SPEED;
				x += SPEED;
			}
			break;
		case LD:
			if(!good) {
				y += BSPEED;
				x -= BSPEED;
			}else {
				y += SPEED;
				x -= SPEED;
			}
			break;
		case R:
			if(!good) {x += BSPEED;}
			else {x += SPEED;}
			break;
		case L:
			if(!good) {x -= BSPEED;}
			else {x -= SPEED;}
			break;
    }
    	if(this.h!=Direction.STOP) {
    		this.hh = this.h;
    	}
    	if(x < 0) x = 0;
		if(y < 30) y = 30;
		if(x + Tank.WIDTH > TankWar.WIDTH) x = TankWar.WIDTH - Tank.WIDTH;
		if(y + Tank.HEIGHT > TankWar.HEIGHT) y = TankWar.HEIGHT - Tank.HEIGHT;
    	
    	if(!good) {
    		Direction[] shx = Direction.values();
    		if(step==0) {
    			step = r.nextInt(170)+3;
    			int rn = r.nextInt(shx.length);
    			h = shx[rn];
    		}
    		step--;
    	if(r.nextInt(400)>397)this.fire();
    	}
    	
    }
    public Rectangle getRect() {
    	return new Rectangle(x, y, WIDTH, HEIGHT);
    }
    
    
    public Missile fire() {
    	if(!live) return null;
    	int x = this.x+Tank.WIDTH/2-Missile.WIDTH/2;
    	int y = this.y+Tank.HEIGHT/2-Missile.HEIGHT/2;
    	Missile mi = new Missile(x, y, this.good, hh, tw);
    	tw.mis.add(mi);
    	return mi;
    }
    public Missile fire(Direction h) {
    	if(!live) return null;
    	int x = this.x+Tank.WIDTH/2-Missile.WIDTH/2;
    	int y = this.y+Tank.HEIGHT/2-Missile.HEIGHT/2;
    	Missile mi = new Missile(x, y, this.good, h, tw);
    	mi.setBigMissile(true);
    	tw.mis.add(mi);
    	return mi;
    }
    public void superFire() {
    	Direction[] h = Direction.values();
    	for(int i=0; i<8; i++) {
    		fire(h[i]);
    	}
    }
    public void stay() {
    	x = oldx;
    	y = oldy;
    }
    public boolean hitWall(Wall w) {
    	if(this.live && getRect().intersects(w.getRect())) {
    		stay();
    		return true;
    	}
    	
    	return false;
    }
    /**
     * 攻击坦克方法
     * @param tk 传入敌方坦克的容器
     * @return 击中敌人返回true 
     */
    public boolean hitTanks(List<Tank> tk) {
    	for(int i=0; i<tk.size(); i++) {
    		Tank t = tk.get(i);
    		if(this!=t) {
    			if(this.live&&t.isLive()&&this.getRect().intersects(t.getRect())) {
    				stay();
    	    		return true;
    			}
    		}
    	}
    	return false;
    }
    public boolean eat(Blood bd) {
    	if(this.live&&bd.isLive()&&this.getRect().intersects(bd.getRect())) {
    		bd.setLive(false);
    		this.setLife(100);
    		return true;
    	}
    	return false;
    }
    public boolean isGood() {
		return good;
	}
	public void keyPressed(KeyEvent e) {
		 int key = e.getKeyCode();
		 switch(key) {
		 case KeyEvent.VK_W:
			 up = true; break;
		 case KeyEvent.VK_S:
			 down = true; break;
		 case KeyEvent.VK_D:
			 right = true; break;
		 case KeyEvent.VK_A:
			 left = true; break;
		 }
		 direction();
    }
	public void keyReleased(KeyEvent e) {
		  switch(e.getKeyCode()) {
		  case KeyEvent.VK_W:
	   		 up = false; break;
	   		 case KeyEvent.VK_S:
	   		 down = false; break;
	   		 case KeyEvent.VK_D:
	   	     right = false; break;
	   		 case KeyEvent.VK_A:
	   	     left = false; break;
	   		 case KeyEvent.VK_J:
				 fire(); break;
	   		 case KeyEvent.VK_I:
	   			 
	   			 superFire();
	   			 break;
	   		 case KeyEvent.VK_F2:
	   			 if(!isLive()&&NumberofLife>0) {
	   				 setLive(true);
	   				 setLife(100);
	   				 NumberofLife--;
	   			 }
	   				break;
				}
		     
		  direction();
			}    
	public int getNumberofLife() {
		return NumberofLife;
	}
	public void direction() {
		if(up&&!down&!right&!left) h = Direction.U;
		else if(up&&!down&right&!left) h = Direction.RU;
		else if(up&&!down&!right&left) h = Direction.LU;
		else if(!up&&down&!right&!left) h = Direction.D;
		else if(!up&&down&right&!left) h = Direction.RD;
		else if(!up&&down&!right&left) h = Direction.LD;
		else if(!up&&!down&right&!left) h = Direction.R;
		else if(!up&&!down&!right&left) h = Direction.L;
		else if(!up&&!down&!right&!left) h = Direction.STOP;
	}
//	public void resurrection() {
//		if(isGood()==true&&!isLive()) {
//			Date cd = new Date(System.currentTimeMillis());
//			Date ed = new Date(System.currentTimeMillis());
//			if(ed.getTime()-cd.getTime()>5) {
//				setLive(true);
//			}
//		}
//	}
//	public void resurrection1() {
//		Date cd = new Date(System.currentTimeMillis());
//		Date ed = new Date(System.currentTimeMillis());
//		if(cd.before(ed)) {
//			System.out.println("当前时间："+cd);
//			setLive(true);
//			System.out.println("五秒后："+ed);
//		}
//	}
	public class BloodTrough {
		public void draw(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.WHITE);
			g.drawRect(x, y-10, WIDTH, 10);
			if(life>60&&life<=100) g.setColor(Color.GREEN);
			if(life>20&&life<=60) g.setColor(Color.YELLOW);
			if(life==20) g.setColor(Color.RED);
			int w = WIDTH*life/100;
			g.fillRect(x, y-10, w, 10);
			g.setColor(c);
		}
	}
	
}
