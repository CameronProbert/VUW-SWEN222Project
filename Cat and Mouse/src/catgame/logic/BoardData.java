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
 *         This class holds all of the Rooms in the game and all of the games
 *         Data which is stored in a the ObjectStorer Here we also have a
 *         GameUtil getting initialised for the roomBuilder and communication
 *         ports between the logic and networking
 * 
 * 
 */
public class BoardData {

	private List<Room> allRooms = new ArrayList<Room>();
	private GameUtil gameUtil;
	private ObjectStorer objStorer;

	public BoardData() {
		this.objStorer = new ObjectStorer();
		this.gameUtil = new GameUtil(this, objStorer);
	}

	/**
	 * Add a room to the roomLevels
	 * 
	 * @return
	 */
	public boolean addRoomToLevel(Room room) {
		return this.allRooms.add(room);
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

	/**
	 * Loads the test room for testing the game
	 */
	public void loadTestData() {
		RoomBuilder testRoom = new RoomBuilder(objStorer);
		addRoom(testRoom.loadRoom("SwenProjectRoomTestOne.csv"));
		TESTattachDoors();
	}

	/**
	 * Attaches all of the doors in the test room
	 */
	public void TESTattachDoors() {
		List<Integer> doorsList = new ArrayList<Integer>();
		doorsList.add(431010);
		doorsList.add(0);
		doorsList.add(411012);
		doorsList.add(0);

		doorsList.add(421013);
		doorsList.add(GameUtil.KEY);
		doorsList.add(431011);
		doorsList.add(0);

		for (int i = 0; i < doorsList.size(); i += 2) {
			Room doorsRoom = getDoorsRoom(doorsList.get(i));
			Room doorsExitRoom;
			int direction;
			if (i % 4 == 0) {
				// look Forward in the list
				direction = 2;
				doorsExitRoom = getDoorsRoom(doorsList.get(i + direction));
			} else {
				// look backwards in the list
				direction = -2;
				doorsExitRoom = getDoorsRoom(doorsList.get(i + direction));
			}
			// we now have both doors and their rooms
			Door currentDoor = ((Door) doorsRoom.getDoorsLocation()
					.get(doorsList.get(i)).getObjectOnCell());
			Door exit = (Door) doorsExitRoom.getDoorsLocation()
					.get(doorsList.get(i + direction)).getObjectOnCell();
			currentDoor.addOtherSide(exit, doorsList.get(i + 1));
		}
	}

	/**
	 * 
	 * @param DoorID
	 * @return the Room of a given door
	 */
	public Room getDoorsRoom(int DoorID) {
		for (Room room : allRooms) {
			if (room.getDoorsLocation().containsKey(DoorID)) {
				return room;
			}
		}
		System.out.println("Cannot find Door using DoorsID :" + DoorID);
		return null;
	}

	/**
	 * initialises the first (and only) level in the game
	 * 
	 */
	public void loadLevelOne() {
		String[] roomFiles = { "RoomOne.csv", "RoomTwo.csv", "RoomThree.csv" };
		RoomBuilder builder = new RoomBuilder(objStorer);
		for (int i = 0; i < roomFiles.length; i++) {
			addRoomToLevel(builder.loadRoom(roomFiles[i]));
		}
		attachDoorsForLevelOne();
	}

	/**
	 * Attaches all of the doors in the firstLevel
	 */
	public void attachDoorsForLevelOne() {
		List<Integer> doorsList = new ArrayList<Integer>();
		doorsList.add(401010);
		doorsList.add(0);
		doorsList.add(431011);
		doorsList.add(0);

		doorsList.add(411012);
		doorsList.add(60);
		doorsList.add(401013);
		doorsList.add(0);

		for (int i = 0; i < doorsList.size(); i += 2) {
			Room doorsRoom = getDoorsRoom(doorsList.get(i));
			Room doorsExitRoom;
			int direction;
			if (i % 4 == 0) {
				// look Forward in the list
				direction = 2;
				doorsExitRoom = getDoorsRoom(doorsList.get(i + direction));
			} else {
				// look backwards in the list
				direction = -2;
				doorsExitRoom = getDoorsRoom(doorsList.get(i + direction));
			}
			// we now have both doors and their rooms
			Door currentDoor = ((Door) doorsRoom.getDoorsLocation()
					.get(doorsList.get(i)).getObjectOnCell());
			Door exit = (Door) doorsExitRoom.getDoorsLocation()
					.get(doorsList.get(i + direction)).getObjectOnCell();
			currentDoor.addOtherSide(exit, doorsList.get(i + 1));
		}
	}
}
