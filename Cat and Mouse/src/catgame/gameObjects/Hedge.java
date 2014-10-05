package catgame.gameObjects;

public class Hedge {
	private final int id;

	public Hedge(int id) {
		this.id = id;
	}

	public int getObjectID() {
		return id;
	}
	
	public String toString(){
		return "Hedge";
	}
}
