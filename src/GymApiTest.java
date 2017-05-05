import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test for GymAPI Class.
 * 
 * @author andyAndyA.
 *
 */

public class GymApiTest {
	
	private GymApi gym;
	private Member newMember;
	private Trainer newTrainer;

	@Before
	public void setUp() throws Exception {
		gym = new GymApi(true); // Making sure the GymApi doesn't load XML data to interfere with testing.
		
		this.newMember = new PremiumMember("159357@email.com", "Vin Diesel", "Furious House", "M", 50, 100, "WIT");
		this.newTrainer = new Trainer("trainer@hotmail.com", "Gerrard", "Liverpool Hotel", "M");
	}

	@Test
	public void testAddMember() {
		/*
		 * Tests the addMember method,
		 * which adds a new member to the members ArrayList.
		 * 
		 */
		gym.addMember(newMember);
		assertEquals(newMember, gym.getMembers().get(0));
	}
	
	@Test
	public void testAddTrainer() {
		/*
		 * Tests the addTrainer method,
		 * which adds a new trainer to the trainers ArrayList.
		 * 
		 */
		gym.addTrainer(newTrainer);
		assertEquals(newTrainer, gym.getTrainers().get(0));
	}
	
	@Test
	public void testNumberOfMembers() {
		/*
		 * Tests the numberOfMembers method,
		 * which returns the number of members in the gym (aka size of members ArrayList).
		 * 
		 */
		gym.addMember(newMember);
		assertEquals(1, gym.numberOfMembers(), 0.01);
	}
	
	@Test
	public void testIsValidMemberIndex() {
		/*
		 * Tests the isValidMemberIndex method,
		 * which returns a boolean value based on if the index given is used by the members ArrayList.
		 * 
		 */
		gym.addMember(newMember);
		assertEquals(true, gym.isValidMemberIndex(0));
		assertEquals(false, gym.isValidMemberIndex(1));
	}
	
	@Test
	public void testIsValidTrainerIndex() {
		/*
		 * Tests the isValidTrainerIndex method,
		 * which returns a boolean value based on if the index given is used by the trainers ArrayList.
		 * 
		 */
		gym.addTrainer(newTrainer);
		assertEquals(true, gym.isValidTrainerIndex(0));
		assertEquals(false, gym.isValidTrainerIndex(1));
	}
	
	@Test
	public void testSearchMembersByEmail() {
		/*
		 * Tests the searchMembersByEmail method,
		 * which returns a member by searching through the members ArrayList via email input (159357@email.com).
		 * 
		 */
		gym.addMember(newMember);
		assertEquals(newMember, gym.searchMembersByEmail("159357@email.com"));
	}

}
