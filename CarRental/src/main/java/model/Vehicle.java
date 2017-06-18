package model;

import org.rentframework.core.Product;

//public class Vehicle implements Product {
public class Vehicle extends Product {
	
	private int makeYear;
	private String make;
	private String model;
	private String plate;
	private String color;
	private int quantity;
	
	private int seats;
	private boolean autogear;
	private int mileage;

	public Vehicle() {
		super();
	}

	public Vehicle(String make, String model, int makeYear,String productName, String description, String plate, 
			String color, 
			int quantity,double dailyFine, double dailyFee, int mileage,boolean autogear,int seats) {
		super();
		this.setProductName(productName);
		this.setModel(model);
		this.setDescription(description);
		this.setPlate(plate);
		this.setColor(color);
		this.setMake(make);
		this.setQuantity(quantity);
		this.setYear(makeYear);
		this.setDailyFee(dailyFee);
		this.setDailyFine(dailyFine);
		this.setMileage(mileage);
		this.setAutogear(autogear);
		this.setSeats(seats);
	}



	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getYear() {
		return makeYear;
	}

	public void setYear(int releaseYear) {
		this.makeYear = releaseYear;
	}

	public int getMileage() {
		return mileage;
	}

	public void setMileage(int mileage) {
		this.mileage = mileage;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	/**
	 * @return the makeYear
	 */
	public int getMakeYear() {
		return makeYear;
	}

	/**
	 * @param makeYear the makeYear to set
	 */
	public void setMakeYear(int makeYear) {
		this.makeYear = makeYear;
	}

	/**
	 * @return the seats
	 */
	public int getSeats() {
		return seats;
	}

	/**
	 * @param seats the seats to set
	 */
	public void setSeats(int seats) {
		this.seats = seats;
	}

	/**
	 * @return the autogear
	 */
	public boolean getAutogear() {
		return autogear;
	}

	/**
	 * @param autogear the autogear to set
	 */
	public void setAutogear(boolean autogear) {
		this.autogear = autogear;
	}

	
}
