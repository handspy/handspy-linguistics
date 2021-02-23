package pt.up.hs.linguistics.service.impl;

import org.zalando.problem.Status;
import pt.up.hs.linguistics.constants.EntityNames;
import pt.up.hs.linguistics.constants.ErrorKeys;
import pt.up.hs.linguistics.domain.Analysis;
import pt.up.hs.linguistics.repository.FullAnalysis;
import pt.up.hs.linguistics.repository.AnalysisRepository;
import pt.up.hs.linguistics.service.PartOfSpeechService;
import pt.up.hs.linguistics.domain.PartOfSpeech;
import pt.up.hs.linguistics.repository.PartOfSpeechRepository;
import pt.up.hs.linguistics.service.dto.PartOfSpeechDTO;
import pt.up.hs.linguistics.service.exceptions.ServiceException;
import pt.up.hs.linguistics.service.mapper.AnalysisMapper;
import pt.up.hs.linguistics.service.mapper.PartOfSpeechMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.zalando.problem.Status.NOT_FOUND;

/**
 * Service Implementation for managing {@link PartOfSpeech}.
 */
@Service
public class PartOfSpeechServiceImpl implements PartOfSpeechService {

    private final Logger log = LoggerFactory.getLogger(PartOfSpeechServiceImpl.class);

    private final AnalysisRepository analysisRepository;
    private final AnalysisMapper analysisMapper;

    private final PartOfSpeechRepository partOfSpeechRepository;
    private final PartOfSpeechMapper partOfSpeechMapper;

    public PartOfSpeechServiceImpl(
        AnalysisRepository analysisRepository,AnalysisMapper analysisMapper,
        PartOfSpeechRepository partOfSpeechRepository,
        PartOfSpeechMapper partOfSpeechMapper
    ) {
        this.analysisRepository = analysisRepository;
        this.analysisMapper = analysisMapper;
        this.partOfSpeechRepository = partOfSpeechRepository;
        this.partOfSpeechMapper = partOfSpeechMapper;
    }

    /**
     * Save a partOfSpeech.
     *
     * @param projectId   ID of the project.
     * @param textId      ID of the text.
     * @param analysisId  ID of the analysis.
     * @param partOfSpeechDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PartOfSpeechDTO save(Long projectId, Long textId, String analysisId, PartOfSpeechDTO partOfSpeechDTO) {
        log.debug("Request to save part-of-speech {} from analysis {} in text {} of project {}", partOfSpeechDTO, analysisId, textId, projectId);
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
        PartOfSpeech partOfSpeech = partOfSpeechMapper.toEntity(partOfSpeechDTO);
        partOfSpeech = partOfSpeechRepository.save(partOfSpeech);
        return partOfSpeechMapper.toDto(partOfSpeech);
    }

    /**
     * Get all the partOfSpeeches.
     *
     * @param projectId   ID of the project.
     * @param textId      ID of the text.
     * @param analysisId  ID of the analysis.
     * @return the list of entities.
     */
    @Override
    public List<PartOfSpeechDTO> findAll(Long projectId, Long textId, String analysisId) {
        log.debug("Request to get all part-of-speeches from analysis {} in text {} of project {}", analysisId, textId, projectId);
        Analysis analysis = analysisRepository
            .findAnalysisByProjectIdAndTextIdAndId(projectId, textId, analysisId)
            .orElse(null);
        if (analysis == null) {
            throw new ServiceException(NOT_FOUND, EntityNames.ANALYSIS, ErrorKeys.ERR_NOT_FOUND, "Analysis not found");
        }
        return partOfSpeechRepository
            .findByAnalysisId(analysisId)
            .parallelStream()
            .map(partOfSpeechMapper::toDto)
            .collect(Collectors.toList());
    }


    /**
     * Get one part-of-speech by id.
     *
     * @param projectId   ID of the project.
     * @param textId      ID of the text.
     * @param analysisId  ID of the analysis.
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<PartOfSpeechDTO> findOne(Long projectId, Long textId, String analysisId, String id) {
        log.debug("Request to get part-of-speech {} from analysis {} in text {} of project {}", id, analysisId, textId, projectId);
        Analysis analysis = analysisRepository
            .findAnalysisByProjectIdAndTextIdAndId(projectId, textId, analysisId)
            .orElse(null);
        if (analysis == null) {
            throw new ServiceException(NOT_FOUND, EntityNames.ANALYSIS, ErrorKeys.ERR_NOT_FOUND, "Analysis not found");
        }
        Optional<PartOfSpeech> pos = partOfSpeechRepository.findByAnalysisIdAndId(analysisId, id);
        if (!pos.isPresent()) {
            return Optional.empty();
        }
        return pos.map(partOfSpeechMapper::toDto);
    }

    /**
     * Delete the part-of-speech by id.
     *
     * @param projectId   ID of the project.
     * @param textId      ID of the text.
     * @param analysisId  ID of the analysis.
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long projectId, Long textId, String analysisId, String id) {
        log.debug("Request to delete part-of-speech {} from analysis {} in text {} of project {}", id, analysisId, textId, projectId);
        Analysis analysis = analysisRepository
            .findAnalysisByProjectIdAndTextIdAndId(projectId, textId, analysisId)
            .orElse(null);
        if (analysis == null) {
            throw new ServiceException(NOT_FOUND, EntityNames.ANALYSIS, ErrorKeys.ERR_NOT_FOUND, "Analysis not found");
        }
        Optional<PartOfSpeech> pos = partOfSpeechRepository
            .findByAnalysisIdAndId(analysisId, id);
        if (!pos.isPresent()) {
            return;
        }
        partOfSpeechRepository.delete(pos.get());
    }
}
