package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.print.attribute.standard.DateTimeAtCompleted;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import beans.Customer;
import beans.FacilityType;
import beans.Manager;
import beans.MembershipFee;
import beans.MembershipType;



public class MembershipFeeDAO {

	private HashMap<String, MembershipFee> membershipFees = new HashMap<String, MembershipFee>();
	private String path;
	public MembershipFeeDAO() {
		
	}
	
	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Mo�e se pristupiti samo iz servleta.
	 */
	public MembershipFeeDAO(String contextPath) {
		this.path = contextPath;
		loadMembershipFees();
	}
	
	public Collection<MembershipFee> findAll() {
		return membershipFees.values();
	}
	
	public MembershipFee findSportsFacility(String uniqueId) {
		return membershipFees.containsKey(uniqueId) ? membershipFees.get(uniqueId) : null;
	}
	
	public MembershipFee save(MembershipFee membershipFee) {
		for (String uniqueId : membershipFees.keySet()) {
			if(uniqueId.equals(membershipFee.getUniqueId())) {
				return null;
			}
		}
		membershipFees.put(membershipFee.getUniqueId(), membershipFee);
		writeInFile();
		return membershipFee;
	}
	
	public MembershipFee update(String uniqueId, MembershipFee membershipFee) {
		MembershipFee membershipFeeToUpdate = this.findSportsFacility(uniqueId);
		if(membershipFeeToUpdate == null) {
			return this.save(membershipFee);
		}
		else {
			membershipFees.remove(uniqueId);
			membershipFees.put(membershipFee.getUniqueId(), membershipFee);
			writeInFile();
			return membershipFee;
		}
	}
	
	public void delete(String name) {
		this.membershipFees.remove(name);
	}
	

	@SuppressWarnings("unchecked")
	private void loadMembershipFees() {
		FileWriter fileWriter = null;
		BufferedReader in = null;
		File file = null;
		try {
			file = new File(path + "/memberships.txt");
			in = new BufferedReader(new FileReader(file));

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setVisibilityChecker(
					VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
			TypeFactory factory = TypeFactory.defaultInstance();
			MapType type = factory.constructMapType(HashMap.class, String.class, Customer.class);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			this.membershipFees = ((HashMap<String, MembershipFee>) objectMapper.readValue(file, type));
		} catch (FileNotFoundException fnfe) {
			try {
				file.createNewFile();
				fileWriter = new FileWriter(file);
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
				objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
				String string = objectMapper.writeValueAsString(membershipFees);
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
		File f = new File(path + "/memberships.txt");
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(f);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			String string = objectMapper.writeValueAsString(this.membershipFees);
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
}
