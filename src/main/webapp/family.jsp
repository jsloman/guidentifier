<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.util.*,
				 com.guidentifier.*,
                 com.guidentifier.model.*,
                 com.guidentifier.dao.*,
                 com.guidentifier.util.WebUtil,
                 com.googlecode.objectify.*" %>
<%! 
	boolean isAdmin = true;
	String thisURL = "/Group";
	DAO dao = new DAO();
	
	void addGroup(Group parent, String name) {
		Group f = new Group(parent, name);
		dao.addGroup(f);
	}
	
	void addThing(Group parent, String name) {
		Thing s = new Thing(parent, name);
		dao.addThing(s);
	}
%>
<%
	Group Group = null;
	Region region = null;
	Category Category = null;

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
	Group = dao.getGroup(idStr);
	if (Group == null) {
		response.sendRedirect("/GroupError/" + idStr);
	}
	Category = Group.getCategory();

	if (isAdmin && request.getParameter("Group.add") != null) {
		addGroup(Group, request.getParameter("Group.name"));
	}
	if (isAdmin && request.getParameter("Thing.add") != null) {
		addThing(Group, request.getParameter("Thing.name"));
	}
	if (isAdmin && request.getParameter("Group.edit") != null) {
		Group.setName(request.getParameter("edit.name"));
		dao.addGroup(Group);
	}

%>
<html>
  <head>
    <meta http-equiv="content-Category" content="text/html; charset=UTF-8">
    <title>Guidentifier: Group - <%= Group.getName() %></title>
    <link rel="stylesheet" type="text/css" href="/style/main.css" />
  </head>
   <body>
<div id="title">
	Guidentifier: Group - <%= Group.getName() %>
</div>
<div id="breadcrumbs">
	<a href="/<%= region != null ? region.getName() : ""%>">Home</a> > <a href="/Category/<%= region == null ? "0" : region.getName() %>/<%= Category.getName() %>"><%= Category.getName() %></a>
<% 
	List<Group> parents = dao.getParents(Group);
	for (Group fiter : parents) {
%>
	> <a href="/Group/<%= region == null ? "0" : region.getName() %>/<%= fiter.getName() %>"><%= fiter.getName() %></a>
<%
	}
%>
	> <%= Group.getName() %>
</div>
<div id="region">
	<p>Region:</p>
	<%= WebUtil.showRegions(dao, region, "Group/", "/" + Group.getName()) %>
</div>
<div id="topcontent">
	<div id="guide">
		<h2>Guide - families:</h2>
		<%= WebUtil.showGroups(dao, Category, region, Group.getName()) %>
	</div>
	<div id="Thing">
		<h2>Thing in Group:</h2>
		<ul>
<%
	Iterable<Thing> Thing = dao.getThing(Group);
	for (Thing s : Thing) {
%>
			<li> <a href="/Thing/<%= region == null ? "0" : region.getName() %>/<%= s.getName()%>" ><%= s.getName()%></a></li>
<%
	}
%>
		</ul>
	</div>
</div>
<div id="info">
	<h2>Info on <%= Group.getName() %></h2>
	<br clear="all"/>
</div>

<%
	if (isAdmin) {
%>
<div id="admin">
	Admin
	<form action="<%= thisURL %>/<%= region == null ? "0" : region.getName() %>/<%= Group.getName() %>" method="post">
	<p>Add sub-Group:</p>
	<input type="text" name="Group.name"/>
	<input type="submit" name="Group.add" value="Add"/>
	<br/>
	<p>Add Thing:</p>
	<input type="text" name="Thing.name"/>
	<input type="submit" name="Thing.add" value="Add"/>
	<p>Edit info:</p>
	Group name: <input type="text" name="edit.name" size="50" value="<%= WebUtil.encodeForWeb(Group.getName()) %>"/><br/>
	<input type="submit" name="Group.edit" value="Save"/>
	<input type="reset" value="Reset"/>
	</form>
</div>
<%
	}
%>
  </body>
</html>