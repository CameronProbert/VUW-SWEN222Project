package catgame.logic;

/**
 * 
 * @author Dan Henton
 *
 *Basicly the same class as the point class,
 *Stores an int x & int y position for the game
 */
public class Position {

	
	int x;
	int y;
	/**
	 * @param x
	 * @param y
	 */
	public Position(int x , int y ){
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public String toString(){
		return "x :"+this.x+" y:"+this.y;
	}
	
}
