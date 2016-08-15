package com.atoht.tank;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.xml.crypto.dsig.keyinfo.KeyValue;


/**
 * 游戏运行主程序
 * @author atoht
 *
 */
public class TankWar extends Frame {
	public static final int WIDTH = 820, HEIGHT = 615;
	Tank tk = new Tank(100,400,true,this);
	List<Missile> mis = new ArrayList<Missile>();
	List<Explode> explodes = new ArrayList<Explode>();
	List<Tank> tanks = new ArrayList<Tank>();
	Wall w1 = new Wall(120, 170, 27, 150, this), w2 = new Wall(500, 307, 170, 32, this);
	Blood bd = new Blood();
	Image bg = GameUtil.getImage("images/bg.png");
	/*
	 * (non-Javadoc)
	 * @see java.awt.Window#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {
		g.drawImage(bg, 0, 0, null);
		if(tanks.size()==0) {
			for(int i=0; i<10; i++) {
				tanks.add(new Tank(60+63*(i+1), 100, false, this));
			}
		}
		for(int i=0; i<tanks.size(); i++) {
			Tank tank = tanks.get(i);
			tank.hitWall(w1);
			tank.hitWall(w2);
			tank.hitTanks(tanks);
			tank.draw(g);
		}
		for(int i=0; i<mis.size(); i++) {
			Missile mi = mis.get(i);
			mi.hitTanks(tanks);
			mi.hitTank(tk);
			mi.hitWall(w1);
			mi.hitWall(w2);
			mi.darw(g);
		}
		for(int i=0; i<explodes.size(); i++) {
			Explode explode = explodes.get(i);
			explode.draw(g);
		}
		Color c = g.getColor();
		g.setColor(Color.WHITE);
		g.drawString("Number of Life count:"+tk.getNumberofLife(), 10, 40);
		g.drawString("mis    count:"+mis.size(), 10, 55);
		g.drawString("Explodes count:"+explodes.size(), 10, 70);
		g.drawString("Tanks  count:"+tanks.size(), 10, 85);
		g.drawString("Tanks  life:"+tk.getLife(), 10, 100);
		g.setColor(c);
		tk.draw(g);
		tk.eat(bd);
		w1.draw(g);
		w2.draw(g);
		bd.draw(g);
	}
	
//	public void update(Graphics g) {
//		if(null==image) {
//			image = this.createImage(WIDTH, HEIGHT);
//		}
//		Graphics goffscreen = image.getGraphics();
//		Color c = goffscreen.getColor();
//		goffscreen.setColor(Color.GREEN);
////		goffscreen.fillRect(0, 0, WIDTH, HEIGHT);
//		goffscreen.drawImage(bg, 0, 0, null);
//		goffscreen.setColor(c);
//		paint(goffscreen);
//		g.drawImage(image, 0, 0, null);
//	}

	public void runWindow() {
		int initTankCount = Integer.parseInt(PropertiesMgr.getProperty("initTankCount"));
		for(int i=0; i<initTankCount; i++) {
			tanks.add(new Tank(60+62*(i+1), 90, false, this));
		}
		
    	this.setTitle("タンク大戦");
    	this.setLocation(230, 50);
    	this.setSize(WIDTH, HEIGHT);
//    	this.setBackground(Color.GREEN);
    	this.addWindowListener(new WindowAdapter(){
    		public void windowClosing(WindowEvent e) {
    			System.exit(0);
    		}
    	});
    	this.setResizable(false);
    	this.setVisible(true);
    	new Thread(new paintThread()).start();
    	this.addKeyListener(new keyMonitor());
    }
    public static void main(String[] args) {
		TankWar tw = new TankWar();
		tw.runWindow();
	}
    private class paintThread implements Runnable {
		public void run() {
			while(true) {
				repaint();
				try {
					Thread.sleep(7);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
    private class keyMonitor extends KeyAdapter {
    	 public void keyPressed(KeyEvent e) {
    		 tk.keyPressed(e);
    	 }
		public void keyReleased(KeyEvent e) {
			tk.keyReleased(e);
			}
		
    	
    }
    private Image offScreenImage = null;
    public void update(Graphics g) {     
        if (offScreenImage == null) {     
            // 截取窗体所在位置的图片     
            offScreenImage = this.createImage(WIDTH, HEIGHT);     
        }     
        // 获得截取图片的画布     
        Graphics gImage = offScreenImage.getGraphics();     
        // 获取画布的底色并且使用这种颜色填充画布（默认的颜色为黑色）     
        Color c = Color.BLACK;     
        gImage.setColor(c);     
        gImage.fillRect(0, 0, WIDTH, HEIGHT); // 有清除上一步图像的功能，相当于gImage.clearRect(0, 0, WIDTH, HEIGHT)     
        // 将截下的图片上的画布传给重绘函数，重绘函数只需要在截图的画布上绘制即可，不必在从底层绘制     
        paint(gImage);     
        //将接下来的图片加载到窗体画布上去，才能考到每次画的效果     
        g.drawImage(offScreenImage, 0, 0, null);     
    }
}


