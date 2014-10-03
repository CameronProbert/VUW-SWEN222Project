package catgame.clientserver;

import static org.junit.Assert.*;

import org.junit.Test;

public class UpdateTests {
	
	@Test
	public void testUpdateFromDescription(){

		
		Update up = new Update(Update.Descriptor.NORTH, 1, 0);
		System.out.println("north: "+up.getCode());
		assertTrue(up.getCode()==1010000);
		
		up = new Update(Update.Descriptor.WEST, 1, 0);
		System.out.println("south: "+up.getCode());
		assertTrue(up.getCode()==2010000);		
		
		up = new Update(Update.Descriptor.EAST, 1, 0);
		System.out.println("east: "+up.getCode());
		assertTrue(up.getCode()==3010000);	
		
		up = new Update(Update.Descriptor.SOUTH, 1, 0);
		System.out.println("south: "+up.getCode());
		assertTrue(up.getCode()==4010000);	
		
		up = new Update(Update.Descriptor.ATTACK, 1, 11);
		System.out.println("attack: "+up.getCode());
		assertTrue(up.getCode()==5010011);	
		
		up = new Update(Update.Descriptor.PICKUP, 1, 11);
		System.out.println("pickup: "+up.getCode());
		assertTrue(up.getCode()==6010011);	
		
		up = new Update(Update.Descriptor.DROP, 1, 11);
		System.out.println("drop: "+up.getCode());
		assertTrue(up.getCode()==7010011);	
		
		up = new Update(Update.Descriptor.NEWROOM, 1, 11);
		System.out.println("new room: "+up.getCode());
		assertTrue(up.getCode()==8010011);	
		
		up = new Update(Update.Descriptor.CONSUME, 1, 11);
		System.out.println("consume: "+up.getCode());
		assertTrue(up.getCode()==9010011);	
	}

}
