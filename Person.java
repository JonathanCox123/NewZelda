import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;
import java.util.Random;

//synopsis: set our HERO's x and y and move according to direction and collision methods
public class Person{

	private int x, y, tick, doubleTick, HP, currFrame, maxFrame, slow, counter, person;
	private boolean animate, bullets, sword, isEnemy, invincible;
	private final int maxtick = 1, WIDTH_HEIGHT = 32;
	private long startTime = 0, endTime = 0;
	
	//initialize bufferedimages - like text - but pictures :D
	private BufferedImage up[];
	private BufferedImage down[];
	private BufferedImage right[];
	private BufferedImage left[];
	private String directory, dir;
	private boolean powers[] = new boolean[4];
	
	public Person(int x, int y, String dir, String directory, int person, int HP, boolean bullets, boolean sword, boolean animate, boolean isEnemy, int tick, int doubleTick, int maxFrame, int slow){
	
		//set initial x and y position
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.HP = HP;
		this.invincible = false;

		//hero powers and health
		bullets = false;
		sword = false;
		HP = 5;
		if(!isEnemy){
			this.powers[0] = true;
			this.powers[1] = true;
			this.powers[2] = true;
			//this.powers[3] = true;
		}
		
		//control current frame and slow down feet movement
		this.animate = animate;
		this.isEnemy = isEnemy;
		this.tick = tick;
		this.doubleTick = doubleTick;
		this.currFrame = currFrame;
		this.maxFrame = maxFrame;
		this.slow = slow;
		this.dir = dir;
		up = new BufferedImage[3];
		down = new BufferedImage[3];
		right = new BufferedImage[3];
		left = new BufferedImage[3];
		//read the image files
		this.directory = directory;
		this.person = person;
		if(person!=0 || !isEnemy){
			setArrays();
		}
	}
	
	public void setArrays(){
		try{
			for(int i = 0;i<=2;i++){
				up[i] = ImageIO.read(new File(directory+person+"/Up"+i+".png"));
				down[i] = ImageIO.read(new File(directory+person+"/Down"+i+".png"));
				left[i] = ImageIO.read(new File(directory+person+"/Left"+i+".png"));
				right[i] = ImageIO.read(new File(directory+person+"/Right"+i+".png"));
			}
		}catch(IOException e){
			System.out.println("Could not process our Person Images!! "+directory+person);
		}
	}
	
	//its better than using public variables.. ugh.
	//heres the animation gets and sets
	public void setDirection(String i){dir = i;}
	public String getDirection(){return dir;}
	public void setAnimate(boolean tf){animate = tf;}
	public int getWIDTHHEIGHT(){return WIDTH_HEIGHT;}
	public void setX(int xx){x = xx;}
	public void setY(int yy){y = yy;}
	public int getX(){return x;}	
	public int getY(){return y;}
	
	//health and powers
	public void setHP(int i){HP=i;}
	public int getHP(){return HP;}
	public void setBullets(boolean tf){bullets=tf;}
	public boolean getBullets(){return bullets;}
	public void setSword(boolean tf){sword=tf;}
	public boolean getSword(){return sword;}
	public void setPowers(boolean[] arr){powers = arr;}
	public boolean[] getPowers(){return powers;}
	
	public void setTimerCounter(int i){counter=i;}
	public int getTimerCounter(){return counter;}
	public int getPerson(){return person;}
	public void setPerson(int i){person = i;}
	public void setCurrentFrame(int i){currFrame = i;}
	public void setStartTime(){startTime = System.currentTimeMillis();}
	public void setEndTime(){endTime = System.currentTimeMillis();}
	public long getStartTime(){return startTime;}
	public long getEndTime(){return endTime;}
	
	public void draw(Graphics g){
		if(isEnemy){
			for(int i = 0;i<dir.length();i++){
				//char tmpDir = dir.charAt(i);
				char tmpDir = dir.charAt(0);
				switch(tmpDir){
					case 'U': case 'u': g.drawImage(up[currFrame], x, y, WIDTH_HEIGHT, WIDTH_HEIGHT, null);break;
					case 'D': case 'd': g.drawImage(down[currFrame], x, y, WIDTH_HEIGHT, WIDTH_HEIGHT, null);break;
					case 'L': case 'l': g.drawImage(left[currFrame], x, y, WIDTH_HEIGHT, WIDTH_HEIGHT, null);break;
					case 'R': case 'r': g.drawImage(right[currFrame], x, y, WIDTH_HEIGHT, WIDTH_HEIGHT, null);break;
					case 'S': case 's': Random rand = new Random();
						int rdm = rand.nextInt(4)+1;
						switch(rdm){
							case 1: g.drawImage(right[1], x, y, WIDTH_HEIGHT, WIDTH_HEIGHT, null);break;
							case 2: g.drawImage(right[1], x, y, WIDTH_HEIGHT, WIDTH_HEIGHT, null);break;
							case 3: g.drawImage(right[1], x, y, WIDTH_HEIGHT, WIDTH_HEIGHT, null);break;
							case 4: g.drawImage(right[1], x, y, WIDTH_HEIGHT, WIDTH_HEIGHT, null);break;
							default: System.out.println("Screw the world");break;
						}break;
					default: System.out.println("Cannot DRAW enemy!"+dir);break;
				}
			}
		}else{
			switch(dir.charAt(0)){
				case 'u': g.drawImage(up[currFrame], x, y, WIDTH_HEIGHT, WIDTH_HEIGHT, null);break;
				case 'd': g.drawImage(down[currFrame], x, y, WIDTH_HEIGHT, WIDTH_HEIGHT, null);break;
				case 'l': g.drawImage(left[currFrame], x, y, WIDTH_HEIGHT, WIDTH_HEIGHT, null);break;
				case 'r': g.drawImage(right[currFrame], x, y, WIDTH_HEIGHT, WIDTH_HEIGHT, null);break;
				default: currFrame = 1;break;
			}
		}
		if(person!=0 || !isEnemy){
			g.setColor(Color.WHITE);
			g.drawOval(x-2, y-2, 2*((WIDTH_HEIGHT+4)/2), 2*((WIDTH_HEIGHT+4)/2));
		}
	}
	
	public void update(){
		updateFRAME();
		setEndTime();
		if((endTime-startTime)>=800){
			invincible = false;
		}
	}

	//update the current frame based on movement and time
	public void updateFRAME(){
		if(animate){
			tick++;
			doubleTick++;
			if(tick>maxtick){
				tick=0;
				if(doubleTick%slow==0){
					currFrame++;
				}
				if(currFrame>maxFrame){
					currFrame=0;
				}
			}
		}else{currFrame = 1;}
	}

	public void switchDir(){
		if(dir.equals("u")){
			setDirection("d");
		}else if(dir.equals("d")){
			setDirection("u");
		}else if(dir.equals("l")){
			setDirection("r");
		}else if(dir.equals("r")){
			setDirection("l");
		}else if(dir.equals("dr")){
			setDirection("l");
		}else if(dir.equals("ur")){
			setDirection("l");
		}else if(dir.equals("dl")){
			setDirection("r");
		}else if(dir.equals("ul")){
			setDirection("r");
		}else if(dir.equalsIgnoreCase("s")){
			Random rand = new Random();
			int rdm = rand.nextInt(4)+1;
			switch(rdm){
				case 0: setDirection("r");break;
				case 1: setDirection("d");break;
				case 2: setDirection("u");break;
				case 3: setDirection("l");break;		
			}
		}else{
			System.out.println("Something happened in switchDir: "+dir);
		}
	}

	public void setInvincible(boolean tf){invincible = tf;}
	public boolean isInvincible(){return invincible;}
}