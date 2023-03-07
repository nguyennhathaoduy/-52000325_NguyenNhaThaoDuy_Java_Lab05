package tdtu.com;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(urlPatterns = "/index")
public class LoginServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private LoginDAO loginDao;

    public void init() 
    {
        loginDao = new LoginDAO();
    }
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() 
    {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//doGet(request, response);
		String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserLogin us = new UserLogin();
        us.setUsername(username);
        us.setPassword(password);
        
        try 
        {
			if(loginDao.validate(us))
			{  
				Cookie name = new Cookie("username", username);
                Cookie pass = new Cookie("password", password);
                name.setMaxAge(60*60*24*30);
                pass.setMaxAge(60*60*24*30);
            	response.addCookie(name);
            	response.addCookie(pass);
                response.sendRedirect("home.http");
                return;  
			}  
			else
			{  
			    System.out.println("<font color=red>User name or password is wrong</font>");
			    RequestDispatcher rd=request.getRequestDispatcher("index.html");  
			    rd.include(request,response);  
			}
		} 
        catch (ClassNotFoundException | ServletException | IOException e) 
        {
			e.printStackTrace();
		}  

	}

}
