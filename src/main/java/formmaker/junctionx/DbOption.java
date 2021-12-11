package formmaker.junctionx;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DbOption {

    public static List<ModelOption> GetListField(int limit, int offset) {
        List<ModelOption> res = new ArrayList<>();
        Connection connection = null;

        try {
            connection = DatabaseConfig.getConnection();

            Statement stmt = connection.createStatement();
            String query = String.format("SELECT id, user_id, name, description FROM form LIMIT %d OFFSET %d", limit, offset);
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                ModelOption option = new ModelOption();
                option.setId(rs.getLong("id"));
                option.setUser_id(rs.getLong("user_id"));
                option.setName(rs.getString("name"));
                option.setSettings(rs.getString("settings"));
                option.setForm_id(rs.getLong("form_id"));

                res.add(option);
            }


        } catch (Exception e) {
            return null;
        }

        return res;
    }
}
