package servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.DBMgr;
import core.DBMgr.TableObject;


public class FckServlet extends HttpServlet {
	
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
    	 * 提交内容
    	 */
    	if ( action.equalsIgnoreCase( "formSubmit" ) ) {
    		formSubmit( request, response );
        }
    	
    	/*
    	 * 修改内容表单界面
    	 */
    	else if( action.equalsIgnoreCase( "update" ) ) {
    		updateForm( request, response );
        }
    	
    	/*
    	 * 删除文章
    	 */
    	else if( action.equalsIgnoreCase( "remove" ) ) {
    		remove( request, response );
        }
    	
	}


	
	
	
	
	
	
	
	
	
	private void formSubmit(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String id = request.getParameter("id");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String tag = request.getParameter("tag");
        String pubTime = request.getParameter("pubTime");
        
        List names = new ArrayList();
        List types = new ArrayList();
        List contents = new ArrayList();
        
        
        names.add( "title" );
        names.add( "content" );
        names.add( "tag" );
        names.add( "pubTime" );
        
        types.add( DBMgr.PSTMT_TYPE_STRING );
        types.add( DBMgr.PSTMT_TYPE_STRING );
        types.add( DBMgr.PSTMT_TYPE_STRING );
        types.add( DBMgr.PSTMT_TYPE_STRING );
        
        contents.add( title );
        contents.add( content ); 
        contents.add( tag );
        contents.add( pubTime ); 
        
        
        if (id != null && !"".equalsIgnoreCase(id.trim())) {
        	names.add( "id" );
        	types.add( DBMgr.PSTMT_TYPE_INT );
        	contents.add( id );
        	
        	DBMgr.executeUpdate( "main_article", names, types, contents );
        } else {
        	DBMgr.executeInsert( "main_article", names, types, contents );
        }
        
        
        response.setContentType("text/html; charset=UTF-8");
        response.sendRedirect("jsp/allArticles.jsp");
        
	}
	
	
	
	
	
	
	private void updateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("id");
        List list = DBMgr.executeQuery( "select * from main_article where id=" + id );
        TableObject tb = (TableObject)list.get(0);
        String content = tb.get( "content" );
        String title = tb.get( "title" );
        String tag = tb.get( "tag" );
        String pubTime = tb.get( "pubTime" );
        
        request.setAttribute( "id", id );
        request.setAttribute( "title", title );
        request.setAttribute( "content", content );
        request.setAttribute( "tag", tag );
        request.setAttribute( "pubTime", pubTime );
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/addOrUpdateArticleForm.jsp"); 
        dispatcher.forward(request, response); 
        
	}
	
	
	
	
	
	

	private void remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String id = request.getParameter("id");
		
		List list = DBMgr.executeQuery( "select avatar from main_article where id=" + id );
		TableObject tb = (TableObject)list.get(0);
	    String avatar = tb.get( "avatar" );
	    DBMgr.execute( "delete from main_article where id=" + id );
		
		ServletContext sctx = getServletContext();
		String absFilePath = sctx.getRealPath(avatar);
		File f = new File(absFilePath);
		f.delete();
		
	    response.setContentType("text/html; charset=UTF-8");
	    response.sendRedirect("jsp/allArticles.jsp"); 
	    
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Constructor of the object.
	 */
	public FckServlet() {
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
