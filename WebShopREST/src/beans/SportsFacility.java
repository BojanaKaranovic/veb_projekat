package beans;

import java.io.Serializable;
import java.util.ArrayList;

public class SportsFacility implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7061576308306978756L;
	private String name;
	private FacilityType type;
	private ArrayList<String> trainings;
	private Boolean status;
	private Location location;
	private String logo;
	private double averageRating;
	private String workTime;
	
	public SportsFacility(String name, FacilityType type, ArrayList<String> trainings, Boolean status, Location location,
			String logo, double averageRating, String workTime) {
		super();
		this.name = name;
		this.type = type;
		this.trainings = trainings;
		this.status = status;
		this.location = location;
		this.logo = logo;
		this.averageRating = averageRating;
		this.workTime = workTime;
	}

	public String getName() {
		return name;
	}

	public SportsFacility() {
		
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


	public ArrayList<String> getTrainings() {
		return trainings;
	}

	public void setTrainings(ArrayList<String> trainings) {
		this.trainings = trainings;
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
