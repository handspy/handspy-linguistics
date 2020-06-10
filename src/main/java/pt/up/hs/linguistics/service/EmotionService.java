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
     * @param projectId   ID of the project.
     * @param textId      ID of the text.
     * @param analysisId  ID of the analysis.
     * @param emotionDTO the entity to save.
     * @return the persisted entity.
     */
    EmotionDTO save(Long projectId, Long textId, String analysisId, EmotionDTO emotionDTO);

    /**
     * Get all the emotions.
     *
     * @param projectId   ID of the project.
     * @param textId      ID of the text.
     * @param analysisId  ID of the analysis.
     * @return the list of entities.
     */
    List<EmotionDTO> findAll(Long projectId, Long textId, String analysisId);


    /**
     * Get the "id" emotion.
     *
     * @param projectId   ID of the project.
     * @param textId      ID of the text.
     * @param analysisId  ID of the analysis.
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmotionDTO> findOne(Long projectId, Long textId, String analysisId, String id);

    /**
     * Delete the "id" emotion.
     *
     * @param projectId   ID of the project.
     * @param textId      ID of the text.
     * @param analysisId  ID of the analysis.
     * @param id the id of the entity.
     */
    void delete(Long projectId, Long textId, String analysisId, String id);
}
