package pt.up.hs.linguistics.service.impl;

import pt.up.hs.linguistics.service.PartOfSpeechService;
import pt.up.hs.linguistics.domain.PartOfSpeech;
import pt.up.hs.linguistics.repository.PartOfSpeechRepository;
import pt.up.hs.linguistics.service.dto.PartOfSpeechDTO;
import pt.up.hs.linguistics.service.mapper.PartOfSpeechMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PartOfSpeech}.
 */
@Service
public class PartOfSpeechServiceImpl implements PartOfSpeechService {

    private final Logger log = LoggerFactory.getLogger(PartOfSpeechServiceImpl.class);

    private final PartOfSpeechRepository partOfSpeechRepository;

    private final PartOfSpeechMapper partOfSpeechMapper;

    public PartOfSpeechServiceImpl(PartOfSpeechRepository partOfSpeechRepository, PartOfSpeechMapper partOfSpeechMapper) {
        this.partOfSpeechRepository = partOfSpeechRepository;
        this.partOfSpeechMapper = partOfSpeechMapper;
    }

    /**
     * Save a partOfSpeech.
     *
     * @param partOfSpeechDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PartOfSpeechDTO save(PartOfSpeechDTO partOfSpeechDTO) {
        log.debug("Request to save PartOfSpeech : {}", partOfSpeechDTO);
        PartOfSpeech partOfSpeech = partOfSpeechMapper.toEntity(partOfSpeechDTO);
        partOfSpeech = partOfSpeechRepository.save(partOfSpeech);
        return partOfSpeechMapper.toDto(partOfSpeech);
    }

    /**
     * Get all the partOfSpeeches.
     *
     * @return the list of entities.
     */
    @Override
    public List<PartOfSpeechDTO> findAll() {
        log.debug("Request to get all PartOfSpeeches");
        return partOfSpeechRepository.findAll().stream()
            .map(partOfSpeechMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one partOfSpeech by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<PartOfSpeechDTO> findOne(String id) {
        log.debug("Request to get PartOfSpeech : {}", id);
        return partOfSpeechRepository.findById(id)
            .map(partOfSpeechMapper::toDto);
    }

    /**
     * Delete the partOfSpeech by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete PartOfSpeech : {}", id);

        partOfSpeechRepository.deleteById(id);
    }
}
