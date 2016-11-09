<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ page import="core.DBMgr" %>
<%@ page import="core.DBMgr.TableObject" %>

<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %> 
<% 
String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE> 
<html> 
<head> 
<base href="<%=basePath%>"> 
<title>后台系统</title> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="jsp/bootstrap.css">
<script src="jsp/jquery-1.11.3.js"></script>
<script src="jsp/bootstrap.js"></script>
</head> 
<body> 



<div class="container">
	<h1><a href="index">预览首页&nbsp;&nbsp;&nbsp;</a><small><a href="SliderAvatarServlet?action=list">修改首页轮播图</a></small></h1>
	<br />
	
	<div class="row clearfix">
	<div class="col-md-12 column">
	<table class="table">
		<thead>
			<tr>
				<th style="width:100px;"><button type="button" class="btn btn-primary" id="add-article">新增文章</button></th>
				<th>Title</th>
				<th>Tag</th>
				<th>Date</th>
			</tr>
			<script>$("#add-article").click(function(){location.href="jsp/addOrUpdateArticleForm.jsp"})</script>
		</thead>
		<tbody>
		<%
		List list = DBMgr.executeQuery( "select id, title, tag, pubTime from main_article order by id desc" );
		for( int i=0; i<list.size(); i++ )
		{
		    TableObject tb = (TableObject)list.get(i);
		    String id = tb.get("id");
		    String title = tb.get("title");
		    String tag = tb.get("tag");
		    String pubTime = tb.get("pubTime");
		%>
			<tr>
				<td>
					<div class="btn-group">
						<button type="button" class="btn btn-default dropdown-toggle" 
								data-toggle="dropdown">请选择 <span class="caret"></span>
						</button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="index-detail?id=<%=id%>">预览</a></li>
							<li><a href="FckServlet?action=update&id=<%=id%>">修改文章</a></li>
							<li><a href="FckServlet?action=remove&id=<%=id%>">删除</a></li>
							<li class="divider"></li>
							<li><a href="ArticleAvatarServlet?action=updateAvatar&id=<%=id%>">修改文章在首页显示的图片</a></li>
						</ul>
					</div>
				</td>
			    <td style="vertical-align: middle;"><%=title%></td>
			    <td style="vertical-align: middle;"><%=tag%></td>
			    <td style="vertical-align: middle;"><%=pubTime%></td>
			</tr>
		<%
		}
		%>
		</tbody>
	</table>
	</div>
	</div>
</div>





</body> 
</html>