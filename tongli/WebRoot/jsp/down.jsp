<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page   import= "java.io.* "%> 

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>文件下载</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
     <jsp:useBean id="mydown" class="com.jspsmart.upload.SmartUpload"/>

<%

  String downfile=request.getParameter("downfile");

  try{

      response.reset();

      out.clear();

      out=pageContext.pushBody();

      mydown.initialize(pageContext);

      mydown.setContentDisposition(null);

      mydown.downloadFile(downfile);

  }catch(Exception e){
	  out.print("<li>文件下载失败：请检查选择的文件是否存在？</li>");
  }

%>

  </body>
</html>
