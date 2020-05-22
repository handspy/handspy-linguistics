package pt.up.hs.linguistics.repository;

import pt.up.hs.linguistics.domain.WordFrequency;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the WordFrequency entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WordFrequencyRepository extends MongoRepository<WordFrequency, String> {
}
