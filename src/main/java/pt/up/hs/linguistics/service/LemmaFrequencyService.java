package pt.up.hs.linguistics.service;

import pt.up.hs.linguistics.service.dto.LemmaFrequencyDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link pt.up.hs.linguistics.domain.LemmaFrequency}.
 */
public interface LemmaFrequencyService {

    /**
     * Save a lemmaFrequency.
     *
     * @param lemmaFrequencyDTO the entity to save.
     * @return the persisted entity.
     */
    LemmaFrequencyDTO save(LemmaFrequencyDTO lemmaFrequencyDTO);

    /**
     * Get all the lemmaFrequencies.
     *
     * @return the list of entities.
     */
    List<LemmaFrequencyDTO> findAll();


    /**
     * Get the "id" lemmaFrequency.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LemmaFrequencyDTO> findOne(String id);

    /**
     * Delete the "id" lemmaFrequency.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
