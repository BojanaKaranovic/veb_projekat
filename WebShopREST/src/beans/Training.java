package beans;

import java.awt.Image;
import java.io.Serializable;

public class Training implements Serializable{

	private String name;
	private TrainingType type;
	private SportsFacility sportFacility;
	private int durationInMinutes;
	private Coach coach;
	private String description;
	private Image image;
	
	public Training(String name, TrainingType type, SportsFacility sportFacility, int durationInMinutes, Coach coach,
			String description, Image image) {
		super();
		this.name = name;
		this.type = type;
		this.sportFacility = sportFacility;
		this.durationInMinutes = durationInMinutes;
		this.coach = coach;
		this.description = description;
		this.image = image;
	}

	public Training(String name, TrainingType type, SportsFacility sportFacility, int durationInMinutes,
			String description, Image image) {
		super();
		this.name = name;
		this.type = type;
		this.sportFacility = sportFacility;
		this.durationInMinutes = durationInMinutes;
		this.description = description;
		this.image = image;
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

	public SportsFacility getSportFacility() {
		return sportFacility;
	}

	public void setSportFacility(SportsFacility sportFacility) {
		this.sportFacility = sportFacility;
	}

	public int getDurationInMinutes() {
		return durationInMinutes;
	}

	public void setDurationInMinutes(int durationInMinutes) {
		this.durationInMinutes = durationInMinutes;
	}

	public Coach getCoach() {
		return coach;
	}

	public void setCoach(Coach coach) {
		this.coach = coach;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
	
	
	
	
}
