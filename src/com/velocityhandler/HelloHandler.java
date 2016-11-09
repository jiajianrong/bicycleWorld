package com.velocityhandler;

import java.io.UnsupportedEncodingException;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

public class HelloHandler extends BaseController {

	private static final long serialVersionUID = 1L;
	
	private static String VM_PATH = "hello.vm";

	protected Template handleRequest(HttpServletRequest request,
			HttpServletResponse response, Context ctx) {

		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=UTF-8"); // 设置响应页面字符编码
		
		
		
		String p1 = "1Charles";
		String p2 = "2Michael";

		Vector personList = new Vector();

		personList.addElement(p1);
		personList.addElement(p2);

		ctx.put("theList", personList); // 将模板数据 list放置到上下文环境context中

		Template template = velo.getTemplate(VM_PATH);
		return template;
	}

}