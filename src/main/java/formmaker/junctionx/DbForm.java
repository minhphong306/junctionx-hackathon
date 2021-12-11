package formmaker.junctionx;

import org.springframework.ui.Model;

import java.sql.Connection;
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
            String query = String.format("SELECT id, user_id, name, description FROM form LIMIT %d OFFSET %d", limit, offset);
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                ModelForm form = new ModelForm();
                form.setId(rs.getLong("id"));
                form.setName(rs.getString("name"));
                form.setDescription(rs.getString("description"));
                form.setUserId(rs.getLong("user_id"));

                res.add(form);
            }


        } catch (Exception e) {
            return null;
        }

        return res;
    }
}
