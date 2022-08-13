package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import beans.Coach;
import beans.Customer;
import beans.Gender;
import beans.Manager;
import beans.SportsFacility;
import beans.UserType;

public class CoachDAO {

	private HashMap<String, Coach> coaches = new HashMap<String, Coach>();
	private String path;
	public CoachDAO() {
	}
	
	public CoachDAO(String contextPath) {
		this.path=contextPath;
		loadCoaches();
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
		writeInFile();
		return coach;
	}
	
	public Coach update(String username, Coach coach) {
		Coach coachToUpdate = this.findCoach(username);
		if(coachToUpdate == null) {
			return this.save(coach);
		}
		else {
			coaches.remove(username);
			coaches.put(username, coach);
			writeInFile();
			return coach;
		}
		
	}
	
	public void delete(String username) {
		this.coaches.remove(username);
	}
	
	@SuppressWarnings("unchecked")
	private void loadCoaches() {
		FileWriter fileWriter = null;
		BufferedReader in = null;
		File file = null;
		try {
			file = new File(path + "/coaches.txt");
			in = new BufferedReader(new FileReader(file));

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setVisibilityChecker(
					VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
			TypeFactory factory = TypeFactory.defaultInstance();
			MapType type = factory.constructMapType(HashMap.class, String.class, Coach.class);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			this.coaches = ((HashMap<String, Coach>) objectMapper.readValue(file, type));
		} catch (FileNotFoundException fnfe) {
			try {
				file.createNewFile();
				fileWriter = new FileWriter(file);
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
				objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
				String string = objectMapper.writeValueAsString(coaches);
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
		File f = new File(path + "/coaches.txt");
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(f);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			String string = objectMapper.writeValueAsString(this.coaches);
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
