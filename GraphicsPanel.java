import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;

//synopsis: this is the heart of the code - it takes in the screen,
//hero, and enemy classes and updates the jframe
public class GraphicsPanel extends JPanel{

	//directions, game progress, screen settings, and font
	private boolean gameProgress, left, right, up, down, pause;
	private int WIDTH, HEIGHT, difficulty, slideY = 0, slideX;
	private Font psf;
	private Hero hero;
	private Screen screen;
	private int panelSelect = 1;
	private BufferedImage heart;
	private Lazer lazy;
	
	public GraphicsPanel(int w, int h){
		hero = new Hero(1);
		screen = new Screen();
		lazy = new Lazer(480,h,true);
		difficulty = 1;
		WIDTH = w;
		HEIGHT = h;
		slideX = (-1)*WIDTH;
		left = false;
		right = false;
		up = false;
		down = false;
		pause = false;
		superinit();
		screen.setLevel(1);
		screen.setWidth(WIDTH);
		screen.setHeight(HEIGHT);
		//remember to just get this from screen...
		try{
			heart = ImageIO.read(new File("Sprites/Enviro/Heart.png"));
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("ERROR IN GP -  HEART");
		}
	}
	
	//load the font
	public void superinit(){
		loadFont();
	}
	
	public void setPerson(int i){
		hero.setPerson(i);
		hero.setArrays();
	}
	
	public void setSlideX(int i){slideX = i;}
	public int getSlideX(){return slideX;}
	
	public void setDifficulty(int i){difficulty = i;}
	public int getDifficulty(){return difficulty;}
	public void setPanelSelect(int i){panelSelect = i;}
	public int getPanelSelect(){return panelSelect;}
	
	public int getWidth(){return WIDTH;}
	public int getHeight(){return HEIGHT;}
	
	public void setLeft(boolean tf){left=tf;}
	public void setRight(boolean tf){right=tf;}
	public void setUp(boolean tf){up=tf;}
	public void setDown(boolean tf){down=tf;}
	
	public void setPaused(boolean tf){pause = tf;screen.setPaused(!screen.getPaused());}
	public boolean getPaused(){return pause;}
	public void setGameProgress(boolean tf){gameProgress = tf;screen.setProgress(!screen.getProgress());}
	public boolean getGameProgress(){return gameProgress;}
	public boolean getActiveLazer(){return lazy.getActive();}
	
	//make a lazer beam - no more than one at a time for now...
	public void activateLazer(){
		lazy.setActive(true);
		int xx = hero.getX();
		int yy = hero.getY();
		switch(hero.getDirection().charAt(0)){
			case 'l': xx-=37;yy+=12;break;
			case 'r': xx+=37;yy+=12;break;
			case 'u': xx+=12;yy-=37;break;
			case 'd': xx+=12;yy+=35;break;
			default: System.out.println("Cannot set lazer x/y");break;
		}
		if(hero.getDirection().equals("r") || hero.getDirection().equals("d")){
			lazy.setX(xx-10);
			lazy.setY(yy-10);
		}else if(hero.getDirection().equals("u")){
			lazy.setX(xx-10);
			lazy.setY(yy);
		}else if(hero.getDirection().equals("l")){
			lazy.setX(xx);
			lazy.setY(yy-10);
		}
		lazy.setDIR(hero.getDirection());
	}
	
	//an extension of the HEROS hp
	public void setHP(){hero.setHP(screen.getHP());}
	
	//draws the beginning menu - easy, normal, and hard
	//big ol' fat code
	public void drawTitleMenu(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(),getHeight());
		g.setColor(Color.GREEN);
		g.setFont(psf);
		
		int centerAlign = (WIDTH/2)-75;
		int topAlign = 180;
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(10));
		if(difficulty==1){
			g.setColor(Color.WHITE);
			g.drawRect(centerAlign-48,topAlign-25,31,31);
			g.setColor(new Color(123,104,238));
			g.fillRect(centerAlign-47,topAlign-24,30,30);
		}else{g.setColor(Color.WHITE);}
			g.drawString("EASY",centerAlign,topAlign);
			topAlign+=35;
		if(difficulty == 2){
			g.setColor(Color.WHITE);
			g.drawRect(centerAlign-48,topAlign-25,31,31);
			g.setColor(Color.BLUE);
			g.fillRect(centerAlign-47,topAlign-24,30,30);
		}else{g.setColor(Color.WHITE);}
			g.drawString("NORMAL",centerAlign,topAlign);
			topAlign+=35;
		if(difficulty == 3){
			g.setColor(Color.WHITE);
			g.drawRect(centerAlign-48,topAlign-25,31,31);
			g.setColor(Color.GREEN);
			g.fillRect(centerAlign-47,topAlign-24,30,30);
		}else{g.setColor(Color.WHITE);}
			g.drawString("HARD",centerAlign,topAlign);
			topAlign+=35;
		if(difficulty == 4){
			g.setColor(Color.WHITE);
			g.drawRect(centerAlign-48,topAlign-25,31,31);
			g.setColor(Color.RED);
			g.fillRect(centerAlign-47,topAlign-24,30,30);
		}else{g.setColor(Color.WHITE);}
			g.drawString("LUNATIC",centerAlign,topAlign);
			topAlign+=35;
		if(difficulty == 5){
			g.setColor(Color.WHITE);
			g.drawRect(centerAlign-48,topAlign-25,31,31);
			g.setColor(new Color(128,0,128));
			g.fillRect(centerAlign-47,topAlign-24,30,30);
		}else{g.setColor(Color.WHITE);}
			g.drawString("LUNATIC+",centerAlign,topAlign);
			topAlign+=35;
		if(difficulty == 6){
			g.setColor(Color.WHITE);
			g.drawRect(centerAlign-48,topAlign-25,31,31);
			g.setColor(new Color(192,192,192));
			g.fillRect(centerAlign-47,topAlign-24,30,30);
		}else{g.setColor(Color.WHITE);}
			g.drawString("EXTRA",centerAlign,topAlign);
			topAlign+=35;
		if(difficulty == 7){
			g.setColor(Color.WHITE);
			g.drawRect(centerAlign-48,topAlign-25,31,31);
			g.setColor(new Color(255,223,0));
			g.fillRect(centerAlign-47,topAlign-24,30,30);
		}else{g.setColor(Color.WHITE);}
			g.drawString("PHANTASM",centerAlign,topAlign);
			topAlign+=35;
	}
	
	//holder of stats and power keys and score
	public void drawGameMenu(Graphics g){
		//use fontmetrics to get drawstring width
		FontMetrics fm = getFontMetrics(psf.deriveFont(18.0f));
		g.setColor(Color.BLACK);
		g.fillRect(480,0,WIDTH-480,HEIGHT);
		g.setFont(psf.deriveFont(14.0f));
		String setting = "";
		switch(difficulty){
			case 1: g.setColor(new Color(123,104,238));setting="EASY";break;
			case 2: g.setColor(Color.BLUE);setting="NORMAL";break;
			case 3: g.setColor(Color.GREEN);setting="HARD";break;
			case 4: g.setColor(Color.RED);setting="LUNATIC";break;
			case 5: g.setColor(new Color(128,0,128));setting="LUNATIC+";break;
			case 6: g.setColor(new Color(192,192,192));setting="EXTRA";break;
			case 7: g.setColor(new Color(255,223,0));setting="PHANTASM";break;
		}
		int width = fm.stringWidth(setting);
		g.drawString(setting,470+(width/3),60);
		int xx = 490;
		int yy = 70;
		for(int i = 0;i<hero.getHP();i++){
			g.drawImage(heart,xx,yy,28,28,null);
			yy+=30;
		}
		g.setColor(Color.WHITE);
		width = fm.stringWidth(setting);
		String score = "";
		if(screen.getScore()>1000){
			score = ((float)screen.getScore()/1000.0)+"k";
		}else{
			score = screen.getScore()+"";
		}g.setFont(psf.deriveFont(12.0f));
		g.drawString("Score:"+score,470+(width/3),30);
		
	}
	
	//end game credits! when you have beaten boss!
	public void drawCredits(Graphics g){
		//blankity, blankity, blank!
	}
	
	//slider panel when paused
	public int drawSlidePanel(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		setHP();
		g.setColor(Color.BLACK);
		g.fillRect(slideX, slideY, 500, HEIGHT);
		int sx = slideX+30, sy = 10, wh = 80;
		g.setColor(Color.WHITE);
		g2.setStroke(new BasicStroke(2));
		g.drawLine(slideX,120,sx+470,120);
		for(int i=0;i<4;i++){
			switch(panelSelect){
				case 1: g.setColor(Color.BLUE);break;
				case 2: g.setColor(Color.RED);break;
				case 3: g.setColor(Color.GREEN);break;
				case 4: g.setColor(Color.YELLOW);break;
				default: System.out.println("Ugh, we got a problem down in panel pause select");break;
			}
			if(i!=(panelSelect-1)){g.setColor(Color.WHITE);}
			g2.setStroke(new BasicStroke(5));
			g.drawRect(sx,sy,wh,wh);
			g.setColor(Color.BLACK);
			g.fillRect(sx,sy,wh,wh);
			sx+=wh+30;
		}sx = slideX+33;
		sy = 12;
		for(int i = 0;i<hero.getPowers().length;i++){
			BufferedImage thisImage = null;
			try{
				if(hero.getPowers()[i]){
					thisImage = ImageIO.read(new File("Sprites/People/Heroes/Hero"+(i+1)+"/Down1.png"));
				}else{
					thisImage = ImageIO.read(new File("Sprites/Extras/Question_Mark.jpg"));
				}g.drawImage(thisImage, sx, sy, 74, 74, null);
				sx+=110;
			}catch(Exception e){
				System.out.println("Could not draw to panel!");
			}
		}
		if(hero.getPowers()[panelSelect-1]){
			return panelSelect;
		}else{
			return hero.getPerson();
		}
	}
	
	//update method
	//determines if lazer is active and ticks enemy/hero
	public void update(){
		restartGame();
		if(gameProgress && !pause){
			processInput(left,right,up,down);
			hero.update();
			lazy.update();
		}handleLevels();
		screen.update();
		
	}
	
	//sets the screen level and position/direction of HERO - almost as good as new Game() or new Hero
	public void restartGame(){
		if(hero.getHP()==0){
			screen.setHP(5);
			hero.setX(240);
			hero.setY(240);
			screen.setLevel(1);
			hero.setDirection("d");
		}
	}
	
	//paint function: self-explanatory: paints the screen, hero, and enemy in that order
	public void paint(Graphics g){
		if(gameProgress){
			if(pause){
				if(slideX<=-40){
					slideX+=20;
				}
			}else{
				if(slideX>=(-1)*(WIDTH)){
					slideX-=20;
				}
			}
			screen.paint(g);
			screen.draw(g);
			hero.draw(g);
			if(lazy.getActive()){lazy.paint(g);}
			drawGameMenu(g);
			int pick = drawSlidePanel(g);
				hero.setPerson(pick);
				hero.setArrays();
		}else{
			drawTitleMenu(g);
		}
	}
	
	//Please don't delete me, I'll be lonely ;_;<--thx, Brian
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		paint(g);
	}
	
	//load the (ps)Press Start font
	public void loadFont(){
		try {
			File fold = new File("ps.ttf");
			try {
  				psf = Font.createFont(Font.TRUETYPE_FONT, fold);
  				psf = psf.deriveFont(24f);
  			} catch (FontFormatException e){
  				e.printStackTrace();
				psf = new Font("Tahoma", Font.PLAIN, 16);
  			}
  		} catch (IOException e){
  			e.printStackTrace();
			psf = new Font("Tahoma", Font.PLAIN, 16);
  		}
	}
	
	//i need to change this - its too similar to enemy to be out in the open like this...
	//take in the bool and set the currframe capabilities and movement
	public void processInput(boolean l,boolean r,boolean u,boolean d){
			int wh = hero.getWIDTHHEIGHT();
			if(screen.checkLazerEnemyCollision(lazy.getX(), lazy.getY(), lazy.getWidth(), lazy.getHeight())){
				//hero.setHP(hero.getHP()-1);
				lazy.setActive(false);
			}
			if(l){//left
				if(!screen.checkCollision(hero.getX()-5, hero.getY(), wh, wh)){
					hero.setX(hero.getX()-5);
				}hero.setDirection("l");
			}if(r){//right
				if(!screen.checkCollision(hero.getX()+5, hero.getY(), wh, wh)){
					hero.setX(hero.getX()+5);
				}hero.setDirection("r");
			}if(u){//up
				if(!screen.checkCollision(hero.getX(), hero.getY()-5, wh, wh)){
					hero.setY(hero.getY()-5);
				}hero.setDirection("u");
			}if(d){//down
				if(!screen.checkCollision(hero.getX(), hero.getY()+5, wh, wh)){
					hero.setY(hero.getY()+5);
				}hero.setDirection("d");
			}//if not any of these then set animate to false - stand still
			if(!(d||r||l||u)){
				hero.setAnimate(false);
			}else{
				hero.setAnimate(true);
			}
			if(screen.checkDamageEnemy(hero.getX(), hero.getY(), wh, wh, hero.isInvincible())){
				hero.setStartTime();
				hero.setHP(hero.getHP()-1);
				int x = hero.getX();
				int y = hero.getY();
				int index = 1;
				if(hero.getDirection().charAt(0)=='r' || hero.getDirection().charAt(0)=='d'){
					index = -1;
				}
				int i;
				for(i = 0;i<32;i++){
					if(screen.checkCollision((x+(i*index)), (y+(i*index)), wh, wh)){
						break;
					}
				}
				if(screen.reboundDir(hero.getDirection()).equals("x")){
					hero.setX(x+(i*index));
				}else{
					hero.setY(y+(i*index));
				}
				hero.setInvincible(true);
			}hero.update();
	}

	///////////////////
	// LEVEL CHANGES //>>>>seriously - this is gonna be the least-desirable part of the code...:/
	///////////////////
	public void handleLevels(){
		int xx = hero.getX()+32;
		int yy = hero.getY()+32;
		int w = 480;
		
		//start level 1 hubs
		if(screen.getLevel()==1){
			switch(LMLCE(xx,yy,w)){
				case 0: screen.setLevel(7);break;
				case 1: screen.setLevel(9);break;
				case 3: screen.setLevel(2);break;
			}
		}//end level 1 hubs
		//level 2 hubs
		else if(screen.getLevel()==2){
			switch(LMLCE(xx,yy,w)){
				case 2: screen.setLevel(1);break;
				case 3: screen.setLevel(3);break;
			}
		}//end level 2 hubs
		//start level 3
		else if (screen.getLevel()==3) {
			switch(LMLCE(xx,yy,w)){
				case 0: screen.setLevel(6);break;			
				case 1: screen.setLevel(5);break;			
				case 2: screen.setLevel(2);break;			
				case 3: screen.setLevel(4);break;
			}
		}//end level3 hubs
		//start level 4
		else if (screen.getLevel()==4) {
			switch(LMLCE(xx,yy,w)){
				case 2: screen.setLevel(3);break;
			}//from left to right level 3
		}//end levlel 4 hubs
		//start level 5 hubs
		else if (screen.getLevel()==5){
			switch(LMLCE(xx,yy,w)){
				case 0: screen.setLevel(3);break;
			}//from right to level 3
		}//end level 5 hubs
		//start level 6 hubs
		else if ((screen.getLevel()==6)){
			switch(LMLCE(xx,yy,w)){
				case 1: screen.setLevel(3);break;
			}//from left to right level 3
		}//start level 7 hubs
		else if(screen.getLevel()==7){
			switch(LMLCE(xx,yy,w)){
				case 0: screen.setLevel(10);break;
				case 1: screen.setLevel(1);break;
			}
		}else if(screen.getLevel()==9){
			switch(LMLCE(xx,yy,w)){
				case 0: screen.setLevel(1);break;
			}
		}else if(screen.getLevel()==10){
			switch(LMLCE(xx,yy,w)){
				case 1: screen.setLevel(7);break;
				case 2: screen.setLevel(11);break;
			}
		}else if(screen.getLevel()==11){
			switch(LMLCE(xx,yy,w)){
				
				case 3:screen.setLevel(10);break;
			}
		}
	}
		
	//LETS MAKE LEVEL CHANGES EASIER
	public int LMLCE(int xx, int yy, int w){// L.M.L.C.E. lets make level changes easier :D
		//going to the right
		if(xx>w){
			hero.setY((HEIGHT/2)-30);
			hero.setX(25);
			return 0;
		}else if(xx<10){//going to the left
			hero.setX(w-45);
			return 1;
		}else if(yy<10){
			hero.setY(HEIGHT-45);//going up
			hero.setX(w/2);
			return 2;
		}else if(yy>HEIGHT-10){//going down
			hero.setY(45);
			hero.setX(w/2);
			return 3;
		}return 4;
	}
	
}