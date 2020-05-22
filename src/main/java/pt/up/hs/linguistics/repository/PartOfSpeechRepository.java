package pt.up.hs.linguistics.repository;

import pt.up.hs.linguistics.domain.PartOfSpeech;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the PartOfSpeech entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartOfSpeechRepository extends MongoRepository<PartOfSpeech, String> {
}
