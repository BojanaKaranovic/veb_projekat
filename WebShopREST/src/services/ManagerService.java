package services;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Administrator;
import beans.Coach;
import beans.Customer;
import beans.Manager;
import beans.SportsFacility;
import beans.User;
import dao.AdministratorDAO;
import dao.CoachDAO;
import dao.CustomerDAO;
import dao.ManagerDAO;
import dao.SportsFacilityDAO;
import dao.UserDAO;

@Path("/managers")
public class ManagerService {
	@Context
	ServletContext ctx;

	public ManagerService() {
		
	}
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		if (ctx.getAttribute("managerDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("managerDAO", new ManagerDAO(contextPath));
		}
		if (ctx.getAttribute("sportsFacilityDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("sportsFacilityDAO", new SportsFacilityDAO(contextPath));
		}
	}

	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
    public void Create(Manager element) throws Exception {
    	ManagerDAO managerDAO = (ManagerDAO) ctx.getAttribute("managerDAO");
        managerDAO.save(element);
    }
	
	@GET
	@Path("/availableManagers")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Manager> availableManagers() {
		ManagerDAO managerDAO = (ManagerDAO) ctx.getAttribute("managerDAO");
		ArrayList<Manager> availableManagers = new ArrayList<Manager>(); 
		for(Manager m : managerDAO.findAll()) {
			if(m.getSportsFacility() == null && !m.isDeleted())
				availableManagers.add(m);
		}
		return availableManagers;
	}
	
	@POST
	@Path("/updatefacility/{m}&{s}")
	@Produces(MediaType.APPLICATION_JSON)
    public void UpdateFacility(@PathParam("m") String m, @PathParam("s") String s) throws Exception {
    	SportsFacilityDAO sportsFacilityDAO = (SportsFacilityDAO)  ctx.getAttribute("sportsFacilityDAO");
    	ManagerDAO managerDAO = (ManagerDAO) ctx.getAttribute("managerDAO");
    	Manager manager = managerDAO.findManager(m);
    	manager.setSportsFacility(sportsFacilityDAO.findSportsFacility(s));
    	
    	
    	managerDAO.update(m,manager);
        
    }
}
