package pt.up.hs.linguistics.service.impl;

import pt.up.hs.linguistics.service.NumericalStatisticsService;
import pt.up.hs.linguistics.domain.NumericalStatistics;
import pt.up.hs.linguistics.repository.NumericalStatisticsRepository;
import pt.up.hs.linguistics.service.dto.NumericalStatisticsDTO;
import pt.up.hs.linguistics.service.mapper.NumericalStatisticsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link NumericalStatistics}.
 */
@Service
public class NumericalStatisticsServiceImpl implements NumericalStatisticsService {

    private final Logger log = LoggerFactory.getLogger(NumericalStatisticsServiceImpl.class);

    private final NumericalStatisticsRepository numericalStatisticsRepository;

    private final NumericalStatisticsMapper numericalStatisticsMapper;

    public NumericalStatisticsServiceImpl(NumericalStatisticsRepository numericalStatisticsRepository, NumericalStatisticsMapper numericalStatisticsMapper) {
        this.numericalStatisticsRepository = numericalStatisticsRepository;
        this.numericalStatisticsMapper = numericalStatisticsMapper;
    }

    /**
     * Save a numericalStatistics.
     *
     * @param numericalStatisticsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public NumericalStatisticsDTO save(NumericalStatisticsDTO numericalStatisticsDTO) {
        log.debug("Request to save NumericalStatistics : {}", numericalStatisticsDTO);
        NumericalStatistics numericalStatistics = numericalStatisticsMapper.toEntity(numericalStatisticsDTO);
        numericalStatistics = numericalStatisticsRepository.save(numericalStatistics);
        return numericalStatisticsMapper.toDto(numericalStatistics);
    }

    /**
     * Get all the numericalStatistics.
     *
     * @return the list of entities.
     */
    @Override
    public List<NumericalStatisticsDTO> findAll() {
        log.debug("Request to get all NumericalStatistics");
        return numericalStatisticsRepository.findAll().stream()
            .map(numericalStatisticsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
     *  Get all the numericalStatistics where AnalysisId is {@code null}.
     *  @return the list of entities.
     */
    public List<NumericalStatisticsDTO> findAllWhereAnalysisIdIsNull() {
        log.debug("Request to get all numericalStatistics where AnalysisId is null");
        return StreamSupport
            .stream(numericalStatisticsRepository.findAll().spliterator(), false)
            .filter(numericalStatistics -> numericalStatistics.getAnalysisId() == null)
            .map(numericalStatisticsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one numericalStatistics by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<NumericalStatisticsDTO> findOne(String id) {
        log.debug("Request to get NumericalStatistics : {}", id);
        return numericalStatisticsRepository.findById(id)
            .map(numericalStatisticsMapper::toDto);
    }

    /**
     * Delete the numericalStatistics by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete NumericalStatistics : {}", id);

        numericalStatisticsRepository.deleteById(id);
    }
}
