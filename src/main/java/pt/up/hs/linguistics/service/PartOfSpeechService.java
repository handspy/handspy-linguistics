package pt.up.hs.linguistics.service;

import pt.up.hs.linguistics.service.dto.PartOfSpeechDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link pt.up.hs.linguistics.domain.PartOfSpeech}.
 */
public interface PartOfSpeechService {

    /**
     * Save a partOfSpeech.
     *
     * @param partOfSpeechDTO the entity to save.
     * @return the persisted entity.
     */
    PartOfSpeechDTO save(PartOfSpeechDTO partOfSpeechDTO);

    /**
     * Get all the partOfSpeeches.
     *
     * @return the list of entities.
     */
    List<PartOfSpeechDTO> findAll();


    /**
     * Get the "id" partOfSpeech.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PartOfSpeechDTO> findOne(String id);

    /**
     * Delete the "id" partOfSpeech.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
