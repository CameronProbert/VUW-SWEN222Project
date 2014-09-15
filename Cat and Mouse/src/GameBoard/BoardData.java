package GameBoard;
/**
 * 
 * @author Dan
 *
 *stores all of the boards data
 */
public class BoardData {
	private int tileCount = 20; 
	private byte[][] groundLayer = new byte[tileCount][tileCount];
	private byte[][] objectLayer = new byte[tileCount*2][tileCount*2];	
	
	public BoardData(){
		
	}
}


