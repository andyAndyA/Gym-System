import java.util.Scanner;

/**
 *  This class manages all input and output for usage in MenuController.
 *  All try and catch blocks are also done here internally.
 * 
 * @author andyAndyA.
 *
 */

public final class UI {
	
	public static Scanner input = new Scanner(System.in);

	public static void println(String str) {
		/*
		 * Simplified println method to prevent constantly typing out System.out.
		 * 
		 */
		System.out.println(str);
	}
	
	public static void print(String str) {
		/*
		 * Simplified print method to prevent constantly typing out System.out.
		 * 
		 */
		System.out.print(str);
	}
	
	public static int getIntOption() {
		/*
		 * Asks the user to input an integer.
		 * 
		 */
		int num = 0;
		
		boolean goodInput = false;
		while (!goodInput) {
			try {
				print("> ");
				num = input.nextInt();
				goodInput = true;
			} catch (Exception e) {
				errCatchMsg(e);
			}
		}
		
		return num;
	}
	
	public static int getMenuOption(int min, int maxInclusive) {
		/*
		 * Asks the user to input an integer between the min and max inclusive.
		 * 
		 */
		int option = getIntOption();
		
		while (option < min || option > maxInclusive) {
			errInputMsg("integer");
			option = getIntOption();
		}
		
		return option;
	}
	
	public static String getStringOption() {
		/*
		 * Asks the user to input a string.
		 * 
		 */
		print("> ");
		
		return input.nextLine();
	}
	
	public static double getDoubleOption() {
		/*
		 * Asks the user to input a double.
		 * 
		 */
		double num = 0;
		
		boolean goodInput = false;
		while (!goodInput) {
			try {
				print("> ");
				num = input.nextDouble();
				goodInput = true;
			} catch (Exception e) {
				errCatchMsg(e);
			}
		}
		
		return num;
	}
	
	public static boolean promptProceed() {
		/*
		 * Asks the user if s/he wants to continue on with an action in the menu.
		 * 
		 */
		println("\nAre you sure you want to proceed?");
		println("0) Yes.");
		println("1) No.");
		
		int promptOption = getMenuOption(0, 1);
		
		return promptOption == 0 ? true : false;
	}
	
	public static void errInputMsg(String type) {
		/*
		 * Requests the user to input a valid data.
		 * 
		 */
		String str =
				"\nError. Please enter a valid " + type + " input.";
		
		println(str);
	}
	
	public static void errCatchMsg(Exception e) {
		/*
		 * Requests the user to input a valid data.
		 * For use in try / catch blocks.
		 * 
		 */
		String str =
				"\nError found: " + e;
		
		println(str);
	}
}
