import java.util.Date;
import java.util.HashMap;
import java.util.TreeSet;

/**
 * Main Driver class to run the different GymAPI methods.
 * 
 * @author andyAndyA.
 *
 */

public class MenuController {
	
	private GymApi gym;
	private Person user; // The current user.
	
	public static void main(String[] args) {
		MenuController app = new MenuController();
		
		app.runStartMenu(); // Login or register the user.
		app.runMainMenu(); // Show the user the main menu.
	}
	
	public MenuController() {
		this.gym = new GymApi();
		this.user = null;
		
		try {
			this.gym.load();
		} catch (Exception e) {
			UI.errCatchMsg(e);
		}
	}
	
	public void runStartMenu() {
		/*
		 * Runs the module, asking if the user wants to login or register.
		 * Directs the user to the appropiate method (loginUser or registerUser).
		 * 
		 */
		UI.println("\nDo you want to Login or Register?");
		UI.println("0) Login.");
		UI.println("1) Register.");
		
		int loginOption = UI.getMenuOption(0, 1);
		
		UI.println("\nAre you a ..");
		UI.println("0) Member.");
		UI.println("1) Trainer.");
		
		int memberOption = UI.getMenuOption(0, 1);
		
		// Could use switch statements, but I prefer if statements.
		if (loginOption == 0) {
			loginUser(memberOption);
		} else {
			registerUser(memberOption);
		}
	}
	
	public void loginUser(int memberOption) {
		/*
		 * Requests the user to input their email to log-in.
		 * 
		 * 0 = Member | 1 = Trainer.
		 * 
		 */
		UI.input.nextLine(); // For handling the Scanner bug.
		
		UI.println("\nWhat is the email of your account?");
		
		String email = UI.getStringOption();
		// Sample usage of Ternary operator. Same syntax as JavaScript.
		// If memberOption is -eq to 0, set bool value to false. Otherwise, set to true.
		boolean searchForTrainer = memberOption == 0 ? false : true;
		
		if (gym.checkIfEmailExists(email, searchForTrainer)) {
			user = searchForTrainer ? gym.searchTrainerByEmail(email) : gym.searchMembersByEmail(email);
		} else {
			UI.println("\nInvalid email. Access denied.");
			System.exit(0);
		}
	}
	
	public void registerUser(int memberOption) {
		/*
		 * Requests the user to input new data for their new member account.
		 * Once user is registered, s/he's redirected to the loginUser method to log-in.
		 * 
		 */
		boolean isTrainer = !(memberOption == 0);
		
		UI.input.nextLine();
		
		UI.println("\nWhat email do you want to use to register?");
		String email = UI.getStringOption();
		while (gym.checkIfEmailExists(email, isTrainer)) {
			UI.println("\nThat email is already registered on the system. Please enter another email.");
			email = UI.getStringOption();
		}
		
		UI.println("\nWhat is your gender? (M for Male, F for Female)");
		String gender = UI.getStringOption();
		
		UI.println("\nWhat is your name?");
		String name = UI.getStringOption();
		
		UI.println("\nWhat is your address?");
		String address = UI.getStringOption();
		
		if (isTrainer) {
			UI.println("\nAs a trainer, what is your specialty?");
			String specialty = UI.getStringOption();
			
			gym.addTrainer(new Trainer(email, name, address, gender, specialty));
		} else {
			Member newMember = null;
			
			UI.println("\nWhat is your height? (Between 1 and 3 metres inclusive)");
			double height = UI.getDoubleOption();
			
			UI.println("\nWhat is your weight? (Between 35 and 250 kilograms inclusive)");
			double weight = UI.getDoubleOption();
			
			UI.println("\nAre you a ..");
			UI.println("0) Premium Member.");
			UI.println("1) Student Member.");
			int memberType = UI.getMenuOption(0, 1);
			
			if (memberType == 0) {
				String packageChoice = this.getPackageChoice();
				
				newMember = new PremiumMember(email, name, address, gender, height, weight, packageChoice);
			} else {
				UI.input.nextLine();
				
				UI.println("\nWhat is your College Name? (e.g. WIT)");
				String collegeName = UI.getStringOption();
				
				UI.println("\nWhat is your Student ID? (e.g. 2002002)");
				String studentId = UI.getStringOption();
				
				newMember = new StudentMember(email, name, address, gender, height, weight, studentId, collegeName);
			}
			
			gym.addMember(newMember);
		}
		
		UI.println("\nThe system is now prompting you to log in..");
		loginUser(memberOption);
	}
	
	public int mainMenu() {
		/*
		 * Displays the main menu options visually to the user.
		 * Returns the user's pick.
		 * 
		 */
		UI.println("\n" + user.getName() + ", what do you want to do?");
		
		int maxMenuOption = 3;
		if (user instanceof Trainer) { // Giving a menu based on the user's object type.
			UI.println("0) Add a new member.");
			UI.println("1) List all members.");
			UI.println("2) List members with an ideal body weight.");
			UI.println("3) List members with a specific BMI category.");
			UI.println("4) Search for a member.");
			UI.println("5) Add an assessment for a member.");
			UI.println("6) View assessments for a member (sorted by date).");
			UI.println("7) Exit the menu.");
			maxMenuOption = 7;
		} else if (user instanceof Member) {
			UI.println("0) View your profile.");
			UI.println("1) Update your profile.");
			UI.println("2) View your progress.");
			UI.println("3) Exit the menu.");
		}
		
		return UI.getMenuOption(0, maxMenuOption);
	}
	
	public void runMainMenu() {
		/*
		 * Handles the main menu of the module, and performs tasks based on the user's pick from
		 * the mainMenu method. Loops infinitely until the user exits the menu (7 or 3).
		 * 
		 * Gym data is automatically saved when the user requests to exit the menu.
		 * 
		 */
		boolean exit = false;
		
		while (!exit) {
			int option = mainMenu();
			
			if (!UI.promptProceed()) { // Ask if they want to proceed.
				continue; // Loop back to get a new option.
			}
			
			if (user instanceof Trainer) {
				// Note: All actions are converted into seperate methods to provide better readibility in this method.
				switch(option) {
					case 0:
						UI.println("\nYou have decided to add a new member.");
						
						Member newMember = this.registerNewMember();
						if (newMember != null) {
							gym.addMember(newMember);
							UI.println("\nYou have finished adding a new member.");
						}
						break;
					case 1:
						UI.println("\nYou have decided to list all members.");
						
						this.runListMembersMenu();
						break;
					case 2:
						UI.println("\nYou have decided to list members with ideal body weights.");
						UI.println(gym.listMembersWithIdealWeight());
						break;
					case 3:
						UI.println("\nYou have decided to list all members within a specific BMI category.");
						UI.println(gym.listMembersBySpecificBMICategory(this.selectCategoryToList()));
						break;
					case 4:
						UI.println("\nYou have decided to search for a member by email.");
						
						this.runSearchMemberMenu();
						break;
					case 5:
						UI.println("\nYou have decided to add an assessment for a member.");
						UI.print("\nWho would you like to add an assessment for?");
						
						String emailToAddAssessment = this.selectEmailToSearch();
						Member memberToAddAssessment = emailToAddAssessment != null ? gym.searchMembersByEmail(emailToAddAssessment) : null;
						if (memberToAddAssessment != null) {
							Assessment newAssessment = this.makeNewAssessment(memberToAddAssessment);
							if (newAssessment != null) {
								memberToAddAssessment.addAssessment(newAssessment);
								UI.println("\nYou have finished adding a new assessment.");
							}
						}
						break;
					case 6:
						UI.println("\nYou have decided to view assessments for a member sorted by date.");
						UI.print("\nWho would you like to view assessments for?");
						
						String emailToViewAssessments = this.selectEmailToSearch();
						Member memberToViewAssessment = emailToViewAssessments != null ? gym.searchMembersByEmail(emailToViewAssessments) : null;
						if (memberToViewAssessment != null) {
							Assessment assessmentChosen = this.getAssessment(memberToViewAssessment);
							if (assessmentChosen != null) {
								UI.println(assessmentChosen.toString());
							}
						}
						break;
					case 7:
						exit = true;
						break;
					
				}
			} else if (user instanceof Member) {
				switch(option) {
					case 0:
						UI.println("\nYou have decided to view your profile.");
						UI.println("\n" + user.toString());
						break;
					case 1:
						UI.println("\nYou have decided to update your profile.");
						this.runUpdateProfileMenu();
						break;
					case 2:
						UI.println("\nYou have decided to view your progress reports.");
						
						Assessment reportChosen = this.getAssessment((Member) user);
						if (reportChosen != null) {
							UI.println(reportChosen.toString());
						}
						break;
					case 3:
						exit = true;
						break;
				}
			}
		}
		
		try {
			gym.save();
		} catch (Exception e) {
			UI.errCatchMsg(e);
		}
	}
	
	public int listMembersMenu() {
		/*
		 * Displays list member option menu visually to the user.
		 * Returns the user's pick.
		 * 
		 */
		UI.println("\n" + user.getName() + ", how do you want to list all the members' details?");
		UI.println("0) All details.");
		UI.println("1) Weight and Height.\n\tWith Imperial and Metric measurements.");
		
		return UI.getMenuOption(0, 1);
	}
	
	public void runListMembersMenu() {
		/*
		 * Runs the listMembersMenu and handles the user's pick appropiately via switch statement.
		 * 
		 */
		int option = this.listMembersMenu();
		
		if (!UI.promptProceed()) {
			option = 2;
		}
		
		switch(option) {
			case 0:
				UI.println("\nYou have decided to list all members' details.");
				UI.println(gym.listMembers());
				break;
			case 1:
				UI.println("\nYou have decided to list all members' weight and heights in imperial and metric measurements.");
				UI.println(gym.listMemberDetailsImperialAndMetric());
				break;
			case 2:
				break;
		}
	}
	
	public int searchMemberMenu() {
		/*
		 * Displays member search option menu visually to the user.
		 * Returns the user's pick.
		 * 
		 */
		UI.println("\n" + user.getName() + ", how do you want to search for a member?");
		UI.println("0) Email.");
		UI.println("1) Index number.");
		
		return UI.getMenuOption(0, 1);
	}
	
	public void runSearchMemberMenu() {
		/*
		 * Runs the searchMemberMenu and handle's user pick appropiately via switch statement.
		 * 
		 */
		int option = this.searchMemberMenu();
		
		if (!UI.promptProceed()) {
			option = 2;
		}
		
		switch(option) {
			case 0:
				UI.println("\nYou have decided to search for a member by email.");
				
				String emailToSearch = this.selectEmailToSearch();
				UI.print(emailToSearch != null ? "\n" + gym.searchMembersByEmail(emailToSearch).toString() + "\n" : ""); // Adding \nbreaks occasionally to format the strings better.
				break;
			case 1:
				UI.println("\nYou have decided to search for a member by index.");
				
				int index = UI.getIntOption();
				UI.print(gym.isValidMemberIndex(index) == true ? "\n" + gym.getMembers().get(index).toString() + "\n" : "\nNo member was found from the index given.\n");
				break;
			case 2:
				break;
		}
	}
	
	public int updateProfileMenu() {
		/*
		 * Displays profile update menu visually to the user.
		 * Returns the user's pick.
		 * 
		 */
		UI.println("\n" + user.getName() + ", what do you want to update from your profile?");
		
		int maxMenuOption = 1;
		UI.println("0) Height.");
		UI.println("1) Weight.");
		
		if (user instanceof PremiumMember) { // Premium members get the option to change their package choices.
			UI.println("2) Chosen Package.");
			maxMenuOption = 2;
		}
		
		return UI.getMenuOption(0, maxMenuOption);
	}
	
	public void runUpdateProfileMenu() {
		/*
		 * Runs the updateProfileMenu method and handles the user's pick appropiately via switch statement.
		 * 
		 */
		int option = this.updateProfileMenu();
		
		if (!UI.promptProceed()) {
			option = 3;
		}
		
		switch (option) {
			// ((Member) user) casting is required as the user variable is declared initially as type Person.
			case 0:
				UI.println("\nYou have decided to update your height.");
				UI.println("\nWhat is your new height? (Between 1 and 3 metres inclusive)");
				
				((Member) user).setHeight(UI.getDoubleOption());
				break;
			case 1:
				UI.println("\nYou have decided to update your weight.");
				UI.println("\nWhat is your new weight? (Between 35 and 250 kilograms inclusive)");
				
				((Member) user).setStartingWeight(UI.getDoubleOption());
				break;
			case 2:
				UI.println("\nYou have decided to update your Chosen Package.");
				
				((PremiumMember) user).setChosenPackage(this.getPackageChoice());
				break;
			case 3:
				break;
		}
	}
	
	// This block of code was used three times, so I decided to make a method to prevent code duplication.
	public String getPackageChoice() {
		/*
		 * Prompts the user to select a package choice if s/he's a student.
		 * 
		 */
		UI.println("\nWhat is your package choice for the Gym?");
		
		int i = 0;
		for (String key : GymApi.gymPackages.keySet()) { // For-each looping through the HashMap.
			UI.println(i + ") " + key + ".");
			UI.println(GymApi.gymPackages.get(key));
			i++;
		}
	
		int packageOption = UI.getMenuOption(0, GymApi.gymPackages.size() - 1);
		
		String packageChoice = "";
		
		int j = 0;
		for (String key : GymApi.gymPackages.keySet()) {
			if (j == packageOption) {
				packageChoice = key;
				break;
			}
			j++;
		}
		
		return packageChoice;
	}
	
	// Methods used in the runMainMenu(). These are located in the MenuController class as the input from the keyboard is taken in here.
	public Member registerNewMember() {
		/*
		 * Prompts the trainer user to register a new member and returns it to the runMainMenu.
		 * Simillar to registerUser method.
		 * 
		 */
		UI.input.nextLine();
		
		Member newMember = null;
		
		UI.println("\nWhat email do you want to the member to register with?");
		String email = UI.getStringOption();
		while (gym.checkIfEmailExists(email, false)) {
			UI.println("\nThat email is already registered on the system. Please enter another email.");
			email = UI.getStringOption();
		}
		
		UI.println("\nWhat is the member's gender? (M for Male, F for Female)");
		String gender = UI.getStringOption();
		
		UI.println("\nWhat is the member's name?");
		String name = UI.getStringOption();
		
		UI.println("\nWhat is the member's address?");
		String address = UI.getStringOption();
		
		UI.println("\nWhat is the member's most recent height measure? (Between 1 and 3 metres inclusive)");
		double height = UI.getDoubleOption();
		
		UI.println("\nWhat is the member's most recent weight measure? (Between 35 and 250 kilograms inclusive)");
		double weight = UI.getDoubleOption();
		
		UI.println("\nIs the new member a ..");
		UI.println("0) Premium Member.");
		UI.println("1) Student Member.");
		int memberType = UI.getMenuOption(0, 1);
		
		if (memberType == 0) {
			String packageChoice = this.getPackageChoice();
			
			newMember = new PremiumMember(email, name, address, gender, height, weight, packageChoice);
		} else {
			UI.input.nextLine();
			
			UI.println("\nWhat is your College Name? (e.g. WIT)");
			String collegeName = UI.getStringOption();
			
			UI.println("\nWhat is your Student ID? (e.g. 2002002)");
			String studentId = UI.getStringOption();
			
			newMember = new StudentMember(email, name, address, gender, height, weight, studentId, collegeName);
		}
		
		if (!UI.promptProceed()) {
			newMember = null;
		}
		
		return newMember;
	}
	
	private String selectCategoryToList() {
		/*
		 * Prompts the user to select a BMI category to list members from.
		 * 
		 */
		UI.println("\nWhich category would you like to choose?");
		
		for (int i = 0; i < GymApi.bmiCategories.length; i++) {
			UI.println(i + ") " + GymApi.bmiCategories[i] + ".");
		}
		int bmiCategoryOption = UI.getMenuOption(0, GymApi.bmiCategories.length - 1);
		
		String category = "";
		for (int i = 0; i < GymApi.bmiCategories.length; i++) { // Looping again to find the option chosen.
			if (i == bmiCategoryOption) {
				category = GymApi.bmiCategories[i];
				break;
			}
		}
		
		return category;
	}
	
	private String selectEmailToSearch() {
		/*
		 * Prompts the user to search for an email.
		 * If the email doesn't exist, the method returns null.
		 * 
		 */
		UI.println("\nWhich email would you like to search for?");
		
		UI.input.nextLine();
		
		String email = UI.getStringOption();
		if (gym.checkIfEmailExists(email, false)) {
			// Do nothing.
		} else {
			UI.println("\nThat email was not found in the database.");
			email = null;
		}
		
		return email;
	}
	
	private Assessment makeNewAssessment(Member member) {
		/*
		 * Prompts the user to make a new assessment for a member in the gym.
		 * 
		 */
		UI.input.nextLine();
		
		String nameOfMember = member.getName();
		UI.println("\nYou have decided to add an assessment for " + nameOfMember + ".");
		
		UI.println("\nWhat was the most recent weight of " + nameOfMember + "? (Between 35 and 250 kilograms inclusive)");
		double weight = UI.getDoubleOption();
		
		UI.println("\nWhat was the most recent chest measurement of " + nameOfMember + "?");
		double chestMeasure = UI.getDoubleOption();
		
		UI.println("\nWhat was the most recent thigh measurement of " + nameOfMember + "?");
		double thighMeasure = UI.getDoubleOption();
		
		UI.println("\nWhat was the most recent upper-arm measurement of " + nameOfMember + "?");
		double upperArmMeasure = UI.getDoubleOption();
		
		UI.println("\nWhat was the most recent waist measurement of " + nameOfMember + "?");
		double waistMeasure = UI.getDoubleOption();
		
		UI.println("\nWhat was the most recent hip measurement of " + nameOfMember + "? (Must be equal / greater than " + waistMeasure + ")");
		double hipMeasure = UI.getDoubleOption();
		
		UI.input.nextLine();
		
		UI.println("\nDo you have any comments to give to " + nameOfMember + "?");
		String comment = UI.getStringOption();
		
		// Casting the user var to Trainer to make it work with the Assessment object.
		Assessment newAssessment = new Assessment(weight, chestMeasure, thighMeasure, upperArmMeasure, waistMeasure, hipMeasure, comment, (Trainer) user);
		
		if (!UI.promptProceed()) {
			newAssessment = null;
		}
		
		return newAssessment;
	}
	
	private Assessment getAssessment(Member member) {
		/*
		 * Prompts the user to select an assessment to view.
		 * Used for both the trainer and member menus.
		 * 
		 */
		Assessment assessmentFound = null;
		
		HashMap<Date, Assessment> allAssessments = member.getAssessments();
		TreeSet<Date> sortedAssessments = (TreeSet<Date>) member.sortedAssessmentDates();
		
		if (allAssessments.isEmpty()) {
			UI.println("\nThere are no assessments for you to view.");
		} else {
			UI.println("\nPlease select an assessment to view.");
			
			int i = 0;
			for (Date assessment : sortedAssessments) {
				UI.println(i + ") " + assessment.toString() + ".");
				i++;
			}
			int assessmentChoice = UI.getMenuOption(0, sortedAssessments.size() - 1);
			
			Date dateOfAssessmentChosen = null;
			// Looping through the sorted set again and finding the assessment chosen and getting the date of that for comparing.
			int j = 0;
			for (Date assessment : sortedAssessments) {
				if (j == assessmentChoice) {
					dateOfAssessmentChosen = assessment;
				}
				j++;
			}
			
			// Checking the unsorted HashMap and comparing the date chosen to the date of the assessments and returning the assessment found.
			for (Date assessment : allAssessments.keySet()) {
				if (dateOfAssessmentChosen.equals(assessment)) {
					assessmentFound = allAssessments.get(assessment);
				}
			}
		}
		
		return assessmentFound;
	}
}