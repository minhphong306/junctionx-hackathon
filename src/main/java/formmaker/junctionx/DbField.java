package formmaker.junctionx;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DbField {

    public static List<ModelField> GetListField(int limit, int offset) {
        List<ModelField> res = new ArrayList<>();
        Connection connection = null;

        try {
            connection = DatabaseConfig.getConnection();

            Statement stmt = connection.createStatement();
            String query = String.format("SELECT id, user_id, name, description FROM form LIMIT %d OFFSET %d", limit, offset);
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                ModelField field = new ModelField();
                field.setId(rs.getLong("id"));
                field.setUser_id(rs.getLong("user_id"));
                field.setName(rs.getString("name"));
                field.setRequired(rs.getBoolean("required"));
                field.setSettings(rs.getString("settings"));

                res.add(field);
            }


        } catch (Exception e) {
            return null;
        }

        return res;
    }
}
