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
import beans.CustomerTypeName;
import beans.Manager;
import beans.User;
import beans.UserType;
import dao.AdministratorDAO;
import dao.CoachDAO;
import dao.CustomerDAO;
import dao.ManagerDAO;
import dao.UserDAO;

@Path("/enum")
public class EnumService {
	@Context
	ServletContext ctx;

	public EnumService() {
			}
	
	@GET
	@Path("/userTypes")
	@Produces(MediaType.APPLICATION_JSON)
	public UserType[] getUserType() {
		UserType[] values = UserType.values();
		return values;
	}
	
	@GET
	@Path("/customerTypeNames")
	@Produces(MediaType.APPLICATION_JSON)
	public CustomerTypeName[] getCustomerType() {
		CustomerTypeName[] values = CustomerTypeName.values();
		return values;
	}
}
