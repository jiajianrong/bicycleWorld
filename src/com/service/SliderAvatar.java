package com.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import core.PathMgr;

public class SliderAvatar {

	static String DIR_PREFIX = "index_slider_avatar";
	static String FILE_PREFIX = "slider-avatar-";
	static String FILE_SUFFIX = ".png";
	
	
	/**
	 * 
	 * @param align whether to fill list up to length=6
	 * @return
	 */
	public static List getAll(boolean align) {
		
		String projectPath = PathMgr.getProjectPath();
		File dir = new File( projectPath + DIR_PREFIX );
		
		File[] files = dir.listFiles();
		List names = new ArrayList();
		
		for (int i=0;i<files.length;i++) {
			String name = files[i].getName();
			long lastModified = files[i].lastModified();
			if (!name.endsWith("txt"))
				names.add( DIR_PREFIX + "/" + name + "?" + lastModified );
		}
		
		if (align) {
			names = alignList(names);
		}
		
		return names;
	}
	
	
	
	public static void remove(String id) {
		String projectPath = PathMgr.getProjectPath();
		String absAvatarPath = projectPath + DIR_PREFIX + "/" + FILE_PREFIX + id + FILE_SUFFIX;

		File file = new File(absAvatarPath);
		file.delete();
	}
	
	
	
	public static void update(HttpServletRequest request) {
		Map paramMap = Utils.uploadUtil(request, DIR_PREFIX, FILE_PREFIX, FILE_SUFFIX);
	}
	
	
	
	
	private static List alignList(List origin) {
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
	
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
