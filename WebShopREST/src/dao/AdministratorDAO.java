package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;

import beans.Administrator;
import beans.Gender;
import beans.UserType;

public class AdministratorDAO {

	private HashMap<String, Administrator> administrators = new HashMap<String, Administrator>();

	public AdministratorDAO() {
	}
	
	public AdministratorDAO(String contextPath) {
		loadCustomers(contextPath);
	}
	
	public Collection<Administrator> findAll() {
		return administrators.values();
	}
	
	public Administrator findAdministrator(String username) {
		return administrators.containsKey(username) ? administrators.get(username) : null;
	}
	
	public Administrator save(Administrator administrator) {
		for (String usename : administrators.keySet()) {
			if (usename.equals(administrator.getUsername())) {
				return null;
			}
		}
		administrators.put(administrator.getUsername(), administrator);
		return administrator;
	}
	
	public Administrator update(String username, Administrator administrator) {
		Administrator administratorToUpdate = this.findAdministrator(username);
		if(administratorToUpdate == null) {
			return this.save(administrator);
		}
		administratorToUpdate.setFirstName(administrator.getFirstName());
		administratorToUpdate.setLastName(administrator.getLastName());
		administratorToUpdate.setEmail(administrator.getEmail());
		administratorToUpdate.setUsername(administrator.getUsername());
		administratorToUpdate.setPassword(administrator.getPassword());
		administratorToUpdate.setGender(administrator.getGender());
		administratorToUpdate.setDateOfBirth(administrator.getDateOfBirth());
		administratorToUpdate.setUserType(administrator.getUserType());
		
		return administratorToUpdate;
	}
	
	public void delete(String username) {
		this.administrators.remove(username);
	}
	
	private void loadCustomers(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/administrators.txt");
			System.out.println(file.getCanonicalPath());
			in = new BufferedReader(new FileReader(file));
			String line, firstName = "", lastName = "", email = "", username = "", password = "", gender = "", dateOfBirth = "", userType = "";
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
					
				}
				administrators.put(username, new Administrator(firstName, lastName, email, username, password, getGender(gender), dateOfBirth, UserType.ADMINISTRATOR));
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
