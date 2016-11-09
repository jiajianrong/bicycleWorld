<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
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
	<h1><a href="jsp/allArticles.jsp">返回上级页面</a></h1>
	<form action="SliderAvatarServlet?action=updateAvatar" method="post" enctype="multipart/form-data" style="visibility: hidden;">
		<input type="hidden" name="id" id="input-id" value="">
		<input type="file" name="avatar" value="" id="input-file" />
		<input type="submit" value="上传" id="input-submit" >
	</form>
	
	
	<div class="row clearfix">
	<div class="col-md-12 column">
	<table class="table">
		<tbody>
		<%
	    List list = (List)request.getAttribute("names");
	    for( int i=0; i<list.size(); i++ )
	    {
		    String name =(String)list.get(i);
		    %>
		    <tr>
		    	<td>
		    		<div class="btn-group">
						<button type="button" class="btn btn-default dropdown-toggle" 
								data-toggle="dropdown">请选择 <span class="caret"></span>
						</button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="javascript:;" class="update-avatar" data-index="<%=i%>">更改</a></li>
							<li> <a href="SliderAvatarServlet?action=removeAvatar&id=<%=i%>">移除</a></li>
						</ul>
					</div>
		    	</td>
		    	<td><img src="<%=name%>" width="20%" style="min-height: 100px;" /></td>
		    </tr>
	        <%
	    }
	    %>
	    </tbody>
    </table>
	</div>
	</div>
	

</div>
 
 
<script>
var _id = document.getElementById('input-id');
var _file = document.getElementById('input-file');
var _submit = document.getElementById("input-submit");

var _avatar = document.getElementsByClassName('update-avatar');
for(var i=0;i<_avatar.length;i++) {
	_avatar[i].addEventListener("click", function(){
		_id.value = this.getAttribute("data-index");
		_file.click();
	})
}

_file.addEventListener( "change", function(){
	_submit.click();
} )
</script>
</body> 
</html>