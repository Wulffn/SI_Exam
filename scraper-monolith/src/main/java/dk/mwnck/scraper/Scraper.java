package dk.mwnck.scraper;

import dk.mwnck.constants.Country;
import dk.mwnck.dto.Car;

import java.util.List;

public interface Scraper {

    Country getCountry();
    List<Car> getCars(Car searchCar);
}
