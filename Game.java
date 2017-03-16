import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class Game implements KeyListener{

	private JFrame jf;
	private boolean left, right, up, down, gameProgress = false, pause = true;
	private int WIDTH = 640, HEIGHT = 480;
	private final int FPS = 1000/60;
	private GraphicsPanel gp;
	
	private Timer time = new Timer(FPS, new ActionListener(){
		public void actionPerformed(ActionEvent e){
			gp.update();
			gp.repaint();
		}
	});
	
	public boolean isOSX() {
		String osName = System.getProperty("os.name");
    	return osName.contains("OS X");
	}
	
	public Game(){
		int osy = 0, osx = 0;
		if (isOSX()){
			osx = 0;
			osy = 22;
		}else{ //Windows
			osx = 6;
			osy = 38;
		}
		//set variables
		jf = new JFrame("Zelda 2.0");
		gp = new GraphicsPanel(WIDTH+osx, HEIGHT+osy);
		jf.add(gp);
		jf.setSize(WIDTH+osx,HEIGHT+osy);
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
		jf.setResizable(false);
		jf.setVisible(true);
		jf.addKeyListener(this);
    	time.setRepeats(true);
		time.start();
	}
	
	public int getWidth(){return WIDTH;}
	public int getHeight(){return HEIGHT;}
	
	@Override
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		switch(key){
			case 27: System.exit(1);
			case 37: gp.setLeft(false);	break;
			case 38: gp.setUp(false);	break;
			case 39: gp.setRight(false);break;
			case 40: gp.setDown(false);	break;
			default: break;
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		switch(key){
			case 27: System.exit(1);
			case 37: gp.setLeft(true);
				if(gp.getPaused()){
					gp.setPanelSelect(gp.getPanelSelect()-1);
					if(gp.getPanelSelect()<1)gp.setPanelSelect(4);
				}
				break;
			case 38: gp.setUp(true);
				if(gp.getGameProgress()==false){
					if(gp.getDifficulty()==1){
						gp.setDifficulty(7);
					}else{gp.setDifficulty(gp.getDifficulty()-1);}
				}
				break;
			case 39: gp.setRight(true);
				if(gp.getPaused()){
					gp.setPanelSelect(gp.getPanelSelect()+1);
					if(gp.getPanelSelect()>4)gp.setPanelSelect(1);
				}
				break;
			case 40: gp.setDown(true);
				if(gp.getGameProgress()==false){
					if(gp.getDifficulty()==7){
						gp.setDifficulty(1);
					}else{gp.setDifficulty(gp.getDifficulty()+1);}
				}
				break;
			case 10:
				if(gp.getGameProgress()==true){
					gp.setPaused(!gp.getPaused());
				}gp.setGameProgress(true);
				break;
			case 88:
				if(gp.getActiveLazer()==false){
					gp.activateLazer();
				}
				break;
			default: break;
		}
	}
	
	@Override public void keyTyped(KeyEvent e){}
	
	public static void main(String[] args){
		System.out.println("\nGame Start");
		new Game();
	}

}