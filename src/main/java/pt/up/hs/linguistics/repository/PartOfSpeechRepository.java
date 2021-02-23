package pt.up.hs.linguistics.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pt.up.hs.linguistics.domain.PartOfSpeech;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.Set;

/**
 * Spring Data MongoDB repository for the PartOfSpeech entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartOfSpeechRepository extends MongoRepository<PartOfSpeech, String> {

    Set<PartOfSpeech> findByAnalysisId(@NotNull String analysisId);

    Optional<PartOfSpeech> findByAnalysisIdAndId(
        @NotNull String analysisId,
        @NotNull String id
    );

    void deleteByAnalysisId(@NotNull String analysisId);

    void deleteByAnalysisIdAndId(@NotNull String analysisId, @NotNull String id);
}
