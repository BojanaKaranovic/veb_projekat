package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;

import beans.Coach;
import beans.Gender;
import beans.SportsFacility;
import beans.UserType;

public class CoachDAO {

	private HashMap<String, Coach> coaches = new HashMap<String, Coach>();

	public CoachDAO() {
	}
	
	public CoachDAO(String contextPath) {
		loadCoaches(contextPath);
	}
	
	public Collection<Coach> findAll() {
		return coaches.values();
	}
	
	public Coach findCoach(String username) {
		return coaches.containsKey(username) ? coaches.get(username) : null;
	}
	
	public Coach save(Coach coach) {
		for (String usename : coaches.keySet()) {
			if (usename.equals(coach.getUsername())) {
				return null;
			}
		}
		coaches.put(coach.getUsername(), coach);
		return coach;
	}
	
	public Coach update(String username, Coach coach) {
		Coach coachToUpdate = this.findCoach(username);
		if(coachToUpdate == null) {
			return this.save(coach);
		}
		coachToUpdate.setFirstName(coach.getFirstName());
		coachToUpdate.setLastName(coach.getLastName());
		coachToUpdate.setEmail(coach.getEmail());
		coachToUpdate.setUsername(coach.getUsername());
		coachToUpdate.setPassword(coach.getPassword());
		coachToUpdate.setGender(coach.getGender());
		coachToUpdate.setDateOfBirth(coach.getDateOfBirth());
		coachToUpdate.setUserType(coach.getUserType());
		
		return coachToUpdate;
	}
	
	public void delete(String username) {
		this.coaches.remove(username);
	}
	
	private void loadCoaches(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/coaches.txt");
			System.out.println(file.getCanonicalPath());
			in = new BufferedReader(new FileReader(file));
			String line, firstName = "", lastName = "", email = "", username = "", password = "", gender = "", dateOfBirth = "", userType = "", numberOfTrainings = "", trainings = "";
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
					numberOfTrainings = st.nextToken().trim();
					for(int i = 0; i < Integer.parseInt(numberOfTrainings); i++) {
						trainings = st.nextToken().trim();
						SportsFacilityDAO spDAO = new SportsFacilityDAO();
						lista.add(spDAO.findSportsFacility(trainings));
					}
					
					}
				coaches.put(username, new Coach(firstName, lastName, email, username, password, getGender(gender), dateOfBirth, UserType.COACH, null));
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
