import java.util.ArrayList;
import java.util.HashMap;
// All Saving and Loading libraries.
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * GymAPI class, a driver-esque class that puts the Members and Trainers together.
 * Contains a bunch of utility methods for use in the MenuController class.
 * 
 * @author andyAndyA.
 *
 */

public class GymApi {
	
	private ArrayList<Member> members;
	private ArrayList<Trainer> trainers;
	
	private static String saveFileName = "gymAPI4";
	
	// Initializing a static HashMap with pre-put key:value pairs. Could have used static { } instead of {{ }}.
	@SuppressWarnings("serial")
	public static HashMap<String, String> gymPackages = new HashMap<String, String>() {{
		put("WIT", "\tAllowed access to gym during term time.\n\t€4 fee for all classes.\n\tNo access to deluxe changing rooms.");
		put("Package 1", "\tAllowed access anytime to gym.\n\tFree access to all classes.\n\tAccess to all changing areas including deluxe changing rooms.");
		put("Package 3", "\tAllowed access to gym at off-peak times.\n\t€5 fee for all classes.\n\tNo access to deluxe changing rooms.");
		put("Package 2", "\tAllowed access anytime to gym.\n\t€3 fee for all classes.\n\tAccess to all changing areas including deluxe changing rooms.");
	}};
	
	public static String[] bmiCategories = {
			"Very Severely Underweight",
			"Severely Underweight",
			"Underweight",
			"Normal",
			"Overweight",
			"Moderately Obese",
			"Severely Obese",
			"Very Severely Obese"
	};
	
	public GymApi() {
		this.members = new ArrayList<Member>();
		this.trainers = new ArrayList<Trainer>();
		
		try {
			this.load();
		} catch (Exception e) {

		}
	}
	
	public GymApi(boolean isTesting) {
		this.members = new ArrayList<Member>();
		this.trainers = new ArrayList<Trainer>();
	}
	
	@SuppressWarnings("unchecked")
	public void load() throws Exception {
		/*
		 * Reads in from the XML file specified in the saveFileName variable,
		 * and updates the members and trainers ArrayList with saved / old data.
		 * This is called when the user exits the MenuController.
		 * 
		 */
		XStream xstream = new XStream(new DomDriver());
		ObjectInputStream is = xstream.createObjectInputStream
										(new FileReader(GymApi.saveFileName + ".xml"));
		members = (ArrayList<Member>) is.readObject();
		trainers = (ArrayList<Trainer>) is.readObject();
		is.close();
	}
	
	public void save() throws Exception {
		/*
		 * Pushes the current members and trainers' ArrayList data out into the XML file,
		 * specified by the saveFileName variable. Implementation of persistance.
		 * This is called every-time the module starts.
		 * 
		 */
		XStream xstream = new XStream(new DomDriver());
		ObjectOutputStream out = xstream.createObjectOutputStream
										(new FileWriter(GymApi.saveFileName + ".xml"));
		out.writeObject(members);
		out.writeObject(trainers);
		out.close();
	}
	
	public boolean checkIfEmailExists(String email, boolean isTrainer) {
		/*
		 * Checks if an email exists in the members or trainers ArrayList.
		 * Used in the MenuController during registration for a new user.
		 * 
		 * isTrainer parameter indicates whether the trainers or members ArrayList is searched.
		 * 
		 */
		boolean emailExists = false;
		
		if (isTrainer) {
			for (int i = 0; i < this.numberOfTrainers(); i++) {
				if (this.trainers.get(i).getEmail().equals(email)) {
					emailExists = true;
					break;
				}
			}
		} else {
			for (int i = 0; i < this.numberOfMembers(); i++) {
				if (this.members.get(i).getEmail().equals(email)) {
					emailExists = true;
					break;
				}
			}
		}
		
		return emailExists;
	}
	
	public String listMembers() {
		/*
		 * Formats every member in the Gym into a string via toString,
		 * to be returned to the MenuController.
		 * 
		 */
		String str = "";
		
		for (int i = 0; i < this.numberOfMembers(); i++) {
			str += "\n\n" + this.members.get(i).toString();
		}
		
		if (str == "") { // Checking if there are any members.
			str = "\nNo members are found in the Gym.";
		} else {
			str = "\n-- Details of Members in the Gym --" + str;
		}
		
		return str;
	}
	
	public String listMembersWithIdealWeight() {
		/*
		 * Formats every member in the Gym into a string via toString,
		 * only those who have an 'ideal' weight will be returned to the MenuController.
		 * 
		 */
		String str = "";
		
		for (int i = 0; i < this.numberOfMembers(); i++) {
			Member currentMember = this.members.get(i);
			Assessment latestAssessment = currentMember.latestAssessment();
			double recentWeight = latestAssessment != null ? latestAssessment.getWeight() : -1;
			
			if (recentWeight == currentMember.calculateIdealWeight()) {
				str += "\n\n" + currentMember.toString();
			}
		}
		
		if (str == "") {
			if (this.numberOfMembers() > 0) {
				str = "\nNo members in the Gym were found with an ideal body weight.";
			} else {
				str = "\nNo members are found in the Gym.";
			}
		} else {
			str = "\n-- Details of Members in the Gym (With ideal body weights) --" + str;
		}
		
		return str;
	}
	
	public String listMembersBySpecificBMICategory(String category) {
		/*
		 * Formats every member in the Gym into a string via toString,
		 * only those who meet the category chosen by the user will be returned to the MenuController.
		 * 
		 */
		String str = "";
		
		for (int i = 0; i < this.numberOfMembers(); i++) {
			Member currentMember = this.members.get(i);
			Assessment latestAssessment = currentMember.latestAssessment();
			double recentWeight = latestAssessment != null ? latestAssessment.getWeight() : -1;
			String memberCategory = recentWeight != -1 ? currentMember.determineBMICategory(recentWeight) : "nil";
			
			if (memberCategory.equals(category)) {
				str += "\n\n" + currentMember.toString();
			}
		}
		
		if (str == "") {
			if (this.numberOfMembers() > 0) {
				str = "\nNo members in the Gym were found with the category " + category + ".";
			} else {
				str = "\nNo members are found in the Gym.";
			}
		} else {
			str = "\n-- Details of Members in the Gym (In the category " + category + ") --" + str;
		}
		
		return str;
	}
	
	public String listMemberDetailsImperialAndMetric() {
		/*
		 * Formats every member in the Gym into a string,
		 * except the weight and height of each member is only returned to the MenuController with imperial and metric measurements.
		 * 
		 */
		String str = "";
		
		for (int i = 0; i < this.numberOfMembers(); i++) {
			Member currentMember = this.members.get(i);
			
			str +=
					"\n\n" + currentMember.getName() + " | " + currentMember.getStartingWeight() + "kg (" + currentMember.getWeightInPounds() + "lbs), " + currentMember.getHeight() + "metres (" + currentMember.getHeightInInches() + "inches)";
		}
		
		if (str == "") {
			str = "\nThere are no members in the Gym.";
		} else {
			str = "\n-- Details of Members in the Gym (With Imperial/Metric measurements) --" + str;
		}
		
		return str;
	}
	
	public void addMember(Member member) {
		/*
		 * Adds a new member (StudentMember or PremiumMember) to the members ArrayList.
		 * 
		 */
		this.members.add(member);
	}
	
	public void addTrainer(Trainer trainer) {
		/*
		 * Adds a new trainer to the trainers ArrayList.
		 * 
		 */
		this.trainers.add(trainer);
	}
	
	public int numberOfMembers() {
		/*
		 * Returns the size of the members ArrayList.
		 * 
		 */
		return this.members.size();
	}
	
	public int numberOfTrainers() {
		/*
		 * Returns the size of the trainers ArrayList.
		 * 
		 */
		return this.trainers.size();
	}

	public ArrayList<Member> getMembers() {
		return members;
	}

	public ArrayList<Trainer> getTrainers() {
		return trainers;
	}
	
	// Utility methods.
	public boolean isValidMemberIndex(int index) {
		/*
		 * Checks if an index is taken in the members ArrayList.
		 * 
		 */
		boolean exists = false;
		if (index >= 0 && index < this.numberOfMembers()) {
			exists = true;
		}
		
		return exists;
	}
	
	public boolean isValidTrainerIndex(int index) {
		/*
		 * Checks if an index is taken in the trainers ArrayList.
		 * 
		 */
		boolean exists = false;
		if (index >= 0 && index < this.numberOfTrainers()) {
			exists = true;
		}
		
		return exists;
	}
	
	public Member searchMembersByEmail(String emailEntered) {
		/*
		 * Returns a member by searching for its' email throughout the members ArrayList.
		 * 
		 */
		Member memberFound = null;
		for (int i = 0; i < this.numberOfMembers(); i++) {
			if (this.members.get(i).getEmail().equals(emailEntered)) {
				memberFound = this.members.get(i);
				break;
			}
		}
		return memberFound;
	}
	
	public Trainer searchTrainerByEmail(String emailEntered) {
		/*
		 * Returns a trainer by searching for its' email throughout the trainers ArrayList.
		 * 
		 */
		Trainer trainerFound = null;
		for (int i = 0; i < this.numberOfTrainers(); i++) {
			if (this.trainers.get(i).getEmail().equals(emailEntered)) {
				trainerFound = this.trainers.get(i);
				break;
			}
		}
		return trainerFound;
	}
}