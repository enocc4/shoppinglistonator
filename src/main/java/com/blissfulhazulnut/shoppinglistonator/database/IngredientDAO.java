package com.blissfulhazulnut.shoppinglistonator.database;

        import java.sql.Connection;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.sql.Statement;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.*;

        import com.blissfulhazulnut.shoppinglistonator.models.Ingredient;
        import com.blissfulhazulnut.shoppinglistonator.models.IngredientNotFoundException;

/**
 * Data access object for a LOLCat entity; this class understands how to load
 * and save LOLCat entities in a SQL database.
 *
 * Stack Overflow has a decent explanation of the Data Access Object design
 * pattern:
 * http://stackoverflow.com/questions/19154202/data-access-object-dao-in-java
 *
 * We're not using an interface because we don't need to implement more than one
 * of these for different databases.
 *
 * @author jennybrown
 *
 */
public class IngredientDAO {

    DataSource ds;

    public IngredientDAO() {
        this.ds = DataSource.getInstance();
    }

    public void save(Ingredient ingredient) throws SQLException {
        System.out.println("Debug. About to save " + ingredient);
        if (ingredient.getId() != null) {
            update(ingredient);
        } else {
            insert(ingredient);
        }
    }

    private void insert(Ingredient ingredient) throws SQLException {
        String sql = "insert into ingredients (title, filename, image_format, image_data) values (?,?,?,?)";
        PreparedStatement pstmt = null;
        Connection conn = null;

        try {
            conn = ds.getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, ingredient.getName());
            pstmt.setString(2, String.valueOf(ingredient.getId()));

            pstmt.executeUpdate();

            ResultSet primaryKeys = pstmt.getGeneratedKeys();
            if (primaryKeys.next()) {
                ingredient.setId(primaryKeys.getInt(1));
            }
        } finally {
            DataSource.silentClose(pstmt);
            DataSource.silentClose(conn);
        }
    }

    private void update(Ingredient ingredient) throws SQLException {
        String sql = "update lolcat set title=?, filename=?, image_format=?, image_data=? where id=?";
        PreparedStatement pstmt = null;
        Connection conn = null;

        try {
            conn = ds.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, ingredient.getName());
            pstmt.setInt(5, ingredient.getId());
            pstmt.executeUpdate();
        } finally {
            DataSource.silentClose(pstmt);
            DataSource.silentClose(conn);
        }
    }

    public void delete(Ingredient ingredient) throws SQLException {
        String sql = "delete from lolcat where id=?";
        PreparedStatement pstmt = null;
        Connection conn = null;

        try {
            conn = ds.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, ingredient.getId());
            pstmt.executeUpdate();
        } finally {
            DataSource.silentClose(pstmt);
            DataSource.silentClose(conn);
        }
    }

    public Ingredient get(int id) throws SQLException, IngredientNotFoundException {
        String sql = "select * from ingredients where id=?";

        PreparedStatement pstmt = null;
        Connection conn = null;
        ResultSet res = null;

        try {
            conn = ds.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            res = pstmt.executeQuery();

            if (res.next()) {
                Ingredient ingredient = loadIngredient(res);
                return ingredient;
            } else {
                throw new IngredientNotFoundException("Error: Ingredient not found with id " + id + "!");
            }
        } finally {
            DataSource.silentClose(pstmt);
            DataSource.silentClose(conn);
        }

    }

    public List<Ingredient> list() throws SQLException {
        List<Ingredient> ingredients = new ArrayList<Ingredient>();
        String sql = "select * from ingredients";

        PreparedStatement pstmt = null;
        Connection conn = null;
        ResultSet res = null;

        try {
            conn = ds.getConnection();
            pstmt = conn.prepareStatement(sql);
            res = pstmt.executeQuery();

            while (res.next()) {
                Ingredient ingredient = loadIngredient(res);
                ingredients.add(ingredient);
            }
            return ingredients;
        } finally {
            DataSource.silentClose(pstmt);
            DataSource.silentClose(conn);
        }

    }

    public List<Ingredient> search(String text) throws SQLException {
        List<Ingredient> ingredients = new ArrayList<Ingredient>();
        String sql = "select * from ingredients where title like ? or filename like ?";

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
                Ingredient ingredient = loadIngredient(res);
                ingredients.add(ingredient);
            }
            return ingredients;
        } finally {
            DataSource.silentClose(pstmt);
            DataSource.silentClose(conn);
        }


    }

    private Ingredient loadIngredient(ResultSet res) throws SQLException {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(res.getInt("id"));
        ingredient.setName(res.getString("title"));

        ingredient.setCreated(res.getDate("created"));
        return ingredient;
    }

}
