package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import beans.TrainingHistory;

public class TrainingHistoryDAO {

	private HashMap<String, TrainingHistory> trainingHistories = new HashMap<String, TrainingHistory>();
	private String path;
	
	public TrainingHistoryDAO() {
	}
	
	public TrainingHistoryDAO(String contextPath) {
		this.path = contextPath;
		loadTrainings();
	}

	public Collection<TrainingHistory> findAll() {
		return trainingHistories.values();
	}

	public TrainingHistory findTraining(String id) {
		return trainingHistories.containsKey(id) ? trainingHistories.get(id) : null;
	}
	
	public TrainingHistory save(TrainingHistory trainingHistory) {
		trainingHistories.put(trainingHistory.getId(), trainingHistory);
		writeInFile();
		return trainingHistory;
	}
	
	public TrainingHistory delete(String id) {
		TrainingHistory t = trainingHistories.remove(id);
		return t;
	}
	
	public void update(TrainingHistory trainingHistory, String id) {
		if(!trainingHistory.getId().equals(id))
		{
			trainingHistories.remove(id);
		}
		trainingHistories.put(trainingHistory.getId(), trainingHistory);
		writeInFile();
	}

	@SuppressWarnings("unchecked")
	private void loadTrainings() {
		FileWriter fileWriter = null;
		BufferedReader in = null;
		File file = null;
		try {
			file = new File(path + "/trainingHistories.txt");
			in = new BufferedReader(new FileReader(file));

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setVisibilityChecker(
					VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
			TypeFactory factory = TypeFactory.defaultInstance();
			MapType type = factory.constructMapType(HashMap.class, String.class, TrainingHistory.class);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			this.trainingHistories = ((HashMap<String, TrainingHistory>) objectMapper.readValue(file, type));
		} catch (FileNotFoundException fnfe) {
			try {
				file.createNewFile();
				fileWriter = new FileWriter(file);
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
				objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
				String string = objectMapper.writeValueAsString(trainingHistories);
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
		File f = new File(path + "/trainingHistories.txt");
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(f);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
			objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
			String string = objectMapper.writeValueAsString(this.trainingHistories);
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
