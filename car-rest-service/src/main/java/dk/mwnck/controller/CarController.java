package dk.mwnck.controller;

import dk.mwnck.dto.Car;
import dk.mwnck.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController
{
    CarRepository carRepository;
    public CarController(CarRepository carRepository)
    {
        this.carRepository = carRepository;
    }

    @GetMapping("/{manufacturer}/{model}/{year}/{km}")
    public ResponseEntity<List<Car>> getCars(@PathVariable String manufacturer, @PathVariable String model,
                                             @PathVariable int year, @PathVariable int km)
    {
        List<Car> cars = carRepository.findCarsByManufacturerAndModelAndYearAndKmBetween(manufacturer, model, year, km-20000, km+20000);
        //List<Car> cars = carRepository.findAll();
        return new ResponseEntity<>(cars, HttpStatus.ACCEPTED);
    }
}
