import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Test for Assessment Class
 * 
 * @author andyAndyA.
 *
 */

public class AssessmentTest {
	
	private Assessment validAssessment;
	private Assessment invalidAssessment;
	private Trainer jimiHendrix;

	@Before
	public void setUp() throws Exception {
		this.jimiHendrix = new Trainer("123456@hotmail.com", "Jimi Hendrix", "WITty Address", "M", "Deadlifts");
		
		this.validAssessment = new Assessment(50, 60, 70, 80, 90, 100, "Valid Comment", this.jimiHendrix);
		// The weight (30) cannot be less than 35. And the hip measure (80) cannot be less than waist measure (90).
		this.invalidAssessment = new Assessment(30, 60, 70, 80, 90, 80, "No validation for comments.", jimiHendrix);
	}
	
	@Test
	public void testConstructor() {
		/*
		 * Tests the constructor for the invalidAssessment variable.
		 * 
		 */
		assertNotNull(validAssessment);
		assertEquals(35.0, invalidAssessment.getWeight(), 0.01);
		assertEquals(60.0, invalidAssessment.getChestMeasure(), 0.01);
		assertEquals(70.0, invalidAssessment.getThighMeasure(), 0.01);
		assertEquals(80.0, invalidAssessment.getUpperArmMeasure(), 0.01);
		assertEquals(90.0, invalidAssessment.getWaistMeasure(), 0.01);
		assertEquals(90.0, invalidAssessment.getHipMeasure(), 0.01);
		assertEquals("No validation for comments.", invalidAssessment.getComment());
		assertEquals(this.jimiHendrix, invalidAssessment.getTrainer());
	}

	@Test
	public void testGetters() {
		/*
		 * Tests the getter methods on the validAssessment variable,
		 * initialised in the constructor.
		 * 
		 */
		assertEquals(50.0, validAssessment.getWeight(), 0.01);
		assertEquals(60.0, validAssessment.getChestMeasure(), 0.01);
		assertEquals(70.0, validAssessment.getThighMeasure(), 0.01);
		assertEquals(80.0, validAssessment.getUpperArmMeasure(), 0.01);
		assertEquals(90.0, validAssessment.getWaistMeasure(), 0.01);
		assertEquals(100.0, validAssessment.getHipMeasure(), 0.01);
		assertEquals("Valid Comment", validAssessment.getComment());
		assertEquals(this.jimiHendrix, validAssessment.getTrainer());
	}
	
	@Test
	public void testSetters() {
		/*
		 * Tests the setter methods onto the validAssessment variable.
		 * 
		 */
		validAssessment.setWeight(40);
		assertEquals(40.0, validAssessment.getWeight(), 0.01);
		validAssessment.setWeight(20); // Invalid change, weight cannot be less than 35.
		assertEquals(40.0, validAssessment.getWeight(), 0.01);
		
		validAssessment.setChestMeasure(50);
		assertEquals(50.0, validAssessment.getChestMeasure(), 0.01);
		validAssessment.setChestMeasure(20);
		assertEquals(20.0, validAssessment.getChestMeasure(), 0.01);
		
		validAssessment.setThighMeasure(50);;
		assertEquals(50.0, validAssessment.getThighMeasure(), 0.01);
		validAssessment.setThighMeasure(20);
		assertEquals(20.0, validAssessment.getThighMeasure(), 0.01);
		
		validAssessment.setUpperArmMeasure(50);;
		assertEquals(50.0, validAssessment.getUpperArmMeasure(), 0.01);
		validAssessment.setUpperArmMeasure(20);
		assertEquals(20.0, validAssessment.getUpperArmMeasure(), 0.01);
		
		validAssessment.setWaistMeasure(50);;
		assertEquals(50.0, validAssessment.getWaistMeasure(), 0.01);
		validAssessment.setWaistMeasure(20);
		assertEquals(20.0, validAssessment.getWaistMeasure(), 0.01);
		
		validAssessment.setHipMeasure(50); // Valid change, hip measure can be equal to waist measure.
		assertEquals(50.0, validAssessment.getHipMeasure(), 0.01);
		validAssessment.setHipMeasure(10); // Invalid change, hip measure cannot be less than waist measure.
		assertEquals(50.0, validAssessment.getHipMeasure(), 0.01);
		
		validAssessment.setComment("This is another Comment.");
		assertEquals("This is another Comment.", validAssessment.getComment());
		validAssessment.setComment("There are no validation for Comments.");
		assertEquals("There are no validation for Comments.", validAssessment.getComment());
		
		Trainer arnold = new Trainer("arnoldUSA@hotmail.com", "Arnold", "President's Office", "M", "Bench Press");
		validAssessment.setTrainer(arnold);
		assertEquals(arnold, validAssessment.getTrainer());
		Trainer domMazetti = new Trainer("dom@mazetti.com", "Dom Mazetti", "Big Bucks House", "M", "Bicep Curls");
		validAssessment.setTrainer(domMazetti);
		assertEquals(domMazetti, validAssessment.getTrainer());
	}
}
