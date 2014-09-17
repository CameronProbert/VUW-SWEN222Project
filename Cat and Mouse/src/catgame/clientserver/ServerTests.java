package catgame.clientserver;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.*;

public class ServerTests {

	private int port = 32768; // default 

	@Test
	public void testSockets(){
		ServerSocket ss;
		try {

			ss = new ServerSocket(8080);
			Socket r = new Socket("http://localhost",port);
			Slave slave = new Slave(r);
			slave.run();

			Socket s = ss.accept();
			Master master = new Master(s, 0, 20, null);
			master.start();

			assertTrue(slave.getNumber()==5);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
