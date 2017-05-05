/**
 * Trainer class for non-member Gym goers.
 * 
 * @author andyAndyA.
 *
 */

public class Trainer extends Person {
	
	private String speciality = "";
	
	public Trainer(String email, String name, String speciality) {
		super(email, name);
		this.setSpeciality(speciality);
	}
	
	public Trainer(String email, String name, String address, String speciality) {
		super(email, name, address);
		this.setSpeciality(speciality);
	}
	
	public Trainer(String email, String name, String address, String gender, String speciality) {	
		super(email, name, address, gender);
		this.setSpeciality(speciality);
	}
	
	public String toString() {
		String str =
				super.toString() + "\n\n" +
				"Speciality: " + this.getSpeciality();
		
		return str;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	
}
