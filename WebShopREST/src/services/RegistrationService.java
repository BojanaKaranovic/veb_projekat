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

@Path("/userRegistration")
public class RegistrationService {
	
	@Context
	ServletContext ctx;
	@Context 
	HttpServletRequest request;
	public RegistrationService() {
		
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
		
	}
	
	@PUT
	@Path("/registration/{customer}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response registration(@PathParam("customer") Customer customer) {
		CustomerDAO customerDao = (CustomerDAO) ctx.getAttribute("customerDAO");
		Customer registratedCustomer = customerDao.save(customer);
		if (registratedCustomer == null) {
			return Response.status(400).entity("Invalid username and/or password").build();
		}
		request.getSession().setAttribute("customer", registratedCustomer);
		return Response.status(200).build();
	}

}
