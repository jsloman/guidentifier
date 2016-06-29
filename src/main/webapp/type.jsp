<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.guidentifier.*,
                 com.guidentifier.model.*,
                 com.guidentifier.dao.*,
                 com.guidentifier.util.WebUtil,
                 com.googlecode.objectify.*,
                 java.util.*" %>
<%! 
	boolean isAdmin = true;
	String thisURL = "/type";
	DAO dao = new DAO();
	
	void addGroup(Category t, String name) {
		Group f = new Group(t, name);
		dao.addGroup(f);
	}
%>
<%
	Region region = null;
	Category type = null;
	if (request.getPathInfo() == null || request.getPathInfo().length() == 1) {
		response.sendRedirect("/");
	}
	String[] parts = request.getPathInfo().split("/");
	if (parts.length != 3) {
		response.sendRedirect("/");
	}
	String regionStr = parts[1];;

	if (!("0".equals(regionStr))) {
		region = dao.getRegion(regionStr);
	}

	String idStr = parts[2];
	type = dao.getCategory(idStr);
	if (type == null) {
		response.sendRedirect("/typeError/" + idStr);
	}
	
	if (isAdmin && request.getParameter("family.add") != null) {
		addGroup(type, request.getParameter("family.name"));
	}
	if (isAdmin && request.getParameter("type.edit") != null) {
		type.setName(request.getParameter("edit.name"));
		dao.addCategory(type);
	}
%>
<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>Guidentifier: Category - <%= type.getName() %></title>
    <link rel="stylesheet" type="text/css" href="/style/main.css" />    
  </head>
   <body><div id="title">
	Guidentifier: Category - <%= type.getName() %>
</div>
<div id="breadcrumbs">
	<a href="/<%= region != null ? region.getName() : ""%>">Home</a> > <%= type.getName() %>
</div>
<div id="region">
	<p>Region:</p>
	<%= WebUtil.showRegions(dao, region, "type/", "/" + type.getName()) %>
</div>
<div id="topcontent">
	<div id="guide">
		<h2>Guide - families:</h2>
		<%= WebUtil.showGroups(dao, type, region, null) %>
	</div>
</div>
<%
	if (isAdmin) {
%>
<div id="admin">
	Admin
	<form action="<%= thisURL %>/<%= region == null ? "0" : region.getName() %>/<%= type.getName() %>" method="post">
	<p>Add family:</p>
	<input type="text" name="family.name"/>
	<input type="submit" name="family.add" value="Add"/>
	<br/>
	<p>Add form:</p>
	<input type="text" name="form.name"/>
	<input type="submit" name="form.add" value="Add"/>
	<br/>
	<p>Edit type:</p>
	Category Name: <input type="text" name="edit.name" size="50" value="<%= WebUtil.encodeForWeb(type.getName()) %>"/><br/>
	<input type="submit" name="type.edit" value="Save"/>
	<input type="reset" value="Reset"/>
	</form>
</div>
<%
	}
%>
  </body>
</html>