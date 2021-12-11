package formmaker.junctionx;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

@RestController
public class GreetingController {

    @RequestMapping("/")
    public String getGreet() {
        return "Hello Linh To <3";
    }

    @RequestMapping("/testdb")
    public String testDB() {
        return System.getenv("JDBC_DATABASE_URL");
    }

    @RequestMapping("/testdb2")
    public String testDB2() {
        StringBuilder res = new StringBuilder();
        Connection connection = null;
        try {
            connection = DatabaseConfig.getConnection();

            Statement stmt = connection.createStatement();
            stmt.executeUpdate("DROP TABLE IF EXISTS ticks");
            stmt.executeUpdate("CREATE TABLE ticks (tick timestamp)");
            stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
            stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
            stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
            ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");
            while (rs.next()) {
               res.append("Read from DB: ").append(rs.getTimestamp("tick"));
            }

        } catch (Exception e) {
            return Arrays.toString(e.getStackTrace());
        }

        return res.toString();
    }
}
