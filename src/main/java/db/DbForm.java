package db;

import model.ModelForm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DbForm {

    public static List<ModelForm> GetListForm(int limit, int offset) {
        List<ModelForm> res = new ArrayList<>();
        Connection connection = null;

        try {
            connection = DatabaseConfig.getConnection();

            Statement stmt = connection.createStatement();
            String query = String.format("SELECT id, user_id, name, description, status FROM form WHERE is_deleted = 0 LIMIT %d OFFSET %d", limit, offset);
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                ModelForm form = new ModelForm();
                form.setId(rs.getLong("id"));
                form.setName(rs.getString("name"));
                form.setDescription(rs.getString("description"));
                form.setUser_id(rs.getLong("user_id"));
                form.setStatus(rs.getInt("status"));

                res.add(form);
            }
            connection.close();


        } catch (Exception e) {
            System.out.println("Error when get forms: " + Arrays.toString(e.getStackTrace()));
            return null;
        }

        return res;
    }

    public static ModelForm GetForm(long id) {
        ModelForm res = new ModelForm();
        Connection connection = null;

        try {
            connection = DatabaseConfig.getConnection();

            Statement stmt = connection.createStatement();
            String query = String.format("SELECT id, user_id, name, description, status FROM form WHERE is_deleted = 0 and id = %d", id);
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                res.setId(rs.getLong("id"));
                res.setName(rs.getString("name"));
                res.setDescription(rs.getString("description"));
                res.setUser_id(rs.getLong("user_id"));
                res.setStatus(rs.getInt("status"));
            }

            connection.close();
        } catch (Exception e) {
            System.out.println("Error when get form: " + Arrays.toString(e.getStackTrace()));
            return null;
        }

        return res;
    }

    public static boolean DeleteForm(long id) {
        Connection connection = null;

        try {
            connection = DatabaseConfig.getConnection();

            Statement stmt = connection.createStatement();
            String query = String.format("UPDATE form SET is_deleted = 1 where id = %d", id);
            int deleteCount = stmt.executeUpdate(query);
            if (deleteCount > 0) {
                return true;
            }

            connection.close();
        } catch (Exception e) {
            System.out.println("Error when delete form: ");
            e.printStackTrace();
            return false;
        }

        return false;
    }

    public static long CreateForm(String name, String description) {
        Connection connection = null;

        try {
            connection = DatabaseConfig.getConnection();

            PreparedStatement statement = connection.prepareStatement("INSERT INTO form (user_id, name, description) values (1, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            statement.setString(2, description);

            int count = statement.executeUpdate();
            if (count <= 0) {
                System.out.println("No row inserted");
                return 0;
            }

            connection.close();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    System.out.println("Creating user failed, no ID obtained.");
                    return 0;
                }
            }

        } catch (Exception e) {
            System.out.println("Error when create form: ");
            e.printStackTrace();
            return 0;
        }
    }

    public static long UpdateForm(long id, String name, String description) {
        Connection connection = null;

        try {
            connection = DatabaseConfig.getConnection();

            PreparedStatement statement = connection.prepareStatement("UPDATE form SET name = ?, description = ? WHERE id = ?",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setLong(3, id);

            int count = statement.executeUpdate();
            if (count <= 0) {
                System.out.println("No row inserted");
                return 0;
            }

            connection.close();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    System.out.println("Creating user failed, no ID obtained.");
                    return 0;
                }
            }

        } catch (Exception e) {
            System.out.println("Error when update form: ");
            e.printStackTrace();
            return 0;
        }
    }

    public static long UpdateFormStatus(long id, int status) {
        Connection connection = null;

        try {
            connection = DatabaseConfig.getConnection();

            PreparedStatement statement = connection.prepareStatement("UPDATE form SET status = ? WHERE id = ?",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, status);
            statement.setLong(2, id);

            System.out.println("Query: " + "UPDATE form SET status = ? WHERE id = ?" + id + "," + status);

            int count = statement.executeUpdate();
            if (count <= 0) {
                System.out.println("No row inserted");
                return 0;
            }

            connection.close();
        } catch (Exception e) {
            System.out.println("Error when update form status: ");
            e.printStackTrace();
            return 0;
        }

        return 0;
    }


}
