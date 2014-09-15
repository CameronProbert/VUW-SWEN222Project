package catgame.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import GameBoard.Room;

public class LogicTests {
@Test
public void testRoomRead(){
	Room testRoom = new Room("TestBoard.csv", "TestBoard.csv");
	assertEquals(20, testRoom.getGroundLayer().length);
	assertEquals(20, testRoom.getGroundLayer()[0].length);
	assertEquals(1,testRoom.getGroundLayer()[0][0]);
	assertEquals(1,testRoom.getObjectLayer()[0][0]);
	assertEquals(39,testRoom.getObjectLayer()[testRoom.getGroundLayer().length-1][testRoom.getGroundLayer()[0].length-1]);
	assertEquals(39,testRoom.getObjectLayer()[testRoom.getGroundLayer().length-1][testRoom.getGroundLayer()[0].length-1]);
}

}
