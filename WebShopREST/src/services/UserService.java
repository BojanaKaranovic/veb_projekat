package services;

import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

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
import beans.Comment;
import beans.Customer;
import beans.CustomerType;
import beans.CustomerTypeName;
import beans.Manager;
import beans.MembershipFee;
import beans.MembershipType;
import beans.SportsFacility;
import beans.Training;
import beans.TrainingHistory;
import beans.User;
import dao.AdministratorDAO;
import dao.CoachDAO;
import dao.CommentDAO;
import dao.CustomerDAO;
import dao.ManagerDAO;
import dao.MembershipFeeDAO;
import dao.SportsFacilityDAO;
import dao.TrainingDAO;
import dao.TrainingHistoryDAO;
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
		if (ctx.getAttribute("membershipFeeDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("membershipFeeDAO", new MembershipFeeDAO(contextPath));
		}
		if (ctx.getAttribute("trainingHistoryDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("trainingHistoryDAO", new TrainingHistoryDAO(contextPath));
		}
		if (ctx.getAttribute("trainingDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("trainingDAO", new TrainingDAO(contextPath));
		}
		if (ctx.getAttribute("commentDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("commentDAO", new CommentDAO(contextPath));
		}
		if (ctx.getAttribute("sportsFacilityDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("sportsFacilityDAO", new SportsFacilityDAO(contextPath));
		}
	}
	
	@GET
	@Path("/login/{username}/{password}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@PathParam("username") String username, @PathParam("password") String password ) throws ParseException {
		CustomerDAO customerDao = (CustomerDAO) ctx.getAttribute("customerDAO");
		ManagerDAO managerDao = (ManagerDAO) ctx.getAttribute("managerDAO");
		AdministratorDAO administratorDao = (AdministratorDAO) ctx.getAttribute("administratorDAO");
		CoachDAO coachDao = (CoachDAO) ctx.getAttribute("coachDAO");
		Customer loggedCustomer = customerDao.findCustomer(username);
		if(loggedCustomer != null) {
			if(loggedCustomer.getPassword().equals(password)) {
				MembershipFeeDAO membershipDAO = (MembershipFeeDAO) ctx.getAttribute("membershipFeeDAO");
				MembershipFee membership = null;
				for(MembershipFee fee : membershipDAO.findAll()) {
					if(fee.getCustomer().equals(loggedCustomer) && fee.isStatus() == true) {
						membership = fee;
					}
				}
				if(membership != null) {
					//"2022-09-05T17:07:33.148Z" format datuma 
					String[] date = membership.getValidityOfMembership().split("T");
					Date exparation=new SimpleDateFormat("yyyy-MM-dd").parse(date[0]);
					Date now = new Date();
					String d2Str = new SimpleDateFormat("yyyy-MM-dd").format(now);
				    now = new SimpleDateFormat("yyyy-MM-dd").parse(d2Str);
					int lost =(int) membership.getCost()/1000*133*4;
					int used = 0;
					if(now.compareTo(exparation) > 0) {
						if(membership.getMembershipType() == MembershipType.MESECNA) {
							if(Integer.parseInt(membership.getEntranceCountPerDay()) > 20) {
								loggedCustomer.setCollectedPoints(loggedCustomer.getCollectedPoints() - lost);
								loggedCustomer.setCollectedPoints(loggedCustomer.getCollectedPoints() < 0 ? 0: loggedCustomer.getCollectedPoints());
							}else {
								used = 31 - Integer.parseInt(membership.getEntranceCountPerDay());
								loggedCustomer.setCollectedPoints(loggedCustomer.getCollectedPoints() + (int) membership.getCost()*used/1000);
							}
						}
						else if(membership.getMembershipType() == MembershipType.POLUGODISNJA) {
							if(Integer.parseInt(membership.getEntranceCountPerDay()) > 133) {
								loggedCustomer.setCollectedPoints(loggedCustomer.getCollectedPoints() - lost);
								loggedCustomer.setCollectedPoints(loggedCustomer.getCollectedPoints() < 0 ? 0: loggedCustomer.getCollectedPoints());
							}else {
								used = 200 - Integer.parseInt(membership.getEntranceCountPerDay());
								loggedCustomer.setCollectedPoints(loggedCustomer.getCollectedPoints() + (int) membership.getCost()*used/1000);
							}
						}else {
							used = 700;
							loggedCustomer.setCollectedPoints(loggedCustomer.getCollectedPoints() + (int) membership.getCost()*used/1000);
						}
						membership.setStatus(false);
						membershipDAO.update(membership.getUniqueId(), membership);
						
						CustomerType customerType = loggedCustomer.getCustomerType();
						if(loggedCustomer.getCollectedPoints() >= 5000) {
							customerType.setDiscount(10);
							customerType.setRequiredPoints(5000);
							customerType.setTypeName(CustomerTypeName.ZLATNI);
						}
						else if(loggedCustomer.getCollectedPoints() < 5000 && loggedCustomer.getCollectedPoints() >= 1000) {
							customerType.setDiscount(5);
							customerType.setRequiredPoints(1000);
							customerType.setTypeName(CustomerTypeName.SREBRNI);
						}
						else if(loggedCustomer.getCollectedPoints() < 1000 && loggedCustomer.getCollectedPoints() >= 100) {
							customerType.setDiscount(1);
							customerType.setRequiredPoints(1000);
							customerType.setTypeName(CustomerTypeName.BRONZANI);
						}
						else {
							customerType.setDiscount(0);
							customerType.setRequiredPoints(0);
							customerType.setTypeName(CustomerTypeName.OBICNI);
						}
						loggedCustomer.setCustomerType(customerType);
						customerDao.update( loggedCustomer.getUsername(), loggedCustomer);
					}
				}
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
	@GET
	@Path("/logout")
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout() {
		request.getSession().removeAttribute("loggedInUser");
		request.getSession().invalidate();
		return Response.status(200).entity("index.html").build();
		
	}
	
	@GET
	@Path("/currentUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User login(@Context HttpServletRequest request) {
		return (User) request.getSession().getAttribute("user");
	}
	
	@GET
	@Path("/loggedInAdmin")
	@Produces(MediaType.APPLICATION_JSON)
	public Administrator getAdministrator() {
		Administrator administrator = (Administrator)request.getSession().getAttribute("loggedInUser");
		return administrator;
	}
	
	@GET
	@Path("/loggedInCustomer")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer getCustomer() {
		Customer customer = (Customer)request.getSession().getAttribute("loggedInUser");
		return customer;
	}
	
	@GET
	@Path("/loggedInManager")
	@Produces(MediaType.APPLICATION_JSON)
	public Manager getManager() {
		Manager manager = (Manager)request.getSession().getAttribute("loggedInUser");
		return manager;
	}
	
	@GET
	@Path("/loggedInCoach")
	@Produces(MediaType.APPLICATION_JSON)
	public Coach getCoach() {
		Coach coach = (Coach)request.getSession().getAttribute("loggedInUser");
		return coach;
	}
	
	@PUT
	@Path("/updateAdmin/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateAdmin(Administrator admin, @PathParam("username") String username){
		AdministratorDAO administratorDAO = (AdministratorDAO) ctx.getAttribute("administratorDAO");
		Administrator a = administratorDAO.findAdministrator(admin.getUsername());
		if((a != null && a.getUsername().equals(username)) || a == null) {
			administratorDAO.update(username, admin);
			request.getSession().setAttribute("loggedInUser", admin);
			return Response.status(200).entity("administratorMainPage.html").build();
		}
		return Response.status(400).build();
	}
	
	@PUT
	@Path("/updateCustomer/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCustomer(Customer customer, @PathParam("username") String username){
		CustomerDAO customerDAO = (CustomerDAO) ctx.getAttribute("customerDAO");
		Customer c = customerDAO.findCustomer(customer.getUsername());
		if((c != null && c.getUsername().equals(username)) || c == null) {
			customerDAO.update(username, customer);
			request.getSession().setAttribute("loggedInUser", customer);
			return Response.status(200).entity("customerMainPage.html").build();
		}
		return Response.status(400).build();
	}
	
	@PUT
	@Path("/updateManager/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateManager(Manager manager, @PathParam("username") String username){
		ManagerDAO managerDAO = (ManagerDAO) ctx.getAttribute("managerDAO");
		Manager m = managerDAO.findManager(manager.getUsername());
		if((m != null && m.getUsername().equals(username)) || m == null) {
			managerDAO.update(username, manager);
			request.getSession().setAttribute("loggedInUser", manager);
			return Response.status(200).entity("managerMainPage.html").build();
		}
		return Response.status(400).build();
	}
	
	@PUT
	@Path("/updateCoach/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCoach(Coach coach, @PathParam("username") String username){
		CoachDAO coachDAO = (CoachDAO) ctx.getAttribute("coachDAO");
		Coach c = coachDAO.findCoach(coach.getUsername());
		if((c != null && c.getUsername().equals(username)) || c == null) {
			coachDAO.update(username, coach);
			request.getSession().setAttribute("loggedInUser", coach);
			return Response.status(200).entity("coachMainPage.html").build();
		}
		return Response.status(400).build();
	}
	
	@GET
	@Path("/registeredUsers")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getRegisteredUsers() {
		ArrayList<User> users = new ArrayList<User>();
		CoachDAO coachDAO = (CoachDAO) ctx.getAttribute("coachDAO");
		for(Coach c : coachDAO.findAll()) {
			if(!c.isDeleted())
				users.add((User)c);
		};
		AdministratorDAO adminDAO = (AdministratorDAO) ctx.getAttribute("administratorDAO");
		for (Administrator a : adminDAO.findAll()) {
			users.add((User)a);
		};
		ManagerDAO managerDAO = (ManagerDAO) ctx.getAttribute("managerDAO");
		for(Manager m : managerDAO.findAll()) {
			if(!m.isDeleted())
				users.add((User)m);
		};
		return users;
	}
	@GET
	@Path("/registeredCustomers")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Customer> getRegisteredCustomers() {
		ArrayList<Customer> customers = new ArrayList<Customer>();
		CustomerDAO customerDAO = (CustomerDAO) ctx.getAttribute("customerDAO");
		for (Customer c : customerDAO.findAll()) {
			customers.add(c);
		};
		return customers;
	}
	
	@POST
	@Path("/createMembershipFee")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public MembershipFee createMembershipFee(MembershipFee membershipFee) {
		Customer c = (Customer)request.getSession().getAttribute("loggedInUser");
		MembershipFeeDAO dao = (MembershipFeeDAO) ctx.getAttribute("membershipFeeDAO");
		String generatedString ;
		ArrayList<String> allIds = getAllIds();
//		/*
//		 * https://www.baeldung.com/java-random-string
//		 */
//		do {
//			byte[] array = new byte[10]; // length is bounded by 10
//			new Random().nextBytes(array);
//			generatedString = new String(array, Charset.forName("UTF-8"));
//		}while(allIds.contains(generatedString));
		String id = "membership" + Integer.toString(dao.findAll().size());
		membershipFee.setCustomer(c);
		membershipFee.setUniqueId(id);
		request.getSession().setAttribute("membershipFee", membershipFee);
		return membershipFee;
	}
	
	@GET
	@Path("/getMembership")
	@Produces(MediaType.APPLICATION_JSON)
	public MembershipFee getMembershipFee() {
		MembershipFee mf = (MembershipFee)request.getSession().getAttribute("membershipFee");
		return mf;
	}
	 
	@POST
	@Path("/payMembershipFee")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response payMembershipFee() {
		Customer customer = (Customer)request.getSession().getAttribute("loggedInUser");
		MembershipFee membershipFee = (MembershipFee)request.getSession().getAttribute("membershipFee");
		CustomerDAO customerDAO = (CustomerDAO)ctx.getAttribute("customerDAO");
		membershipFee.setStatus(true);
		double cost = membershipFee.getCost();
		double discount = customer.getCustomerType().getDiscount();
		cost = cost - cost * discount;
		membershipFee.setCost(cost);
		MembershipFeeDAO membershipFeeDAO = (MembershipFeeDAO) ctx.getAttribute("membershipFeeDAO");
		for(MembershipFee fee : membershipFeeDAO.findAll()) {
			if(fee.getCustomer().equals(customer)) {
				fee.setStatus(false);
				membershipFeeDAO.update(fee.getUniqueId(), fee);
			}
		}
		membershipFeeDAO.save(membershipFee);
		customerDAO.update(customer.getUsername(), customer);
		request.getSession().setAttribute("loggedInUser", customer);
		
		return Response.status(200).entity("customerMainPage.html").build();
	}
	
	private ArrayList<String> getAllIds(){
		ArrayList<String> allIds = new ArrayList<String>();
		MembershipFeeDAO dao = (MembershipFeeDAO) ctx.getAttribute("membershipFeeDAO");
		for (MembershipFee membership : dao.findAll()) {
			allIds.add(membership.getUniqueId());
		}
		return allIds;
	}
	
	@POST
	@Path("/addTraining/{time}/{date}")
	@Produces(MediaType.APPLICATION_JSON)
	public int addTraining(Training t, @PathParam("time") String time, @PathParam("date") String date) {
		TrainingHistoryDAO thDAO = (TrainingHistoryDAO) ctx.getAttribute("trainingHistoryDAO");
		CustomerDAO customerDAO = (CustomerDAO) ctx.getAttribute("customerDAO");
		Customer c = (Customer)request.getSession().getAttribute("loggedInUser");
		boolean isVisited = false;
		if(c.getVisitedFacility()!=null) {
		for(String facility: c.getVisitedFacility())
		{
			if(facility.equals(t.getSportFacility()))
			{
				isVisited = true;
				break;
			}
		}}
		else {
			c.setVisitedFacility(new ArrayList<String>());
		}
		if(!isVisited)
		{
			c.getVisitedFacility().add(t.getSportFacility());
			c.setVisitedFacility(c.getVisitedFacility());
			customerDAO.update(c.getUsername(), c);
			request.getSession().setAttribute("loggedInUser", c);
		}
		
		MembershipFeeDAO membershipFeeDAO = (MembershipFeeDAO) ctx.getAttribute("membershipFeeDAO");
		MembershipFee mf = membershipFeeDAO.find(findMembershipFee(c.getUsername()));
		if(mf == null)
			return 0;
		
		if(!mf.getEntranceCountPerDay().equals("Neograniceno")){
			int number = Integer.parseInt(mf.getEntranceCountPerDay());
			if(number <= 0)
				return 3;
			number --;
			mf.setEntranceCountPerDay(Integer.toString(number));
			membershipFeeDAO.update(mf.getUniqueId(),mf);
		}
	    int numOfTH = thDAO.findAll().size() + 1;
	    String id = "th" + Integer.toString(numOfTH);
	    String dateTimeOfCheckIn = date + ' ' + time;
		TrainingHistory th = new TrainingHistory(id, dateTimeOfCheckIn, t.getName(), c.getUsername(), t.getCoach());
		thDAO.save(th);
		
		CoachDAO coachDAO = (CoachDAO) ctx.getAttribute("coachDAO");
		Coach coach = coachDAO.findCoach(t.getCoach());
		coach.addTrainingHistory(th.getId());
		coachDAO.update(coach.getUsername(), coach);
		
		if(!isVisited) 
		{
			ctx.setAttribute("visitedSportFacility", t.getSportFacility());
			return 2;
		}
			
		return 1;
	}
	
	private String findMembershipFee(String username) {
		String membershipFee = "";
		MembershipFeeDAO membershipFeeDAO = (MembershipFeeDAO) ctx.getAttribute("membershipFeeDAO");
		for(MembershipFee mf : membershipFeeDAO.findAll()) {
				
					if(mf.getCustomer().getUsername().equals(username)) {
						membershipFee = mf.getUniqueId();
						break;
					}
				
		}
		return membershipFee;
	}
	
	@POST
	@Path("/addComment")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addComment(Comment com) {
		CommentDAO commentDAO = (CommentDAO) ctx.getAttribute("commentDAO");
		SportsFacilityDAO sportFacilityDAO = (SportsFacilityDAO) ctx.getAttribute("sportsFacilityDAO");
		String sf = (String) ctx.getAttribute("visitedSportFacility");
		Customer c = (Customer)request.getSession().getAttribute("loggedInUser");
		com.setSportsFacility(sf);
		com.setCustomer(c.getUsername());
		int numOfComments = commentDAO.findAll().size() + 1;
		int sum = com.getGrade();
		int num = 1;
		for(Comment comment : commentDAO.findAll()){
			if(comment.getSportsFacility().equals(com.getSportsFacility()))
			{
				sum += comment.getGrade();
				num ++;
			}
		}
		SportsFacility sp = sportFacilityDAO.findSportsFacility(com.getSportsFacility());
		sp.setAverageRating((double)sum/num);
		sportFacilityDAO.save(sp);
		com.setId("comment" + Integer.toString(numOfComments));
		commentDAO.save(com);
		return Response.status(200).entity("customerMainPage.html").build();
	}
	
	@GET
	@Path("/managerSportsFacility")
	@Produces(MediaType.APPLICATION_JSON)
	public SportsFacility managerSportFacility() {
		SportsFacilityDAO sportFacilityDAO = (SportsFacilityDAO) ctx.getAttribute("sportsFacilityDAO");
		Manager m = (Manager)request.getSession().getAttribute("loggedInUser");
		String sportsFacilityName = m.getSportsFacility();
		SportsFacility sportsFacility = sportFacilityDAO.findSportsFacility(sportsFacilityName);
		return sportsFacility;
	}
	
	@GET
	@Path("/customersSportsFacility")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Customer> customersSportFacility() {
		SportsFacilityDAO sportFacilityDAO = (SportsFacilityDAO) ctx.getAttribute("sportsFacilityDAO");
		Manager m = (Manager)request.getSession().getAttribute("loggedInUser");
		String sportsFacilityName = m.getSportsFacility();
		SportsFacility sportsFacility = sportFacilityDAO.findSportsFacility(sportsFacilityName);
		
		ArrayList<Customer> customers = new ArrayList<Customer>();
		
		if(sportsFacility != null) {
			CustomerDAO customerDAO = (CustomerDAO) ctx.getAttribute("customerDAO");
			for(Customer c : customerDAO.findAll()) {
				if(c.getVisitedFacility().contains(sportsFacility.getName())) {
					customers.add(c);
				}
			}
		}
		return customers;
	}
	
	@GET
	@Path("/coachesSportsFacility")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Coach> coachesSportFacility() {
		SportsFacilityDAO sportFacilityDAO = (SportsFacilityDAO) ctx.getAttribute("sportsFacilityDAO");
		Manager m = (Manager)request.getSession().getAttribute("loggedInUser");
		String sportsFacilityName = m.getSportsFacility();
		SportsFacility sportsFacility = sportFacilityDAO.findSportsFacility(sportsFacilityName);
		
		ArrayList<Coach> coaches = new ArrayList<Coach>();
		
		if(sportsFacility != null) {
			CoachDAO coachDAO = (CoachDAO) ctx.getAttribute("coachDAO");
			TrainingDAO trainingDAO  = (TrainingDAO) ctx.getAttribute("trainingDAO");
			for(Training training : trainingDAO.findAllTrainings()) {
				if(training.getSportFacility().equals(sportsFacilityName)) {
					if(!coaches.contains(coachDAO.findCoach(training.getCoach()))){
						coaches.add(coachDAO.findCoach(training.getCoach()));
					}
				}
			}
		}
		return coaches;
	}
	
	@GET
	@Path("/coaches")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Coach> getAllCoaches() {
		CoachDAO coachDAO = (CoachDAO) ctx.getAttribute("coachDAO");
		return coachDAO.findAll();
		
	}
	
	@POST
	@Path("/createTraining/{coach}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean createTraining(Training training, @PathParam("coach") String coach) {
		boolean success = false;
		Manager manager = (Manager)request.getSession().getAttribute("loggedInUser");
		TrainingDAO trainingDAO = (TrainingDAO) ctx.getAttribute("trainingDAO");
		SportsFacilityDAO sportsFacilityDAO = (SportsFacilityDAO) ctx.getAttribute("sportsFacilityDAO");
		if(sportsFacilityDAO.findSportsFacility(manager.getSportsFacility()) != null) {
			training.setSportFacility(manager.getSportsFacility());
			ArrayList<String>list = sportsFacilityDAO.findSportsFacility(manager.getSportsFacility()).getTrainings();
			list.add(training.getName());
			sportsFacilityDAO.findSportsFacility(manager.getSportsFacility()).setTrainings(list); 
			CoachDAO coachDAO = (CoachDAO) ctx.getAttribute("coachDAO");
			String[] name = coach.split(" ");
			for(Coach c : coachDAO.findAll()) 
			{
				if(c.getFirstName().equals(name[0]) && c.getLastName().equals(name[1])) {
					training.setCoach(c.getUsername());
					break;
				}
			}
			Training tr = trainingDAO.findTraining(training.getName());
			if(tr == null) {
				tr = trainingDAO.save(training);
				success = true;
			}
		}
		return success;
	}

	@PUT
	@Path("/updateTraining/{coach}/{name}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean updateTraining(Training training, @PathParam("coach") String coach,  @PathParam("name") String name) {
		boolean success = false;
		Manager manager = (Manager)request.getSession().getAttribute("loggedInUser");
		SportsFacilityDAO sportsFacilityDAO = (SportsFacilityDAO) ctx.getAttribute("sportsFacilityDAO");
		SportsFacility sportsFacility = sportsFacilityDAO.findSportsFacility(manager.getSportsFacility()) ;
		TrainingDAO trainingDAO = (TrainingDAO) ctx.getAttribute("trainingDAO");
		if(sportsFacility != null) {
			training.setSportFacility(manager.getSportsFacility());
			CoachDAO coachDAO = (CoachDAO) ctx.getAttribute("coachDAO");
			String[] nameCoach = coach.split(" ");
			for(Coach c : coachDAO.findAll()) 
			{
				if(c.getFirstName().equals(nameCoach[0]) && c.getLastName().equals(nameCoach[1])) {
					training.setCoach(c.getUsername());
					break;
				}
			}
			Training tr = trainingDAO.findTraining(training.getName());
			
			if((tr != null && tr.getName().equals(name)) || tr == null)  {
				trainingDAO.update(training.getName(), training);
				if(!training.getName().equals(name)) {
					ArrayList<String> trainings = sportsFacility.getTrainings();
					trainings.remove(name);
					trainings.add(training.getName());
					
					sportsFacility.setTrainings(trainings);
					sportsFacilityDAO.update(sportsFacility.getName(), sportsFacility);
					
				}
				
				
				success = true;
			}
		}
		return success;
	}
	
	
}
