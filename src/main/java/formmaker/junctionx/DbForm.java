package formmaker.junctionx;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DbForm {

    public static List<ModelForm> GetListForm(int limit, int offset) {
        List<ModelForm> res = new ArrayList<>();
        Connection connection = null;

        try {
            connection = DatabaseConfig.getConnection();

            Statement stmt = connection.createStatement();
            String query = String.format("SELECT id, user_id, name, description FROM form WHERE is_deleted = 0 LIMIT %d OFFSET %d", limit, offset);
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                ModelForm form = new ModelForm();
                form.setId(rs.getLong("id"));
                form.setName(rs.getString("name"));
                form.setDescription(rs.getString("description"));
                form.setUser_id(rs.getLong("user_id"));

                res.add(form);
            }


        } catch (Exception e) {
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
            String query = String.format("SELECT id, user_id, name, description FROM form WHERE is_deleted = 0 and id = %d", id);
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                res.setId(rs.getLong("id"));
                res.setName(rs.getString("name"));
                res.setDescription(rs.getString("description"));
                res.setUser_id(rs.getLong("user_id"));
            }


        } catch (Exception e) {
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

        } catch (Exception e) {
            return false;
        }

        return false;
    }
}
