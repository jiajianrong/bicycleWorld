<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %> 
<% 
String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
request.setCharacterEncoding("UTF-8");

String id = "";
String title = "";
String content = "";
String tag = "";
String pubTime = "";

if ( request.getAttribute("id") != null ) {
	id = (String) request.getAttribute("id");
}
if ( request.getAttribute("title") != null ) {
	title = (String) request.getAttribute("title");
}
if ( request.getAttribute("content") != null ) {
	content = (String) request.getAttribute("content");
}
if ( request.getAttribute("tag") != null ) {
	tag = (String) request.getAttribute("tag");
}
if ( request.getAttribute("pubTime") != null ) {
	pubTime = (String) request.getAttribute("pubTime");
}
%>
<!DOCTYPE> 
<html> 
<head> 
<base href="<%=basePath%>"> 
<title>后台系统</title> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<link rel="stylesheet" type="text/css" href="jsp/bootstrap.css">
<link rel="stylesheet" href="jsp/jquery.datetimepicker.css"/>
<script src="jsp/jquery-1.11.3.js"></script>
<script src="jsp/jquery.datetimepicker.js"></script>
</head> 
<body>

<div class="container">
	<h1><a href="jsp/allArticles.jsp">返回上级页面</a></h1>
	
	
	<div class="row clearfix">
		<div class="col-md-12 column">
			<form role="form" action="FckServlet?action=formSubmit" method="post" class="form-inline" style="overflow: hidden;">
				
				<input type="hidden" name="id" value="<%=id%>" >
				
				<div class="page-header">
					<div class="form-group" style="width: 60%">
						 <input type="text" class="form-control" name="title" value="<%=title%>" placeholder="请输入标题" style="width: 100%" >
					</div>
					<div class="form-group" style="width: 10%">
						 <input type="text" class="form-control" name="tag" value="<%=tag%>" placeholder="请输入标签" style="width: 100%" >
					</div>
					<div class="form-group" style="width: 13%">
						 <input type="text" class="form-control" name="pubTime" id="pubTime" value="<%=pubTime%>" placeholder="请选择日期" style="width: 100%" >
					</div>
					<div class="form-group" style="width: 10%">
						 <input type="submit" value="全部保存" class="btn btn-primary">
					</div>
				</div>
				
				
				<div id="fck-editor-wrap">
					<FCK:editor instanceName="content" height="70%"> 
					    <jsp:attribute name="value"><%=content%></jsp:attribute> 
				    </FCK:editor> 
				</div>
				
			</form>
		</div>
	</div>
</div>










<script>
Date.prototype.format=function(){
    var _0=function(){
        return this<10?("0"+this):this;
    };
    return function(s){
        var map={
            y:this.getFullYear(),
            M:_0.call(this.getMonth()+1),
            d:_0.call(this.getDate()),
            H:_0.call(this.getHours()),
            m:_0.call(this.getMinutes()),
            s:_0.call(this.getSeconds())};
        return (s||"{y}-{M}-{d} {H}:{m}:{s}").replace( /{(y|M|d|H|m|s)+}/g, function(s,t){
            return map[t];
        });
    };
}();


$(function(){
	// -----------
	// 设置发布时间
	// -----------
	var $pubTime = $('#pubTime');
	
	if (!$pubTime.val()) {
		var now = new Date();
		var timeStr = now.format("{yyyy}/{MM}/{dd} {H}:{m}");
		$pubTime.val(timeStr);
	}
	
	$.datetimepicker.setLocale('ch');
	$pubTime.datetimepicker();
	
	
	// -----------
	// 设置fck iframe高度
	// -----------
	setTimeout(function(){
		var bodyHeight = +($(document.body).css('height').match(/\d+/)[0]);
		var fckTop = $('#fck-editor-wrap').offset().top;
		var offset = bodyHeight - fckTop - 40;
		
		top.frames['content___Frame'].style.height = offset+"px";
	},400)
})
</script>
</body> 
</html>