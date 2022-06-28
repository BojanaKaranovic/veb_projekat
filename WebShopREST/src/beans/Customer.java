package beans;

import java.io.Serializable;
//import java.time.String;
import java.util.ArrayList;
import java.util.Date;

public class Customer extends User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6010286421963152947L;
	private ArrayList<SportsFacility> visitedFacility;
	private int collectedPoints;
	private CustomerType customerType;
	
	
	public Customer() {
		super();
	}
	
	public Customer(String firstName, String lastName, String email, String username, String password, Gender gender,
			String dateOfBirth, UserType userType, ArrayList<SportsFacility> visitedFacility, int collectedPoints, CustomerType customerType) {
		super(firstName, lastName, email, username, password, gender, dateOfBirth, userType);
		this.visitedFacility = visitedFacility;
		this.collectedPoints = collectedPoints;
		this.customerType = customerType;
		// TODO Auto-generated constructor stub
	}
	public ArrayList<SportsFacility> getVisitedFacility() {
		return visitedFacility;
	}
	public void setVisitedFacility(ArrayList<SportsFacility> visitedFacility) {
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
	
	
}
