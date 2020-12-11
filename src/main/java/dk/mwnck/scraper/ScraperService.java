package dk.mwnck.scraper;

import dk.mwnck.constants.Country;
import dk.mwnck.constants.Currency;
import dk.mwnck.dto.Car;
import dk.mwnck.dto.SearchCriteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ScraperService {

    static Scraper[] scrapers = {new AutoScout24Scraper(), new MobileDeScraper(), new BilbasenScraper()};

    static final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static List<Car> getCars(List<SearchCriteria> criteria) throws InterruptedException
    {
        List<Callable<Boolean>> tasks = new ArrayList();
        List<Car> cars = new ArrayList();
        for(SearchCriteria criterion:criteria)
        {
            tasks.add(() -> cars.addAll(ScraperService.getCarsByCriterion(criterion)));
        }

        List<Future<Boolean>> results = executorService.invokeAll(tasks);
        return cars;
    }

    public static List<Car> getCarsByCriterion(SearchCriteria criterion) throws InterruptedException
    {
        List<Callable<Boolean>> tasks = new ArrayList<>();
        List<Car> cars = new ArrayList();

        for (int i = 0; i < scrapers.length; i++) {
            int finalI = i;
            Car searchCar = new Car(criterion.getManufacturer(), criterion.getModel(), "0", criterion.getKm(), criterion.getYear(), Country.NONE, Currency.NONE );
            tasks.add(() -> cars.addAll(scrapers[finalI].getCars(searchCar)));
        }

        ExecutorService executor = Executors.newFixedThreadPool(3);

        List<Future<Boolean>> results = executor.invokeAll(tasks);
        return cars;
    }
}
