package formmaker.junctionx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JunctionxApplication {

	public static void main(String[] args) {
		System.out.println("== Start server");
		SpringApplication.run(JunctionxApplication.class, args);
	}

}
