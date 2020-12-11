package dk.mwnck.repositories;


import dk.mwnck.dto.Car;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends MongoRepository<Car, String>
{
    @Query(value = "{ 'km' : {$gte : ?3, $lte: ?4 }}")
    List<Car> findCarsByManufacturerAndModelAndYearAndKmBetween(String manufacturer, String model, int year, int from, int to);
}
