package dao;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;

import beans.Address;
import beans.FacilityType;
import beans.Location;
import beans.Product;
import beans.SportsFacility;
import beans.TrainingType;

public class SportsFacilityDAO {

	private HashMap<String, SportsFacility> sportsFacilitys = new HashMap<String, SportsFacility>();

public SportsFacilityDAO() {
		
	}
	
	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Mo�e se pristupiti samo iz servleta.
	 */
	public SportsFacilityDAO(String contextPath) {
		//loadProducts(contextPath);
		loadSportsFacilitys(contextPath);
	}
	
	public Collection<SportsFacility> findAll() {
		return sportsFacilitys.values();
	}
	
	public SportsFacility findSportsFacility(String name) {
		return sportsFacilitys.containsKey(name) ? sportsFacilitys.get(name) : null;
	}
	
	public SportsFacility save(SportsFacility sportsFacility) {
		for (String name : sportsFacilitys.keySet()) {
			if(name.equals(sportsFacility.getName())) {
				return null;
			}
		}
		sportsFacilitys.put(sportsFacility.getName(), sportsFacility);
		return sportsFacility;
	}
	
	public SportsFacility update(String name, SportsFacility sportsFacility) {
		SportsFacility sportsFacilityToUpdate = this.findSportsFacility(name);
		if(sportsFacilityToUpdate == null) {
			return this.save(sportsFacility);
		}
		sportsFacilityToUpdate.setName(sportsFacility.getName());
		sportsFacilityToUpdate.setStatus(sportsFacility.getStatus());
		sportsFacilityToUpdate.setAverageRating(sportsFacility.getAverageRating());
		sportsFacilityToUpdate.setLocation(sportsFacility.getLocation());
		sportsFacilityToUpdate.setLogo(sportsFacility.getLogo());
		sportsFacilityToUpdate.setTrainingType(sportsFacility.getTrainingType());
		sportsFacilityToUpdate.setType(sportsFacility.getType());
		sportsFacilityToUpdate.setWorkTime(sportsFacility.getWorkTime());
		
		return sportsFacilityToUpdate;
	}
	
	public void delete(String name) {
		this.sportsFacilitys.remove(name);
	}
	

	/**
	 * U�itava korisnike iz WebContent/users.txt fajla i dodaje ih u mapu {@link #products}.
	 * Klju� je id proizovda.
	 * @param contextPath Putanja do aplikacije u Tomcatu
	 */
	private void loadSportsFacilitys(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/sportsFacilitys.txt");
			System.out.println(file.getCanonicalPath());
			in = new BufferedReader(new FileReader(file));
			String line, name = "", type = "", trainingType = "", status = "", longitude = "",latitude = "", street = "", number = "", city = "", zipcode = "", logo = "", averageRating = "", workTime = "";
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					name = st.nextToken().trim();
					type = st.nextToken().trim();
					trainingType = st.nextToken().trim();
					status = st.nextToken().trim();
					longitude = st.nextToken().trim();
					latitude = st.nextToken().trim();
					street = st.nextToken().trim();
					number = st.nextToken().trim();
					city = st.nextToken().trim();
					zipcode = st.nextToken().trim();
					logo = st.nextToken().trim();
					averageRating = st.nextToken().trim();
					workTime = st.nextToken().trim();
				}
				
				Address address = new Address(street, Integer.parseInt(number), city, zipcode);
				Location location = new Location(Double.parseDouble(longitude), Double.parseDouble(latitude), address);
				sportsFacilitys.put(name, new SportsFacility(name, getFacilityType(type) , getTrainingType(trainingType), Boolean.parseBoolean(status), location, logo, Double.parseDouble(averageRating), workTime));
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
	public FacilityType getFacilityType(String type) {
		FacilityType facilityType;
		if(type.equalsIgnoreCase("teretana")) {
			facilityType = FacilityType.TERETANA;
		}
		else if(type.equalsIgnoreCase("bazen")) {
			facilityType = FacilityType.BAZEN;
		}
		else if(type.equalsIgnoreCase("sportski centar")) {
			facilityType = FacilityType.SPORTSKI_CENTAR;
		}
		else {
			facilityType = FacilityType.PLESNI_STUDIO;
		}
		return facilityType;
	}
	public TrainingType getTrainingType(String trainingType) {
		TrainingType training;
		if(trainingType.equalsIgnoreCase("grupni")) {
			training = TrainingType.GRUPNI;
		}
		else if(trainingType.equalsIgnoreCase("personalni")) {
			training = TrainingType.PERSONALNI;
		}
		else {
			training = TrainingType.TERETANA;
		}
		return training;
	}
}
