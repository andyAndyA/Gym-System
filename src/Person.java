/**
 * The highest class of the Gym hierachy. An abstract class that defines a person / basic human.
 * Contains fields such as person's email and name.
 * 
 * @author andyAndyA.
 *
 */

public abstract class Person {
	
	// Default values for each field.
	private String email = "Error404@gmail.com";
	private String name = "Error404";
	private String address = "Not Found";
	private String gender = "Unspecified";
	
	public Person(String email, String name) {
		this.setEmail(email);
		this.setName(name);
	}
	
	public Person(String email, String name, String address) {
		this.setEmail(email);
		this.setName(name);
		this.setAddress(address);
	}
	
	public Person(String email, String name, String address, String gender) {
		this.setEmail(email);
		this.setName(name);
		this.setAddress(address);
		this.setGender(gender);
	}
	
	public String toString() {
		String str =
				"-- Details for " + this.getName() + " (" + this.getGender() + ") --\n" +
				"Email: " + this.getEmail() + "\n" +
				"Address: " + this.getAddress();
		
		return str;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		/*
		 * Sets the name of the person.
		 * Validation rule applied; character count cannot be greater than 30.
		 * 
		 */
		if (name.length() <= 30) {
			this.name = name;
		}
		
		if (this.name.equals("Error404")) { // Checking if the name is being set for the first time.
			this.name = name.substring(0, 30);
		}
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		/*
		 * Sets the gender of the person.
		 * Only accepts 'M' and 'F' as parameters.
		 * 
		 */
		if (gender.equalsIgnoreCase("M") || gender.equalsIgnoreCase("F")) {
			this.gender = gender;
		}
	}
}
