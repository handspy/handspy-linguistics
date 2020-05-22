package pt.up.hs.linguistics.service.impl;

import pt.up.hs.linguistics.service.WordFrequencyService;
import pt.up.hs.linguistics.domain.WordFrequency;
import pt.up.hs.linguistics.repository.WordFrequencyRepository;
import pt.up.hs.linguistics.service.dto.WordFrequencyDTO;
import pt.up.hs.linguistics.service.mapper.WordFrequencyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link WordFrequency}.
 */
@Service
public class WordFrequencyServiceImpl implements WordFrequencyService {

    private final Logger log = LoggerFactory.getLogger(WordFrequencyServiceImpl.class);

    private final WordFrequencyRepository wordFrequencyRepository;

    private final WordFrequencyMapper wordFrequencyMapper;

    public WordFrequencyServiceImpl(WordFrequencyRepository wordFrequencyRepository, WordFrequencyMapper wordFrequencyMapper) {
        this.wordFrequencyRepository = wordFrequencyRepository;
        this.wordFrequencyMapper = wordFrequencyMapper;
    }

    /**
     * Save a wordFrequency.
     *
     * @param wordFrequencyDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public WordFrequencyDTO save(WordFrequencyDTO wordFrequencyDTO) {
        log.debug("Request to save WordFrequency : {}", wordFrequencyDTO);
        WordFrequency wordFrequency = wordFrequencyMapper.toEntity(wordFrequencyDTO);
        wordFrequency = wordFrequencyRepository.save(wordFrequency);
        return wordFrequencyMapper.toDto(wordFrequency);
    }

    /**
     * Get all the wordFrequencies.
     *
     * @return the list of entities.
     */
    @Override
    public List<WordFrequencyDTO> findAll() {
        log.debug("Request to get all WordFrequencies");
        return wordFrequencyRepository.findAll().stream()
            .map(wordFrequencyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one wordFrequency by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<WordFrequencyDTO> findOne(String id) {
        log.debug("Request to get WordFrequency : {}", id);
        return wordFrequencyRepository.findById(id)
            .map(wordFrequencyMapper::toDto);
    }

    /**
     * Delete the wordFrequency by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete WordFrequency : {}", id);

        wordFrequencyRepository.deleteById(id);
    }
}
