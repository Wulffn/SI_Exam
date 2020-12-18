package dk.mwnck.schedulers;


import dk.mwnck.dto.Car;
import dk.mwnck.dto.SearchCriteria;
import dk.mwnck.repositories.CarRepository;
import dk.mwnck.repositories.SearchCriteriaRepository;
import dk.mwnck.scraper.ScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScraperScheduler
{
    @Autowired
    SearchCriteriaRepository searchCriteriaRepository;

    @Autowired
    CarRepository carRepository;

    // run at some point in time every day.
    // syntax is: sec min hour dayOfMonth month year
//    @Scheduled(cron = "0 5 0 * * ?", zone = "Europe/Copenhagen", initialDelay = 3000L)
//    public void scheduleTaskUsingCronExpression()
//    {
//
//        long now = System.currentTimeMillis() / 1000;
//        System.out.println(
//                "schedule tasks using cron jobs - " + now);
//
//        try
//        {
//            scrapeCars();
//        }
//        catch(Exception e)
//        {
//            System.getLogger("cron scraper failure: ").log(System.Logger.Level.ERROR,"BURN: " + e.getMessage());
//        }
//    }

    @Scheduled(fixedDelay = 60000L * 60L * 24L, initialDelay = 10000L)
    public void doSomething() throws InterruptedException
    {
        scrapeCars();
    }


    public void scrapeCars() throws InterruptedException
    {
        System.out.println("Deleting cars...");
        carRepository.deleteAll();
        System.out.println("Cars deleted!");
        System.out.println("Getting cars...");
        List<SearchCriteria> criteria = searchCriteriaRepository.findAll();
        for(SearchCriteria cr : criteria)
            System.out.println(cr.toString());

        List<Car> cars = ScraperService.getCars(criteria);
        for(Car car : cars)
            carRepository.save(car);

        System.out.println("Cars saved.");
    }
}
