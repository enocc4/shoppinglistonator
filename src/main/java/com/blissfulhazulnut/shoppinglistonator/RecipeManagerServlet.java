package com.blissfulhazulnut.shoppinglistonator;

import java.io.IOException;

import java.sql.Array;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.blissfulhazulnut.shoppinglistonator.database.RecipeDAO;
import com.blissfulhazulnut.shoppinglistonator.models.RecipeNotFoundException;
import com.blissfulhazulnut.shoppinglistonator.models.Recipe;

public class RecipeManagerServlet extends HttpServlet implements Servlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pathInfo = req.getPathInfo();
        System.out.println(pathInfo);
        if (pathInfo == null || "".equals(pathInfo)) {
            list(req, resp);
        } else if (pathInfo.equals("/add")) {
            addForm(req, resp);
        } else if (pathInfo.equals("/edit")) {
            editForm(req, resp);
        } else if (pathInfo.equals("/delete")) {
            deleteConfirm(req, resp);
        } else if (pathInfo.equals("/afterSave")) {
            afterSave(req, resp);
        } else if (pathInfo.equals("/thisWeeksRecipes")){
            list(req, resp);
        } else if (pathInfo.equals("/ShoppingList")){
            shopping(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || "".equals(pathInfo)) {
            list(req, resp);
        } else if (pathInfo.equals("/save")) {
            save(req, resp);
        }else {
            shopping(req, resp);
        }
    }

    void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RecipeDAO dao = new RecipeDAO();
        List<Recipe> recipes = null;

        try {
            recipes = dao.list();
            req.setAttribute("recipes", recipes);
        } catch (SQLException e) {

            e.printStackTrace();

        }



        req.getRequestDispatcher("/Recipes/thisWeeksRecipes.jsp").forward(req, resp);
    }

    void addForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/Recipes/addRecipe.jsp").forward(req, resp);
    }

    void editForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/Recipes/edit.jsp").forward(req, resp);
    }

    void deleteConfirm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/Recipes/delete-confirm.jsp").forward(req, resp);
    }

    /** From a post, saves the newly added or modified LOLcat */
    void save(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //dumpRequest(req);  // debug

        RecipeDAO dao = new RecipeDAO();
        String result = null;

        if (req.getParameter("id") != null) {
            // edit-existing scenario since we have an id already
            try {
                Recipe recipe = dao.get(Integer.parseInt(req.getParameter("id")));




                // Retrieves <input type="file" name="file">
                Part filePart = req.getPart("image");
                String fileName = filePart.getName(); // TODO
                System.out.println("DEBUG - fileName is " + fileName);


                dao.save(recipe);
                result = "Changes saved!";
            } catch (RecipeNotFoundException e) {
                result = "Error - Recipe not found with that id! Edit aborted.";
            } catch (SQLException e) {
                result = "Error - A database error occurred.";
                e.printStackTrace();
            }
        } else {
            // add-new scenario since there's no id
            Recipe recipe = new Recipe();
            recipe.setName(req.getParameter("title"));


            // Retrieves <input type="file" name="file">
            Part filePart = req.getPart("image");
            String fileName = filePart.getName(); // TODO
            System.out.println("DEBUG - fileName is " + fileName);

            try {
                dao.save(recipe);
                result = "Success!";
            } catch (SQLException e) {
                result = "Error - A database error occurred.";
                e.printStackTrace();
            }
        }

        // Redirect to a GET so if the user presses reload we don't get a
        // duplicate image POSTed.
        resp.sendRedirect(req.getContextPath() + "/recipes/manage/aftersave?result=" /*+ URLEncoder.encode(result, StandardCharsets.UTF_8)*/);

    }



    void afterSave(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("result", req.getParameter("result"));
        req.getRequestDispatcher("/Recipes/after-save.jsp").forward(req, resp);
    }

    /** Spits out a ton of info about the request so you can troubleshoot */
    public static void dumpRequest(HttpServletRequest req)
    {
        for (String name : req.getParameterMap().keySet()) {
            System.out.println("parameter " + name + " values " + req.getParameterValues(name));
        }
        for (Cookie c : req.getCookies()) {
            System.out.println("cookie " + c.getDomain() + " " + c.getPath() + " " + c.getName() + " " + c.getValue());
        }
        Enumeration<String> headers = req.getHeaderNames();
        while(headers.hasMoreElements()) {
            String header = headers.nextElement();
            System.out.println("header " + header + " value " + req.getHeader(header));
        }

    }

    void recipe(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RecipeDAO dao = new RecipeDAO();
        List<Recipe> recipes = null;

        try {
            recipes = dao.list();
            req.setAttribute("recipes", recipes);
        } catch (SQLException e) {

            e.printStackTrace();

        }
        req.getRequestDispatcher("/Recipes/thisWeeksRecipes.jsp").forward(req, resp);
    }

    void shopping(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        String Monday = req.getParameter("Monday");
        String Tuesday = req.getParameter("Tuesday");
        String Wednesday = req.getParameter("Wednesday");
        String Thursday = req.getParameter("Thursday");
        String Friday = req.getParameter("Friday");

        System.out.println(Monday);
        System.out.println(Tuesday);
        System.out.println(Wednesday);
        System.out.println(Thursday);
        System.out.println(Friday);

        req.getRequestDispatcher("/Recipes/ShoppingList.jsp").forward(req, resp);
    }

}
