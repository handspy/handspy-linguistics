package pt.up.hs.linguistics.repository;

import pt.up.hs.linguistics.domain.Emotion;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Spring Data MongoDB repository for the Emotion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmotionRepository extends MongoRepository<Emotion, String> {

    Set<Emotion> findByAnalysisId(@NotNull String analysisId);

    Optional<Emotion> findByAnalysisIdAndId(
        @NotNull String analysisId,
        @NotNull String id
    );

    void deleteByAnalysisId(@NotNull String analysisId);

    void deleteByAnalysisIdAndId(@NotNull String analysisId, @NotNull String id);
}
