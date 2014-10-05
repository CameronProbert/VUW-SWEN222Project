package catgame.gameObjects;

public class Bush implements NonMovable {

	private final int id;

	public Bush(int id) {
		this.id = id;
	}

	public int getObjectID() {
		return id;
	}
	
	public String toString(){
		return "Bush";
	}
}
