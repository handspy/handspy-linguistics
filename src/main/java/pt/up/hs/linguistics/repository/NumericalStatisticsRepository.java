package pt.up.hs.linguistics.repository;

import pt.up.hs.linguistics.domain.NumericalStatistics;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the NumericalStatistics entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NumericalStatisticsRepository extends MongoRepository<NumericalStatistics, String> {
}
