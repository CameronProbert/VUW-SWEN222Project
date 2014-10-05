package catgame.gameObjects;

public class RightHedge implements NonMovable{
	private final int id;

	public RightHedge(int id) {
		this.id = id;
	}

	public int getObjectID() {
		return id;
	}
	
	public String toString(){
		return "Hedge";
	}
}
