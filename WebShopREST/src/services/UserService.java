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

@Path("/userLogin")
public class UserService {
	
	@Context
	ServletContext ctx;
	@Context 
	HttpServletRequest request;
	public UserService() {
		
	}
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		// Ovaj objekat se instancira vi�e puta u toku rada aplikacije
		// Inicijalizacija treba da se obavi samo jednom
		if (ctx.getAttribute("customerDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("customerDAO", new CustomerDAO(contextPath));
		}
		if (ctx.getAttribute("managerDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("managerDAO", new ManagerDAO(contextPath));
		}
		if (ctx.getAttribute("administratorDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("administratorDAO", new AdministratorDAO(contextPath));
		}
		if (ctx.getAttribute("coachDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("coachDAO", new CoachDAO(contextPath));
		}
	}
	
	@GET
	@Path("/login/{username}/{password}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@PathParam("username") String username, @PathParam("password") String password ) {
		CustomerDAO customerDao = (CustomerDAO) ctx.getAttribute("customerDAO");
		ManagerDAO managerDao = (ManagerDAO) ctx.getAttribute("managerDAO");
		AdministratorDAO administratorDao = (AdministratorDAO) ctx.getAttribute("administratorDAO");
		CoachDAO coachDao = (CoachDAO) ctx.getAttribute("coachDAO");
		Customer loggedCustomer = customerDao.findCustomer(username);
		if(loggedCustomer != null) {
			if(loggedCustomer.getPassword().equals(password)) {
				request.getSession().setAttribute("loggedInUser", loggedCustomer);
				return Response.status(200).entity("customerMainPage.html").build();
			}
		}
		Manager loggedManager = managerDao.findManager(username);
		if(loggedManager != null) {
			if(loggedManager.getPassword().equals(password)) {
				request.getSession().setAttribute("loggedInUser", loggedManager);
				return Response.status(200).entity("managerMainPage.html").build();
			}
		}
		Coach loggedCoach = coachDao.findCoach(username);
		if(loggedCoach != null) {
			if(loggedCoach.getPassword().equals(password)) {
				request.getSession().setAttribute("loggedInUser", loggedCoach);
				return Response.status(200).entity("coachMainPage.html").build();
			}
		}
		Administrator loggedAdministrator = administratorDao.findAdministrator(username);
		if(loggedAdministrator != null) {
			if(loggedAdministrator.getPassword().equals(password)) {
				request.getSession().setAttribute("loggedInUser", loggedAdministrator);
				return Response.status(200).entity("administratorMainPage.html").build();
			}
		}
		return Response.status(400).entity("Invalid username and/or password").build();
		
	}
	
	@POST
	@Path("/registration")
	@Produces(MediaType.APPLICATION_JSON)
	public Response registration(Customer customer) {
		CustomerDAO customerDao = (CustomerDAO) ctx.getAttribute("customerDAO");

		if (customerDao.save(customer) != null) {
			return Response.status(200).entity("index.html").build();
			
		}
		return Response.status(400).entity("Invalid username and/or password").build();
	}

	@POST
	@Path("/manager")
	@Produces(MediaType.APPLICATION_JSON)
	public Response registration(Manager manager) {
		ManagerDAO managerDao = (ManagerDAO) ctx.getAttribute("managerDAO");

		if (managerDao.save(manager) != null) {
			return Response.status(200).entity("index.html").build();
			
		}
		return Response.status(400).entity("Invalid username and/or password").build();
	}
	@POST
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void logout(@Context HttpServletRequest request) {
		request.getSession().invalidate();
		Response.status(200).entity("sportsFacilities.html").build();
	}
	
	@GET
	@Path("/currentUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User login(@Context HttpServletRequest request) {
		return (User) request.getSession().getAttribute("user");
	}
}
