package services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Manager;
import beans.Product;
import beans.SportsFacility;
import beans.Training;
import dao.ManagerDAO;
import dao.ProductDAO;
import dao.SportsFacilityDAO;
import dao.TrainingDAO;

@Path("/sportsFacilities")
public class SportsFacilityService {

	@Context
	ServletContext ctx;

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
}
