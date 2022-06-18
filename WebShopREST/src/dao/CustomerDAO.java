package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;

import beans.Customer;
import beans.CustomerType;
import beans.CustomerTypeName;
import beans.Gender;
import beans.Product;
import beans.SportsFacility;
import beans.UserType;

public class CustomerDAO {

	private HashMap<String, Customer> customers = new HashMap<String, Customer>();

	public CustomerDAO() {
	}
	
	public CustomerDAO(String contextPath) {
		loadCustomers(contextPath);
	}
	
	public Collection<Customer> findAll() {
		return customers.values();
	}
	
	public Customer findCustomer(String username) {
		return customers.containsKey(username) ? customers.get(username) : null;
	}
	
	public Customer save(Customer customer) {
		for (String usename : customers.keySet()) {
			if (usename.equals(customer.getUsername())) {
				return null;
			}
		}
		customers.put(customer.getUsername(), customer);
		return customer;
	}
	
	public Customer update(String username, Customer customer) {
		Customer customerToUpdate = this.findCustomer(username);
		if(customerToUpdate == null) {
			return this.save(customer);
		}
		customerToUpdate.setFirstName(customer.getFirstName());
		customerToUpdate.setLastName(customer.getLastName());
		customerToUpdate.setEmail(customer.getEmail());
		customerToUpdate.setUsername(customer.getUsername());
		customerToUpdate.setPassword(customer.getPassword());
		customerToUpdate.setGender(customer.getGender());
		customerToUpdate.setDateOfBirth(customer.getDateOfBirth());
		customerToUpdate.setUserType(customer.getUserType());
		customerToUpdate.setVisitedFacility(customer.getVisitedFacility());
		customerToUpdate.setCollectedPoints(customer.getCollectedPoints());
		customerToUpdate.setCustomerType(customer.getCustomerType());
		
		return customerToUpdate;
	}
	
	public void delete(String username) {
		this.customers.remove(username);
	}
	
	private void loadCustomers(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/customers.txt");
			System.out.println(file.getCanonicalPath());
			in = new BufferedReader(new FileReader(file));
			String line, firstName = "", lastName = "", email = "", username = "", password = "", gender = "", dateOfBirth = "", userType = "", numberOfVisitedFacilities = "", visitedFacilities = "", collectedPoints = "", customerTypeName = "", discount = "", requiredPoints = "";
			ArrayList<SportsFacility> lista = new ArrayList<SportsFacility>();
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					firstName = st.nextToken().trim();
					lastName = st.nextToken().trim();
					email = st.nextToken().trim();
					username = st.nextToken().trim();
					password = st.nextToken().trim();
					gender = st.nextToken().trim();
					dateOfBirth = st.nextToken().trim();
					userType = st.nextToken().trim();
					numberOfVisitedFacilities = st.nextToken().trim();
					for(int i = 0; i < Integer.parseInt(numberOfVisitedFacilities); i++) {
						visitedFacilities = st.nextToken().trim();
						SportsFacilityDAO spDAO = new SportsFacilityDAO();
						lista.add(spDAO.findSportsFacility(visitedFacilities));
					}
					collectedPoints = st.nextToken().trim();
					customerTypeName = st.nextToken().trim();
					discount = st.nextToken().trim();
					requiredPoints = st.nextToken().trim();
					}
				CustomerType customerType = new CustomerType(getCustomerTypeName(customerTypeName), Double.parseDouble(discount), Integer.parseInt(requiredPoints));
				customers.put(username, new Customer(firstName, lastName, email, username, password, getGender(gender), LocalDate.parse(dateOfBirth), UserType.CUSTOMER, lista, Integer.parseInt(collectedPoints), customerType));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ( in != null ) {
				try {
					in.close();
				}
				catch (Exception e) { }
			}
		}
	}
	public CustomerTypeName getCustomerTypeName(String name) {
		CustomerTypeName customerTypeName;
		if(name.equalsIgnoreCase("zlatni")) {
			customerTypeName = CustomerTypeName.ZLATNI;
		}
		else if(name.equalsIgnoreCase("srebrni")) {
			customerTypeName = CustomerTypeName.SREBRNI;
		}
		else if(name.equalsIgnoreCase("bronzani")) {
			customerTypeName = CustomerTypeName.BRONZANI;
		}
		else {
			customerTypeName = CustomerTypeName.OBICNI;
		}
		return customerTypeName;
	}
	public Gender getGender(String gender) {
		Gender g;
		if((gender.equalsIgnoreCase("muski")) || (gender.equalsIgnoreCase("m")) || (gender.equalsIgnoreCase("male"))) {
			g = Gender.MALE;
		}
		else {
			g = Gender.FEMALE;
		}
		return g;
	}
	
}
