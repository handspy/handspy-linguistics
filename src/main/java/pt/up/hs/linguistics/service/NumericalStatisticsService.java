package pt.up.hs.linguistics.service;

import pt.up.hs.linguistics.service.dto.NumericalStatisticsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link pt.up.hs.linguistics.domain.NumericalStatistics}.
 */
public interface NumericalStatisticsService {

    /**
     * Save a numericalStatistics.
     *
     * @param numericalStatisticsDTO the entity to save.
     * @return the persisted entity.
     */
    NumericalStatisticsDTO save(NumericalStatisticsDTO numericalStatisticsDTO);

    /**
     * Get all the numericalStatistics.
     *
     * @return the list of entities.
     */
    List<NumericalStatisticsDTO> findAll();
    /**
     * Get all the NumericalStatisticsDTO where AnalysisId is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<NumericalStatisticsDTO> findAllWhereAnalysisIdIsNull();


    /**
     * Get the "id" numericalStatistics.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NumericalStatisticsDTO> findOne(String id);

    /**
     * Delete the "id" numericalStatistics.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
