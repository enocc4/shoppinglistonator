
<%@ page import="java.util.List,com.blissfulhazulnut.shoppinglistonator.models.Recipe;" %>
<html>
<head>
    <link href="/styles.css" rel="stylesheet" type="text/css">
</head>
<body>
<form action="RecipeManagerServlet" method="POST">
    <label> Monday </label>
    <select name="Monday">
        <%
            List<Recipe> recipes = (List<Recipe>) request.getAttribute("recipes");
            for (Recipe recipe : recipes) {
            %>

                <option> <%=recipe.getName()%></option>

            <%
        }
        if (recipes.isEmpty() || recipes == null) {
            %><p>There are no recipes in the system yet.  How sad! Maybe you want to <a href="<%=request.getContextPath() %>/recipes/manage/add">Add a New One</a>!</p><%
            }
        %>
    </select>

    <label> Tuesday </label>
    <select name="Tuesday" >
        <%

            for (Recipe recipe : recipes) {
            %>

                <option> <%=recipe.getName()%></option>

            <%
        }
        if (recipes.isEmpty() || recipes == null) {
            %><p>There are no recipes in the system yet.  How sad! Maybe you want to <a href="<%=request.getContextPath() %>/recipes/manage/add">Add a New One</a>!</p><%
            }
        %>
    </select>
    <label> Wednesday </label>
    <select name="Wednesday">
        <%

            for (Recipe recipe : recipes) {
            %>

                <option> <%=recipe.getName()%></option>

            <%
        }
        if (recipes.isEmpty() || recipes == null) {
            %><p>There are no recipes in the system yet.  How sad! Maybe you want to <a href="<%=request.getContextPath() %>/recipes/manage/add">Add a New One</a>!</p><%
            }
        %>
    </select>

    <label> Thursday </label>
    <select name="Thursday">
        <%

            for (Recipe recipe : recipes) {
            %>

                <option name=<%recipe.getName();%> > <%=recipe.getName()%></option>

            <%
        }
        if (recipes.isEmpty() || recipes == null) {
            %><p>There are no recipes in the system yet.  How sad! Maybe you want to <a href="<%=request.getContextPath() %>/recipes/manage/add">Add a New One</a>!</p><%
            }
        %>
    </select>

    <label> Friday </label>
    <select name="Friday">
        <%

            for (Recipe recipe : recipes) {
            %>

                <option> <%=recipe.getName()%></option>

            <%
        }
        if (recipes.isEmpty() || recipes == null) {
            %><p>There are no recipes in the system yet.  How sad! Maybe you want to <a href="<%=request.getContextPath() %>/recipes/manage/add">Add a New One</a>!</p><%
            }
        %>
    </select>
    <input type="submit" value="Go to Shopping List"><input type="reset" value="reset">
</form>



<p><a href="<%=request.getContextPath() %>/recipes/manage">Go to Recipes</a></p>
<p><a href="/addRecipe.jsp"> Add recipe</a></p>

</body>
</html>