package beans;


public class TrainingHistory {
	private String id;
	private String dateTimeOfCheckIn;
	private String training;
	private String customer;
	private String coach;
	
	
	
	public TrainingHistory(String id, String dateTimeOfCheckIn, String training, String customer, String coach) {
		super();
		this.id = id;
		this.dateTimeOfCheckIn = dateTimeOfCheckIn;
		this.training = training;
		this.customer = customer;
		this.coach = coach;
	}

	public TrainingHistory(String dateTimeOfCheckIn, String training, String customer, String coach) {
		super();
		this.dateTimeOfCheckIn = dateTimeOfCheckIn;
		this.training = training;
		this.customer = customer;
		this.coach = coach;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDateTimeOfCheckIn() {
		return dateTimeOfCheckIn;
	}
	public void setDateTimeOfCheckIn(String dateTimeOfCheckIn) {
		this.dateTimeOfCheckIn = dateTimeOfCheckIn;
	}
	public String getTraining() {
		return training;
	}
	public void setTraining(String training) {
		this.training = training;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getCoach() {
		return coach;
	}
	public void setCoach(String coach) {
		this.coach = coach;
	}
	
	
	
}
