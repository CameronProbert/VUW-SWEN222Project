package catgame.clientserver;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SlaveReceiver {
	
	private boolean locked = true;
	private Slave slave;
	private int uid;
	private NetworkHandler net;
	private final int MASSUPDATE = 35;
	private final int MINORUPDATE = 30;
	
	public SlaveReceiver(Slave slave, GameRunner net){
		this.slave = slave;
		this.net = (NetworkHandler)net;
	}

	public void run(){
		new Thread(r).start();
	}

	Runnable r = new Runnable(){
		public void run() {
			Socket s = slave.getSocket();
			DataInputStream input;
			try {
				input = new DataInputStream(s.getInputStream());

				// First job, is to read the period so we can create the clock				
				uid = input.readInt();		
				System.out.println("reading uid from server");
				// now make new game for the client
				//game = new NetworkHandler(NetworkHandler.Type.CLIENT);
				net.addClientPlayer(uid);

				// now read the other players IDs
				int noPlayers = input.readInt();
				System.out.println("reading noPlayers from server");
				List<Integer> playerIds = new ArrayList<Integer>();

				for(; noPlayers>0; noPlayers--){
					playerIds.add(input.readInt());
					System.out.println("reading player ids from server");
				}

				net.setPlayerIds(playerIds);
				
				while(locked){
					workOutUpdate(input);
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	private void workOutUpdate(DataInputStream input){
		
		try {
			
			int todo = input.readInt();
			if(todo==MINORUPDATE){
				System.out.println("received 30");
				recieveUpdate(input);
			}
			else if (todo==MASSUPDATE){
				System.out.println("received 35");
				recieveMassUpdate(input);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void recieveMassUpdate(DataInputStream input) {
		// TODO Auto-generated method stub
		
	}

	private void recieveUpdate(DataInputStream input) {
		System.out.println("still running");
		try {
			int updateFromMaster = input.readInt();
			if(updateFromMaster!=0){
				net.update(new Update(updateFromMaster), false);// will not record last update
				System.out.println("update recieved to actually use");
			}
			System.out.printf("received update from the server : %d\n", updateFromMaster);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

		

	}

	public void unlock(){
		this.locked=false;
	}

}
