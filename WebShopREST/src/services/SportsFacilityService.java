package services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Coach;
import beans.Manager;
import beans.Product;
import beans.SportsFacility;
import beans.Training;
import beans.TrainingHistory;
import beans.TrainingType;
import dao.CoachDAO;
import dao.ManagerDAO;
import dao.ProductDAO;
import dao.SportsFacilityDAO;
import dao.TrainingDAO;
import dao.TrainingHistoryDAO;

@Path("/sportsFacilities")
public class SportsFacilityService {

	@Context
	ServletContext ctx;
	@Context 
	HttpServletRequest request;
	
	public SportsFacilityService() {
	}
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira vi�e puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("sportsFacilityDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("sportsFacilityDAO", new SportsFacilityDAO(contextPath));
		}
		if (ctx.getAttribute("managerDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("managerDAO", new ManagerDAO(contextPath));
		}
		if (ctx.getAttribute("trainingDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("trainingDAO", new TrainingDAO(contextPath));
		}
		if (ctx.getAttribute("trainingHistoryDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("trainingHistoryDAO", new TrainingHistoryDAO(contextPath));
		}
		if (ctx.getAttribute("coachDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("coachDAO", new CoachDAO(contextPath));
		}
	}
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<SportsFacility> getSportsFacilities() {
		SportsFacilityDAO dao = (SportsFacilityDAO) ctx.getAttribute("sportsFacilityDAO");
		ArrayList<SportsFacility> visibleSportFacilities = new ArrayList<SportsFacility>();
		for(SportsFacility sf : dao.findAllSportFacilitiesSorted())
			if(!sf.isDeleted())
				visibleSportFacilities.add(sf);
		for(SportsFacility sf: visibleSportFacilities) {
			SimpleDateFormat sdformat = new SimpleDateFormat("HH:mm");
			Date time = new Date();
		    String timeStr = sdformat.format(time);
		    String[] timeParts = timeStr.split(":");
		    String[] workTimeParts = sf.getWorkTime().split("-");
		    String[] startParts = workTimeParts[0].split(":");
		    String[] endParts = workTimeParts[1].split(":");
		    if(Integer.parseInt(timeParts[0]) < Integer.parseInt(startParts[0]) || Integer.parseInt(timeParts[0]) > Integer.parseInt(endParts[0])) {
		    	sf.setStatus(false);
		    }
		    else {
		    	sf.setStatus(true);
		    }
		}
		return visibleSportFacilities;
	}
	
	@GET
	@Path("/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public SportsFacility getSportsFacility(@PathParam("name") String name) {
		SportsFacilityDAO dao = (SportsFacilityDAO) ctx.getAttribute("sportsFacilityDAO");
		SportsFacility spF = dao.findSportsFacility(name);
		if(spF != null) {
			ctx.setAttribute("reviewedSportFacility", spF);
		}
		return spF;
	}
	
	@POST
	@Path("/createSportsFacility/{manager}")
	@Produces(MediaType.APPLICATION_JSON)
	public SportsFacility getSportsFacilities(SportsFacility sportsFacility, @PathParam("manager") String manager) {
		SportsFacilityDAO dao = (SportsFacilityDAO) ctx.getAttribute("sportsFacilityDAO");
		SportsFacility sp = dao.findSportsFacility(sportsFacility.getName());
		if(sp == null) {
			dao.save(sportsFacility);
			
			ManagerDAO managerDAO = (ManagerDAO) ctx.getAttribute("managerDAO");
			String[] name = manager.split(" ");
			for(Manager m : managerDAO.findAll()) 
			{
				if(m.getFirstName().equals(name[0]) && m.getLastName().equals(name[1])) {
					Manager newManager = m;
					newManager.setSportsFacility(sportsFacility.getName());
					managerDAO.update(manager, newManager);
					break;
				}
			}
			return sportsFacility;
		}
		return sp;
	}
	
	@PUT
	@Path("/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public SportsFacility getSportsFacilities(@PathParam("name") String name, SportsFacility sportsFacility) {
		SportsFacilityDAO dao = (SportsFacilityDAO) ctx.getAttribute("sportsFacilityDAO");
		return dao.update(name, sportsFacility);
	}
	
	@DELETE
	@Path("/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public void getSportsFacilities(@PathParam("name") String name) {
		SportsFacilityDAO dao = (SportsFacilityDAO) ctx.getAttribute("sportsFacilityDAO");
		dao.delete(name);
	}
	
	@GET
	@Path("/getAllTrainings")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Training> getTraining() {
		ArrayList<Training> trainings = new ArrayList<Training>();
		TrainingDAO tDAO = (TrainingDAO) ctx.getAttribute("trainingDAO");
		for(Training t : tDAO.findAllTrainings())
		{
			if(!t.isDeleted())
				trainings.add(t);		
		}
		return trainings;
	}
	
	@GET
	@Path("/getTrainingsForSportsFacility/{sportsFacility}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Training> getTrainings(@PathParam ("sportsFacility") String sportsFacility) {
		ArrayList<Training> trainings = new ArrayList<Training>();
		TrainingDAO trainingDAO = (TrainingDAO) ctx.getAttribute("trainingDAO");
		TrainingHistoryDAO trainingHistoryDAO = (TrainingHistoryDAO) ctx.getAttribute("trainingHistoryDAO");
		SportsFacilityDAO sportsFacilityDAO = (SportsFacilityDAO) ctx.getAttribute("sportsFacilityDAO");
		SportsFacility sp= sportsFacilityDAO.findSportsFacility(sportsFacility);
		if(sp != null) {
			for(Training training : trainingDAO.findAllTrainings()) {
				int counter = 0;
				for(TrainingHistory th : trainingHistoryDAO.findAll()) {
					if(th.getTraining().equals(training.getName())) {
						counter++;
					}
				}
				if(training.getSportsFacility().equals(sportsFacility)) {
					for(int i = 0; i < counter; i++) 
						trainings.add(training);
					
				}
				
			}
		}
		request.getSession().setAttribute("managersTrainings", trainings);
		return trainings;
	}
	
	@GET
	@Path("/getDates")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<String> getTrainingDates() {
		ArrayList<String> dates = new ArrayList<String>();
		TrainingHistoryDAO thDAO = (TrainingHistoryDAO) ctx.getAttribute("trainingHistoryDAO");
		ArrayList<Training> trainings = (ArrayList<Training>) request.getSession().getAttribute("managersTrainings");
		for(Training t : trainings){
			for(TrainingHistory th : thDAO.findAll()){
				if(t.getName().equals(th.getTraining()) && !dates.contains(th.getDateTimeOfCheckIn())) {
					dates.add(th.getDateTimeOfCheckIn());
					break;
				}
			}
		}
		return dates;
	}
	
	@GET
	@Path("/getTrainingsForSportsFacilityWithoutTH/{sportsFacility}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Training> getTrainingsWithoutTH(@PathParam ("sportsFacility") String sportsFacility) {
		ArrayList<Training> trainings = new ArrayList<Training>();
		TrainingDAO trainingDAO = (TrainingDAO) ctx.getAttribute("trainingDAO");
		TrainingHistoryDAO trainingHistoryDAO = (TrainingHistoryDAO) ctx.getAttribute("trainingHistoryDAO");
		SportsFacilityDAO sportsFacilityDAO = (SportsFacilityDAO) ctx.getAttribute("sportsFacilityDAO");
		SportsFacility sp= sportsFacilityDAO.findSportsFacility(sportsFacility);
		if(sp != null) {
			for(Training training : trainingDAO.findAllTrainings()) {
				if(training.getSportFacility().equals(sportsFacility)) {
					boolean without= true;
					for(TrainingHistory th : trainingHistoryDAO.findAll()) {
						if(th.getTraining().equals(training.getName())) {
							without = false;
							break;
						}
					}
					if(without) {
						trainings.add(training);
					}
					
				}
				
				
				
			}
		}
		return trainings;
	}
	
	@GET
	@Path("/getTrainingsCoach/{coach}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Training> getTrainingsCoach(@PathParam("coach") String username){
		ArrayList<Training> trainings = new ArrayList<Training>();
		TrainingDAO trainingDAO = (TrainingDAO) ctx.getAttribute("trainingDAO");
		TrainingHistoryDAO thDAO = (TrainingHistoryDAO) ctx.getAttribute("trainingHistoryDAO");
		for(Training t : trainingDAO.findAllTrainings()){
			if(t.getCoach().equals(username))
			{
				for(TrainingHistory th : thDAO.findAll()){
					if(th.getTraining().equals(t.getName()))
						trainings.add(t);
				}
			}
		}
		request.getSession().setAttribute("coachTrainings", trainings);
		return trainings;
	}
	
	@GET
	@Path("/getPersonalCoach/{coach}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Training> getPersonalCoach(@PathParam("coach") String username){
		ArrayList<Training> trainings = new ArrayList<Training>();
		TrainingDAO trainingDAO = (TrainingDAO) ctx.getAttribute("trainingDAO");
		TrainingHistoryDAO thDAO = (TrainingHistoryDAO) ctx.getAttribute("trainingHistoryDAO");
		for(Training t : trainingDAO.findAllTrainings()){
			if(t.getCoach().equals(username) && t.getType() == TrainingType.PERSONALNI)
			{
				for(TrainingHistory th : thDAO.findAll()){
					if(th.getTraining().equals(t.getName()))
						trainings.add(t);
				}
			}
		}
		request.getSession().setAttribute("coachPersonalTrainings", trainings);
		return trainings;
	}
	
	@GET
	@Path("/getGroupCoach/{coach}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Training> getGroupCoach(@PathParam("coach") String username){
		ArrayList<Training> trainings = new ArrayList<Training>();
		TrainingDAO trainingDAO = (TrainingDAO) ctx.getAttribute("trainingDAO");
		TrainingHistoryDAO thDAO = (TrainingHistoryDAO) ctx.getAttribute("trainingHistoryDAO");
		for(Training t : trainingDAO.findAllTrainings()){
			if(t.getCoach().equals(username) && t.getType() == TrainingType.GRUPNI)
			{
				for(TrainingHistory th : thDAO.findAll()){
					if(th.getTraining().equals(t.getName()))
						trainings.add(t);
				}
			}
		}
		request.getSession().setAttribute("coachGroupTrainings", trainings);
		return trainings;
	}
	
	@GET
	@Path("/getDatesCoach/{coach}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<String> getTrainingDatesCoach(@PathParam("coach") String username) {
		ArrayList<String> dates = new ArrayList<String>();
		TrainingHistoryDAO thDAO = (TrainingHistoryDAO) ctx.getAttribute("trainingHistoryDAO");
		ArrayList<Training> trainings = (ArrayList<Training>) request.getSession().getAttribute("coachTrainings");
		for(Training t : trainings){
			for(TrainingHistory th : thDAO.findAll()){
				if(t.getName().equals(th.getTraining()) && th.getCoach().equals(username) && !dates.contains(th.getDateTimeOfCheckIn())) {
					dates.add(th.getDateTimeOfCheckIn());
					break;
				}
			}
		}
		return dates;
	}
	
	@GET
	@Path("/getPersonalDatesCoach/{coach}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<String> getPersonalTrainingDatesCoach(@PathParam("coach") String username) {
		ArrayList<String> dates = new ArrayList<String>();
		TrainingHistoryDAO thDAO = (TrainingHistoryDAO) ctx.getAttribute("trainingHistoryDAO");
		ArrayList<Training> trainings = (ArrayList<Training>) request.getSession().getAttribute("coachPersonalTrainings");
		for(Training t : trainings){
			for(TrainingHistory th : thDAO.findAll()){
				if(t.getName().equals(th.getTraining()) && th.getCoach().equals(username) && !dates.contains(th.getDateTimeOfCheckIn())) {
					dates.add(th.getDateTimeOfCheckIn());
					break;
				}
			}
		}
		return dates;
	}
	
	@GET
	@Path("/getGroupDatesCoach/{coach}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<String> getGroupTrainingDatesCoach(@PathParam("coach") String username) {
		ArrayList<String> dates = new ArrayList<String>();
		TrainingHistoryDAO thDAO = (TrainingHistoryDAO) ctx.getAttribute("trainingHistoryDAO");
		ArrayList<Training> trainings = (ArrayList<Training>) request.getSession().getAttribute("coachGroupTrainings");
		for(Training t : trainings){
			for(TrainingHistory th : thDAO.findAll()){
				if(t.getName().equals(th.getTraining()) && th.getCoach().equals(username) && !dates.contains(th.getDateTimeOfCheckIn())) {
					dates.add(th.getDateTimeOfCheckIn());
					break;
				}
			}
		}
		return dates;
	}
	@GET
	@Path("/cancelPersonalTraining/{name}/{date}")
	@Produces(MediaType.APPLICATION_JSON)
	public int cancelTraining (@PathParam("name") String name, @PathParam("date") String date) throws ParseException {
		int success = 0;
		TrainingHistoryDAO trainingHisoryDAO = (TrainingHistoryDAO) ctx.getAttribute("trainingHistoryDAO");
		TrainingHistory trainingHistory = null;
		for(TrainingHistory th : trainingHisoryDAO.findAll()){
			if(th.getTraining().equals(name) && th.getDateTimeOfCheckIn().equals(date)) {
				trainingHistory = th;
				success=1;
				break;
			}
		}
		if(trainingHistory != null) {
			success=2;
			Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(date.split(" ")[1]);
			Date now = new Date();
			String d2Str = new SimpleDateFormat("yyyy-MM-dd").format(now);
		    now = new SimpleDateFormat("yyyy-MM-dd").parse(d2Str);
			long difference = date1.getTime() - now.getTime();
			@SuppressWarnings("unchecked")
			ArrayList<Training> coachPersonalTrainings = (ArrayList<Training>) request.getSession().getAttribute("coachPersonalTrainings");
		    for(Training t : coachPersonalTrainings){
				if(t.getName().equals(name) && (difference/(1000 * 60 * 60 * 24)) % 365 > 2) {
					trainingHisoryDAO.delete(trainingHistory.getId());
					
					CoachDAO coachDAO = (CoachDAO) ctx.getAttribute("coachDAO");
					Coach coach = coachDAO.findCoach(t.getCoach());
					ArrayList<String> trainings = coach.getTrainingHistory();
					trainings.remove(trainingHistory.getId());
					coach.setTrainingHistory(trainings);
					coachDAO.update(coach.getUsername(), coach);
					
					success = 3;
				}
			}
		}
		return success;
	}
	@GET
	@Path("/training/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Training getTraining(@PathParam("name") String name) {
		TrainingDAO dao = (TrainingDAO) ctx.getAttribute("trainingDAO");
		Training training = dao.findTraining(name);
		if(training != null) {
			ctx.setAttribute("training", training);
		}
		return training;
	}
	
	@GET
	@Path("/getTrainingsCustomer/{customer}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Training> getTrainingsCustomer(@PathParam("customer") String username) throws ParseException{
		ArrayList<Training> trainings = new ArrayList<Training>();
		TrainingDAO trainingDAO = (TrainingDAO) ctx.getAttribute("trainingDAO");
		TrainingHistoryDAO thDAO = (TrainingHistoryDAO) ctx.getAttribute("trainingHistoryDAO");
		for(TrainingHistory th : thDAO.findAll()){
			Date date1=new SimpleDateFormat("HH:mm yyyy-MM-dd").parse(th.getDateTimeOfCheckIn()); 
			Date date2 = new Date();
			long differenceTime = date2.getTime() - date1.getTime();
		    long differenceDays = (differenceTime / (1000 * 60 * 60 * 24)) % 365;
			if(th.getCustomer().equals(username) && differenceDays <= 30)
			{
			
				Training t = trainingDAO.findTraining(th.getTraining());
				trainings.add(t);

			}
		}
		request.getSession().setAttribute("customersTrainings", trainings);
		return trainings;
	}
	
	@GET
	@Path("/getDatesCustomer/{customer}")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<String> getTrainingDatesCustomer(@PathParam("customer") String username) {
		ArrayList<String> dates = new ArrayList<String>();
		TrainingHistoryDAO thDAO = (TrainingHistoryDAO) ctx.getAttribute("trainingHistoryDAO");
		ArrayList<Training> trainings = (ArrayList<Training>) request.getSession().getAttribute("customersTrainings");
		for(Training t : trainings){
			for(TrainingHistory th : thDAO.findAll()){
				if(t.getName().equals(th.getTraining()) && th.getCustomer().equals(username) && !dates.contains(th.getDateTimeOfCheckIn())) {
					dates.add(th.getDateTimeOfCheckIn());
					break;
				}
			}
		}
		return dates;
	}
}
