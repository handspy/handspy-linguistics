package pt.up.hs.linguistics.web.rest;

import pt.up.hs.linguistics.constants.EntityNames;
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
@RequestMapping("/api/projects/{projectId}/texts/{textId}/analyses/{analysisId}")
public class EmotionResource {

    private final Logger log = LoggerFactory.getLogger(EmotionResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmotionService emotionService;

    public EmotionResource(EmotionService emotionService) {
        this.emotionService = emotionService;
    }

    /**
     * {@code POST  /emotions} : Create a new emotion.
     *
     * @param projectId   ID of the project.
     * @param textId      ID of the text analyzed.
     * @param analysisId  ID of the analysis.
     * @param emotionDTO the emotionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emotionDTO, or with status {@code 400 (Bad Request)} if the emotion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/emotions")
    public ResponseEntity<EmotionDTO> createEmotion(
        @PathVariable("projectId") Long projectId,
        @PathVariable("textId") Long textId,
        @PathVariable("analysisId") String analysisId,
        @Valid @RequestBody EmotionDTO emotionDTO
    ) throws URISyntaxException {
        log.debug("REST request to save emotion {} from analysis {} in text {} of project {}", emotionDTO, analysisId, textId, projectId);
        if (emotionDTO.getId() != null) {
            throw new BadRequestAlertException("A new emotion cannot already have an ID", EntityNames.EMOTION, "idexists");
        }
        EmotionDTO result = emotionService.save(projectId, textId, analysisId, emotionDTO);
        return ResponseEntity.created(new URI("/api/projects/" + projectId + "/texts/" + textId + "/analyses/" + analysisId + "/emotions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, EntityNames.EMOTION, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /emotions} : Updates an existing emotion.
     *
     * @param projectId   ID of the project.
     * @param textId      ID of the text analyzed.
     * @param analysisId  ID of the analysis.
     * @param emotionDTO the emotionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emotionDTO,
     * or with status {@code 400 (Bad Request)} if the emotionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emotionDTO couldn't be updated.
     */
    @PutMapping("/emotions")
    public ResponseEntity<EmotionDTO> updateEmotion(
        @PathVariable("projectId") Long projectId,
        @PathVariable("textId") Long textId,
        @PathVariable("analysisId") String analysisId,
        @Valid @RequestBody EmotionDTO emotionDTO
    ) {
        log.debug("REST request to update emotion {} from analysis {} in text {} of project {}", emotionDTO, analysisId, textId, projectId);
        if (emotionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", EntityNames.EMOTION, "idnull");
        }
        EmotionDTO result = emotionService.save(projectId, textId, analysisId, emotionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, EntityNames.EMOTION, emotionDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /emotions} : get all the emotions.
     *
     * @param projectId   ID of the project.
     * @param textId      ID of the text analyzed.
     * @param analysisId  ID of the analysis.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emotions in body.
     */
    @GetMapping("/emotions")
    public List<EmotionDTO> getAllEmotions(
        @PathVariable("projectId") Long projectId,
        @PathVariable("textId") Long textId,
        @PathVariable("analysisId") String analysisId
    ) {
        log.debug("REST request to get all emotions from analysis {} in text {} of project {}", analysisId, textId, projectId);
        return emotionService.findAll(projectId, textId, analysisId);
    }

    /**
     * {@code GET  /emotions/:id} : get the "id" emotion.
     *
     * @param projectId   ID of the project.
     * @param textId      ID of the text analyzed.
     * @param analysisId  ID of the analysis.
     * @param id the id of the emotionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emotionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/emotions/{id}")
    public ResponseEntity<EmotionDTO> getEmotion(
        @PathVariable("projectId") Long projectId,
        @PathVariable("textId") Long textId,
        @PathVariable("analysisId") String analysisId,
        @PathVariable String id
    ) {
        log.debug("REST request to get emotion {} from analysis {} in text {} of project {}", id, analysisId, textId, projectId);
        Optional<EmotionDTO> emotionDTO = emotionService.findOne(projectId, textId, analysisId, id);
        return ResponseUtil.wrapOrNotFound(emotionDTO);
    }

    /**
     * {@code DELETE  /emotions/:id} : delete the "id" emotion.
     *
     * @param projectId   ID of the project.
     * @param textId      ID of the text analyzed.
     * @param analysisId  ID of the analysis.
     * @param id the id of the emotionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/emotions/{id}")
    public ResponseEntity<Void> deleteEmotion(
        @PathVariable("projectId") Long projectId,
        @PathVariable("textId") Long textId,
        @PathVariable("analysisId") String analysisId,
        @PathVariable String id
    ) {
        log.debug("REST request to delete emotion {} from analysis {} in text {} of project {}", id, analysisId, textId, projectId);
        emotionService.delete(projectId, textId, analysisId, id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, EntityNames.EMOTION, id))
            .build();
    }
}
