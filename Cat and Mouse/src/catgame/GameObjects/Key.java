package catgame.GameObjects;
/**
 * 
 * @author Dan Henton
 *
 */
public class Key implements GameItem{	
	private int id;

	public Key(int id){
		this.id = id;
	}
	
	public int getObjectID() {
		return id;
	}

	@Override
	public boolean use() {
		// TODO Auto-generated method stub
		return false;
	}

}
