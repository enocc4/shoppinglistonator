package com.blissfulhazulnut.shoppinglistonator;

import java.io.IOException;
import java.sql.SQLException;

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
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String pathInfo = req.getPathInfo();
            if (pathInfo == null || "".equals(pathInfo)) {
                show(req, resp);
            } else if (pathInfo.equals("/image")) {
                streamImage(req, resp);
            }
        }

        /** Simple front page shows one random lolcat each time it's reloaded */
        void show(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        }

        /** Binary image data returned to client based on image id */
        void streamImage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            int id = Integer.parseInt(req.getParameter("id"));
            IngredientDAO dao = new IngredientDAO();
            try {
                Ingredient ingredient = dao.get(id);
                resp.setContentType("image/" + ingredient.getImageFormat());
                resp.setContentLength(ingredient.getImageData().length);

                ServletOutputStream out = resp.getOutputStream();
                out.write(ingredient.getImageData(), 0, ingredient.getImageData().length);
                out.close();

            } catch (SQLException e) {
                e.printStackTrace(); // no data will return.
            } catch (IngredientNotFoundException e) {
                System.out.println("Unknown ingredient id " + id + " requested image data."); // no data will return.
            }
        }

    }


