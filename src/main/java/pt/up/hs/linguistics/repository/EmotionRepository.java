package pt.up.hs.linguistics.repository;

import pt.up.hs.linguistics.domain.Emotion;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Emotion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmotionRepository extends MongoRepository<Emotion, String> {
}