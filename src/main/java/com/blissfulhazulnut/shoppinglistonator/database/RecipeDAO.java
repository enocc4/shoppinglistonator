package com.blissfulhazulnut.shoppinglistonator.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.blissfulhazulnut.shoppinglistonator.models.Recipe;
import com.blissfulhazulnut.shoppinglistonator.models.RecipeNotFoundException;


public class RecipeDAO {
    DataSource ds;

    public RecipeDAO() {
        this.ds = DataSource.getInstance();
    }

    public void save(Recipe recipe) throws SQLException {
        System.out.println("Debug. About to save " + recipe);
        if (recipe.getId() != null) {
            update(recipe);
        } else {
            insert(recipe);
        }
    }

    private void insert(Recipe recipe) throws SQLException {
        String sql = "insert into recipe (name) values (?) (?) (?)";
        PreparedStatement pstmt = null;
        Connection conn = null;

        try {
            conn = ds.getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, recipe.getName());
            pstmt.setInt(2, Integer.valueOf(recipe.getId()));
            pstmt.setString(3, String.valueOf(recipe.getDescription()));
            pstmt.setString(4, String.valueOf(recipe.getInstructions()));

            pstmt.executeUpdate();

            ResultSet primaryKeys = pstmt.getGeneratedKeys();
            if (primaryKeys.next()) {
                recipe.setId(primaryKeys.getInt(1));
            }
        } finally {
            DataSource.silentClose(pstmt);
            DataSource.silentClose(conn);
        }
    }

    private void update(Recipe recipe) throws SQLException {
        String sql = "update lolcat set name=?, where id=?";
        PreparedStatement pstmt = null;
        Connection conn = null;

        try {
            conn = ds.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, recipe.getName());
            pstmt.setInt(5, recipe.getId());
            pstmt.executeUpdate();
        } finally {
            DataSource.silentClose(pstmt);
            DataSource.silentClose(conn);
        }
    }

    public void delete(Recipe recipe) throws SQLException {
        String sql = "delete from lolcat where id=?";
        PreparedStatement pstmt = null;
        Connection conn = null;

        try {
            conn = ds.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, recipe.getId());
            pstmt.executeUpdate();
        } finally {
            DataSource.silentClose(pstmt);
            DataSource.silentClose(conn);
        }
    }

    public Recipe get(int id) throws SQLException, RecipeNotFoundException {
        String sql = "select * from recipe where id=?";

        PreparedStatement pstmt = null;
        Connection conn = null;
        ResultSet res = null;

        try {
            conn = ds.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            res = pstmt.executeQuery();

            if (res.next()) {
                Recipe recipe = loadRecipe(res);
                return recipe;
            } else {
                throw new RecipeNotFoundException("Error: Recipe not found with id " + id + "!");
            }
        } finally {
            DataSource.silentClose(pstmt);
            DataSource.silentClose(conn);
        }

    }

    public List<Recipe> list() throws SQLException {

        List<Recipe> recipes = new ArrayList<>();
        String sql = "select * from recipe";

        PreparedStatement pstmt = null;
        Connection conn = null;
        ResultSet res;

        try {

            conn = ds.getConnection();

            pstmt = conn.prepareStatement(sql);

            res = pstmt.executeQuery();




            while (res.next()) {
                Recipe recipe = loadRecipe(res);
                recipes.add(recipe);

            }

            return recipes;

        } finally {

            DataSource.silentClose(pstmt);
            DataSource.silentClose(conn);
        }

    }

    public List<Recipe> search(String text) throws SQLException {

        List<Recipe> recipes = new ArrayList<Recipe>();
        String sql = "select * from recipe where name like ?";

        PreparedStatement pstmt = null;
        Connection conn = null;
        ResultSet res = null;

        try {

            conn = ds.getConnection();
            pstmt = conn.prepareStatement(sql);

            // % is the wildcard character, so "title like %ppl%" would match on applesauce
            pstmt.setString(1, "%" + text + "%");
            pstmt.setString(2, "%" + text + "%");
            res = pstmt.executeQuery();

            while (res.next()) {
                Recipe recipe = loadRecipe(res);
                recipes.add(recipe);
            }
            return recipes;
        } finally {

            DataSource.silentClose(pstmt);
            DataSource.silentClose(conn);
        }


    }

    private Recipe loadRecipe(ResultSet res) throws SQLException {
        Recipe recipe = new Recipe();
        recipe.setId(res.getInt("id"));
        recipe.setName(res.getString("name"));
        recipe.setDescription(res.getString("description"));
        recipe.setInstructions(res.getString("instructions"));

        //recipe.setCreated(res.getDate("created"));
        return recipe;
    }

}
