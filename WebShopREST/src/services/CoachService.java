package services;
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
import beans.User;
import dao.AdministratorDAO;
import dao.CoachDAO;
import dao.CustomerDAO;
import dao.ManagerDAO;
import dao.UserDAO;

@Path("/coaches")
public class CoachService {
	@Context
	ServletContext ctx;

	public CoachService() {
			}
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		if (ctx.getAttribute("coachDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("coachDAO", new CoachDAO(contextPath));
		}
	}

	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
    public void Create(Coach element) throws Exception {
		CoachDAO coachDAO = (CoachDAO) ctx.getAttribute("coachDAO");
		coachDAO.save(element);
    }
}
