package beans;

import java.io.Serializable;
//import java.time.String;
import java.util.Date;

public class Administrator extends User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3499842602493088965L;

	public Administrator() {
		super();
	}

	public Administrator(String firstName, String lastName, String email, String username, String password,
			Gender gender, String dateOfBirth, UserType userType) {
		super(firstName, lastName, email, username, password, gender, dateOfBirth, userType);
		// TODO Auto-generated constructor stub
	}

	

	
}
