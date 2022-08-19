package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonGenerator;

import beans.Address;
import beans.FacilityType;
import beans.Location;
import beans.SportsFacility;
import beans.TrainingType;

public class SportsFacilityDAO {

	private HashMap<String, SportsFacility> sportsFacilities = new HashMap<String, SportsFacility>();
	private String path;
public SportsFacilityDAO() {
		
	}
	
	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Mo�e se pristupiti samo iz servleta.
	 */
	public SportsFacilityDAO(String contextPath) {
		//loadProducts(contextPath);
		this.path = contextPath;
		loadSportsFacilities();
	}
	
	public Collection<SportsFacility> findAll() {
		return sportsFacilities.values();
	}
	
	public Collection<SportsFacility> findAllSportFacilitiesSorted() {
		ArrayList<SportsFacility> sorted = new ArrayList <SportsFacility>();
		for(SportsFacility sf : sportsFacilities.values())
		{
			if(sf.getStatus())
				sorted.add(sf);
		}
		for(SportsFacility sf : sportsFacilities.values())
		{
			if(!sf.getStatus())
				sorted.add(sf);
		}
		return sorted;
	}
	
	public SportsFacility findSportsFacility(String name) {
		return sportsFacilities.containsKey(name) ? sportsFacilities.get(name) : null;
	}
	
	public SportsFacility save(SportsFacility sportsFacility) {
		for (String name : sportsFacilities.keySet()) {
			if(name.equals(sportsFacility.getName())) {
				return null;
			}
		}
		sportsFacilities.put(sportsFacility.getName(), sportsFacility);
		writeInFile();
		return sportsFacility;
	}
	
	public SportsFacility update(String name, SportsFacility sportsFacility) {
		SportsFacility sportsFacilityToUpdate = this.findSportsFacility(name);
		if(sportsFacilityToUpdate == null) {
			return this.save(sportsFacility);
		}
		else {
			sportsFacilities.remove(name);
			sportsFacilities.put(name, sportsFacility);
			writeInFile();
			return sportsFacility;
		}
		
	}
	
	public void delete(String name) {
		this.sportsFacilities.remove(name);
	}
	

	@SuppressWarnings("unchecked")
	private void loadSportsFacilities() {
		FileWriter fileWriter = null;
		BufferedReader in = null;
		File file = null;
		try {
			file = new File(path + "/sportsFacilities.txt");
			in = new BufferedReader(new FileReader(file));

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setVisibilityChecker(
					VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
			TypeFactory factory = TypeFactory.defaultInstance();
			MapType type = factory.constructMapType(HashMap.class, String.class, SportsFacility.class);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			this.sportsFacilities = ((HashMap<String, SportsFacility>) objectMapper.readValue(file, type));
		} catch (FileNotFoundException fnfe) {
			try {
				file.createNewFile();
				fileWriter = new FileWriter(file);
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
				objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
				String string = objectMapper.writeValueAsString(sportsFacilities);
				fileWriter.write(string);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (fileWriter != null) {
					try {
						fileWriter.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void writeInFile() {
		File f = new File(path + "/sportsFacilities.txt");
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(f);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			String string = objectMapper.writeValueAsString(this.sportsFacilities);
			fileWriter.write(string);
			fileWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileWriter != null) {
				try {
					fileWriter.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
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
