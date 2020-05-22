package pt.up.hs.linguistics.service.impl;

import pt.up.hs.linguistics.service.LemmaFrequencyService;
import pt.up.hs.linguistics.domain.LemmaFrequency;
import pt.up.hs.linguistics.repository.LemmaFrequencyRepository;
import pt.up.hs.linguistics.service.dto.LemmaFrequencyDTO;
import pt.up.hs.linguistics.service.mapper.LemmaFrequencyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link LemmaFrequency}.
 */
@Service
public class LemmaFrequencyServiceImpl implements LemmaFrequencyService {

    private final Logger log = LoggerFactory.getLogger(LemmaFrequencyServiceImpl.class);

    private final LemmaFrequencyRepository lemmaFrequencyRepository;

    private final LemmaFrequencyMapper lemmaFrequencyMapper;

    public LemmaFrequencyServiceImpl(LemmaFrequencyRepository lemmaFrequencyRepository, LemmaFrequencyMapper lemmaFrequencyMapper) {
        this.lemmaFrequencyRepository = lemmaFrequencyRepository;
        this.lemmaFrequencyMapper = lemmaFrequencyMapper;
    }

    /**
     * Save a lemmaFrequency.
     *
     * @param lemmaFrequencyDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public LemmaFrequencyDTO save(LemmaFrequencyDTO lemmaFrequencyDTO) {
        log.debug("Request to save LemmaFrequency : {}", lemmaFrequencyDTO);
        LemmaFrequency lemmaFrequency = lemmaFrequencyMapper.toEntity(lemmaFrequencyDTO);
        lemmaFrequency = lemmaFrequencyRepository.save(lemmaFrequency);
        return lemmaFrequencyMapper.toDto(lemmaFrequency);
    }

    /**
     * Get all the lemmaFrequencies.
     *
     * @return the list of entities.
     */
    @Override
    public List<LemmaFrequencyDTO> findAll() {
        log.debug("Request to get all LemmaFrequencies");
        return lemmaFrequencyRepository.findAll().stream()
            .map(lemmaFrequencyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one lemmaFrequency by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<LemmaFrequencyDTO> findOne(String id) {
        log.debug("Request to get LemmaFrequency : {}", id);
        return lemmaFrequencyRepository.findById(id)
            .map(lemmaFrequencyMapper::toDto);
    }

    /**
     * Delete the lemmaFrequency by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete LemmaFrequency : {}", id);

        lemmaFrequencyRepository.deleteById(id);
    }
}
