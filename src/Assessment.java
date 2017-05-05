/**
 * Assessment class to handle progress reports for a Member.
 * 
 * @author andyAndyA.
 *
 */

public class Assessment {
	
	private double weight = 35;
	private double chestMeasure = 0;
	private double thighMeasure = 0;
	private double upperArmMeasure = 0;
	private double waistMeasure = 0;
	private double hipMeasure = this.waistMeasure;
	private String comment = "";
	private Trainer trainer;
	
	public Assessment(double weight, double chestMeasure, double thighMeasure, double upperArmMeasure, double waistMeasure, double hipMeasure) {
		this.setWeight(weight);
		this.setChestMeasure(chestMeasure);
		this.setThighMeasure(thighMeasure);
		this.setUpperArmMeasure(upperArmMeasure);
		this.setWaistMeasure(waistMeasure);
		this.setHipMeasure(hipMeasure);
	}
	
	public Assessment(double weight, double chestMeasure, double thighMeasure, double upperArmMeasure, double waistMeasure, double hipMeasure, String comment, Trainer trainer) {
		this.setWeight(weight);
		this.setChestMeasure(chestMeasure);
		this.setThighMeasure(thighMeasure);
		this.setUpperArmMeasure(upperArmMeasure);
		this.setWaistMeasure(waistMeasure);
		this.setHipMeasure(hipMeasure);
		this.setComment(comment);
		this.setTrainer(trainer);
	}
	
	public String toString() {
		String str =
				"\n-- Assessment Details --\n" +
				"Comment (by " + this.trainer.getName() + "): \n\t" + this.comment + "\n\n" +
				"Weight: " + this.weight + " | Chest Measure: " + this.chestMeasure + "\n" +
				"Thigh Measure: " + this.thighMeasure + " | Upper Arm Measure: " + this.upperArmMeasure + "\n" +
				"Waist Measure: " + this.waistMeasure + " | Hip Measure: " + this.hipMeasure;
		
		return str;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public void setWeight(double weight) {
		/*
		 * For setting the most recentS weight of the assessment's owner.
		 * Validation rule is applied; weight cannot be less than 35 or greater than 250.
		 * 
		 */
		if (weight >= 35 && weight <= 250) {
			this.weight = weight;
		}
	}
	
	public double getChestMeasure() {
		return chestMeasure;
	}
	
	public void setChestMeasure(double chestMeasure) {
		this.chestMeasure = chestMeasure;
	}
	
	public double getThighMeasure() {
		return thighMeasure;
	}
	
	public void setThighMeasure(double thighMeasure) {
		this.thighMeasure = thighMeasure;
	}
	
	public double getUpperArmMeasure() {
		return upperArmMeasure;
	}
	
	public void setUpperArmMeasure(double upperArmMeasure) {
		this.upperArmMeasure = upperArmMeasure;
	}
	
	public double getWaistMeasure() {
		return waistMeasure;
	}
	
	public void setWaistMeasure(double waistMeasure) {
		this.waistMeasure = waistMeasure;
	}
	
	public double getHipMeasure() {
		return hipMeasure;
	}
	
	public void setHipMeasure(double hipMeasure) {
		/*
		 * For setting the most recent hip measure of the assessment's owner.
		 * Validation rule is applied; hip measure cannot be less than the waist measure.
		 * 
		 */
		if (hipMeasure >= this.waistMeasure) {
			this.hipMeasure = hipMeasure;
		}
		
		if (this.hipMeasure == 0) { // Checking if this the first time the hip measure is being checked.
			this.hipMeasure = this.waistMeasure;
		}
	}
	
	public String getComment() {
		return this.comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public Trainer getTrainer() {
		return trainer;
	}
	
	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}
}
