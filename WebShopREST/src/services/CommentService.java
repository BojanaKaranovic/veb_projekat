package services;
import java.util.ArrayList;
import java.util.Collection;

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
import beans.CommentStatus;
import beans.Customer;
import beans.Manager;
import beans.SportsFacility;
import beans.User;
import dao.AdministratorDAO;
import dao.CoachDAO;
import dao.CommentDAO;
import dao.CustomerDAO;
import dao.ManagerDAO;
import dao.SportsFacilityDAO;
import dao.UserDAO;

@Path("/comments")
public class CommentService {
	@Context
	ServletContext ctx;

	public CommentService() {
		
	}
	
	@PostConstruct
	// ctx polje je null u konstruktoru, mora se pozvati nakon konstruktora (@PostConstruct anotacija)
	public void init() {
		if (ctx.getAttribute("commentDAO") == null) {
	    	String contextPath = ctx.getRealPath("");
			ctx.setAttribute("commentDAO", new CommentDAO(contextPath));
		}
		
	}

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Comment> allComments() {
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		return dao.findAll();
	}
	
	@GET
	@Path("/waitingForApproval")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Comment> waitingForApproval() {
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		ArrayList<Comment> comments = new ArrayList<Comment>();
		for(Comment c : dao.findAll())
			if(c.getStatus() == CommentStatus.WAITING)
				comments.add(c);
		return comments;
	}
	
	@GET
	@Path("/allowedComments")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Comment> allowedComments() {
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		ArrayList<Comment> comments = new ArrayList<Comment>();
		for(Comment c : dao.findAll())
			if(c.getStatus() == CommentStatus.ALLOWED)
				comments.add(c);
		return comments;
	}
	
	@POST
	@Path("/allowComment")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Comment allowComment(Comment c) {
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		c.setStatus(CommentStatus.ALLOWED);
		dao.update(c.getId(),c);
		return c;
	}
	
	@POST
	@Path("/declineComment")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Comment declineComment(Comment c) {
		CommentDAO dao = (CommentDAO) ctx.getAttribute("commentDAO");
		c.setStatus(CommentStatus.DECLINED);
		dao.update(c.getId(), c);
		return c;
	}
}
