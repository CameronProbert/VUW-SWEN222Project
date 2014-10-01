package catgame.GameObjects;

public class Bush implements NonMovavble {

	private final int id;

	public Bush(int id) {
		this.id = id;
	}

	public int getObjectID() {
		return id;
	}
	
	public String toString(){
		return "Rock";
	}
}
