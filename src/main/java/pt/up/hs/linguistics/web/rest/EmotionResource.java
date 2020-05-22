package pt.up.hs.linguistics.web.rest;

import pt.up.hs.linguistics.service.EmotionService;
import pt.up.hs.linguistics.web.rest.errors.BadRequestAlertException;
import pt.up.hs.linguistics.service.dto.EmotionDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link pt.up.hs.linguistics.domain.Emotion}.
 */
@RestController
@RequestMapping("/api")
public class EmotionResource {

    private final Logger log = LoggerFactory.getLogger(EmotionResource.class);

    private static final String ENTITY_NAME = "linguisticsEmotion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmotionService emotionService;

    public EmotionResource(EmotionService emotionService) {
        this.emotionService = emotionService;
    }

    /**
     * {@code POST  /emotions} : Create a new emotion.
     *
     * @param emotionDTO the emotionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emotionDTO, or with status {@code 400 (Bad Request)} if the emotion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/emotions")
    public ResponseEntity<EmotionDTO> createEmotion(@Valid @RequestBody EmotionDTO emotionDTO) throws URISyntaxException {
        log.debug("REST request to save Emotion : {}", emotionDTO);
        if (emotionDTO.getId() != null) {
            throw new BadRequestAlertException("A new emotion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmotionDTO result = emotionService.save(emotionDTO);
        return ResponseEntity.created(new URI("/api/emotions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /emotions} : Updates an existing emotion.
     *
     * @param emotionDTO the emotionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emotionDTO,
     * or with status {@code 400 (Bad Request)} if the emotionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emotionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/emotions")
    public ResponseEntity<EmotionDTO> updateEmotion(@Valid @RequestBody EmotionDTO emotionDTO) throws URISyntaxException {
        log.debug("REST request to update Emotion : {}", emotionDTO);
        if (emotionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmotionDTO result = emotionService.save(emotionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, emotionDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /emotions} : get all the emotions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emotions in body.
     */
    @GetMapping("/emotions")
    public List<EmotionDTO> getAllEmotions() {
        log.debug("REST request to get all Emotions");
        return emotionService.findAll();
    }

    /**
     * {@code GET  /emotions/:id} : get the "id" emotion.
     *
     * @param id the id of the emotionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emotionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/emotions/{id}")
    public ResponseEntity<EmotionDTO> getEmotion(@PathVariable String id) {
        log.debug("REST request to get Emotion : {}", id);
        Optional<EmotionDTO> emotionDTO = emotionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(emotionDTO);
    }

    /**
     * {@code DELETE  /emotions/:id} : delete the "id" emotion.
     *
     * @param id the id of the emotionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/emotions/{id}")
    public ResponseEntity<Void> deleteEmotion(@PathVariable String id) {
        log.debug("REST request to delete Emotion : {}", id);

        emotionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
