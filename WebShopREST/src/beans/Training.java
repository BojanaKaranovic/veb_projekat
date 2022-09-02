package beans;


public class Training{

	private String name;
	private TrainingType type;
	private String sportFacility;
	private int durationInMinutes;
	private String coach;
	private String description;
	private String image;
	private boolean deleted;
	private double price;
	
	public Training() {
	}



	public Training(String name, TrainingType type, String sportFacility, int durationInMinutes, String coach,
			String description, String image, boolean deleted, double price) {
		super();
		this.name = name;
		this.type = type;
		this.sportFacility = sportFacility;
		this.durationInMinutes = durationInMinutes;
		this.coach = coach;
		this.description = description;
		this.image = image;
		this.deleted = deleted;
		this.price = price;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TrainingType getType() {
		return type;
	}

	public void setType(TrainingType type) {
		this.type = type;
	}

	public String getSportFacility() {
		return sportFacility;
	}

	public void setSportFacility(String sportFacility) {
		this.sportFacility = sportFacility;
	}

	public int getDurationInMinutes() {
		return durationInMinutes;
	}

	public void setDurationInMinutes(int durationInMinutes) {
		this.durationInMinutes = durationInMinutes;
	}

	public String getCoach() {
		return coach;
	}

	public void setCoach(String coach) {
		this.coach = coach;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}



	public boolean isDeleted() {
		return deleted;
	}



	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}



	public String getSportsFacility() {
		return sportFacility;
	}



	public void setSportsFacility(String sportsFacility) {
		this.sportFacility = sportsFacility;
	}



	public double getPrice() {
		return price;
	}



	public void setPrice(double price) {
		this.price = price;
	}
	
	
	
	
	
}
