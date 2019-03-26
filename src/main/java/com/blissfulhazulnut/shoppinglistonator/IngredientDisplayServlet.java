package com.blissfulhazulnut.shoppinglistonator;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.blissfulhazulnut.shoppinglistonator.database.IngredientDAO;
import com.blissfulhazulnut.shoppinglistonator.models.IngredientNotFoundException;
import com.blissfulhazulnut.shoppinglistonator.models.Ingredient;


public class IngredientDisplayServlet  extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            System.out.print("333333333333333hi");

            PrintWriter pw = response.getWriter();

            pw.write("Here are the ingredients!\\n\\n");
            pw.print("ingred_name + ','");

        }
    }


