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
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import beans.Manager;
import beans.SportsFacility;
import beans.Customer;
import beans.Gender;
import beans.UserType;

public class ManagerDAO {

	private HashMap<String, Manager> managers = new HashMap<String, Manager>();
	private String path;
	public ManagerDAO() {
	}
	
	public ManagerDAO(String contextPath) {
		loadManagers();
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
		writeInFile();
		return manager;
	}
	
	public Manager update(String username, Manager manager) {
		Manager managerToUpdate = this.findManager(username);
		if(managerToUpdate == null) {
			return this.save(manager);
		}
		else {
			managers.remove(username);
			managers.put(username, manager);
			writeInFile();
			return manager;
		}
	}
	
	public void delete(String username) {
		this.managers.remove(username);
	}
	
	@SuppressWarnings("unchecked")
	private void loadManagers() {
		FileWriter fileWriter = null;
		BufferedReader in = null;
		File file = null;
		try {
			file = new File(path + "/managers.txt");
			in = new BufferedReader(new FileReader(file));

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setVisibilityChecker(
					VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
			TypeFactory factory = TypeFactory.defaultInstance();
			MapType type = factory.constructMapType(HashMap.class, String.class, Customer.class);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			this.managers = ((HashMap<String, Manager>) objectMapper.readValue(file, type));
		} catch (FileNotFoundException fnfe) {
			try {
				file.createNewFile();
				fileWriter = new FileWriter(file);
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
				objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
				String string = objectMapper.writeValueAsString(managers);
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
		File f = new File(path + "/managers.txt");
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(f);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			String string = objectMapper.writeValueAsString(this.managers);
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
