/**
 * PremiumMember class to implement a member that isn't a student.
 * Has no additional fields, class is just a concrete implementation of Member.
 * 
 * @author andyAndyA.
 *
 */

public class PremiumMember extends Member {
	
	public PremiumMember(String email, String name, double weight, double height, String packageChoice) {
		super(email, name, weight, height);
		this.chosenPackage(packageChoice);
	}
	
	public PremiumMember(String email, String name, String address, double height, double weight, String packageChoice) {
		super(email, name, address, height, weight);
		this.chosenPackage(packageChoice);
	}
	
	public PremiumMember(String email, String name, String address, String gender, double height, double weight, String packageChoice) {
		super(email, name, address, gender, height, weight);
		this.chosenPackage(packageChoice);
	}
	
	public void chosenPackage(String packageChoice) {
		super.setChosenPackage(packageChoice);
	}
}