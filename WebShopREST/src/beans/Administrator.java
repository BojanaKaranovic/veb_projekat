package beans;

public class Administrator extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3499842602493088965L;

	public Administrator() {
		super();
	}

	public Administrator(String firstName, String lastName, String email, String username, String password,
			String gender) {
		super(firstName, lastName, email, username, password, gender);
	}

	
}
