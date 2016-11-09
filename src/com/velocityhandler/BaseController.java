package com.velocityhandler;

import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.view.VelocityViewServlet;

public abstract class BaseController extends VelocityViewServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected VelocityEngine velo;
	
	public void init() throws ServletException {

        velo = new VelocityEngine();// velocity引擎对象

        Properties prop = new Properties();// 设置vm模板的装载路径

        String path = this.getServletContext().getRealPath("/");
        
        prop.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
        prop.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        prop.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
        prop.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, path + "views/");

        try {
            velo.init(prop);
            // 初始化设置，下面用到getTemplate("*.vm")输出时;
            //一定要调用velo对象去做,即velo.getTemplate("*.vm")
        } catch (Exception e) {
            e.printStackTrace();
        }
        

    }
	
	
	protected abstract Template handleRequest(HttpServletRequest request, HttpServletResponse response, Context ctx);
}
