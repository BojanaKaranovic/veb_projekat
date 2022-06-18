package beans;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Coach extends User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2979678277886764774L;
	
	private ArrayList<TrainingHistory> trainingHistory;
	
	

	public Coach(String firstName, String lastName, String email, String username, String password, Gender gender,
			LocalDate dateOfBirth, UserType userType, ArrayList<TrainingHistory> trainingHistory) {
		super(firstName, lastName, email, username, password, gender, dateOfBirth, userType);
		this.trainingHistory = trainingHistory;
		// TODO Auto-generated constructor stub
	}

	public ArrayList<TrainingHistory> getTrainingHistory() {
		return trainingHistory;
	}

	public void setTrainingHistory(ArrayList<TrainingHistory> trainingHistory) {
		this.trainingHistory = trainingHistory;
	}
	
}
