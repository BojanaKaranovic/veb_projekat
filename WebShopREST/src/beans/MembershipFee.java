package beans;


public class MembershipFee{

	private String uniqueId;
	private MembershipType membershipType;
	private String paymentDate;
	private String validityOfMembership;
	private double cost;
	private Customer customer;
	private boolean status; //active or inactive
	private String entranceCountPerDay;
	
	
	
	public MembershipFee() {
	}
	public MembershipFee(String uniqueId, MembershipType membershipType, String paymentDate,
			String validityOfMembership, double cost, Customer customer, boolean status,
			String entranceCountPerDay) {
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
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getValidityOfMembership() {
		return validityOfMembership;
	}
	public void setValidityOfMembership(String validityOfMembership) {
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
	public String getEntranceCountPerDay() {
		return entranceCountPerDay;
	}
	public void setEntranceCountPerDay(String entranceCountPerDay) {
		this.entranceCountPerDay = entranceCountPerDay;
	}
	
	
}
