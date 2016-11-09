package com.velocityhandler;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import core.DBMgr;
import core.DBMgr.TableObject;

public class IndexDetailController extends BaseController {

    private static final long serialVersionUID = 1L;


    protected Template handleRequest(HttpServletRequest request, HttpServletResponse response, Context ctx) {
    	
    	try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	
    	response.setContentType( "text/html;charset=UTF-8" ) ;  //设置响应页面字符编码  
    	
    	
    	
//		String configPath = PathMgr.getProjectPath() + "configs/article-1/";
//    	PropMgr info = new PropMgr(configPath + "info.txt");
//		String articleTitle = info.getValue("article.title");
//		try {
//			articleTitle = new String(articleTitle.getBytes("iso-8859-1"), "UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//      Vector personList = new Vector();
//      personList.addElement("Charles");
//      personList.addElement("Michael");
//      ctx.put("theList", personList); // 将模板数据 list放置到上下文环境context中
		
		
		String id = request.getParameter("id");
		List list = DBMgr.executeQuery( "select * from main_article where id=" + id );
        TableObject tb = (TableObject)list.get(0);
        String articleTitle = tb.get("title");
        String articleContent = tb.get("content");
		
        
        ctx.put("articleTitle", articleTitle);
        ctx.put("articleContent", articleContent);
		
		

        Template template = velo.getTemplate("page/index-detail/index-detail.vm");
        return template;
    }

}          