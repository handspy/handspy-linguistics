package pt.up.hs.linguistics.repository;

import pt.up.hs.linguistics.domain.LemmaFrequency;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the LemmaFrequency entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LemmaFrequencyRepository extends MongoRepository<LemmaFrequency, String> {
}
