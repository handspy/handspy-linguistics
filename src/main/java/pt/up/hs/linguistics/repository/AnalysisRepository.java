package pt.up.hs.linguistics.repository;

import pt.up.hs.linguistics.domain.Analysis;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data MongoDB repository for the Analysis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnalysisRepository extends MongoRepository<Analysis, String> {

    List<FullAnalysis> findFullAnalysesByProjectIdAndTextId(@NotNull Long projectId, @NotNull Long textId);

    Optional<FullAnalysis> findFirstFullAnalysisByProjectIdAndTextId(@NotNull Long projectId, @NotNull Long textId);

    Optional<FullAnalysis> findFullAnalysisByProjectIdAndTextIdAndId(
        @NotNull Long projectId,
        @NotNull Long textId,
        @NotNull String id
    );

    List<Analysis> findAnalysesByProjectIdAndTextId(@NotNull Long projectId, @NotNull Long textId);

    Optional<Analysis> findFirstAnalysisByProjectIdAndTextId(@NotNull Long projectId, @NotNull Long textId);

    Optional<Analysis> findAnalysisByProjectIdAndTextIdAndId(
        @NotNull Long projectId,
        @NotNull Long textId,
        @NotNull String id
    );

    void deleteByProjectIdAndTextId(@NotNull Long projectId, @NotNull Long textId);

    void deleteByProjectIdAndTextIdAndId(
        @NotNull Long projectId,
        @NotNull Long textId,
        @NotNull String id
    );
}
