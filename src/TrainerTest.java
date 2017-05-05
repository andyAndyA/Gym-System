import static org.junit.Assert.*;

/**
 * Test for Assessment Class
 * 
 * @author Wei Kit Wong.
 *
 */

import org.junit.Before;
import org.junit.Test;

/*
 * Test for Trainer Class.
 * 
 * @author andyAndyA.
 * 
 */

public class TrainerTest {
	
	private Trainer validTrainer;
	private Trainer invalidTrainer;

	@Before
	public void setUp() throws Exception {
		validTrainer = new Trainer("123456@hotmail.com", "Jimi Hendrix", "WITty Address", "M", "Deadlifts");
		// Only the names and genders have validation built in.
		invalidTrainer = new Trainer("654321@outlook.ie", "Valid name is 30 chars to hereANYTHINGAFTERTHEREISTOOLONG", "Big Bucks Housing Estate", "Y", "Bench Pressing");
	}

	@Test
	public void testConstructor() {
		/*
		 * Tests the constructor on the invalidTrainer variable.
		 * 
		 */
		assertNotNull(validTrainer);
		assertEquals("654321@outlook.ie", invalidTrainer.getEmail());
		assertEquals("Valid name is 30 chars to here", invalidTrainer.getName());
		assertEquals("Big Bucks Housing Estate", invalidTrainer.getAddress());
		assertEquals("Unspecified", invalidTrainer.getGender());
		assertEquals("Bench Pressing", invalidTrainer.getSpeciality());
	}
	
	@Test
	public void testGetters() {
		/*
		 * Tests the getter methods on the validTrainer variable,
		 * initialised in the constructor.
		 * 
		 */
		assertEquals("123456@hotmail.com", validTrainer.getEmail());
		assertEquals("Jimi Hendrix", validTrainer.getName());
		assertEquals("WITty Address", validTrainer.getAddress());
		assertEquals("M", validTrainer.getGender());
		assertEquals("Deadlifts", validTrainer.getSpeciality());
	}
	
	@Test
	public void testSetters() {
		/*
		 * Tests the setter methods onto the validTrainer variable.
		 * 
		 */
		validTrainer.setEmail("13579@hotmail.com");
		assertEquals("13579@hotmail.com", validTrainer.getEmail());
		validTrainer.setEmail("anotherValidEmailBecauseNoValidation@msn.com");
		assertEquals("anotherValidEmailBecauseNoValidation@msn.com", validTrainer.getEmail());
		
		validTrainer.setName("Tom and Jerry");
		assertEquals("Tom and Jerry", validTrainer.getName());
		validTrainer.setName("ThisNameIsMeantToBeLongSoImMakingItLongerByTypingMoreAndMoreAndEvenMore");
		assertEquals("Tom and Jerry", validTrainer.getName());
		
		validTrainer.setAddress("Insert random address here.");
		assertEquals("Insert random address here.", validTrainer.getAddress());
		validTrainer.setAddress("Insert another random address here, this should have no validation.");
		assertEquals("Insert another random address here, this should have no validation.", validTrainer.getAddress());
	
		validTrainer.setGender("F");
		assertEquals("F", validTrainer.getGender());
		validTrainer.setGender("Invalid Gender.");
		assertEquals("F", validTrainer.getGender());
		
		validTrainer.setSpeciality("Squats");
		assertEquals("Squats", validTrainer.getSpeciality());
		validTrainer.setSpeciality("Lots and lots of Leg Pressing.");
		assertEquals("Lots and lots of Leg Pressing.", validTrainer.getSpeciality());
	}
}
