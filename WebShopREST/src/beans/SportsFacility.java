package beans;

import java.io.Serializable;

public class SportsFacility implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7061576308306978756L;
	private String name;
	private FacilityType type;
	private TrainingType trainingType;
	private Boolean status;
	private Location location;
	private String logo;
	private double averageRating;
	private String workTime;
	
	public SportsFacility(String name, FacilityType type, TrainingType trainingType, Boolean status, Location location,
			String logo, double averageRating, String workTime) {
		super();
		this.name = name;
		this.type = type;
		this.trainingType = trainingType;
		this.status = status;
		this.location = location;
		this.logo = logo;
		this.averageRating = averageRating;
		this.workTime = workTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public FacilityType getType() {
		return type;
	}

	public void setType(FacilityType type) {
		this.type = type;
	}


	public TrainingType getTrainingType() {
		return trainingType;
	}

	public void setTrainingType(TrainingType trainingType) {
		this.trainingType = trainingType;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}

	public String getWorkTime() {
		return workTime;
	}

	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}
	
	
	
}
