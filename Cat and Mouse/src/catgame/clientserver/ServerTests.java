package catgame.clientserver;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Test;

public class ServerTests {
	
	private int port = 32768;
	
	@Test
	public void testOtherClientRecieves(){
		
		ServerSocket ss;

		NetworkHandler server = new NetworkHandler(NetworkHandler.Type.SERVER);
		try {
			ss = new ServerSocket(port);
			
			Socket c1 = new Socket("localhost", port );
			Slave slave1 = new Slave(c1);
			NetworkHandler cR1 = new NetworkHandler(NetworkHandler.Type.CLIENT);
			GameRunner clientRun1 = (GameRunner) cR1;
			SlaveReceiver slaveR1 = new SlaveReceiver(slave1, clientRun1);
			slaveR1.run();
			
			Socket s1 = ss.accept();				
			Master m1 = new Master(s1,1,20,server);
			m1.start();
			
			Socket c2 = new Socket("localhost", port );
			Slave slave2 = new Slave(c2);
			NetworkHandler cR2 = new NetworkHandler(NetworkHandler.Type.CLIENT);
			GameRunner clientRun2 = (GameRunner) cR2;
			SlaveReceiver slaveR2 = new SlaveReceiver(slave2, clientRun1);
			slaveR2.run();
			
			Socket s2 = ss.accept();				
			Master m2 = new Master(s2,2,20,server);
			m2.start();
			
			Update update = new Update(1010000); //  move forward
			System.out.println("my update was : " + update.getCode());
			slave1.sendUpdate(update);
			
			System.out.println("servers last update was : " + server.getLatestUpdate().getCode());
			assertTrue(server.getLatestUpdate().getCode()==01010000);
			
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
