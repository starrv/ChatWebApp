package main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/process")
public class Process extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		if(req.getParameter("signIn")!=null)
		{
			resp.getWriter().print("sign in");
			signIn();
		}
		else if(req.getParameter("createAccount")!=null)
		{
			resp.getWriter().print("create account");
			createAccount();
		}
		else if(req.getParameter("logOut")!=null)
		{
			resp.getWriter().print("log out");
			logOut();
		}
	}
	
	
	private void signIn()
	{
		 
	}
	
	private void createAccount() 
	{
		
	}
	
	private void logOut()
	{
		
	}

}
