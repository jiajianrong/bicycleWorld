package servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.SliderAvatar;
import com.service.Utils;


public class SliderAvatarServlet extends HttpServlet {
	
	
	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	    
		String action = request.getParameter("action");
    	
    	
    	/*
    	 * list页
    	 */
    	if( action.equalsIgnoreCase( "list" ) ) {
    		list( request, response );
        }
    	
    	/*
    	 * 提交avatar
    	 */
		else if ( action.equalsIgnoreCase( "updateAvatar" ) ) {
    		updateAvatar( request, response );
        }
    	
    	/*
    	 * 删除avatar
    	 */
    	else if ( action.equalsIgnoreCase( "removeAvatar" ) ) {
    		removeAvatar( request, response );
        }
    	
	}


	
	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ServletContext sctx = getServletContext();
		// String absFilePath = sctx.getRealPath(DIR_PREFIX);
		
		List names = SliderAvatar.getAll(true);
		request.setAttribute("names", names);
		
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/addOrRemoveSliderAvatar.jsp"); 
	    dispatcher.forward(request, response); 
	}
	
	
	
	
	
	private void removeAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		SliderAvatar.remove(id);
		
        response.setContentType("text/html; charset=UTF-8");
        response.sendRedirect("SliderAvatarServlet?action=list");
	}
	
	
	
	private void updateAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException {
		SliderAvatar.update(request);
		
        response.setContentType("text/html; charset=UTF-8");
        response.sendRedirect("SliderAvatarServlet?action=list");
	}
	
	
	
	
	
	/**
	 * Constructor of the object.
	 */
	public SliderAvatarServlet() {
		super();
	}
	
	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occure
	 */
	public void init() throws ServletException {
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	
	
	
	public static void main(String[] a ) {
		File f = new File("E:/fxgl.sql");
		System.out.println(f.getAbsolutePath() + "中文");
		System.out.println(System.getProperty("file.encoding"));
		
	}

}
