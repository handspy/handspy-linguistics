package pt.up.hs.linguistics.service.impl;

import pt.up.hs.linguistics.service.EmotionService;
import pt.up.hs.linguistics.domain.Emotion;
import pt.up.hs.linguistics.repository.EmotionRepository;
import pt.up.hs.linguistics.service.dto.EmotionDTO;
import pt.up.hs.linguistics.service.mapper.EmotionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Emotion}.
 */
@Service
public class EmotionServiceImpl implements EmotionService {

    private final Logger log = LoggerFactory.getLogger(EmotionServiceImpl.class);

    private final EmotionRepository emotionRepository;

    private final EmotionMapper emotionMapper;

    public EmotionServiceImpl(EmotionRepository emotionRepository, EmotionMapper emotionMapper) {
        this.emotionRepository = emotionRepository;
        this.emotionMapper = emotionMapper;
    }

    /**
     * Save a emotion.
     *
     * @param emotionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EmotionDTO save(EmotionDTO emotionDTO) {
        log.debug("Request to save Emotion : {}", emotionDTO);
        Emotion emotion = emotionMapper.toEntity(emotionDTO);
        emotion = emotionRepository.save(emotion);
        return emotionMapper.toDto(emotion);
    }

    /**
     * Get all the emotions.
     *
     * @return the list of entities.
     */
    @Override
    public List<EmotionDTO> findAll() {
        log.debug("Request to get all Emotions");
        return emotionRepository.findAll().stream()
            .map(emotionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one emotion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<EmotionDTO> findOne(String id) {
        log.debug("Request to get Emotion : {}", id);
        return emotionRepository.findById(id)
            .map(emotionMapper::toDto);
    }

    /**
     * Delete the emotion by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Emotion : {}", id);

        emotionRepository.deleteById(id);
    }
}
