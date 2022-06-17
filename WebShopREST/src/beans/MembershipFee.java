package beans;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class MembershipFee implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8161356488262447249L;
	private String uniqueId;
	private MembershipType membershipType;
	private Date paymentDate;
	private LocalDateTime validityOfMembership;
	private double cost;
	private Customer customer;
	private boolean status; //active or inactive
	private double entranceCountPerDay;
	
	
	
	public MembershipFee(String uniqueId, MembershipType membershipType, Date paymentDate,
			LocalDateTime validityOfMembership, double cost, Customer customer, boolean status,
			double entranceCountPerDay) {
		super();
		this.uniqueId = uniqueId;
		this.membershipType = membershipType;
		this.paymentDate = paymentDate;
		this.validityOfMembership = validityOfMembership;
		this.cost = cost;
		this.customer = customer;
		this.status = status;
		this.entranceCountPerDay = entranceCountPerDay;
	}
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public MembershipType getMembershipType() {
		return membershipType;
	}
	public void setMembershipType(MembershipType membershipType) {
		this.membershipType = membershipType;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	public LocalDateTime getValidityOfMembership() {
		return validityOfMembership;
	}
	public void setValidityOfMembership(LocalDateTime validityOfMembership) {
		this.validityOfMembership = validityOfMembership;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public double getEntranceCountPerDay() {
		return entranceCountPerDay;
	}
	public void setEntranceCountPerDay(double entranceCountPerDay) {
		this.entranceCountPerDay = entranceCountPerDay;
	}
	
	
}
