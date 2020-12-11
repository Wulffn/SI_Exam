package dk.mwnck.repositories;

import dk.mwnck.dto.Car;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends MongoRepository<Car, String>
{
}
