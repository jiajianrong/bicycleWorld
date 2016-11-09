package com.service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class Utils {

	
	
	public static Map uploadUtil(HttpServletRequest request, String dir_prefix, String file_prefix, String file_suffix) {
		
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
				ServletContext sctx = request.getServletContext();
				String path = sctx.getRealPath(dir_prefix);
				String fileName = fileItem.getName();
				
				if ( fileName == null || "".equalsIgnoreCase(fileName.trim()) ) {
					return rtnMap;
				}
				//fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
				String fileNameSuffix = fileName.substring(fileName.lastIndexOf("."));
				
				if (file_prefix!=null && file_suffix!=null) {
					id = file_prefix + id;
					fileNameSuffix = file_suffix;
				}
				
				String absAvatarPath = path + "/" + id + fileNameSuffix;
				String relAvatarPath = dir_prefix + "/" + id + fileNameSuffix;
				
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
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
