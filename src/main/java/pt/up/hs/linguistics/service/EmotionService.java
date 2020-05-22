package pt.up.hs.linguistics.service;

import pt.up.hs.linguistics.service.dto.EmotionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link pt.up.hs.linguistics.domain.Emotion}.
 */
public interface EmotionService {

    /**
     * Save a emotion.
     *
     * @param emotionDTO the entity to save.
     * @return the persisted entity.
     */
    EmotionDTO save(EmotionDTO emotionDTO);

    /**
     * Get all the emotions.
     *
     * @return the list of entities.
     */
    List<EmotionDTO> findAll();


    /**
     * Get the "id" emotion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmotionDTO> findOne(String id);

    /**
     * Delete the "id" emotion.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
