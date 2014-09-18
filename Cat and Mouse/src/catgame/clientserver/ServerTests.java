package catgame.clientserver;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import org.junit.*;

public class ServerTests {

	private int port = 32768; // default 

	@Test
	public void testSockets(){
		ServerSocket ss;
		try {


			ss = new ServerSocket(port);
			Socket r = new Socket("localhost",port);
			Socket s = ss.accept();
			Master master = new Master(s, 5, 20, null); // uid is five
			master.start();
			Slave slave = new Slave(r);
			slave.run();

			assertTrue(slave.getUID()==5);
			assertTrue(numbersMatch(slave.getNumbers()));


		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testManySockets(){

		ServerSocket ss;
		try {

			ss = new ServerSocket(port);

			Socket s1 = new Socket("localhost",port);
			startMaster(1, ss);
			Slave slave1 = new Slave(s1);
			slave1.run();

			Socket s2 = new Socket("localhost",port);
			startMaster(2, ss);
			Slave slave2 = new Slave(s2);
			slave2.run();

			Socket s3 = new Socket("localhost",port);
			startMaster(3, ss);
			Slave slave3 = new Slave(s3);
			slave3.run();

			Socket s4 = new Socket("localhost",port);
			startMaster(4, ss);
			Slave slave4 = new Slave(s4);
			slave4.run();

			Socket s5 = new Socket("localhost",port);
			startMaster(5, ss);
			Slave slave5 = new Slave(s5);
			slave5.run();



			assertTrue(slave1.getUID()==1);
			assertTrue(slave2.getUID()==2);
			assertTrue(slave3.getUID()==3);
			assertTrue(slave4.getUID()==4);
			assertTrue(slave5.getUID()==5);
			assertTrue(numbersMatch(slave1.getNumbers()));
			assertTrue(numbersMatch(slave2.getNumbers()));
			assertTrue(numbersMatch(slave3.getNumbers()));
			assertTrue(numbersMatch(slave4.getNumbers()));
			assertTrue(numbersMatch(slave5.getNumbers()));

			ss.close();


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private void startMaster(int id, ServerSocket ss){
		Socket m1;
		try {
			m1 = ss.accept();
			Master master1 = new Master(m1, id, 20, null); 
			master1.start();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean numbersMatch(List<Integer> ints){
		if(ints.get(0)!=0){
			return false;
		}
		if(ints.get(1)!=1){
			return false;
		}
		if(ints.get(2)!=2){
			return false;
		}
		if(ints.get(3)!=3){
			return false;
		}
		if(ints.get(4)!=4){
			return false;
		}
		return true;
	}

}
