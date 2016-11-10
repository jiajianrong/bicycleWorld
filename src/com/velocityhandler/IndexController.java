package com.velocityhandler;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import com.service.SliderAvatar;
import com.service.Tag;

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
		
		setArticles(request, response, ctx);
		setSliderAvatars(request, response, ctx);
		setTags(request, response, ctx);
		
		return velo.getTemplate("page/index/index.vm");
	}
	
	
	/**
	 * 设置文章列表
	 * @param request
	 * @param response
	 * @param ctx
	 */
	private void setArticles(HttpServletRequest request, HttpServletResponse response, Context ctx) {
		String tag = request.getParameter("tag");
		String sql = "select * from main_article";
		if (tag!=null)
			sql += " where tag='" + tag + "'";
		sql += " order by id desc";
				
		List list = DBMgr.executeQuery( sql );

		for (int i = 0; i < list.size(); i++) {
			TableObject tb = (TableObject) list.get(i);
			tb.set("pubTime", formatDate(tb.get("pubTime")));
		}

		ctx.put("articles", list);
	}
	
	
	/**
	 * 设置轮播图
	 * @param request
	 * @param response
	 * @param ctx
	 */
	private void setSliderAvatars(HttpServletRequest request, HttpServletResponse response, Context ctx) {
		List names = SliderAvatar.getAll(false);
		ctx.put("sliderAvatars", names);
	}
	
	
	
	/**
	 * 设置右侧tags
	 * @param request
	 * @param response
	 * @param ctx
	 */
	private void setTags(HttpServletRequest request, HttpServletResponse response, Context ctx) {
		List tags = Tag.getAll();
		ctx.put("tags", tags);
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