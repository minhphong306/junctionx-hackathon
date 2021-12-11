package formmaker.junctionx;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @RequestMapping("/")
    public String getGreet() {
        return "Hello Linh To <3";
    }

    @RequestMapping("/testdb")
    public String testDB() {
        return  System.getenv("JDBC_DATABASE_URL");
    }
}
