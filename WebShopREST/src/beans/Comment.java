package beans;

public class Comment {

	private String id;
	private String customer;
	private String sportsFacility;
	private String text;
	private int grade;
	
	
	public Comment(String customer, String sportsFacility, String text, int grade) {
		super();
		this.customer = customer;
		this.sportsFacility = sportsFacility;
		this.text = text;
		this.grade = grade;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getSportsFacility() {
		return sportsFacility;
	}
	public void setSportsFacility(String sportsFacility) {
		this.sportsFacility = sportsFacility;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	
	
}
