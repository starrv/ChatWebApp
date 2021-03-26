package main;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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
		resp.getWriter().println("hello");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		if(req.getParameter("signIn")!=null)
		{
			if(signIn(req.getParameter("username"),req.getParameter("password")))
			{
				Cookie cookie=new Cookie("user",req.getParameter("username"));
				cookie.setMaxAge(2592000);
				resp.addCookie(cookie);
				resp.sendRedirect("./chat.jsp");
			}
			else
			{
				resp.getWriter().println("An error has occurred");
			}
		}
		else if(req.getParameter("createAccount")!=null)
		{
			if(createAccount(req.getParameter("email"),req.getParameter("username"),req.getParameter("password")))
			{
				resp.getWriter().println("Account created");
			}
			else
			{
				resp.getWriter().println("An error has occurred");
			}
		}
		else if(req.getParameter("logOut")!=null)
		{
			Cookie[] cookies=req.getCookies();
			String cookieName="";
			for(int index=0; index<cookies.length; index++)
			{
				cookieName=cookies[index].getName();
				if(cookieName=="user")
				{
					cookies[index].setMaxAge(-1);
					resp.sendRedirect("./index.html");
					resp.getWriter().println("You are logged out");
					break;
				}
			}
			resp.getWriter().println("There was an error logging out");
		}
	}
	
	private static Connection getConnection() throws URISyntaxException, SQLException 
	{
	    String dbUrl = System.getenv("JDBC_DATABASE_URL");
	    return DriverManager.getConnection(dbUrl);
	}
	
	
	private boolean signIn(String username, String password)
	{
		try 
		{
			Connection conn=getConnection();
			String sql="Select email, username, password from user_accounts where email=username or username=username and password=password";
			PreparedStatement stmt=conn.prepareStatement(sql);
			boolean result=stmt.execute();
			if(result)
			{
				if(stmt.getFetchSize()>0)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
			else
			{
				return false;
			}
		} 
		catch (URISyntaxException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
	}
	
	private boolean createAccount(String email, String username, String password) 
	{
		try 
		{
			Connection conn=getConnection();
			String sql="insert into user_accounts(email, username, password) values email,username,password";
			PreparedStatement stmt=conn.prepareStatement(sql);
			boolean result=stmt.execute();
			if(result)
			{
				return true;
			}
			else
			{
				return false;
			}
		} 
		catch (URISyntaxException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
	}

}
