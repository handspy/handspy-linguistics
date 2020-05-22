package pt.up.hs.linguistics.repository;

import pt.up.hs.linguistics.domain.Analysis;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Analysis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnalysisRepository extends MongoRepository<Analysis, String> {
}
