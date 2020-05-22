package pt.up.hs.linguistics.service;

import pt.up.hs.linguistics.service.dto.AnalysisDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link pt.up.hs.linguistics.domain.Analysis}.
 */
public interface AnalysisService {

    /**
     * Save a analysis.
     *
     * @param analysisDTO the entity to save.
     * @return the persisted entity.
     */
    AnalysisDTO save(AnalysisDTO analysisDTO);

    /**
     * Get all the analyses.
     *
     * @return the list of entities.
     */
    List<AnalysisDTO> findAll();


    /**
     * Get the "id" analysis.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnalysisDTO> findOne(String id);

    /**
     * Delete the "id" analysis.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
