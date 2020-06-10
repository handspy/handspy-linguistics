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
     * @param projectId   ID of the project.
     * @param textId      ID of the text.
     * @param analysisId  ID of the analysis.
     * @param partOfSpeechDTO the entity to save.
     * @return the persisted entity.
     */
    PartOfSpeechDTO save(Long projectId, Long textId, String analysisId, PartOfSpeechDTO partOfSpeechDTO);

    /**
     * Get all the partOfSpeeches.
     *
     * @param projectId   ID of the project.
     * @param textId      ID of the text.
     * @param analysisId  ID of the analysis.
     * @return the list of entities.
     */
    List<PartOfSpeechDTO> findAll(Long projectId, Long textId, String analysisId);


    /**
     * Get the "id" partOfSpeech.
     *
     * @param projectId   ID of the project.
     * @param textId      ID of the text.
     * @param analysisId  ID of the analysis.
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PartOfSpeechDTO> findOne(Long projectId, Long textId, String analysisId, String id);

    /**
     * Delete the "id" partOfSpeech.
     *
     * @param projectId   ID of the project.
     * @param textId      ID of the text.
     * @param analysisId  ID of the analysis.
     * @param id the id of the entity.
     */
    void delete(Long projectId, Long textId, String analysisId, String id);
}
