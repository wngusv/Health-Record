package _healthcare;

public class FoodData {
	private String foodName;
	private String oneServing;
	private double calories;

	public FoodData(String foodName, String oneServing, double calories) {
		this.foodName = foodName;
		this.oneServing = oneServing;
		this.calories = calories;
	}

	public String getFoodName() {
		return foodName;
	}

	public String getOneServing() {
		return oneServing;
	}

	public double getCalories() {
		return calories;
	}

}

