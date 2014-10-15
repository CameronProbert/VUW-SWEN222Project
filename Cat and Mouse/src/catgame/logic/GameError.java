package catgame.logic;

/**
 *
 * @author Dan Henton
 *
 *Error handeler for the CatGame 
 *
 */
public class GameError extends Error{
	public GameError(String msg){
		super(msg);
	}
}
