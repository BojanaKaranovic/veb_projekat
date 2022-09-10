package beans;

import java.io.Serializable;
import java.util.ArrayList;

public class Coach extends User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2979678277886764774L;
	
	private ArrayList<String> trainingHistory;
	private boolean deleted;
	

	public Coach() {
	}

	public Coach(String firstName, String lastName, String email, String username, String password, Gender gender,
			String dateOfBirth, UserType userType, ArrayList<String> trainingHistory) {
		super(firstName, lastName, email, username, password, gender, dateOfBirth, userType);
		this.trainingHistory = trainingHistory;
		this.deleted = false;
	}

	public ArrayList<String> getTrainingHistory() {
		return trainingHistory;
	}

	public void setTrainingHistory(ArrayList<String> trainingHistory) {
		this.trainingHistory = trainingHistory;
	}
	
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	public void addTrainingHistory(String trainingHistory){
		this.trainingHistory.add(trainingHistory);
	}
	
	public void removeTrainingHistory(String trainingHistory){
		this.trainingHistory.remove(trainingHistory);
	}
}
