package beans;

import java.io.Serializable;

public class Comment implements Serializable{

	
	private Customer customer;
	private SportsFacility sportsFacility;
	private String text;
	private int grade;
	
	
	public Comment(Customer customer, SportsFacility sportsFacility, String text, int grade) {
		super();
		this.customer = customer;
		this.sportsFacility = sportsFacility;
		this.text = text;
		this.grade = grade;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public SportsFacility getSportsFacility() {
		return sportsFacility;
	}
	public void setSportsFacility(SportsFacility sportsFacility) {
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
