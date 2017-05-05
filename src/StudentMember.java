/**
 * Class that implements a student gym-goer.
 * Contains additional fields related to the student's college.
 * 
 * @author andyAndyA.
 *
 */

public class StudentMember extends Member {

	private String studentId = "000000"; // Make a variable in the main class to handle IDs.
	private String collegeName = "WIT";
	
	public StudentMember(String email, String name, double weight, double height, String studentId, String collegeName) {
		super(email, name, weight, height);
		this.setStudentId(studentId);
		this.setCollegeName(collegeName);
		this.chosenPackage(collegeName);
	}
	
	public StudentMember(String email, String name, String address, double height, double weight, String studentId, String collegeName) {
		super(email, name, address, height, weight);
		this.setStudentId(studentId);
		this.setCollegeName(collegeName);
		this.chosenPackage(collegeName);
	}
	
	public StudentMember(String email, String name, String address, String gender, double height, double weight, String studentId, String collegeName) {
		super(email, name, address, gender, height, weight);
		this.setStudentId(studentId);
		this.setCollegeName(collegeName);
		this.chosenPackage(collegeName);
	}
	
	public StudentMember(String email, String name, String address, String gender, double height, double weight, String studentId, String collegeName, String packageChoice) {
		super(email, name, address, gender, height, weight);
		this.setStudentId(studentId);
		this.setCollegeName(collegeName);
		this.chosenPackage(packageChoice);
	}

	public String toString() {
		String str =
				super.toString() + "\n\n" +
				"Student ID: " + this.getStudentId() + " | College Name: " + this.getCollegeName();
		
		return str;
	}
	
	public void chosenPackage(String packageChoice) {
		/*
		 * Checks if the package choice or the college name of the member given is a valid gym package.
		 * 
		 * 
		 */
		boolean validInput = false;
		boolean validCollegeInput = false;
		
		for (String pack : GymApi.gymPackages.keySet()) {
			if (pack.equals(packageChoice)) {
				validInput = true;
			} else if (pack.equals(this.collegeName)) {
				validCollegeInput = true;
			}
		}
		if (validInput) {
			super.setChosenPackage(packageChoice);
		} else if (validCollegeInput) {
			super.setChosenPackage(this.collegeName);
		}
		// else Defaults to Package 3 in the super class.
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		/*
		 * Sets the student id of the member.
		 * Validation rule applied; character count of the ID cannot be greater than 6.
		 * 
		 */
		if (studentId.length() == 6) {
			this.studentId = studentId;
		}
		
		if (this.studentId.equals("000000")) { // Checking if this is the first time the studentId is being set.
			this.studentId = studentId.substring(0, 6);
		}
	}

	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		if (collegeName != "") { // Set to default; WIT.
			this.collegeName = collegeName;
		}
	}
}
