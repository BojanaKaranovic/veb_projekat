package beans;

import java.io.Serializable;

public class CustomerType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9081775863304348653L;
	private CustomerTypeName typeName;
	private double discount;
	private int requiredPoints;
	
	
	
	
	public CustomerType() {
	}
	public CustomerType(CustomerTypeName typeName, double discount, int requiredPoints) {
		this.typeName = typeName;
		this.discount = discount;
		this.requiredPoints = requiredPoints;
	}
	public CustomerTypeName getTypeName() {
		return typeName;
	}
	public void setTypeName(CustomerTypeName typeName) {
		this.typeName = typeName;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public int getRequiredPoints() {
		return requiredPoints;
	}
	public void setRequiredPoints(int requiredPoints) {
		this.requiredPoints = requiredPoints;
	}
	
	
}
