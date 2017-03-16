//synopsis: config AI and update directions of enemies
public class Enemy extends Person{	
	public Enemy(int _x, int _y, String _direction, int _person){
		//set initial x and y values
		super(_x, _y, _direction,"Sprites/People/Enemies/Enemy", _person,1,false,false,false,true,0,0,1,10);
	}
}