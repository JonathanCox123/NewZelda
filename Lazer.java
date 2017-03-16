import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;

public class Lazer{
	
	private int x, y, h = 80, w = 20;
	private int screenX, screenY;
	private String direction;
	private boolean isActive, horizontal, vertical;
	private BufferedImage udside, lrside;
	private boolean myLazer;
	
	public Lazer(int width, int height, boolean myLazer){
		screenX = width;
		screenY = height;
		this.myLazer = myLazer;
		isActive = false;
		String _dir = "Sprites/Lazers/";
		try{
			lrside = ImageIO.read(new File(_dir+"lazer_h.png"));
			udside = ImageIO.read(new File(_dir+"lazer_v.png"));
		}catch(Exception e){
			System.out.println("Can not load lazers!!!");
			e.printStackTrace();
		}
	}
	
	public int getX(){return x;}
	public void setX(int i){x=i;}
	public int getY(){return y;}
	public void setY(int i){y=i;}	
	public int getWidth(){return w;}
	public void setWidth(int i){w=i;}
	public int getHeight(){return h;}
	public void getHeight(int i){h=i;}
	public void setActive(boolean tf){isActive=tf;}
	public boolean getActive(){return isActive;}
	public void setDIR(String i){direction=i;}
	public boolean getIsMyLazer(){return myLazer;}
	
	public void keepLazer(){
		if(!(((x+w)<screenX)&&(x>0)&&(y>0)&&((y+h)<screenY))){
			isActive = false;
		}
	}
	
	public void update(){
		if(isActive==true){
			horizontal = false;
			vertical = false;
			switch(direction.charAt(0)){
			//change the w and h situation:
				case 'l': horizontal=true;x-=8;w=32;h=5;break;
				case 'r': horizontal=true;x+=8;w=32;h=5;break;
				case 'u': vertical=true;y-=8;w=5;h=32;break;
				case 'd': vertical=true;y+=8;w=5;h=32;break;
				default: System.out.println("Cannot process lazer dir");break;
			}
		}else{
			setX(-10);
			setY(-10);
		}
	}
	
	//draw the lazer with different x or y update
	public void paint(Graphics g){
		if(horizontal){
			g.drawImage(lrside,x,y,32,32,null);
		}else if(vertical){
			g.drawImage(udside,x,y,32,32,null);
		}keepLazer();//checks if lazer is out of bounds
	}
	
}