import static org.junit.Assert.*;
import java.util.Date;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for StudentMember Class
 * 
 * @author SDrohan and MMeagher.
 *
 */

public class StudentMemberTest {
  
  private StudentMember stud1;
  private StudentMember studInvalid;
  private Date date1, date2;
  private Assessment assessment1, assessment2;
  private Trainer rocky;
  private HashMap<Date, Assessment> assessments;
 
  
  /**
   * Set up test fixtures.
   * 
   * Called before every test method.
   */
  @Before
  public void setUp() {
    stud1 = new StudentMember("joesoap@wit.ie", "Joe Soap", "wit", "M", 1.8, 65, "200011","WIT", "Package 1" );
    studInvalid = new StudentMember("invalidstudent@wit.ie", "Valid name is 30 chars to hereANYTHINGAFTERTHEREISTOOLONG", "wit", "U", 0.8, 20, "123456789","WIT", "Package 1" );
  }
  
  /**
   * Teardown test fixtures.
   * 
   * Called after each test method.
   */
  @After
  public void tearDown() {
    
  }
  
  /**
   * Test the constructor.  
   */
  @Test
  public void testConstructor() {
    assertNotNull(stud1); // Will test these in testGetters().
    assertEquals("Valid name is 30 chars to here", studInvalid.getName());
    assertEquals("invalidstudent@wit.ie", studInvalid.getEmail());
    assertEquals("Unspecified", studInvalid.getGender());
    assertEquals("wit", studInvalid.getAddress());
    assertEquals(35.0, studInvalid.getStartingWeight(), 0.01);
    assertEquals(1.0, studInvalid.getHeight(), 0.01);    
    assertEquals("123456", studInvalid.getStudentId());
    assertEquals("Package 1", studInvalid.getChosenPackage());
  }
  
  /**
   * Test all getters using valid data.
   */
  @Test
  public void testGetters() {
    assertEquals("Joe Soap", stud1.getName());
    assertEquals("joesoap@wit.ie", stud1.getEmail());
    assertEquals("M", stud1.getGender());
    assertEquals("wit", stud1.getAddress());
    assertEquals(65.0, stud1.getStartingWeight(), 0.01);
    assertEquals(1.8, stud1.getHeight(), 0.01);    
    assertEquals("200011", stud1.getStudentId());
    assertEquals("Package 1", stud1.getChosenPackage());
  }
  
  /**
   * Test all setters for StudentMember.
   */
  @Test
  public void testSetters() {
    stud1.setName("Jane Soap");
    assertEquals("Jane Soap", stud1.getName());
    stud1.setName("This name is far too long for the validation rules");
    assertEquals("Jane Soap", stud1.getName()); 
    
    assertEquals("joesoap@wit.ie", stud1.getEmail());
    stud1.setEmail("someoneElse@wit.ie");
    assertEquals("someoneElse@wit.ie", stud1.getEmail());
    
    assertEquals("M", stud1.getGender());
    stud1.setGender("anythinginvalid");
    assertEquals("M", stud1.getGender());
    
    assertEquals("wit", stud1.getAddress());
    stud1.setAddress("no validation rules on address");
    assertEquals("no validation rules on address", stud1.getAddress());
    
    assertEquals(65.0, stud1.getStartingWeight(), 0.01);
    stud1.setStartingWeight(100);  	// Valid change.
    assertEquals(100, stud1.getStartingWeight(), 0.01);
    stud1.setStartingWeight(34);  	// Invalid change - too low.
    assertEquals(100, stud1.getStartingWeight(), 0.01);
    
    stud1.setStartingWeight(251);  	// Invalid change - too high.
    assertEquals(100, stud1.getStartingWeight(), 0.01);
    
    assertEquals(1.8, stud1.getHeight(), 0.01);    
    stud1.setHeight(2.5);  	// Valid change.
    assertEquals(2.5, stud1.getHeight(), 0.01);    
    stud1.setHeight(0.99);  // Invalid change - too low.
    assertEquals(2.5, stud1.getHeight(), 0.01);   
    stud1.setHeight(3.01);  // Invalid change - too high.
    assertEquals(2.5, stud1.getHeight(), 0.01);   
    
    assertEquals("200011", stud1.getStudentId());
    stud1.setStudentId("123456");  		// Valid change.
    assertEquals("123456", stud1.getStudentId());
    stud1.setStudentId("123456789");  	// Invalid change - too long.
    assertEquals("123456", stud1.getStudentId());
    stud1.setStudentId("123456");  		// valid change.
    
    assertEquals("Package 1", stud1.getChosenPackage());
    stud1.setChosenPackage("Package 2");
    assertEquals("Package 2", stud1.getChosenPackage());
  }
  
  /**
   * Test setting the assessments collection.
   */
  @Test
  public void testSetAssessments() {
  rocky = new Trainer("trainer@arena.ie", "Rocky Balboa", "waterford", "M", "big boxing muscles");
  assessment1 = new Assessment(50, 40, 25, 30, 37, 43, "Doing well", rocky);
  assessment2 = new Assessment(55, 42, 27, 32, 39, 44,"Doing great", rocky);
  assessments = new HashMap<Date, Assessment>();
  date1 = new Date(100000000); 		// Random date.
  date2 = new Date(200000000);  	// Different random date.
  assessments.put(date1, assessment1);
  assessments.put(date2, assessment2);
  stud1.setAssessments(assessments);
  assertEquals(assessments.get(date1).getWeight(), 50, .01);
  assertEquals(assessments.get(date2).getWeight(), 55, .01);
  
  }
  
  /**
   * Test the toString Method for StudentMember.
   */
  
  /*
  @Test
  public void testToString() {
    assertEquals(
    			"StudentMember [" +
				"Email: " +"joesoap@wit.ie" + 
				", Name:" + "Joe Soap" + 
				" (" + "M" + ")" + 
				", Address:" + "wit" + "." + 
				"\n\tHeight: "+ 1.8 + " metres" +
	            ",  Starting Weight: " + 65.0 + " kgs" +
	            ", StudentId=" + "200011" + 
	            " , College Name=" + "WIT" + 
	            ", Chosen Package=" + "Package 1"
	            + "]"
	            ,
	            stud1.toString());
  }
  */
}
