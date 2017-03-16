import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;
import java.util.*;

public class Screen{

	private BufferedImage img[];
	private GraphicsPanel gp;
	private Enemy enemy[];
	private int difficulty = 1;
	private int level;
	private int HEARTS = 0;
	private boolean paused = false, gameProgress = false;
	private int WIDTH, HEIGHT, ticker=0;
	private int maps[][][];
	private int amountEnemies;
	private String map_str[][][];
	private int score;
	
	public int getHP(){return HEARTS;}
	public void setHP(int i){HEARTS=i;}
	
	public Screen(){
		setMultiDimensions();
		img = new BufferedImage[31];
		String _dir = "Sprites/Enviro/";
		try {
			//pics by Alistair!
			img[0] = null;
			img[1] = ImageIO.read(new File(_dir+"Heart.png"));
			img[2] = ImageIO.read(new File(_dir+"Bush1.png"));
			img[3] = ImageIO.read(new File(_dir+"Bush2.png"));
			img[4] = ImageIO.read(new File(_dir+"Bush3.png"));
			img[5] = ImageIO.read(new File(_dir+"Bush4.png"));
			img[6] = ImageIO.read(new File(_dir+"Bush5.png"));
			img[7] = ImageIO.read(new File(_dir+"Rock1.png"));
			img[8] = ImageIO.read(new File(_dir+"Rock3.png"));
			img[9] = ImageIO.read(new File(_dir+"Gem1.png"));
			img[10] = ImageIO.read(new File(_dir+"Gem2.png"));
			img[11] = ImageIO.read(new File(_dir+"Gem3.png"));
			img[12] = ImageIO.read(new File(_dir+"Gem4.png"));
			img[13] = ImageIO.read(new File(_dir+"Gem5.png"));
			img[14] = ImageIO.read(new File(_dir+"Gem6.png"));
			img[15] = ImageIO.read(new File(_dir+"Gem7.png"));
			img[16] = ImageIO.read(new File(_dir+"Gem8.png"));
			img[17] = ImageIO.read(new File(_dir+"Gem9.png"));
			img[18] = ImageIO.read(new File(_dir+"Gem10.png"));
			img[19] = ImageIO.read(new File(_dir+"Fire.png"));
			img[20] = ImageIO.read(new File(_dir+"Circle1.png"));
			img[21] = ImageIO.read(new File(_dir+"Circle2.png"));
			img[22] = ImageIO.read(new File(_dir+"Circle3.png"));
			img[23] = ImageIO.read(new File(_dir+"Dirt1.png"));
			img[24] = ImageIO.read(new File(_dir+"Dirt2.png"));
			img[25] = ImageIO.read(new File(_dir+"Dirt3.png"));
			img[26] = ImageIO.read(new File(_dir+"Shrub1.png"));
			img[27] = ImageIO.read(new File(_dir+"Tree1.png"));
			img[28] = ImageIO.read(new File(_dir+"Tree2.png"));
			img[29] = ImageIO.read(new File(_dir+"Tree3.png"));
			img[30] = ImageIO.read(new File(_dir+"Stairs.png"));
		}catch(Exception e){
			e.printStackTrace();
		}
		HEARTS=5;
		score=1000;
	}
	
	public void setMultiDimensions(){
		BufferedReader bf;
		int amountLevels = 12;
		maps = new int[amountLevels][15][15];
		map_str = new String[amountLevels][15][15];
		try{
			for(int counter1 = 0;counter1<amountLevels;counter1++){
				bf = new BufferedReader(new FileReader(new File("LEVELS/Level"+(counter1+1)+".txt")));
				String line = " ";
				int counter2 = 0;
				while(line!=null&&counter2<map_str[0].length){
					line = bf.readLine();
					String[] input = line.split(",");
					for(int counter3 = 0;counter3<map_str[0][0].length;counter3++){
						if((input[counter3]).length()>1){
							String[] split2input = (input[counter3].split("\\."));
							maps[counter1][counter2][counter3] = Integer.parseInt(split2input[0]);
						}else{
							maps[counter1][counter2][counter3] = Integer.parseInt(""+input[counter3]);
						}
						map_str[counter1][counter2][counter3] = input[counter3];
					}counter2++;
				}
			}
		}catch(Exception e){
			System.out.println("Cannot enter Buffered Reader");
		}
	}
	
	public void setWidth(int i){WIDTH = i;}
	public void setHeight(int i){HEIGHT = i;}
	public int getScore(){return score;}
	public void setProgress(boolean tf){gameProgress=tf;}
	public boolean getProgress(){return gameProgress;}
	public void setPaused(boolean tf){paused=tf;}
	public boolean getPaused(){return paused;}
	
	public String randomMovement(){
		Random random = new Random();
		int randomNum = random.nextInt(9)+1;
		switch(randomNum){
			case 1: return "u";
			case 2: return "d";
			case 3: return "l";
			case 4: return "r";
			case 5: return "ur";
			case 6: return "dr";
			case 7: return "ul";
			case 8: return "dl";
			case 9: return "s";
			default: System.out.println("What the glub happened? randomMovement");return "E";
		}
	}
	
	public void setLevel(int i){
		System.out.println("Level "+i+"!");
		level = i;
		amountEnemies = 0;
		switch(level){
			case 1: 	amountEnemies = 4;break;
			case 6: 	amountEnemies = 4;break;
			case 9: 	amountEnemies = 15;break;
			case 12:	amountEnemies = 104;
			default: System.out.println("This level has no enemies!");break;
		}updateEnemies();
	}	
	
	public void updateEnemies(){
		enemy =  new Enemy[amountEnemies];
		int x = 0, y = 0, counter = -1;
		for(int i = 0;i<15;i++){
		x=0;
			for(int k = 0;k<15;k++){
			String dir = ".";
			int person = 0;
				if(map_str[level-1][i][k].length()>1){
					if(map_str[level-1][i][k].length()>1){
						String[] tmp = map_str[level-1][i][k].split("\\.");
						if(tmp.length==2){
							if(Character.isLetter(tmp[1].charAt(0))){
								person = Integer.parseInt(tmp[1].substring(1,tmp[1].length())+"");
								dir = (tmp[1].charAt(0)+"");	
								counter++;enemy[counter] = new Enemy(x,y,dir,person);
							}
						}
					}
				}x+=32;
			}y+=32;
		}
	}
	
	public int getLevel(){return level;}
	
	public void draw(Graphics g){
		int x = 0, y = 0;
		for(int i = 0;i<15;i++){
			x = 0;
			for(int k = 0;k<15;k++){
				g.drawImage(img[maps[(level-1)][i][k]],x,y,null);
				x+=32;
			}y+=32;
		}
		//draw the enemy..
		for(int i=0;i<enemy.length;i++){
			 enemy[i].draw(g);
		}
	}
	
	public boolean checkCollision(int p1x, int p1y, int p1w, int p1h){
		int x=0,y=0;
		int holder[][] = maps[0];
		holder = maps[(level-1)];
		for(int i=0;i<15;i++){
			x=0;
			for(int j=0;j<15;j++){
				if(holder[i][j]==2 || holder[i][j]==3 || holder[i][j]==4 || holder[i][j]==5 || holder[i][j]==6 || holder[i][j]==7 || holder[i][j]==8 || holder[i][j]==28){
					if(bounding_box_collision(p1x,p1y,p1w,p1h,x,y,32,32)){
						return true;    
					}
				}else if(holder[i][j]==1){
					if(bounding_box_collision(p1x,p1y,p1w,p1h,x,y,32,32)){
						HEARTS++;
						holder[i][j]=0;
					}
				}else if((holder[i][j]==9) || (holder[i][j]==10) || (holder[i][j]==11) || (holder[i][j]==12) || (holder[i][j]==13) || (holder[i][j]==14) || (holder[i][j]==15)
				|| (holder[i][j]==16)|| (holder[i][j]==17)|| (holder[i][j]==18)){
					if(bounding_box_collision(p1x,p1y,p1w,p1h,x,y,32,32)){
						score+=50;
						holder[i][j]=0;
					}
				}x=x+32;
			}y=y+32;
		}return false;
	}
	
	public boolean checkLazerEnemyCollision(int lx, int ly, int lw, int lh){
		for(int k = 0;k<enemy.length;k++){	
			if(enemy[k].getPerson()!=0){
				if(strict_bounding_box_collision(lx,ly,lw,lh,enemy[k].getX(),enemy[k].getY(),32,32)){
					enemy[k] = new Enemy(240,240, "s", 0);
					score+=20;
					return true;
				}
			}
		}return false;
	}
	
	public boolean checkDamageEnemy(int p1x, int p1y, int p1w, int p1h, boolean tf){	
		if(!tf){
			for(int k = 0;k<enemy.length;k++){
				if(enemy[k].getPerson()!=0){
					if(bounding_box_collision(p1x,p1y,p1w,p1h,enemy[k].getX(),enemy[k].getY(),32,32)){
						enemy[k].switchDir();
						enemy[k].setTimerCounter(0);
						HEARTS--;
						return true;
					}
				}
			}
		}return false;
	}
	
	boolean bounding_box_collision(int b1_x, int b1_y, int b1_w, int b1_h, int b2_x, int b2_y, int b2_w, int b2_h){
        if ((b1_x > b2_x + b2_w - 12)  || // is b1 on the right side of b2?
            (b1_y > b2_y + b2_h - 5)   || // is b1 under b2?
            (b2_x > b1_x + b1_w - 12)  || // is b2 on the right side of b1?
            (b2_y > b1_y + b1_h - 5)){    // is b2 under b1?
            return false;// no collision
        }return true;// collision
	}
	
	boolean strict_bounding_box_collision(int b1_x, int b1_y, int b1_w, int b1_h, int b2_x, int b2_y, int b2_w, int b2_h){
        if ((b1_x > b2_x + b2_w)  || // is b1 on the right side of b2?
            (b1_y > b2_y + b2_h)  || // is b1 under b2?
            (b2_x > b1_x + b1_w)  || // is b2 on the right side of b1?
            (b2_y > b1_y + b1_h)){    // is b2 under b1?
            return false;// no collision
        }return true;// collision
	}
	
	public void paint(Graphics g){
		Color myColor = null;
		switch(level){
			case 1: case 2: case 3: case 4: case 5: case 6: case 7: case 8:
				myColor = new Color(101,196,66);
				break;
			case 10: case 11:
				myColor = new Color(105,182,71);
				break;
			case 9: 
				myColor = new Color(186,36,17);
				break;
		}g.setColor(myColor);
		g.fillRect(0, 0, 1000, 1000);
	}
	
	//check enemy positions
	public void processDirection(int i){
		if(enemy[i].getPerson()!=0){
			int wh = enemy[i].getWIDTHHEIGHT();
			String dir = enemy[i].getDirection();
			for(int j = 0;j<dir.length();j++){
			char tmpDir = dir.charAt(j);
			int xx = enemy[i].getX();
			int yy = enemy[i].getY();
				switch(tmpDir){
					case 'u': 
						if((yy-50)>0){
							if(!checkCollision(xx, yy-2, wh, wh)){
								enemy[i].setY(enemy[i].getY()-2);
							}else{enemy[i].setDirection(randomMovement());enemy[i].setTimerCounter(0);}
						}else{enemy[i].switchDir();}
						enemy[i].setAnimate(true);break;
					case 'd': 
						if((yy+50)<HEIGHT){
							if(!checkCollision(xx, yy+2, wh, wh)){
								enemy[i].setY(enemy[i].getY()+2);
							}else{enemy[i].setDirection(randomMovement());enemy[i].setTimerCounter(0);}
						}else{enemy[i].switchDir();}
						enemy[i].setAnimate(true);break;
					case 'l': 
						if((xx-34)>0){
							if(!checkCollision(xx-2, yy, wh, wh)){
								enemy[i].setX(enemy[i].getX()-2);
							}else{enemy[i].setDirection(randomMovement());enemy[i].setTimerCounter(0);}
						}else{enemy[i].switchDir();}
						enemy[i].setAnimate(true);break;
					case 'r':
						if((xx+34)<480){
							if(!checkCollision(xx+2, yy, wh, wh)){
								enemy[i].setX(enemy[i].getX()+2);
							}else{enemy[i].setDirection(randomMovement());enemy[i].setTimerCounter(0);}
						}else{enemy[i].switchDir();}
						enemy[i].setAnimate(true);break;
					case 's': break;
					default: enemy[i].setCurrentFrame(1);break;
				}if((tmpDir!='d' && tmpDir!='r' && tmpDir!='l' && tmpDir!='u' && tmpDir!='s')){
					enemy[i].setAnimate(false);
				}
			}
		}
	}
	
	public void update(){
		if(paused==false && gameProgress==true){
			for(int i = 0;i<enemy.length;i++){
				if(enemy[i].getPerson()!=0){
					enemy[i].setTimerCounter(enemy[i].getTimerCounter()+1);
					processDirection(i);
					Random rand = new Random();
					int rdn = rand.nextInt(155)+50;
					if(enemy[i].getTimerCounter()>rdn){
						enemy[i].setDirection(randomMovement());
						enemy[i].setTimerCounter(0);
					}enemy[i].update();
				}
			}
		}
	}
	
	public String reboundDir(String dir){
		switch(dir.charAt(0)){
			case 'l': return "x";
			case 'r': return "x";
			case 'u': return "y";
			case 'd': return "y";
		}return "";
	}
	
}