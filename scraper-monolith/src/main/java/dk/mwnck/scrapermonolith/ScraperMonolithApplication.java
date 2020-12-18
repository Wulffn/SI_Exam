package dk.mwnck.scrapermonolith;

import dk.mwnck.schedulers.ScraperScheduler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@EnableScheduling
@ComponentScan(basePackages = "dk.mwnck")
@SpringBootApplication
@EnableMongoRepositories(basePackages = "dk.mwnck.repositories")
public class ScraperMonolithApplication
{

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(ScraperMonolithApplication.class, args);

    }

}
