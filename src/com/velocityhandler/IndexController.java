package com.velocityhandler;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import core.DBMgr;
import core.DBMgr.TableObject;

public class IndexController extends BaseController {

	private static final long serialVersionUID = 1L;

	protected Template handleRequest(HttpServletRequest request, HttpServletResponse response, Context ctx) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=UTF-8"); // 设置响应页面字符编码
		
		setArticleList(request, response, ctx);
		setSliderAvatars(request, response, ctx);

		Template template = velo.getTemplate("page/index/index.vm");

		return template;
	}
	
	
	
	private void setArticleList(HttpServletRequest request, HttpServletResponse response, Context ctx) {
		List list = DBMgr.executeQuery("select * from main_article order by id desc");
		Vector articleVector = new Vector();

		for (int i = 0; i < list.size(); i++) {
			TableObject tb = (TableObject) list.get(i);

			String pubTime = tb.get("pubTime");
			String formatted = formatDate(pubTime);
			tb.set("pubTime", formatted);

			articleVector.addElement(tb);
		}

		ctx.put("articles", articleVector);
	}
	
	
	
	private void setSliderAvatars(HttpServletRequest request, HttpServletResponse response, Context ctx) {
		String DIR_PREFIX = "index_slider_avatar";
		
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
		
		ctx.put("sliderAvatars", names);
	}
	
	

	
	private String formatDate(String dateStr) {
		if (dateStr == null || "".equals(dateStr))
			return "";

		String[] parts = dateStr.split("[\\/:\\s]");
		String m = parts[1];
		String d = parts[2];

		m = m.replaceAll("^(0+)", "");
		d = d.replaceAll("^(0+)", "");

		return m + "月" + d + "日";
	}
	
	
	
	

	public static void main(String[] arr) {
		String[] parts = " 2016/08/13 21:00".split("[\\/:\\s]");

		for (int i = 0; i < parts.length; i++)
			System.out.println(parts[i]);
	}
}