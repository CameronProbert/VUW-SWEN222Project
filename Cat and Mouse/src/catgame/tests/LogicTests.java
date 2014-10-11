package catgame.tests;

import static org.junit.Assert.*;

import java.time.DayOfWeek;

import org.junit.Test;

import catgame.gameObjects.Door;
import catgame.gameObjects.NonPlayableCharacter;
import catgame.gameObjects.PlayableCharacter;
import catgame.logic.BoardData;
import catgame.logic.GameUtil.Direction;
import catgame.logic.GameError;
import catgame.logic.GameUtil;
import catgame.logic.ObjectBuilder;
import catgame.logic.ObjectStorer;
import catgame.logic.Position;
import catgame.logic.Room;
import catgame.logic.RoomBuilder;

public class LogicTests {

	/**
	 * Test that the games public enum Direction is working correctly, and all
	 * enums map to the correct value
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
	 * Test the TranslateForGrid algorithm, Checks that all inputs have a
	 * correct output
	 */
	@Test
	public void testTranslateForGid() {

		Room testRoom = new Room(0);
		// NORTH
		assertEquals(0, testRoom.directionTranslator(Direction.NORTH, Direction.UP));
		assertEquals(1, testRoom.directionTranslator(Direction.NORTH, Direction.RIGHT));
		assertEquals(2, testRoom.directionTranslator(Direction.NORTH, Direction.DOWN));
		assertEquals(3, testRoom.directionTranslator(Direction.NORTH, Direction.LEFT));
		// EAST
		assertEquals(1, testRoom.directionTranslator(Direction.EAST, Direction.UP));
		assertEquals(2, testRoom.directionTranslator(Direction.EAST, Direction.RIGHT));
		assertEquals(3, testRoom.directionTranslator(Direction.EAST, Direction.DOWN));
		assertEquals(0, testRoom.directionTranslator(Direction.EAST, Direction.LEFT));
		// SOUTH
		assertEquals(2, testRoom.directionTranslator(Direction.SOUTH, Direction.UP));
		assertEquals(3, testRoom.directionTranslator(Direction.SOUTH, Direction.RIGHT));
		assertEquals(0, testRoom.directionTranslator(Direction.SOUTH, Direction.DOWN));
		assertEquals(1, testRoom.directionTranslator(Direction.SOUTH, Direction.LEFT));
		// WEST
		assertEquals(3, testRoom.directionTranslator(Direction.WEST, Direction.UP));
		assertEquals(0, testRoom.directionTranslator(Direction.WEST, Direction.RIGHT));
		assertEquals(1, testRoom.directionTranslator(Direction.WEST, Direction.DOWN));
		assertEquals(2, testRoom.directionTranslator(Direction.WEST, Direction.LEFT));
	}

	/**
	 * Tests for GenorateID in the room builder class. All Id's are required to
	 * be 6 digits long if they aren't there will be a gameError Thrown
	 */
	@Test
	public void TestGenorateId() {
		ObjectBuilder ObjBuilder = new ObjectBuilder();
		String lenghtTest = ObjBuilder.genorateObjectId(10, 10, 10) + "";
		assertEquals(6, lenghtTest.length());
		assertEquals(101010, ObjBuilder.genorateObjectId(10, 10, 10));
		try {
			assertFalse(ObjBuilder.genorateObjectId(01, 01, 01) + "".length() == 6);
		} catch (GameError e) {
			return;
		}
		fail();
	}

	@Test
	public void TestGenorateRandomObjectType() {
		ObjectBuilder ObjBuilder = new ObjectBuilder();
		assertEquals(10, ObjBuilder.genorateRandomObjectType(100));
		assertTrue(ObjBuilder.genorateRandomObjectType(5) > 9 && ObjBuilder.genorateRandomObjectType(5) <= 15);
		assertTrue(ObjBuilder.genorateRandomObjectType(3) > 9 && ObjBuilder.genorateRandomObjectType(3) <= 13);
		assertTrue(ObjBuilder.genorateRandomObjectType(1) == 10);
	}

	@Test
	public void TestGetPlayersGrid() {
		ObjectStorer objStore = new ObjectStorer();
		RoomBuilder testBuilder = new RoomBuilder();
		Room testRoom = testBuilder.loadRoom(objStore);
		assertEquals(101010, testRoom.getCharactorCell(101010).getObjectOnCell().getObjectID());
	}

	@Test
	public void TestPlayerAttempedMoveChangeDirection() {
		BoardData gameData = new BoardData();
		gameData.loadTestData();
		gameData.getGameUtil().TESTsetViewDirection(Direction.NORTH);
		Room testRoom = gameData.getAllRooms().get(0);
		// testRoom.
		((PlayableCharacter) testRoom.getCharactorCell(101010).getObjectOnCell()).changeDirection(Direction.NORTH);
		Position preMovePos = testRoom.getCharactorCell(101010).getPosition();
		// we know that the character isn't moving because it is returning -1
		assertEquals(-1, testRoom.movePlayer(101010, Direction.RIGHT, gameData.getGameUtil().getViewDirection()));
		assertEquals(Direction.EAST, ((PlayableCharacter) testRoom.getCharactorCell(101010).getObjectOnCell()).getFacingDirection());
		assertEquals(-1, testRoom.movePlayer(101010, Direction.DOWN, gameData.getGameUtil().getViewDirection()));
		assertEquals(Direction.SOUTH, ((PlayableCharacter) testRoom.getCharactorCell(101010).getObjectOnCell()).getFacingDirection());
		assertEquals(-1, testRoom.movePlayer(101010, Direction.LEFT, gameData.getGameUtil().getViewDirection()));
		assertEquals(Direction.WEST, ((PlayableCharacter) testRoom.getCharactorCell(101010).getObjectOnCell()).getFacingDirection());
		assertEquals(-1, testRoom.movePlayer(101010, Direction.UP, gameData.getGameUtil().getViewDirection()));
		assertEquals(Direction.NORTH, ((PlayableCharacter) testRoom.getCharactorCell(101010).getObjectOnCell()).getFacingDirection());

	}

	@Test
	public void TestPlayerMoveNorth() {
		BoardData gameData = new BoardData();
		gameData.loadTestData();
		gameData.getGameUtil().TESTsetViewDirection(Direction.NORTH);
		Room testRoom = gameData.getAllRooms().get(0);
		// testRoom.
		((PlayableCharacter) testRoom.getCharactorCell(101010).getObjectOnCell()).changeDirection(Direction.NORTH);
		Position preMovePos = testRoom.getCharactorCell(101010).getPosition();
		assertEquals(101010, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell().getObjectID());
		assertEquals(1, testRoom.movePlayer(101010, Direction.UP, gameData.getGameUtil().getViewDirection()));
		assertEquals(null, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell());
		assertEquals(101010, testRoom.getBoardGrid()[preMovePos.getY() - 1][preMovePos.getX()].getObjectOnCell().getObjectID());
	}

	@Test
	public void TestPlayerMoveRight() {
		BoardData gameData = new BoardData();
		gameData.loadTestData();
		gameData.getGameUtil().TESTsetViewDirection(Direction.NORTH);
		Room testRoom = gameData.getAllRooms().get(0);
		// testRoom.
		((PlayableCharacter) testRoom.getCharactorCell(101010).getObjectOnCell()).changeDirection(Direction.EAST);
		Position preMovePos = testRoom.getCharactorCell(101010).getPosition();
		assertEquals(101010, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell().getObjectID());
		assertEquals(1, testRoom.movePlayer(101010, Direction.RIGHT, gameData.getGameUtil().getViewDirection()));
		assertEquals(null, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell());
		assertEquals(101010, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX() + 1].getObjectOnCell().getObjectID());
	}

	@Test
	public void TestPlayerMoveDown() {
		BoardData gameData = new BoardData();
		gameData.loadTestData();
		gameData.getGameUtil().TESTsetViewDirection(Direction.NORTH);
		Room testRoom = gameData.getAllRooms().get(0);
		// testRoom.
		((PlayableCharacter) testRoom.getCharactorCell(101010).getObjectOnCell()).changeDirection(Direction.SOUTH);
		Position preMovePos = testRoom.getCharactorCell(101010).getPosition();
		assertEquals(101010, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell().getObjectID());
		assertEquals(1, testRoom.movePlayer(101010, Direction.DOWN, gameData.getGameUtil().getViewDirection()));
		assertEquals(null, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell());
		assertEquals(101010, testRoom.getBoardGrid()[preMovePos.getY() + 1][preMovePos.getX()].getObjectOnCell().getObjectID());
	}

	@Test
	public void TestPlayerMoveLeft() {
		BoardData gameData = new BoardData();
		gameData.loadTestData();
		gameData.getGameUtil().TESTsetViewDirection(Direction.NORTH);
		Room testRoom = gameData.getAllRooms().get(0);
		// testRoom.
		((PlayableCharacter) testRoom.getCharactorCell(101010).getObjectOnCell()).changeDirection(Direction.WEST);
		Position preMovePos = testRoom.getCharactorCell(101010).getPosition();
		assertEquals(101010, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell().getObjectID());
		assertEquals(1, testRoom.movePlayer(101010, Direction.LEFT, gameData.getGameUtil().getViewDirection()));
		assertEquals(null, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell());
		assertEquals(101010, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX() - 1].getObjectOnCell().getObjectID());
	}

	@Test
	public void TestAttackMinion() {
		BoardData gameData = new BoardData();
		gameData.loadTestData();
		gameData.getGameUtil().TESTsetViewDirection(Direction.NORTH);
		Room testRoom = gameData.getAllRooms().get(0);
		assertEquals(-1, testRoom.movePlayer(101010, Direction.RIGHT, gameData.getGameUtil().getViewDirection()));
		assertEquals(1, testRoom.movePlayer(101010, Direction.RIGHT,gameData.getGameUtil().getViewDirection()));
		assertEquals(1, testRoom.movePlayer(101010, Direction.RIGHT,gameData.getGameUtil().getViewDirection()));
		assertEquals(1, testRoom.movePlayer(101010, Direction.RIGHT,gameData.getGameUtil().getViewDirection()));
		assertEquals(-1, testRoom.movePlayer(101010, Direction.UP,gameData.getGameUtil().getViewDirection()));
		assertEquals(1, testRoom.movePlayer(101010, Direction.UP,gameData.getGameUtil().getViewDirection()));
		assertEquals(1, testRoom.movePlayer(101010, Direction.UP,gameData.getGameUtil().getViewDirection()));
		// We know that the player successfully attacked because of the return 1
		assertEquals(1, testRoom.playerAttack(101010,gameData.getGameUtil().getViewDirection()));
		// checking that he attacked and was attacked back
		assertTrue(((PlayableCharacter) testRoom.getCharactorCell(101010).getObjectOnCell()).getHealth() < 100);
	}
	
	@Test
	public void TestDoor(){
		BoardData gameData = new BoardData();
		gameData.loadTestData();
		gameData.getGameUtil().TESTsetViewDirection(Direction.NORTH);
		Room testRoom = gameData.getAllRooms().get(0);
		assertTrue(testRoom.getBoardGrid()[4][0].getObjectOnCell() instanceof Door);
		assertTrue(testRoom.getBoardGrid()[4][5].getObjectOnCell() instanceof Door);
		((Door)testRoom.getBoardGrid()[4][0].getObjectOnCell()).addOtherSide(testRoom.getBoardGrid()[4][5].getObjectOnCell().getObjectID(),0);
		assertEquals(411011,((Door) testRoom.getBoardGrid()[4][0].getObjectOnCell()).getOtherSide());
	}
}
