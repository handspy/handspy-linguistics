package pt.up.hs.linguistics.service;

import pt.up.hs.linguistics.service.dto.AnalysisDTO;
import pt.up.hs.linguistics.service.dto.EmotionDTO;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link pt.up.hs.linguistics.domain.Analysis}.
 */
public interface AnalysisService {

    /**
     * Perform linguistic analysis and save its result merged with
     * provided {@link AnalysisDTO} entity.
     *
     * @param projectId   ID of the project to which the analysis belongs.
     * @param textId      ID of the protocol to which the analysis belongs.
     * @param analysisDTO the entity to save.
     * @return the persisted entity.
     */
    AnalysisDTO analyze(
        Long projectId, Long textId,
        AnalysisDTO analysisDTO
    );

    /**
     * Save a analysis.
     *
     * @param projectId   ID of the project to which the analysis belongs.
     * @param textId      ID of the text to which the analysis belongs.
     * @param analysisDTO the entity to save.
     * @return the persisted entity.
     */
    AnalysisDTO save(Long projectId, Long textId, AnalysisDTO analysisDTO);

    /**
     * Get all the analyses.
     *
     * @param projectId ID of the project to which the analysis belongs.
     * @param textId    ID of the text to which the analysis belongs.
     * @return the list of entities.
     */
    List<AnalysisDTO> findAll(Long projectId, Long textId);

    /**
     * Get the "id" analysis.
     *
     * @param projectId ID of the project to which the analysis belongs.
     * @param textId    ID of the text to which the analysis belongs.
     * @param id        the id of the entity.
     * @param full      return full analysis.
     * @return the entity.
     */
    Optional<AnalysisDTO> findOne(Long projectId, Long textId, String id, boolean full);

    /**
     * Delete the "id" analysis.
     *
     * @param projectId ID of the project to which the analysis belongs.
     * @param textId    ID of the text to which the analysis belongs.
     * @param id        the id of the entity.
     */
    void delete(Long projectId, Long textId, String id);

    /**
     *
     * @param projectId   ID of the project to which the analysis belongs.
     * @param textId      ID of the text to which the analysis belongs.
     * @param id          ID of the analysis.
     * @param emotionDTOs the emotion DTOs.
     * @return the saved emotions.
     */
    Collection<EmotionDTO> upsertEmotional(Long projectId, Long textId, String id, Collection<EmotionDTO> emotionDTOs);
}
