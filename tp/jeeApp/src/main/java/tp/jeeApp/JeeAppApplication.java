package tp.jeeApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class JeeAppApplication {

	public static void main(String[] args) {
		//SpringApplication.run(JeeAppApplication.class, args);
		 SpringApplication app = new SpringApplication(JeeAppApplication.class);    
		 app.setAdditionalProfiles("postgres","dev");      
		 ConfigurableApplicationContext context = app.run(args); 
		 System.out.println("http://localhost:8080/jeeApp"); 
	}

}
