package catgame.gameObjects;

public class LeftHedge implements NonMovable {
	private final int id;

	public LeftHedge(int id) {
		this.id = id;
	}

	public int getObjectID() {
		return id;
	}
	
	public String toString(){
		return "Hedge";
	}
}
