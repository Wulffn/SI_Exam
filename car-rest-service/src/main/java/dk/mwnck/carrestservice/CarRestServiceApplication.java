package dk.mwnck.carrestservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "dk.mwnck.repositories")
@ComponentScan(basePackages = "dk.mwnck")
@SpringBootApplication
public class CarRestServiceApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(CarRestServiceApplication.class, args);
    }

}
