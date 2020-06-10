package pt.up.hs.linguistics.repository;

import pt.up.hs.linguistics.domain.Analysis;

import org.springframework.data.mongodb.repository.Query;
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

    List<Analysis> findByProjectIdAndTextId(@NotNull Long projectId, @NotNull Long textId);

    Optional<Analysis> findFirstByProjectIdAndTextId(@NotNull Long projectId, @NotNull Long textId);

    Optional<Analysis> findByProjectIdAndTextIdAndId(
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
