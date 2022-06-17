package beans;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TrainingHistory implements Serializable {

	private LocalDateTime dateTimeOfCheckIn;
	private Training training;
	private Customer customer;
	private Coach coach;
	
	
	
	public TrainingHistory(LocalDateTime dateTimeOfCheckIn, Training training, Customer customer) {
		super();
		this.dateTimeOfCheckIn = dateTimeOfCheckIn;
		this.training = training;
		this.customer = customer;
	}

	public TrainingHistory(LocalDateTime dateTimeOfCheckIn, Training training, Customer customer, Coach coach) {
		super();
		this.dateTimeOfCheckIn = dateTimeOfCheckIn;
		this.training = training;
		this.customer = customer;
		this.coach = coach;
	}
	
	public LocalDateTime getDateTimeOfCheckIn() {
		return dateTimeOfCheckIn;
	}
	public void setDateTimeOfCheckIn(LocalDateTime dateTimeOfCheckIn) {
		this.dateTimeOfCheckIn = dateTimeOfCheckIn;
	}
	public Training getTraining() {
		return training;
	}
	public void setTraining(Training training) {
		this.training = training;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Coach getCoach() {
		return coach;
	}
	public void setCoach(Coach coach) {
		this.coach = coach;
	}
	
	
	
}
