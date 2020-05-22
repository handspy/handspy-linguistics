package pt.up.hs.linguistics.service.impl;

import pt.up.hs.linguistics.service.AnalysisService;
import pt.up.hs.linguistics.domain.Analysis;
import pt.up.hs.linguistics.repository.AnalysisRepository;
import pt.up.hs.linguistics.service.dto.AnalysisDTO;
import pt.up.hs.linguistics.service.mapper.AnalysisMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Analysis}.
 */
@Service
public class AnalysisServiceImpl implements AnalysisService {

    private final Logger log = LoggerFactory.getLogger(AnalysisServiceImpl.class);

    private final AnalysisRepository analysisRepository;

    private final AnalysisMapper analysisMapper;

    public AnalysisServiceImpl(AnalysisRepository analysisRepository, AnalysisMapper analysisMapper) {
        this.analysisRepository = analysisRepository;
        this.analysisMapper = analysisMapper;
    }

    /**
     * Save a analysis.
     *
     * @param analysisDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AnalysisDTO save(AnalysisDTO analysisDTO) {
        log.debug("Request to save Analysis : {}", analysisDTO);
        Analysis analysis = analysisMapper.toEntity(analysisDTO);
        analysis = analysisRepository.save(analysis);
        return analysisMapper.toDto(analysis);
    }

    /**
     * Get all the analyses.
     *
     * @return the list of entities.
     */
    @Override
    public List<AnalysisDTO> findAll() {
        log.debug("Request to get all Analyses");
        return analysisRepository.findAll().stream()
            .map(analysisMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one analysis by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<AnalysisDTO> findOne(String id) {
        log.debug("Request to get Analysis : {}", id);
        return analysisRepository.findById(id)
            .map(analysisMapper::toDto);
    }

    /**
     * Delete the analysis by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Analysis : {}", id);

        analysisRepository.deleteById(id);
    }
}
