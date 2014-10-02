package catgame.clientserver;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SlaveReceiver {
	
	private boolean locked = true;
	private Slave slave;
	
	public SlaveReceiver(Slave slave){
		this.slave = slave;
	}

	public void run(){
		new Thread(r).start();
	}

	Runnable r = new Runnable(){
		public void run() {
			while(locked){
				workOutUpdate();
			}
		}
	};

	private void workOutUpdate(){
		Socket s = slave.getSocket();
		try {
			DataInputStream input = new DataInputStream(s.getInputStream());
			int todo = input.readInt();
			if(todo==30){
				System.out.println("received 30");
				recieveUpdate(input);
			}
			else if (todo==35){
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
			int uid = input.readInt();
			// now read the other players IDs
			int noPlayers = input.readInt();
			List<Integer> playerIds = new ArrayList<Integer>();

			for(; noPlayers>0; noPlayers--){
				playerIds.add(input.readInt());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

		

	}

	public void unlock(){
		this.locked=false;
	}

}
