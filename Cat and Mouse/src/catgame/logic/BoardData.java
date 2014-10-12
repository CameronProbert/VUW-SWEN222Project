package catgame.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import catgame.gameObjects.Door;

/**
 * 
 * @author Dan
 * 
 *         This class holds all of the rooms in the game. TODO Fill out the
 *         boardData
 */
public class BoardData {

	private List<Room> allRooms = new ArrayList<Room>();
	private GameUtil gameUtil;
	private ObjectStorer objStorer;

	public BoardData() {
		this.gameUtil = new GameUtil(this);
		this.objStorer = new ObjectStorer();
	}

	/**
	 * TODO Loading level
	 * 
	 * @return
	 */
	public List<Room> loadLevel() {
		ArrayList toLoad = new ArrayList<Room>();
		
		return toLoad;
	}

	/**
	 * 
	 * @return all of the rooms
	 */
	public List<Room> getAllRooms() {
		return allRooms;
	}

	/**
	 * Populates the allRooms list with a new list of rooms that was created
	 * when game is loaded from a saved game
	 * 
	 * @param newRooms
	 *            List<Room>
	 */
	public void populateRooms(List<Room> newRooms) {
		allRooms = newRooms;
	}

	public void addRoom(Room room) {
		allRooms.add(room);
	}

	public GameUtil getGameUtil() {
		return gameUtil;
	}

	public ObjectStorer getObjStorer() {
		return objStorer;
	}

	public void loadTestData() {
		RoomBuilder testRoom = new RoomBuilder();
		addRoom(testRoom.loadRoom(objStorer));
		TESTattachDoors();
	}

	public void TESTattachDoors() {
		List<Integer> doorsList = new ArrayList<Integer>();
		doorsList.add(431010);
		doorsList.add(411011);
		for (int i = 0; i < doorsList.size(); i++) {
			Room doorsRoom = getDoorsRoom(doorsList.get(i));
			Room doorsExitRoom;
			int direction;
			if (i % 2 == 0) {
				// look Forward in the list
				direction = 1;
				doorsExitRoom = getDoorsRoom(doorsList.get(i + direction));
			} else {
				// look backwards in the list
				direction = -1;
				doorsExitRoom = getDoorsRoom(doorsList.get(i + direction));
			}
			// we now have both doors and their rooms
			Door currentDoor = ((Door) doorsRoom.getDoorsLocation().get(doorsList.get(i)).getObjectOnCell());
			Door exit = (Door) doorsExitRoom.getDoorsLocation().get(doorsList.get(i + direction)).getObjectOnCell();
			currentDoor.addOtherSide(exit, exit.getObjectID());
		}

	}

	public Room getDoorsRoom(int DoorID) {
		for (Room room : allRooms) {
			if (room.getDoorsLocation().containsKey(DoorID)) {
				return room;
			}
		}
		System.out.println("Cannot find Door using DoorsID :" + DoorID);
		return null;
	}
}
