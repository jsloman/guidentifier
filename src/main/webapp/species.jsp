<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.util.List,
				 com.guidentifier.*,
                 com.guidentifier.model.*,
                 com.guidentifier.dao.*,
                 com.guidentifier.util.WebUtil,
                 com.googlecode.objectify.*" %>
<%! 
	boolean isAdmin = true;
	String thisURL = "/species";
	DAO dao = new DAO();
%>
<%
	Thing species = null;
	Category type = null;
	Group family = null;
	Region region = null;

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
	species = dao.getThing(idStr);
	if (species == null) {
		response.sendRedirect("/speciesError/" + idStr);
	}
	type = species.getCategory();
	family = species.getGroup();

	if (isAdmin && request.getParameter("species.edit") != null) {
		species.setName(request.getParameter("edit.name"));
		dao.addThing(species);
	}

%>
<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>Guidentifier: Thing - <%= species.getName() %></title>
    <link rel="stylesheet" type="text/css" href="/style/main.css" />
  </head>
   <body>
<div id="title">
	Guidentifier: Thing - <%= species.getName() %>
</div>
<div id="breadcrumbs">
	<a href="/<%= region != null ? region.getName() : ""%>">Home</a> > <a href="/type/<%= region == null ? "0" : region.getName() %>/<%= type.getName() %>"><%= type.getName() %></a>
<% 
	List<Group> parents = dao.getParents(family);
	for (Group fiter : parents) {
%>
	> <a href="/family/<%= region == null ? "0" : region.getName() %>/<%= fiter.getName() %>"><%= fiter.getName() %></a>
<%
	}
%>
	> <a href="/family/<%= region == null ? "0" : region.getName() %>/<%= family.getName() %>"><%= family.getName() %></a>
	> <%= species.getName() %>
</div>
<div id="info">
	Info on <%= species.getName() %>
	<br clear="all"/>
</div>

<%
	if (isAdmin) {
%>
<div id="admin">
	Admin
	<form action="<%= thisURL %>/<%= region == null ? "0" : region.getName() %>/<%= species.getName() %>" method="post">
	<p>Edit info:</p>
	Thing name: <input type="text" name="edit.name" size="50" value="<%= WebUtil.encodeForWeb(species.getName()) %>"/><br/>
	<input type="submit" name="species.edit" value="Save"/>
	<input type="reset" value="Reset"/>
	</form>
</div>
<%
	}
%>
  </body>
</html>