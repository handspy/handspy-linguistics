package pt.up.hs.linguistics.service;

import pt.up.hs.linguistics.service.dto.SentenceDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link pt.up.hs.linguistics.domain.Sentence}.
 */
public interface SentenceService {

    /**
     * Save a sentence.
     *
     * @param projectId   ID of the project.
     * @param textId      ID of the text.
     * @param analysisId  ID of the analysis.
     * @param sentenceDTO the entity to save.
     * @return the persisted entity.
     */
    SentenceDTO save(Long projectId, Long textId, String analysisId, SentenceDTO sentenceDTO);

    /**
     * Get all the sentences.
     *
     * @param projectId   ID of the project.
     * @param textId      ID of the text.
     * @param analysisId  ID of the analysis.
     * @return the list of entities.
     */
    List<SentenceDTO> findAll(Long projectId, Long textId, String analysisId);


    /**
     * Get the "id" sentence.
     *
     * @param projectId   ID of the project.
     * @param textId      ID of the text.
     * @param analysisId  ID of the analysis.
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SentenceDTO> findOne(Long projectId, Long textId, String analysisId, String id);

    /**
     * Delete the "id" sentence.
     *
     * @param projectId   ID of the project.
     * @param textId      ID of the text.
     * @param analysisId  ID of the analysis.
     * @param id the id of the entity.
     */
    void delete(Long projectId, Long textId, String analysisId, String id);
}
