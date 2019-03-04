<%@ page import="java.util.List,com.blissfulhazlenut.shoppinlistonator.models.Ingredient" %>
<html>
<head>
<title>ingredients List</title></head>
<body>
<h1>ingredients List</h1>

<p align="right"><a href="<%=request.getContextPath() %>/ingredients/manage/add">Add New ingredient</a></p>


<!--  This page uses pure jsp for looping. An cleaner, more readable alternative is JSTL tags. -->

<%
	List<Ingredient> ingredients = (List<Ingredient>) request.getAttribute("ingredients");
	for (Ingredient ingredient : ingredients) {
		%>
		<div style="border: 1px solid #AAAAAA; padding: 15px; width: 300px;">
		<img src="<%=request.getContextPath() %>/ingredients/show/image?id=<%=ingredient.getId() %>" height=200>
		<br>Title: <%=ingredient.getTitle() %>
		<br>Filename: <%=ingredient.getFilename() %>
		<br>Format: <%=ingredient.getImageFormat() %>
		<br><a href="<%=request.getContextPath() %>/ingredients/manage/edit?id=<%=ingredient.getId() %>">Edit</a>
		| <a href="<%=request.getContextPath() %>/ingredients/manage/delete?id=<%=ingredient.getId() %>">Delete</a>
		</div><p></p>
		<%
	}
	if (ingredients.isEmpty()) {
		%><p>There are no ingredients in the system yet.  How sad! Maybe you want to <a href="<%=request.getContextPath() %>/ingredients/manage/add">Add a New One</a>!</p><%
	}
%>

</body></html>
