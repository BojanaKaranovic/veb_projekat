package beans;

import java.util.ArrayList;

public class Customer extends User {

	private ArrayList<String> visitedFacility;
	private int collectedPoints;
	private CustomerType customerType;
	private boolean deleted;
	
	public Customer() {
		super();
	}
	
	public Customer(String firstName, String lastName, String email, String username, String password, Gender gender,
			String dateOfBirth, UserType userType, ArrayList<String> visitedFacility, int collectedPoints, CustomerType customerType) {
		super(firstName, lastName, email, username, password, gender, dateOfBirth, userType);
		this.visitedFacility = visitedFacility;
		this.collectedPoints = collectedPoints;
		this.customerType = customerType;
		this.deleted = false;
	}
	public ArrayList<String> getVisitedFacility() {
		return visitedFacility;
	}
	public void setVisitedFacility(ArrayList<String> visitedFacility) {
		this.visitedFacility = visitedFacility;
	}
	public int getCollectedPoints() {
		return collectedPoints;
	}
	public void setCollectedPoints(int colectedPoints) {
		this.collectedPoints = colectedPoints;
	}
	public CustomerType getCustomerType() {
		return customerType;
	}
	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	
}
