package formmaker.junctionx;

import response.FormSingleField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DbField {

    public static List<ModelField> GetListField(long formId, int limit, int offset) {
        List<ModelField> res = new ArrayList<>();
        Connection connection = null;

        try {
            connection = DatabaseConfig.getConnection();

            Statement stmt = connection.createStatement();
            String query = String.format("SELECT id, user_id, form_id, name, type, settings, required FROM field WHERE form_id = %d and is_deleted = 0 LIMIT %d OFFSET %d", formId, limit, offset);
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                ModelField field = new ModelField();
                field.setId(rs.getLong("id"));
                field.setUser_id(rs.getLong("user_id"));
                field.setForm_id(rs.getLong("form_id"));
                field.setName(rs.getString("name"));
                field.setType(rs.getString("type"));
                field.setSettings(rs.getString("settings"));
                field.setRequired(rs.getBoolean("required"));

                res.add(field);
            }
            connection.close();
        } catch (Exception e) {
            return null;
        }

        return res;
    }

    public static long CreateField(long formId, FormSingleField field) {
        Connection connection = null;

        try {
            connection = DatabaseConfig.getConnection();

            String query = String.format("INSERT INTO field (" +
                    "user_id, form_id, " +
                    "name, type," +
                    " settings, required) values" +
                    " (1, %d, " +
                    "%s, %s, " +
                    "%s, %b)",
                    formId, field.getName(),
                    field.getType(), field.getSettings(),
                    field.isRequired());
            PreparedStatement statement = connection.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);
            int count = statement.executeUpdate(query);
            if (count <= 0) {
                System.out.println("No row inserted");
                return 0;
            }

            connection.close();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }
                else {
                    System.out.println("Creating user failed, no ID obtained.");
                    return 0;
                }
            }

        } catch (Exception e) {
            return 0;
        }
    }
}
