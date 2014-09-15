package GameObjects;
/**
 * 
 * @author Dan Henton
 *
 */
public class Food implements GameItem{

	
	private int id;
	private int heal;

	public Food(int id , int heal){
		this.id = id;
		this.heal = heal;
		
	}
	public int getObjectID() {
		return id;
	}
	public int getHeal() {
		return heal;
	}
	 
}
