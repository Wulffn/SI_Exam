package dk.mwnck.repositories;

import dk.mwnck.dto.SearchCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchCriteriaRepository extends MongoRepository<SearchCriteria, String>
{

}
