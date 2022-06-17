package beans;

import java.io.Serializable;
import java.util.ArrayList;

public class Customer extends User implements Serializable {
	private ArrayList<SportsFacility> visitedFacility;
	private int colectedPoints;
	private CustomerType customerType;
}
