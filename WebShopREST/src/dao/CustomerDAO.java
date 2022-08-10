package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
import java.util.Map;
import java.util.StringTokenizer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.type.TypeFactory;

import beans.Customer;
import beans.CustomerType;
import beans.CustomerTypeName;
import beans.Gender;
import beans.MembershipFee;
import beans.Product;
import beans.SportsFacility;
import beans.UserType;

public class CustomerDAO {

	private HashMap<String, Customer> customers = new HashMap<String, Customer>();
	private String path;
	
	public CustomerDAO() {
	}
	
	public CustomerDAO(String contextPath) {
		this.path = contextPath;
		loadCustomers();
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
		writeInFile();
		return customer;
	}
	
	public Customer update(String username, Customer customer) {
		if(!customer.getUsername().equals(username))
		{
			customers.remove(username);
		}
		customers.put(customer.getUsername(), customer);
		writeInFile();
		return customer;
	}
	
	public void delete(String username) {
		this.customers.remove(username);
	}
	
	@SuppressWarnings("unchecked")
	private void loadCustomers() {
		FileWriter fileWriter = null;
		BufferedReader in = null;
		File file = null;
		try {
			file = new File(path + "/customers.txt");
			in = new BufferedReader(new FileReader(file));

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setVisibilityChecker(
					VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
			TypeFactory factory = TypeFactory.defaultInstance();
			MapType type = factory.constructMapType(HashMap.class, String.class, Customer.class);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			this.customers = ((HashMap<String, Customer>) objectMapper.readValue(file, type));
		} catch (FileNotFoundException fnfe) {
			try {
				file.createNewFile();
				fileWriter = new FileWriter(file);
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
				objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
				String stringCustomers = objectMapper.writeValueAsString(customers);
				fileWriter.write(stringCustomers);
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
	
	public void writeInFile() {
		File f = new File(path + "/customers.txt");
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(f);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			String stringCustomers = objectMapper.writeValueAsString(this.customers);
			fileWriter.write(stringCustomers);
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
}
