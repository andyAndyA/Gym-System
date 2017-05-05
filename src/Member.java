import java.util.Date;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Abstract class that inherits from the Person class.
 * Contains basic fields for a member of the gym.
 * 
 * @author andyAndyA.
 *
 */

public abstract class Member extends Person {
	
	private double height = 1; // in meters.
	private double weight = 35; // in kilograms.
	private String chosenPackage = "Package 3";
	private HashMap<Date, Assessment> assessments;
	
	public Member(String email, String name, double height, double weight) {
		super(email, name);
		this.setHeight(height);
		this.setStartingWeight(weight);
		
		this.assessments = new HashMap<Date, Assessment>();
	}
	
	public Member(String email, String name, String address, double height, double weight) {
		super(email, name, address);
		this.setHeight(height);
		this.setStartingWeight(weight);
		
		this.assessments = new HashMap<Date, Assessment>();
	}
	
	public Member(String email, String name, String address, String gender, double height, double weight) {
		super(email, name, address, gender);
		this.setHeight(height);
		this.setStartingWeight(weight);
		
		this.assessments = new HashMap<Date, Assessment>();
	}
	
	public String toString() {
		String str =
				super.toString() + "\n\n" +
				"Height: " + this.getHeight() + " | Weight: " + this.getStartingWeight() + "\n" +
				"Chosen Package: " + this.getChosenPackage() + " | Assessments: " + this.getAssessments().size();
		
		return str;
	}
	
	public void addAssessment(Assessment newAssessment) {
		/*
		 * Adds a new assessment to the assessments HashMap.
		 * Helps automate process of setting dates.
		 * 
		 */
		this.assessments.put(new Date(), newAssessment);
	}
	
	public double calculateBMI(double weight) {
		/*
		 * Calculates the body mass index of the member.
		 * For use locally inside the object.
		 * 
		 */
		double bmi = weight / this.height * 2;
		return bmi;
	}
	
	public String determineBMICategory(double weight) {
		/*
		 * Determines the category of the member depending on their BMI.
		 * 
		 */
		double bmi = this.calculateBMI(weight);
		String category = GymApi.bmiCategories[0];
		
		if (bmi < 15) {
			category = GymApi.bmiCategories[0];
		} else if (bmi < 16) {
			category = GymApi.bmiCategories[1];
		} else if (bmi < 18.5) {
			category = GymApi.bmiCategories[2];
		} else if (bmi < 25) {
			category = GymApi.bmiCategories[3];
		} else if (bmi < 30) {
			category = GymApi.bmiCategories[4];
		} else if (bmi < 35) {
			category = GymApi.bmiCategories[5];
		} else if (bmi < 40) {
			category = GymApi.bmiCategories[6];
		} else {
			category = GymApi.bmiCategories[7];
		}
		
		return category;
	}
	
	public double calculateIdealWeight() {
		/*
		 * Uses the devine formula to calculate the member's ideal weight, for use in the GymAPI;
		 * 	men: 	Ideal Body Weight (in kgs) 	= 	50 		+ 2.3kg per inch over 5ft.
		 * 	women: 	Ideal Body Weight (in kgs) 	= 	45.5	+ 2.3kg per inch over 5ft.
		 * 
		 * Thanks to LMahoney for teaching me about the formula.
		 * 
		 */
		double idealWeight = 0;
		String gender = super.getGender();
		
		if (gender.equals("Unspecified")) {
			// Do nothing.
		} else {
			idealWeight = gender.equals("M") ? 50 : 45.5;
			
			double heightInIches = this.getHeightInInches();
			double perInchAdd = 0;
			if (heightInIches > 60) {
				perInchAdd = heightInIches - 60;
				perInchAdd *= 2.3;
			}
			idealWeight += perInchAdd;
		}
		
		return idealWeight;
	}
	
	public Assessment latestAssessment() {
		/*
		 * Returns the most recent assessment record from the assessments HashMap.
		 * 
		 */
		Assessment latest = null;
		if (this.assessments.size() >= 1) { // Preventing errors by checking if there's an assessment in the ArrayList.
			latest = this.assessments.get(this.sortedAssessmentDates().last());
		}
		return latest;
	}
	
	public SortedSet<Date> sortedAssessmentDates() {
		/*
		 * Returns the assessment dates sorted in date order.
		 * 
		 */
		return new TreeSet<Date>(this.assessments.keySet());
	}

	public double getHeight() {
		return this.height;
	}
	
	public double getHeightInInches() {
		/*
		 * Returns the height of the member in inches.
		 * 
		 */
		double height = this.height;
		height *= 39.3701;
		return height;
	}

	public void setHeight(double height) {
		/*
		 * Sets the member's starting height.
		 * Validation rule is applied; height cannot be less than 1 or greater than 3.
		 * 
		 */
		if (height >= 1 && height <= 3) {
			this.height = height;
		}
	}

	public double getStartingWeight() {
		return this.weight;
	}
	
	public double getWeightInPounds() {
		/*
		 * Returns the weight of the member in pounds.
		 * 
		 */
		double weight = this.weight;
		weight *= 2.20462;
		return weight;
	}

	public void setStartingWeight(double weight) {
		if (weight >= 35 && weight <= 250) {
			this.weight = weight;
		}
	}

	public String getChosenPackage() {
		return chosenPackage;
	}

	public void setChosenPackage(String chosenPackage) {
		this.chosenPackage = chosenPackage;
	}
	
	public HashMap<Date, Assessment> getAssessments() {
		return this.assessments;
	}
	
	public void setAssessments(HashMap<Date, Assessment> assessments) {
		this.assessments = assessments;
	}
}
