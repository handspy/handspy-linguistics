package pt.up.hs.linguistics.repository;

import pt.up.hs.linguistics.domain.Emotion;
import pt.up.hs.linguistics.domain.PartOfSpeech;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data MongoDB repository for the PartOfSpeech entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartOfSpeechRepository extends MongoRepository<PartOfSpeech, String> {

    List<PartOfSpeech> findByAnalysisId(@NotNull String analysisId);

    Optional<PartOfSpeech> findByAnalysisIdAndId(
        @NotNull String analysisId,
        @NotNull String id
    );

    void deleteByAnalysisId(@NotNull String analysisId);

    void deleteByAnalysisIdAndId(@NotNull String analysisId, @NotNull String id);
}
