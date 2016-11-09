package servlet;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import core.DBMgr;
import core.DBMgr.TableObject;


public class ArticleAvatarServlet extends HttpServlet {
	
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
    	 * 提交avatar
    	 */
    	if ( action.equalsIgnoreCase( "avatarSubmit" ) ) {
    		avatarSubmit( request, response );
        }
    	
    	/*
    	 * 跳转到修改avatar界面
    	 */
    	else if( action.equalsIgnoreCase( "updateAvatar" ) ) {
    		updateAvatar( request, response );
        }
    	
	}


	
	

	
	/**
	 * 跳转到修改avatar页面
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void updateAvatar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String id = request.getParameter("id");
	    List list = DBMgr.executeQuery( "select id, title, avatar from main_article where id=" + id );
	    TableObject tb = (TableObject)list.get(0);
	    String avatar = tb.get( "avatar" );
	    String title = tb.get( "title" );
	    
	    request.setAttribute( "id", id );
	    request.setAttribute( "title", title );
	    request.setAttribute( "avatar", avatar );
	    
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/addOrUpdateArticleAvatar.jsp"); 
	    dispatcher.forward(request, response); 
	}
	
	
	
	
	
	/**
	 * 提交修改后的avatar
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void avatarSubmit(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map paramMap = uploadUtil(request);
		String id = (String)paramMap.get("id");
		String title = (String)paramMap.get("title");
		String avatar = paramMap.get("avatar")==null ? null : (String)paramMap.get("avatar") + "?" + (new Date()).getTime();
		
		
        List names = new ArrayList();
        List types = new ArrayList();
        List contents = new ArrayList();
        
        names.add( "title" );
        if (avatar!=null) names.add( "avatar" );
        
        types.add( DBMgr.PSTMT_TYPE_STRING );
        if (avatar!=null) types.add( DBMgr.PSTMT_TYPE_STRING );
        
        contents.add( title );
        if (avatar!=null) contents.add( avatar );
        
        
        if (id != null && !"".equalsIgnoreCase(id.trim())) {
        	names.add( "id" );
        	types.add( DBMgr.PSTMT_TYPE_INT );
        	contents.add( id );
        	
        	DBMgr.executeUpdate( "main_article", names, types, contents );
        } else {
        	DBMgr.executeInsert( "main_article", names, types, contents );
        }
        
        
        response.setContentType("text/html; charset=UTF-8");
        response.sendRedirect("ArticleAvatarServlet?action=updateAvatar&id=" + id);
	}
	
	
	
	

	
	
	
	private Map uploadUtil(HttpServletRequest request) {
		
		DiskFileItemFactory factory = new DiskFileItemFactory(); 
		ServletFileUpload upload = new ServletFileUpload(factory); 
		List items = null;
		Map rtnMap = new HashMap(); 
		String id = null;
		
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e) {
			e.printStackTrace();
		} 
		
		
		for (Object object : items) {
			FileItem fileItem = (FileItem) object;
			if (fileItem.isFormField()) {
				try {
					rtnMap.put(fileItem.getFieldName(), fileItem.getString("utf-8"));
					// 提取id
					if ( "id".equalsIgnoreCase(fileItem.getFieldName()) ) {
						id = fileItem.getString("utf-8");
					}
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				
			}
		}
		
		
		for (Object object : items) {
			FileItem fileItem = (FileItem) object;
			if ( !fileItem.isFormField() ) {
				ServletContext sctx = getServletContext();
				String path = sctx.getRealPath("/index_avatar");
				String fileName = fileItem.getName();
				
				if ( fileName == null || "".equalsIgnoreCase(fileName.trim()) ) {
					return rtnMap;
				}
				//fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
				String fileNameSuffix = fileName.substring(fileName.lastIndexOf("."));
				
				
				String absAvatarPath = path + "/" + id + fileNameSuffix;
				String relAvatarPath = "index_avatar/" + id + fileNameSuffix;
				
				rtnMap.put(fileItem.getFieldName(), relAvatarPath);

				File file = new File(absAvatarPath);
				//if (!file.exists()) {
				try {
					fileItem.write(file);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//}
			}
		}
		
		return rtnMap;
	}
	
	
	
	
	
	/**
	 * Constructor of the object.
	 */
	public ArticleAvatarServlet() {
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
