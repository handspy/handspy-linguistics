package pt.up.hs.linguistics.service;

import pt.up.hs.linguistics.service.dto.WordFrequencyDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link pt.up.hs.linguistics.domain.WordFrequency}.
 */
public interface WordFrequencyService {

    /**
     * Save a wordFrequency.
     *
     * @param wordFrequencyDTO the entity to save.
     * @return the persisted entity.
     */
    WordFrequencyDTO save(WordFrequencyDTO wordFrequencyDTO);

    /**
     * Get all the wordFrequencies.
     *
     * @return the list of entities.
     */
    List<WordFrequencyDTO> findAll();


    /**
     * Get the "id" wordFrequency.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WordFrequencyDTO> findOne(String id);

    /**
     * Delete the "id" wordFrequency.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
