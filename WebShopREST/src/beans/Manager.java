package beans;

import java.io.Serializable;
import java.util.Date;

public class Manager extends User implements Serializable {

	private SportsFacility sportsFacility;

	public Manager(String firstName, String lastName, String email, String username, String password, Gender gender,
			Date dateOfBirth, UserType userType, SportsFacility sportsFacility) {
		super(firstName, lastName, email, username, password, gender, dateOfBirth, userType);
		this.sportsFacility = sportsFacility;
		// TODO Auto-generated constructor stub
	}

	public SportsFacility getSportsFacility() {
		return sportsFacility;
	}

	public void setSportsFacility(SportsFacility sportsFacility) {
		this.sportsFacility = sportsFacility;
	}
	
	

}
