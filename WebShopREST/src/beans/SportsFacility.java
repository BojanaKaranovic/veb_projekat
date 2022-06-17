package beans;

import java.awt.Image;
import java.io.Serializable;

public class SportsFacility implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7061576308306978756L;
	private String name;
	private FacilityType type;
	private ContentToOffer content;
	private Boolean status;
	private Location location;
	private Image logo;
	private double averageRating;
	private String workTime;
	
	public SportsFacility(String name, FacilityType type, ContentToOffer content, Boolean status, Location location,
			Image logo, double averageRating, String workTime) {
		super();
		this.name = name;
		this.type = type;
		this.content = content;
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

	public ContentToOffer getContent() {
		return content;
	}

	public void setContent(ContentToOffer content) {
		this.content = content;
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

	public Image getLogo() {
		return logo;
	}

	public void setLogo(Image logo) {
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
