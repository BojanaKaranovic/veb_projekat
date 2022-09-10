package beans;


public class Manager extends User{

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
