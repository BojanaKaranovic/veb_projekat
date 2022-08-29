package beans;

import java.io.Serializable;
//import java.time.String;
import java.util.Date;

public class Manager extends User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3389978403845631848L;
	private String sportsFacility;
	private boolean deleted;
	
	public Manager() {
		super();
	}

	public Manager(String firstName, String lastName, String email, String username, String password, Gender gender,
			String dateOfBirth, UserType userType, String sportsFacility) {
		super(firstName, lastName, email, username, password, gender, dateOfBirth, userType);
		this.sportsFacility = sportsFacility;
		this.deleted = false;
		// TODO Auto-generated constructor stub
	}

	public String getSportsFacility() {
		return sportsFacility;
	}

	public void setSportsFacility(String sportsFacility) {
		this.sportsFacility = sportsFacility;
	}
	
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

}
