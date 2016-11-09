<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %> 
<% 
String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
request.setCharacterEncoding("UTF-8");

String id = "";
String title = "";
String avatar = "";

if ( request.getAttribute("id") != null ) {
    id = (String) request.getAttribute("id");
}
if ( request.getAttribute("title") != null ) {
    title = (String) request.getAttribute("title");
}
if ( request.getAttribute("avatar") != null ) {
    avatar = (String) request.getAttribute("avatar");
}
%>
<!DOCTYPE> 
<html> 
<head> 
<base href="<%=basePath%>"> 
<title>后台系统</title> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<link rel="stylesheet" type="text/css" href="jsp/bootstrap.css">
<style>
 	form {
 		position: relative; 
 	}
 	form #input-image {
 		position: absolute; 
 		left: 0px; 
 		top: 0;
 		height: 320px;
 		width: 650px;
 		pointer-events: none;
 	}
 	form #input-file {
 		display: block;
 		height: 320px;
 		width: 650px;
 		opacity: 0;
 	}
 	form #input-submit {
 		visibility: hidden;
 	}
</style>
</head> 
<body>
<div class="container">
	<h1><a href="jsp/allArticles.jsp">返回上级页面</a></h1>
	<br /><br />
	
	<form action="ArticleAvatarServlet?action=avatarSubmit" method="post" enctype="multipart/form-data">
		<input type="hidden" name="id" value="<%=id%>">
		<input type="hidden" name="title" value="<%=title%>">
		<input type="file" name="avatar" value="<%=avatar%>" id="input-file" />
		<img src="<%=avatar%>" id="input-image">
		<input type="submit" value="上传" id="input-submit" >
	</form> 
</div>


<script>
	var _file = document.getElementById("input-file");
	var _submit = document.getElementById("input-submit");
    _file.addEventListener( "change", function(){
 		_submit.click();
 	} )
</script>
</body> 
</html>