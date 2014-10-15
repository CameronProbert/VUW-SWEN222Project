package catgame.tests;

import static org.junit.Assert.*;

import java.time.DayOfWeek;

import org.junit.Test;

import catgame.gameObjects.Boss;
import catgame.gameObjects.Chest;
import catgame.gameObjects.Door;
import catgame.gameObjects.GameItem;
import catgame.gameObjects.Key;
import catgame.gameObjects.Minion;
import catgame.gameObjects.NonPlayableCharacter;
import catgame.gameObjects.PlayableCharacter;
import catgame.gamestarting.GameRunner.GameState;
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

	private String testRoomFile = "SwenProjectRoomTestOne.csv";
	private int playerOne = 101010;

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
	public void testGenorateId() {
		ObjectBuilder ObjBuilder = new ObjectBuilder();
		String lenghtTest = ObjBuilder.genorateObjectId(10, 10, 10) + "";
		assertEquals(6, lenghtTest.length());
		assertEquals(playerOne, ObjBuilder.genorateObjectId(10, 10, 10));
		try {
			assertFalse(ObjBuilder.genorateObjectId(01, 01, 01) + "".length() == 6);
		} catch (GameError e) {
			return;
		}
		fail();
	}

	@Test
	public void testGenorateRandomObjectType() {
		ObjectBuilder ObjBuilder = new ObjectBuilder();
		assertEquals(10, ObjBuilder.genorateRandomObjectType(100));
		assertTrue(ObjBuilder.genorateRandomObjectType(5) > 9 && ObjBuilder.genorateRandomObjectType(5) <= 15);
		assertTrue(ObjBuilder.genorateRandomObjectType(3) > 9 && ObjBuilder.genorateRandomObjectType(3) <= 13);
		assertTrue(ObjBuilder.genorateRandomObjectType(1) == 10);
	}

	@Test
	public void testGetPlayersGrid() {
		ObjectStorer objStore = new ObjectStorer();
		RoomBuilder testBuilder = new RoomBuilder(objStore);
		Room testRoom = testBuilder.loadRoom();
		assertEquals(playerOne, testRoom.getCharactorCell(playerOne).getObjectOnCell().getObjectID());
	}

	@Test
	public void testPlayerAttempedMoveChangeDirection() {
		BoardData gameData = new BoardData();
		gameData.loadTestData();
		gameData.getGameUtil().TESTsetViewDirection(Direction.NORTH);
		Room testRoom = gameData.getAllRooms().get(0);
		// testRoom.
		((PlayableCharacter) testRoom.getCharactorCell(playerOne).getObjectOnCell()).changeDirection(Direction.NORTH);
		Position preMovePos = testRoom.getCharactorCell(playerOne).getPosition();
		// we know that the character isn't moving because it is returning -1
		assertEquals(-1, testRoom.movePlayer(playerOne, Direction.RIGHT, gameData.getGameUtil().getViewDirection()));
		assertEquals(Direction.EAST, ((PlayableCharacter) testRoom.getCharactorCell(playerOne).getObjectOnCell()).getFacingDirection());
		assertEquals(-1, testRoom.movePlayer(playerOne, Direction.DOWN, gameData.getGameUtil().getViewDirection()));
		assertEquals(Direction.SOUTH, ((PlayableCharacter) testRoom.getCharactorCell(playerOne).getObjectOnCell()).getFacingDirection());
		assertEquals(-1, testRoom.movePlayer(playerOne, Direction.LEFT, gameData.getGameUtil().getViewDirection()));
		assertEquals(Direction.WEST, ((PlayableCharacter) testRoom.getCharactorCell(playerOne).getObjectOnCell()).getFacingDirection());
		assertEquals(-1, testRoom.movePlayer(playerOne, Direction.UP, gameData.getGameUtil().getViewDirection()));
		assertEquals(Direction.NORTH, ((PlayableCharacter) testRoom.getCharactorCell(playerOne).getObjectOnCell()).getFacingDirection());

	}

	// Test the movement for the game
	// if testTranslateForGid() passes then all movements will work correctly on=
	@Test
	public void testPlayerMoveUpNORTH() {
		BoardData gameData = new BoardData();
		gameData.loadTestData();
		gameData.getGameUtil().TESTsetViewDirection(Direction.NORTH);
		Room testRoom = gameData.getAllRooms().get(0);
		// testRoom.
		((PlayableCharacter) testRoom.getCharactorCell(playerOne).getObjectOnCell()).changeDirection(Direction.NORTH);
		Position preMovePos = testRoom.getCharactorCell(playerOne).getPosition();
		assertEquals(playerOne, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell().getObjectID());
		assertEquals(1, gameData.getGameUtil().moveUp(playerOne));
		assertEquals(null, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell());
		assertEquals(playerOne, testRoom.getBoardGrid()[preMovePos.getY() - 1][preMovePos.getX()].getObjectOnCell().getObjectID());
	}

	@Test
	public void testPlayerMoveRightNORTH() {
		BoardData gameData = new BoardData();
		gameData.loadTestData();
		gameData.getGameUtil().TESTsetViewDirection(Direction.NORTH);
		Room testRoom = gameData.getAllRooms().get(0);
		// testRoom.
		((PlayableCharacter) testRoom.getCharactorCell(playerOne).getObjectOnCell()).changeDirection(Direction.EAST);
		Position preMovePos = testRoom.getCharactorCell(playerOne).getPosition();
		assertEquals(playerOne, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell().getObjectID());
		assertEquals(1, gameData.getGameUtil().moveRight(playerOne));
		assertEquals(null, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell());
		assertEquals(playerOne, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX() + 1].getObjectOnCell().getObjectID());
	}

	@Test
	public void testPlayerMoveDownNORTH() {
		BoardData gameData = new BoardData();
		gameData.loadTestData();
		gameData.getGameUtil().TESTsetViewDirection(Direction.NORTH);
		Room testRoom = gameData.getAllRooms().get(0);
		// testRoom.
		((PlayableCharacter) testRoom.getCharactorCell(playerOne).getObjectOnCell()).changeDirection(Direction.SOUTH);
		Position preMovePos = testRoom.getCharactorCell(playerOne).getPosition();
		assertEquals(playerOne, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell().getObjectID());
		assertEquals(1, gameData.getGameUtil().moveDown(playerOne));
		assertEquals(null, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell());
		assertEquals(playerOne, testRoom.getBoardGrid()[preMovePos.getY() + 1][preMovePos.getX()].getObjectOnCell().getObjectID());
	}

	@Test
	public void testPlayerMoveLeftNORTH() {
		BoardData gameData = new BoardData();
		gameData.loadTestData();
		gameData.getGameUtil().TESTsetViewDirection(Direction.NORTH);
		Room testRoom = gameData.getAllRooms().get(0);
		// testRoom.
		((PlayableCharacter) testRoom.getCharactorCell(playerOne).getObjectOnCell()).changeDirection(Direction.WEST);
		Position preMovePos = testRoom.getCharactorCell(playerOne).getPosition();
		assertEquals(playerOne, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell().getObjectID());
		assertEquals(1, gameData.getGameUtil().moveLeft(playerOne));
		assertEquals(null, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell());
		assertEquals(playerOne, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX() - 1].getObjectOnCell().getObjectID());
	}
	
	@Test
	public void testPlayerMoveUpEAST() {
		BoardData gameData = new BoardData();
		gameData.loadTestData();
		gameData.getGameUtil().TESTsetViewDirection(Direction.EAST);
		Room testRoom = gameData.getAllRooms().get(0);
		// testRoom.
		((PlayableCharacter) testRoom.getCharactorCell(playerOne).getObjectOnCell()).changeDirection(Direction.NORTH);
		Position preMovePos = testRoom.getCharactorCell(playerOne).getPosition();
		assertEquals(playerOne, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell().getObjectID());
		assertEquals(-1, gameData.getGameUtil().moveUp(playerOne));
		assertEquals(1, gameData.getGameUtil().moveUp(playerOne));
		assertEquals(null, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell());
		assertEquals(playerOne, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()+1].getObjectOnCell().getObjectID());
	}

	@Test
	public void testPlayerMoveRightEAST() {
		BoardData gameData = new BoardData();
		gameData.loadTestData();
		gameData.getGameUtil().TESTsetViewDirection(Direction.EAST);
		Room testRoom = gameData.getAllRooms().get(0);
		// testRoom.
		((PlayableCharacter) testRoom.getCharactorCell(playerOne).getObjectOnCell()).changeDirection(Direction.EAST);
		Position preMovePos = testRoom.getCharactorCell(playerOne).getPosition();
		assertEquals(playerOne, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell().getObjectID());
		assertEquals(-1, gameData.getGameUtil().moveRight(playerOne));
		assertEquals(1, gameData.getGameUtil().moveRight(playerOne));
		assertEquals(null, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell());
		assertEquals(playerOne, testRoom.getBoardGrid()[preMovePos.getY()+1][preMovePos.getX()].getObjectOnCell().getObjectID());
	}

	@Test
	public void testPlayerMoveDownEAST() {
		BoardData gameData = new BoardData();
		gameData.loadTestData();
		gameData.getGameUtil().TESTsetViewDirection(Direction.EAST);
		Room testRoom = gameData.getAllRooms().get(0);
		// testRoom.
		((PlayableCharacter) testRoom.getCharactorCell(playerOne).getObjectOnCell()).changeDirection(Direction.SOUTH);
		Position preMovePos = testRoom.getCharactorCell(playerOne).getPosition();
		assertEquals(playerOne, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell().getObjectID());
		assertEquals(-1, gameData.getGameUtil().moveDown(playerOne));
		assertEquals(1, gameData.getGameUtil().moveDown(playerOne));
		assertEquals(null, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell());
		assertEquals(playerOne, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()-1].getObjectOnCell().getObjectID());
	}

	@Test
	public void testPlayerMoveLeftEAST() {
		BoardData gameData = new BoardData();
		gameData.loadTestData();
		gameData.getGameUtil().TESTsetViewDirection(Direction.EAST);
		Room testRoom = gameData.getAllRooms().get(0);
		// testRoom.
		((PlayableCharacter) testRoom.getCharactorCell(playerOne).getObjectOnCell()).changeDirection(Direction.WEST);
		Position preMovePos = testRoom.getCharactorCell(playerOne).getPosition();
		assertEquals(playerOne, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell().getObjectID());
		assertEquals(-1, gameData.getGameUtil().moveLeft(playerOne));
		assertEquals(1, gameData.getGameUtil().moveLeft(playerOne));
		assertEquals(null, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell());
		assertEquals(playerOne, testRoom.getBoardGrid()[preMovePos.getY()-1][preMovePos.getX()].getObjectOnCell().getObjectID());
	}

	@Test
	public void testPlayerMoveUpSOUTH() {
		BoardData gameData = new BoardData();
		gameData.loadTestData();
		gameData.getGameUtil().TESTsetViewDirection(Direction.SOUTH);
		Room testRoom = gameData.getAllRooms().get(0);
		// testRoom.
		((PlayableCharacter) testRoom.getCharactorCell(playerOne).getObjectOnCell()).changeDirection(Direction.NORTH);
		Position preMovePos = testRoom.getCharactorCell(playerOne).getPosition();
		assertEquals(playerOne, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell().getObjectID());
		assertEquals(-1, gameData.getGameUtil().moveUp(playerOne));
		assertEquals(1, gameData.getGameUtil().moveUp(playerOne));
		assertEquals(null, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell());
		assertEquals(playerOne, testRoom.getBoardGrid()[preMovePos.getY() + 1][preMovePos.getX()].getObjectOnCell().getObjectID());
	}

	@Test
	public void testPlayerMoveRightSOUTH() {
		BoardData gameData = new BoardData();
		gameData.loadTestData();
		gameData.getGameUtil().TESTsetViewDirection(Direction.SOUTH);
		Room testRoom = gameData.getAllRooms().get(0);
		// testRoom.
		((PlayableCharacter) testRoom.getCharactorCell(playerOne).getObjectOnCell()).changeDirection(Direction.EAST);
		Position preMovePos = testRoom.getCharactorCell(playerOne).getPosition();
		assertEquals(playerOne, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell().getObjectID());
		assertEquals(-1, gameData.getGameUtil().moveRight(playerOne));
		assertEquals(1, gameData.getGameUtil().moveRight(playerOne));
		assertEquals(null, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell());
		assertEquals(playerOne, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX() - 1].getObjectOnCell().getObjectID());
	}

	@Test
	public void testPlayerMoveDownSOUTH() {
		BoardData gameData = new BoardData();
		gameData.loadTestData();
		gameData.getGameUtil().TESTsetViewDirection(Direction.SOUTH);
		Room testRoom = gameData.getAllRooms().get(0);
		// testRoom.
		((PlayableCharacter) testRoom.getCharactorCell(playerOne).getObjectOnCell()).changeDirection(Direction.SOUTH);
		Position preMovePos = testRoom.getCharactorCell(playerOne).getPosition();
		assertEquals(playerOne, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell().getObjectID());
		assertEquals(-1, gameData.getGameUtil().moveDown(playerOne));
		assertEquals(1, gameData.getGameUtil().moveDown(playerOne));
		assertEquals(null, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell());
		assertEquals(playerOne, testRoom.getBoardGrid()[preMovePos.getY() - 1][preMovePos.getX()].getObjectOnCell().getObjectID());
	}

	@Test
	public void testPlayerMoveLeftSOUTH() {
		BoardData gameData = new BoardData();
		gameData.loadTestData();
		gameData.getGameUtil().TESTsetViewDirection(Direction.SOUTH);
		Room testRoom = gameData.getAllRooms().get(0);
		// testRoom.
		((PlayableCharacter) testRoom.getCharactorCell(playerOne).getObjectOnCell()).changeDirection(Direction.WEST);
		Position preMovePos = testRoom.getCharactorCell(playerOne).getPosition();
		assertEquals(playerOne, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell().getObjectID());
		assertEquals(-1, gameData.getGameUtil().moveLeft(playerOne));
		assertEquals(1, gameData.getGameUtil().moveLeft(playerOne));
		assertEquals(null, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell());
		assertEquals(playerOne, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX() + 1].getObjectOnCell().getObjectID());
	}
	
	@Test
	public void testPlayerMoveUpWEST() {
		BoardData gameData = new BoardData();
		gameData.loadTestData();
		gameData.getGameUtil().TESTsetViewDirection(Direction.WEST);
		Room testRoom = gameData.getAllRooms().get(0);
		// testRoom.
		((PlayableCharacter) testRoom.getCharactorCell(playerOne).getObjectOnCell()).changeDirection(Direction.NORTH);
		Position preMovePos = testRoom.getCharactorCell(playerOne).getPosition();
		assertEquals(playerOne, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell().getObjectID());
		assertEquals(-1, gameData.getGameUtil().moveUp(playerOne));
		assertEquals(1, gameData.getGameUtil().moveUp(playerOne));
		assertEquals(null, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell());
		assertEquals(playerOne, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()-1].getObjectOnCell().getObjectID());
	}

	@Test
	public void testPlayerMoveRightWEST() {
		BoardData gameData = new BoardData();
		gameData.loadTestData();
		gameData.getGameUtil().TESTsetViewDirection(Direction.WEST);
		Room testRoom = gameData.getAllRooms().get(0);
		// testRoom.
		((PlayableCharacter) testRoom.getCharactorCell(playerOne).getObjectOnCell()).changeDirection(Direction.EAST);
		Position preMovePos = testRoom.getCharactorCell(playerOne).getPosition();
		assertEquals(playerOne, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell().getObjectID());
		assertEquals(-1, gameData.getGameUtil().moveRight(playerOne));
		assertEquals(1, gameData.getGameUtil().moveRight(playerOne));
		assertEquals(null, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell());
		assertEquals(playerOne, testRoom.getBoardGrid()[preMovePos.getY()-1][preMovePos.getX()].getObjectOnCell().getObjectID());
	}

	@Test
	public void testPlayerMoveDownWEST() {
		BoardData gameData = new BoardData();
		gameData.loadTestData();
		gameData.getGameUtil().TESTsetViewDirection(Direction.WEST);
		Room testRoom = gameData.getAllRooms().get(0);
		// testRoom.
		((PlayableCharacter) testRoom.getCharactorCell(playerOne).getObjectOnCell()).changeDirection(Direction.SOUTH);
		Position preMovePos = testRoom.getCharactorCell(playerOne).getPosition();
		assertEquals(playerOne, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell().getObjectID());
		assertEquals(-1, gameData.getGameUtil().moveDown(playerOne));
		assertEquals(1, gameData.getGameUtil().moveDown(playerOne));
		assertEquals(null, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell());
		assertEquals(playerOne, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()+1].getObjectOnCell().getObjectID());
	}

	@Test
	public void testPlayerMoveLeftWEST() {
		BoardData gameData = new BoardData();
		gameData.loadTestData();
		gameData.getGameUtil().TESTsetViewDirection(Direction.WEST);
		Room testRoom = gameData.getAllRooms().get(0);
		// testRoom.
		((PlayableCharacter) testRoom.getCharactorCell(playerOne).getObjectOnCell()).changeDirection(Direction.WEST);
		Position preMovePos = testRoom.getCharactorCell(playerOne).getPosition();
		assertEquals(playerOne, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell().getObjectID());
		assertEquals(-1, gameData.getGameUtil().moveLeft(playerOne));
		assertEquals(1, gameData.getGameUtil().moveLeft(playerOne));
		assertEquals(null, testRoom.getBoardGrid()[preMovePos.getY()][preMovePos.getX()].getObjectOnCell());
		assertEquals(playerOne, testRoom.getBoardGrid()[preMovePos.getY()+1][preMovePos.getX()].getObjectOnCell().getObjectID());
	}
	
	@Test
	public void testAttackMinion() {
		BoardData gameData = new BoardData();
		gameData.loadTestData();
		gameData.getGameUtil().TESTsetViewDirection(Direction.NORTH);
		Room testRoom = gameData.getAllRooms().get(0);
		assertEquals(-1, testRoom.movePlayer(playerOne, Direction.RIGHT, gameData.getGameUtil().getViewDirection()));
		assertEquals(1, testRoom.movePlayer(playerOne, Direction.RIGHT, gameData.getGameUtil().getViewDirection()));
		assertEquals(1, testRoom.movePlayer(playerOne, Direction.RIGHT, gameData.getGameUtil().getViewDirection()));
		assertEquals(1, testRoom.movePlayer(playerOne, Direction.RIGHT, gameData.getGameUtil().getViewDirection()));
		assertEquals(-1, testRoom.movePlayer(playerOne, Direction.UP, gameData.getGameUtil().getViewDirection()));
		assertEquals(1, testRoom.movePlayer(playerOne, Direction.UP, gameData.getGameUtil().getViewDirection()));
		assertEquals(1, testRoom.movePlayer(playerOne, Direction.UP, gameData.getGameUtil().getViewDirection()));
		// We know that the player successfully attacked because of the return 1
		assertEquals(1, testRoom.playerAction(playerOne, gameData.getGameUtil().getViewDirection()));
		// checking that he attacked and was attacked back
		assertTrue(((PlayableCharacter) testRoom.getCharactorCell(playerOne).getObjectOnCell()).getHealth() < 100);
	}

	@Test
	public void testLootChest() {
		BoardData gameData = new BoardData();
		gameData.loadTestData();
		gameData.getGameUtil().TESTsetViewDirection(Direction.NORTH);
		Room testRoom = gameData.getAllRooms().get(0);
		assertEquals(-1, testRoom.movePlayer(playerOne, Direction.LEFT, gameData.getGameUtil().getViewDirection()));
		assertEquals(1, testRoom.movePlayer(playerOne, Direction.LEFT, gameData.getGameUtil().getViewDirection()));
		assertEquals(-1, testRoom.movePlayer(playerOne, Direction.UP, gameData.getGameUtil().getViewDirection()));
		assertEquals(1, testRoom.movePlayer(playerOne, Direction.UP, gameData.getGameUtil().getViewDirection()));
		assertTrue(testRoom.getObjectAheadOfCharactor(playerOne, gameData.getGameUtil().getViewDirection()) instanceof Chest);
		assertEquals(1, testRoom.PlayerLoot(playerOne, gameData.getGameUtil().getViewDirection()));
		assertEquals(3, testRoom.getPlayerInRoom(playerOne).getInventory().size());
	}
	

	@Test
	public void testLootDeadMinion() {
		BoardData gameData = new BoardData();
		gameData.loadTestData();
		gameData.getGameUtil().TESTsetViewDirection(Direction.NORTH);
		Room testRoom = gameData.getAllRooms().get(0);
		assertEquals(-1, testRoom.movePlayer(playerOne, Direction.RIGHT, gameData.getGameUtil().getViewDirection()));
		assertEquals(1, testRoom.movePlayer(playerOne, Direction.RIGHT, gameData.getGameUtil().getViewDirection()));
		assertEquals(1, testRoom.movePlayer(playerOne, Direction.RIGHT, gameData.getGameUtil().getViewDirection()));
		assertEquals(1, testRoom.movePlayer(playerOne, Direction.RIGHT, gameData.getGameUtil().getViewDirection()));
		assertEquals(-1, testRoom.movePlayer(playerOne, Direction.UP, gameData.getGameUtil().getViewDirection()));
		assertEquals(1, testRoom.movePlayer(playerOne, Direction.UP, gameData.getGameUtil().getViewDirection()));
		assertEquals(1, testRoom.movePlayer(playerOne, Direction.UP, gameData.getGameUtil().getViewDirection()));
		// We know that the player successfully attacked because of the return 1
		assertEquals(1, testRoom.playerAction(playerOne, gameData.getGameUtil().getViewDirection()));
		// try and loot an alive body
		assertTrue(testRoom.getObjectAheadOfCharactor(playerOne, gameData.getGameUtil().getViewDirection()) instanceof Minion);
		assertEquals(-1, testRoom.PlayerLoot(playerOne, gameData.getGameUtil().getViewDirection()));
		assertEquals(1, testRoom.getPlayerInRoom(playerOne).getInventory().size());
		// kill it
		assertEquals(1, testRoom.playerAction(playerOne, gameData.getGameUtil().getViewDirection()));
		assertEquals(1, testRoom.playerAction(playerOne, gameData.getGameUtil().getViewDirection()));
		assertEquals(-1, testRoom.playerAction(playerOne, gameData.getGameUtil().getViewDirection()));
		// minion is now dead now loot it
		assertTrue(testRoom.getObjectAheadOfCharactor(playerOne, gameData.getGameUtil().getViewDirection()) instanceof Minion);
		assertEquals(1, testRoom.PlayerLoot(playerOne, gameData.getGameUtil().getViewDirection()));
		assertEquals(2, testRoom.getPlayerInRoom(playerOne).getInventory().size());

	}

	@Test
	public void testLootDeadBoss() {
		BoardData gameData = new BoardData();
		gameData.loadTestData();
		gameData.getGameUtil().TESTsetViewDirection(Direction.NORTH);
		Room testRoom = gameData.getAllRooms().get(0);
		assertEquals(-1, testRoom.movePlayer(playerOne, Direction.RIGHT, gameData.getGameUtil().getViewDirection()));
		assertEquals(1, testRoom.movePlayer(playerOne, Direction.RIGHT, gameData.getGameUtil().getViewDirection()));
		assertEquals(1, testRoom.movePlayer(playerOne, Direction.RIGHT, gameData.getGameUtil().getViewDirection()));
		assertEquals(-1, testRoom.movePlayer(playerOne, Direction.UP, gameData.getGameUtil().getViewDirection()));
		assertEquals(1, testRoom.movePlayer(playerOne, Direction.UP, gameData.getGameUtil().getViewDirection()));
		assertEquals(1, testRoom.movePlayer(playerOne, Direction.UP, gameData.getGameUtil().getViewDirection()));
		// We know that the player successfully attacked because of the return 1
		assertEquals(1, testRoom.playerAction(playerOne, gameData.getGameUtil().getViewDirection()));
		// try and loot an alive body
		assertTrue(testRoom.getObjectAheadOfCharactor(playerOne, gameData.getGameUtil().getViewDirection()) instanceof Boss);
		assertEquals(-1, testRoom.PlayerLoot(playerOne, gameData.getGameUtil().getViewDirection()));
		assertEquals(1, testRoom.getPlayerInRoom(playerOne).getInventory().size());
		// kill it
		assertEquals(1, testRoom.playerAction(playerOne, gameData.getGameUtil().getViewDirection()));
		assertEquals(1, testRoom.playerAction(playerOne, gameData.getGameUtil().getViewDirection()));
		assertEquals(1, testRoom.playerAction(playerOne, gameData.getGameUtil().getViewDirection()));
		assertEquals(1, testRoom.playerAction(playerOne, gameData.getGameUtil().getViewDirection()));
		assertEquals(1, testRoom.playerAction(playerOne, gameData.getGameUtil().getViewDirection()));
		assertEquals(1, testRoom.playerAction(playerOne, gameData.getGameUtil().getViewDirection()));
		assertEquals(1, testRoom.playerAction(playerOne, gameData.getGameUtil().getViewDirection()));
		assertEquals(1, testRoom.playerAction(playerOne, gameData.getGameUtil().getViewDirection()));
		assertEquals(-1, testRoom.playerAction(playerOne, gameData.getGameUtil().getViewDirection()));
		// minion is now dead now loot it
		assertTrue(testRoom.getObjectAheadOfCharactor(playerOne, gameData.getGameUtil().getViewDirection()) instanceof Boss);
		assertEquals(1, testRoom.PlayerLoot(playerOne, gameData.getGameUtil().getViewDirection()));
		assertEquals(3, testRoom.getPlayerInRoom(playerOne).getInventory().size());

	}

	@Test
	public void testFindItemInObjectStorer() {
		BoardData gameData = new BoardData();
		gameData.loadTestData();
		// find a key
		assertEquals(601010, gameData.getObjStorer().findItemInGame(601010).getObjectID());
	}

	@Test
	public void testFindPlayerInObjectStorer() {
		BoardData gameData = new BoardData();
		gameData.loadTestData();
		assertEquals(playerOne, gameData.getObjStorer().findCharacter(playerOne).getObjectID());
	}

	@Test
	public void testFindNoPlayerInObjectStorer() {
		BoardData gameData = new BoardData();
		gameData.loadTestData();
		try {
			gameData.getObjStorer().findCharacter(101011).getObjectID();
			fail();
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	public void testFindNpcInObjectStorer() {
		BoardData gameData = new BoardData();
		gameData.loadTestData();
		assertEquals(141010, gameData.getObjStorer().findNCP(141010).getObjectID());
	}

	@Test
	public void testPlayerEat() {
		BoardData gameData = new BoardData();
		gameData.loadTestData();
		gameData.getGameUtil().TESTsetViewDirection(Direction.NORTH);
		Room testRoom = gameData.getAllRooms().get(0);
		((PlayableCharacter) testRoom.getCharactorCell(playerOne).getObjectOnCell()).changeHealth(-20);
		assertEquals(80, ((PlayableCharacter) testRoom.getCharactorCell(playerOne).getObjectOnCell()).getHealth());
		assertEquals(1, ((PlayableCharacter) testRoom.getCharactorCell(playerOne).getObjectOnCell()).eat(801014));
		assertEquals(100, ((PlayableCharacter) testRoom.getCharactorCell(playerOne).getObjectOnCell()).getHealth());
	}

	@Test
	public void testGetPlayersRoom() {
		BoardData gameData = new BoardData();
		gameData.loadTestData();
		gameData.getGameUtil().TESTsetViewDirection(Direction.NORTH);
		Room testRoom = gameData.getAllRooms().get(0);
		assertEquals(testRoom, gameData.getGameUtil().findPlayersRoom(playerOne));
	}

	@Test
	public void testGetPlayerInRoom() {
		BoardData gameData = new BoardData();
		gameData.loadTestData();
		gameData.getGameUtil().TESTsetViewDirection(Direction.NORTH);
		Room testRoom = gameData.getAllRooms().get(0);
		assertEquals(testRoom, gameData.getGameUtil().findPlayersRoom(playerOne));
		assertEquals(playerOne, testRoom.getCharactorCell(playerOne).getObjectOnCell().getObjectID());
	}

	@Test
	public void testUseDoor() {
		BoardData gameData = new BoardData();
		gameData.loadTestData();
		gameData.getGameUtil().TESTsetViewDirection(Direction.NORTH);
		Room testRoom = gameData.getAllRooms().get(0);
		assertEquals(-1, testRoom.movePlayer(playerOne, Direction.DOWN, gameData.getGameUtil().getViewDirection()));
		assertEquals(1, testRoom.movePlayer(playerOne, Direction.DOWN, gameData.getGameUtil().getViewDirection()));
		assertEquals(-1, testRoom.movePlayer(playerOne, Direction.RIGHT, gameData.getGameUtil().getViewDirection()));
		assertEquals(1, testRoom.movePlayer(playerOne, Direction.RIGHT, gameData.getGameUtil().getViewDirection()));
		assertEquals(1, testRoom.movePlayer(playerOne, Direction.RIGHT, gameData.getGameUtil().getViewDirection()));
		testRoom.movePlayer(playerOne, Direction.RIGHT, gameData.getGameUtil().getViewDirection());
		// check to see if the player is on the other side of the door/room goes
		// from being at x:4 y:4 to...
		assertEquals(1, testRoom.getCharactorCell(playerOne).getPosition().getX());
		assertEquals(3, testRoom.getCharactorCell(playerOne).getPosition().getY());
	}

	@Test
	public void testLockedDoors() {
		BoardData gameData = new BoardData();
		gameData.loadTestData();
		gameData.getGameUtil().TESTsetViewDirection(Direction.NORTH);
		Room testRoom = gameData.getAllRooms().get(0);
		assertEquals(-1, testRoom.movePlayer(playerOne, Direction.RIGHT, gameData.getGameUtil().getViewDirection()));
		assertEquals(1, testRoom.movePlayer(playerOne, Direction.RIGHT, gameData.getGameUtil().getViewDirection()));
		assertEquals(-1, testRoom.movePlayer(playerOne, Direction.DOWN, gameData.getGameUtil().getViewDirection()));
		assertEquals(1, testRoom.movePlayer(playerOne, Direction.DOWN, gameData.getGameUtil().getViewDirection()));
		assertEquals(-1, testRoom.movePlayer(playerOne, Direction.DOWN, gameData.getGameUtil().getViewDirection())); //locked door is returning the -1
		((PlayableCharacter) testRoom.getCharactorCell(playerOne).getObjectOnCell()).addToInventory(new Key(601010));
		testRoom.movePlayer(playerOne, Direction.DOWN, gameData.getGameUtil().getViewDirection());
		assertEquals(1, testRoom.getCharactorCell(playerOne).getPosition().getX());
		assertEquals(4, testRoom.getCharactorCell(playerOne).getPosition().getY());
	}
}
