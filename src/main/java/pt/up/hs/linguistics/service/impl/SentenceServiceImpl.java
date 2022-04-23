package pt.up.hs.linguistics.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.zalando.problem.Status;
import pt.up.hs.linguistics.constants.EntityNames;
import pt.up.hs.linguistics.constants.ErrorKeys;
import pt.up.hs.linguistics.domain.Analysis;
import pt.up.hs.linguistics.domain.Sentence;
import pt.up.hs.linguistics.repository.AnalysisRepository;
import pt.up.hs.linguistics.repository.FullAnalysis;
import pt.up.hs.linguistics.repository.SentenceRepository;
import pt.up.hs.linguistics.service.SentenceService;
import pt.up.hs.linguistics.service.dto.SentenceDTO;
import pt.up.hs.linguistics.service.exceptions.ServiceException;
import pt.up.hs.linguistics.service.mapper.AnalysisMapper;
import pt.up.hs.linguistics.service.mapper.SentenceMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.zalando.problem.Status.NOT_FOUND;

/**
 * Service Implementation for managing {@link Sentence}.
 */
@Service
public class SentenceServiceImpl implements SentenceService {

    private final Logger log = LoggerFactory.getLogger(SentenceServiceImpl.class);

    private final AnalysisRepository analysisRepository;
    private final AnalysisMapper analysisMapper;

    private final SentenceRepository sentenceRepository;
    private final SentenceMapper sentenceMapper;

    public SentenceServiceImpl(
        AnalysisRepository analysisRepository,
        AnalysisMapper analysisMapper,
        SentenceRepository sentenceRepository,
        SentenceMapper sentenceMapper
    ) {
        this.analysisRepository = analysisRepository;
        this.analysisMapper = analysisMapper;
        this.sentenceRepository = sentenceRepository;
        this.sentenceMapper = sentenceMapper;
    }

    /**
     * Save a sentence.
     *
     * @param projectId   ID of the project.
     * @param textId      ID of the text.
     * @param analysisId  ID of the analysis.
     * @param sentenceDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SentenceDTO save(Long projectId, Long textId, String analysisId, SentenceDTO sentenceDTO) {
        log.debug("Request to save sentence {} from analysis {} in text {} of project {}", sentenceDTO, analysisId, textId, projectId);
        Optional<FullAnalysis> analysis = analysisRepository
            .findFullAnalysisByProjectIdAndTextIdAndId(projectId, textId, analysisId);
        if (!analysis.isPresent()) {
            throw new ServiceException(
                Status.NOT_FOUND,
                EntityNames.ANALYSIS,
                ErrorKeys.ERR_NOT_FOUND,
                "Analysis was not found"
            );
        }
        Sentence sentence = sentenceMapper.toEntity(sentenceDTO);
        sentence.analysis(analysisMapper.fromId(analysisId));
        sentence = sentenceRepository.save(sentence);
        return sentenceMapper.toDto(sentence);
    }

    /**
     * Get all the sentences.
     *
     * @param projectId   ID of the project.
     * @param textId      ID of the text.
     * @param analysisId  ID of the analysis.
     * @return the list of entities.
     */
    @Override
    public List<SentenceDTO> findAll(Long projectId, Long textId, String analysisId) {
        log.debug("Request to get all sentences from analysis {} in text {} of project {}", analysisId, textId, projectId);
        Analysis analysis = analysisRepository
            .findAnalysisByProjectIdAndTextIdAndId(projectId, textId, analysisId)
            .orElse(null);
        if (analysis == null) {
            throw new ServiceException(NOT_FOUND, EntityNames.ANALYSIS, ErrorKeys.ERR_NOT_FOUND, "Analysis not found");
        }
        return sentenceRepository.findByAnalysisId(analysisId)
            .parallelStream()
            .peek(sentence -> sentence.setAnalysis(analysis))
            .map(sentenceMapper::toDto)
            .collect(Collectors.toList());
    }


    /**
     * Get one sentence by id.
     *
     * @param projectId   ID of the project.
     * @param textId      ID of the text.
     * @param analysisId  ID of the analysis.
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<SentenceDTO> findOne(Long projectId, Long textId, String analysisId, String id) {
        log.debug("Request to get sentence {} from analysis {} in text {} of project {}", id, analysisId, textId, projectId);
        Analysis analysis = analysisRepository
            .findAnalysisByProjectIdAndTextIdAndId(projectId, textId, analysisId)
            .orElse(null);
        if (analysis == null) {
            throw new ServiceException(NOT_FOUND, EntityNames.ANALYSIS, ErrorKeys.ERR_NOT_FOUND, "Analysis not found");
        }
        Optional<Sentence> sentence = sentenceRepository
            .findByAnalysisIdAndId(analysisId, id);
        if (!sentence.isPresent()) {
            return Optional.empty();
        }
        return sentence.map(sentenceMapper::toDto);
    }

    /**
     * Delete the sentence by id.
     *
     * @param projectId   ID of the project.
     * @param textId      ID of the text.
     * @param analysisId  ID of the analysis.
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long projectId, Long textId, String analysisId, String id) {
        log.debug("Request to delete sentence {} from analysis {} in text {} of project {}", id, analysisId, textId, projectId);
        Analysis analysis = analysisRepository
            .findAnalysisByProjectIdAndTextIdAndId(projectId, textId, analysisId)
            .orElse(null);
        if (analysis == null) {
            throw new ServiceException(NOT_FOUND, EntityNames.ANALYSIS, ErrorKeys.ERR_NOT_FOUND, "Analysis not found");
        }
        Optional<Sentence> sentence = sentenceRepository
            .findByAnalysisIdAndId(analysisId, id);
        if (!sentence.isPresent()) {
            return;
        }
        sentenceRepository.delete(sentence.get());
    }
}
