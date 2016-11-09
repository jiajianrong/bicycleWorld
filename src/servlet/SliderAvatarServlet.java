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


public class SliderAvatarServlet extends HttpServlet {
	
	String DIR_PREFIX = "index_slider_avatar";
	String FILE_PREFIX = "slider-avatar-";
	String FILE_SUFFIX = ".png";
	
	
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
		ServletContext sctx = getServletContext();
		String absFilePath = sctx.getRealPath(DIR_PREFIX);
		File dir = new File(absFilePath);
		
		File[] files = dir.listFiles();
		List names = new ArrayList();
		
		for (int i=0;i<files.length;i++) {
			String name = files[i].getName();
			long lastModified = files[i].lastModified();
			if (!name.endsWith("txt"))
				names.add( DIR_PREFIX + "/" + name + "?" + lastModified );
		}
		
		names = alignList(names);
		
		request.setAttribute("names", names);
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/addOrRemoveSliderAvatar.jsp"); 
	    dispatcher.forward(request, response); 
	}
	
	
	private List alignList(List origin) {
		List rtn = new ArrayList();
		
		int count = 0;
		
		for (int i=0; i<6; i++) {
			if (count<origin.size()) {
				String v = (String)origin.get(count);
				if (v.indexOf(FILE_PREFIX+i)!=-1) {
					rtn.add(v);
					count++;
				}
				else
					rtn.add("");
			} else {
				rtn.add("");
			}
		}
		
		return rtn;
	}
	
	
	
	private void removeAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		
		ServletContext sctx = getServletContext();
		String path = sctx.getRealPath(DIR_PREFIX);
		String absAvatarPath = path + "/" + FILE_PREFIX + id + FILE_SUFFIX;

		File file = new File(absAvatarPath);
		file.delete();
		
        response.setContentType("text/html; charset=UTF-8");
        response.sendRedirect("SliderAvatarServlet?action=list");
	}
	
	
	
	private void updateAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map paramMap = uploadUtil(request);
		
        response.setContentType("text/html; charset=UTF-8");
        response.sendRedirect("SliderAvatarServlet?action=list");
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
				String path = sctx.getRealPath(DIR_PREFIX);
				String fileName = fileItem.getName();
				
				if ( fileName == null || "".equalsIgnoreCase(fileName.trim()) ) {
					return rtnMap;
				}
				//fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
				String fileNameSuffix = fileName.substring(fileName.lastIndexOf("."));
				
				id = FILE_PREFIX + id;
				fileNameSuffix = FILE_SUFFIX;
				
				String absAvatarPath = path + "/" + id + fileNameSuffix;
				String relAvatarPath = DIR_PREFIX + "/" + id + fileNameSuffix;
				
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
