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

import beans.Manager;
import beans.CustomerType;
import beans.CustomerTypeName;
import beans.Gender;
import beans.Product;
import beans.SportsFacility;
import beans.UserType;

public class ManagerDAO {

	private HashMap<String, Manager> managers = new HashMap<String, Manager>();

	public ManagerDAO() {
	}
	
	public ManagerDAO(String contextPath) {
		loadManagrs(contextPath);
	}
	
	public Collection<Manager> findAll() {
		return managers.values();
	}
	
	public Manager findManager(String username) {
		return managers.containsKey(username) ? managers.get(username) : null;
	}
	
	public Manager save(Manager customer) {
		for (String usename : managers.keySet()) {
			if (usename.equals(customer.getUsername())) {
				return null;
			}
		}
		managers.put(customer.getUsername(), customer);
		return customer;
	}
	
	public Manager update(String username, Manager customer) {
		Manager managerToUpdate = this.findManager(username);
		if(managerToUpdate == null) {
			return this.save(customer);
		}
		managerToUpdate.setFirstName(customer.getFirstName());
		managerToUpdate.setLastName(customer.getLastName());
		managerToUpdate.setEmail(customer.getEmail());
		managerToUpdate.setUsername(customer.getUsername());
		managerToUpdate.setPassword(customer.getPassword());
		managerToUpdate.setGender(customer.getGender());
		managerToUpdate.setDateOfBirth(customer.getDateOfBirth());
		managerToUpdate.setUserType(customer.getUserType());
		
		return managerToUpdate;
	}
	
	public void delete(String username) {
		this.managers.remove(username);
	}
	
	private void loadManagrs(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/customers.txt");
			System.out.println(file.getCanonicalPath());
			in = new BufferedReader(new FileReader(file));
			String line, firstName = "", lastName = "", email = "", username = "", password = "", gender = "", dateOfBirth = "", userType = "", sportsFacility = "";
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
					sportsFacility = st.nextToken().trim();
					}
				SportsFacilityDAO spDAO = new SportsFacilityDAO();
				managers.put(username, new Manager(firstName, lastName, email, username, password, getGender(gender), LocalDate.parse(dateOfBirth), UserType.MANAGER,spDAO.findSportsFacility(sportsFacility)));
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
