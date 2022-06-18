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
import beans.Gender;
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
	
	public Manager save(Manager manager) {
		for (String usename : managers.keySet()) {
			if (usename.equals(manager.getUsername())) {
				return null;
			}
		}
		managers.put(manager.getUsername(), manager);
		return manager;
	}
	
	public Manager update(String username, Manager manager) {
		Manager managerToUpdate = this.findManager(username);
		if(managerToUpdate == null) {
			return this.save(manager);
		}
		managerToUpdate.setFirstName(manager.getFirstName());
		managerToUpdate.setLastName(manager.getLastName());
		managerToUpdate.setEmail(manager.getEmail());
		managerToUpdate.setUsername(manager.getUsername());
		managerToUpdate.setPassword(manager.getPassword());
		managerToUpdate.setGender(manager.getGender());
		managerToUpdate.setDateOfBirth(manager.getDateOfBirth());
		managerToUpdate.setUserType(manager.getUserType());
		
		return managerToUpdate;
	}
	
	public void delete(String username) {
		this.managers.remove(username);
	}
	
	private void loadManagrs(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/managers.txt");
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
