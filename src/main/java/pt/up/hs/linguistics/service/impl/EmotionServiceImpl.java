package pt.up.hs.linguistics.service.impl;

import org.zalando.problem.Status;
import pt.up.hs.linguistics.constants.EntityNames;
import pt.up.hs.linguistics.constants.ErrorKeys;
import pt.up.hs.linguistics.domain.Analysis;
import pt.up.hs.linguistics.repository.AnalysisRepository;
import pt.up.hs.linguistics.service.EmotionService;
import pt.up.hs.linguistics.domain.Emotion;
import pt.up.hs.linguistics.repository.EmotionRepository;
import pt.up.hs.linguistics.service.dto.EmotionDTO;
import pt.up.hs.linguistics.service.exceptions.ServiceException;
import pt.up.hs.linguistics.service.mapper.EmotionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Emotion}.
 */
@Service
public class EmotionServiceImpl implements EmotionService {

    private final Logger log = LoggerFactory.getLogger(EmotionServiceImpl.class);

    private final AnalysisRepository analysisRepository;

    private final EmotionRepository emotionRepository;
    private final EmotionMapper emotionMapper;

    public EmotionServiceImpl(
        AnalysisRepository analysisRepository,
        EmotionRepository emotionRepository,
        EmotionMapper emotionMapper
    ) {
        this.analysisRepository = analysisRepository;
        this.emotionRepository = emotionRepository;
        this.emotionMapper = emotionMapper;
    }

    /**
     * Save a emotion.
     *
     * @param projectId   ID of the project.
     * @param textId      ID of the text.
     * @param analysisId  ID of the analysis.
     * @param emotionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EmotionDTO save(Long projectId, Long textId, String analysisId, EmotionDTO emotionDTO) {
        log.debug("Request to save emotion {} from analysis {} in text {} of project {}", emotionDTO, analysisId, textId, projectId);
        Optional<Analysis> analysis = analysisRepository
            .findByProjectIdAndTextIdAndId(projectId, textId, analysisId);
        if (!analysis.isPresent()) {
            throw new ServiceException(
                Status.NOT_FOUND,
                EntityNames.ANALYSIS,
                ErrorKeys.ERR_NOT_FOUND,
                "Analysis was not found"
            );
        }
        Emotion emotion = emotionMapper.toEntity(emotionDTO);
        emotion.analysis(analysis.get());
        emotion = emotionRepository.save(emotion);
        return emotionMapper.toDto(emotion);
    }

    /**
     * Get all the emotions.
     *
     * @param projectId   ID of the project.
     * @param textId      ID of the text.
     * @param analysisId  ID of the analysis.
     * @return the list of entities.
     */
    @Override
    public List<EmotionDTO> findAll(Long projectId, Long textId, String analysisId) {
        log.debug("Request to get all emotions from analysis {} in text {} of project {}", analysisId, textId, projectId);
        return emotionRepository
            .findByAnalysisId(analysisId)
            .stream()
            .filter(emotion ->
                Objects.equals(emotion.getAnalysis().getProjectId(), projectId) &&
                    Objects.equals(emotion.getAnalysis().getTextId(), textId)
            )
            .map(emotionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one emotion by id.
     *
     * @param projectId   ID of the project.
     * @param textId      ID of the text.
     * @param analysisId  ID of the analysis.
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<EmotionDTO> findOne(Long projectId, Long textId, String analysisId, String id) {
        log.debug("Request to get emotion {} from analysis {} in text {} of project {}", id, analysisId, textId, projectId);
        Optional<Emotion> emotion = emotionRepository.findByAnalysisIdAndId(analysisId, id);
        if (!emotion.isPresent() || !(
            Objects.equals(emotion.get().getAnalysis().getProjectId(), projectId) &&
                Objects.equals(emotion.get().getAnalysis().getTextId(), textId)
        )) {
            return Optional.empty();
        }
        return emotion.map(emotionMapper::toDto);
    }

    /**
     * Delete the emotion by id.
     *
     * @param projectId   ID of the project.
     * @param textId      ID of the text.
     * @param analysisId  ID of the analysis.
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long projectId, Long textId, String analysisId, String id) {
        log.debug("Request to delete emotion {} from analysis {} in text {} of project {}", id, analysisId, textId, projectId);
        Optional<Emotion> emotion = emotionRepository.findByAnalysisIdAndId(analysisId, id);
        if (!emotion.isPresent() || !(
            Objects.equals(emotion.get().getAnalysis().getProjectId(), projectId) &&
                Objects.equals(emotion.get().getAnalysis().getTextId(), textId)
        )) {
            return;
        }
        emotionRepository.delete(emotion.get());
    }
}
