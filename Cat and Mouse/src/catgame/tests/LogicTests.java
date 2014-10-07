package catgame.tests;

import static org.junit.Assert.*;

import java.awt.Dimension;

import org.junit.Test;

import catgame.gui.renderpanel.RenderPanel;
import catgame.logic.GameUtil.Direction;
import catgame.logic.GameError;
import catgame.logic.GameUtil;
import catgame.logic.Position;
import catgame.logic.Room;
import catgame.logic.RoomBuilder;

public class LogicTests {
	
	/**
	 * Test that the games public enum Direction is working correctly,
	 * and all enums map to the correct value
	 */
	@Test
	public void testDirectionEnum() {
		Direction north = Direction.NORTH;
		assertEquals("NORTH", north.toString());
		assertEquals(0, north.getValue());

		Direction up = Direction.UP;
		assertEquals("UP", up.toString());
		assertEquals(0, up.getValue());

		Direction east = Direction.EAST;
		assertEquals("EAST", east.toString());
		assertEquals(1, east.getValue());

		Direction right = Direction.RIGHT;
		assertEquals("RIGHT", right.toString());
		assertEquals(1, right.getValue());

		Direction south = Direction.SOUTH;
		assertEquals("SOUTH", south.toString());
		assertEquals(2, south.getValue());

		Direction down = Direction.DOWN;
		assertEquals("DOWN", down.toString());
		assertEquals(2, down.getValue());

		Direction west = Direction.WEST;
		assertEquals("WEST", west.toString());
		assertEquals(3, west.getValue());

		Direction left = Direction.LEFT;
		assertEquals("LEFT", left.toString());
		assertEquals(3, left.getValue());
	}

	/**
	 * Test the TranslateForGrid algorithm, Checks that all inputs have a correct output
	 */
	@Test
	public void testTranslateForGid() {
		
		Room testRoom = new Room(0);
		// NORTH
		assertEquals(0, testRoom.translateForGrid(Direction.NORTH, Direction.UP));
		assertEquals(1, testRoom.translateForGrid(Direction.NORTH, Direction.RIGHT));
		assertEquals(2, testRoom.translateForGrid(Direction.NORTH, Direction.DOWN));
		assertEquals(3, testRoom.translateForGrid(Direction.NORTH, Direction.LEFT));
		// EAST
		assertEquals(1, testRoom.translateForGrid(Direction.EAST, Direction.UP));
		assertEquals(2, testRoom.translateForGrid(Direction.EAST, Direction.RIGHT));
		assertEquals(3, testRoom.translateForGrid(Direction.EAST, Direction.DOWN));
		assertEquals(0, testRoom.translateForGrid(Direction.EAST, Direction.LEFT));
		// SOUTH
		assertEquals(2, testRoom.translateForGrid(Direction.SOUTH, Direction.UP));
		assertEquals(3, testRoom.translateForGrid(Direction.SOUTH, Direction.RIGHT));
		assertEquals(0, testRoom.translateForGrid(Direction.SOUTH, Direction.DOWN));
		assertEquals(1, testRoom.translateForGrid(Direction.SOUTH, Direction.LEFT));
		// WEST
		assertEquals(3, testRoom.translateForGrid(Direction.WEST, Direction.UP));
		assertEquals(0, testRoom.translateForGrid(Direction.WEST, Direction.RIGHT));
		assertEquals(1, testRoom.translateForGrid(Direction.WEST, Direction.DOWN));
		assertEquals(2, testRoom.translateForGrid(Direction.WEST, Direction.LEFT));
	}

	/**
	 * Tests for GenorateID in the room builder class. All Id's are required to be 6 digits long
	 * if they aren't there will be a gameError Thrown
	 */
	@Test
	public void TestGenorateId() {
		RoomBuilder testRoom = new RoomBuilder();
		String lenghtTest = testRoom.genorateObjectId(10, 10, 10) + "";
		assertEquals(6, lenghtTest.length());
		assertEquals(101010, testRoom.genorateObjectId(10, 10, 10));
		try{
		assertFalse(testRoom.genorateObjectId(01, 01, 01)+"".length() == 6);
		}catch(GameError e){
			return;
		}
		fail();
	}
	
	@Test
	public void TestGenorateRandomObjectType(){
		RoomBuilder testRoom = new RoomBuilder();
		assertEquals(10, testRoom.genorateRandomObjectType(100));
		assertTrue(testRoom.genorateRandomObjectType(5) > 9 && testRoom.genorateRandomObjectType(5) <= 15);
		assertTrue(testRoom.genorateRandomObjectType(3) > 9 && testRoom.genorateRandomObjectType(3) <= 13);
		assertTrue(testRoom.genorateRandomObjectType(1) == 10);
	}
	
	@Test
	public void TestGetPlayersGrid(){
		RoomBuilder testBuilder = new RoomBuilder();
		Room testRoom = testBuilder.loadRoom();
		assertEquals(101010, testRoom.getCharactorCell(101010).getObjectOnCell().getObjectID());
	}
	
	@Test
	public void TestPlayerMoveNorth(){
		GameUtil util= new GameUtil();
		util.viewDirection = Direction.NORTH;
		RoomBuilder testBuilder = new RoomBuilder();
		Room testRoom = testBuilder.loadRoom();
		//testRoom.
		Position preMovePos = testRoom.getCharactorCell(101010).getPosition();
		assertEquals(101010, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell().getObjectID());
		assertEquals(1, testRoom.movePlayer(101010, Direction.UP));
		assertEquals(null, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell());
		assertEquals(101010, testRoom.getBoardGrid()[preMovePos.getY()-1][preMovePos.getX()].getObjectOnCell().getObjectID());		
	}
}
