package pt.up.hs.linguistics.web.rest;

import pt.up.hs.linguistics.constants.EntityNames;
import pt.up.hs.linguistics.service.PartOfSpeechService;
import pt.up.hs.linguistics.web.rest.errors.BadRequestAlertException;
import pt.up.hs.linguistics.service.dto.PartOfSpeechDTO;

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
 * REST controller for managing {@link pt.up.hs.linguistics.domain.PartOfSpeech}.
 */
@RestController
@RequestMapping("/api/projects/{projectId}/texts/{textId}/analyses/{analysisId}")
public class PartOfSpeechResource {

    private final Logger log = LoggerFactory.getLogger(PartOfSpeechResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartOfSpeechService partOfSpeechService;

    public PartOfSpeechResource(PartOfSpeechService partOfSpeechService) {
        this.partOfSpeechService = partOfSpeechService;
    }

    /**
     * {@code POST  /part-of-speeches} : Create a new partOfSpeech.
     *
     * @param projectId   ID of the project.
     * @param textId      ID of the text analyzed.
     * @param analysisId  ID of the analysis.
     * @param partOfSpeechDTO the partOfSpeechDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partOfSpeechDTO, or with status {@code 400 (Bad Request)} if the partOfSpeech has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/part-of-speeches")
    public ResponseEntity<PartOfSpeechDTO> createPartOfSpeech(
        @PathVariable("projectId") Long projectId,
        @PathVariable("textId") Long textId,
        @PathVariable("analysisId") String analysisId,
        @Valid @RequestBody PartOfSpeechDTO partOfSpeechDTO
    ) throws URISyntaxException {
        log.debug("REST request to save part-of-speech {} from analysis {} in text {} of project {}", partOfSpeechDTO, analysisId, textId, projectId);
        if (partOfSpeechDTO.getId() != null) {
            throw new BadRequestAlertException("A new partOfSpeech cannot already have an ID", EntityNames.PART_OF_SPEECH, "idexists");
        }
        PartOfSpeechDTO result = partOfSpeechService.save(projectId, textId, analysisId, partOfSpeechDTO);
        return ResponseEntity.created(new URI("/api/projects/" + projectId + "/texts/" + textId + "/analyses/" + analysisId + "/part-of-speeches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, EntityNames.PART_OF_SPEECH, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /part-of-speeches} : Updates an existing partOfSpeech.
     *
     * @param projectId   ID of the project.
     * @param textId      ID of the text analyzed.
     * @param analysisId  ID of the analysis.
     * @param partOfSpeechDTO the partOfSpeechDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partOfSpeechDTO,
     * or with status {@code 400 (Bad Request)} if the partOfSpeechDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partOfSpeechDTO couldn't be updated.
     */
    @PutMapping("/part-of-speeches")
    public ResponseEntity<PartOfSpeechDTO> updatePartOfSpeech(
        @PathVariable("projectId") Long projectId,
        @PathVariable("textId") Long textId,
        @PathVariable("analysisId") String analysisId,
        @Valid @RequestBody PartOfSpeechDTO partOfSpeechDTO
    ) {
        log.debug("REST request to update part-of-speech {} from analysis {} in text {} of project {}", partOfSpeechDTO, analysisId, textId, projectId);
        if (partOfSpeechDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", EntityNames.PART_OF_SPEECH, "idnull");
        }
        PartOfSpeechDTO result = partOfSpeechService.save(projectId, textId, analysisId, partOfSpeechDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, EntityNames.PART_OF_SPEECH, partOfSpeechDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /part-of-speeches} : get all the partOfSpeeches.
     *
     * @param projectId   ID of the project.
     * @param textId      ID of the text analyzed.
     * @param analysisId  ID of the analysis.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partOfSpeeches in body.
     */
    @GetMapping("/part-of-speeches")
    public List<PartOfSpeechDTO> getAllPartOfSpeeches(
        @PathVariable("projectId") Long projectId,
        @PathVariable("textId") Long textId,
        @PathVariable("analysisId") String analysisId
    ) {
        log.debug("REST request to get all part-of-speeches from analysis {} in text {} of project {}", analysisId, textId, projectId);
        return partOfSpeechService.findAll(projectId, textId, analysisId);
    }

    /**
     * {@code GET  /part-of-speeches/:id} : get the "id" part-of-speech.
     *
     * @param projectId   ID of the project.
     * @param textId      ID of the text analyzed.
     * @param analysisId  ID of the analysis.
     * @param id the id of the partOfSpeechDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partOfSpeechDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/part-of-speeches/{id}")
    public ResponseEntity<PartOfSpeechDTO> getPartOfSpeech(
        @PathVariable("projectId") Long projectId,
        @PathVariable("textId") Long textId,
        @PathVariable("analysisId") String analysisId,
        @PathVariable String id
    ) {
        log.debug("REST request to get part-of-speech {} from analysis {} in text {} of project {}", id, analysisId, textId, projectId);
        Optional<PartOfSpeechDTO> partOfSpeechDTO = partOfSpeechService.findOne(projectId, textId, analysisId, id);
        return ResponseUtil.wrapOrNotFound(partOfSpeechDTO);
    }

    /**
     * {@code DELETE  /part-of-speeches/:id} : delete the "id" partOfSpeech.
     *
     * @param projectId   ID of the project.
     * @param textId      ID of the text analyzed.
     * @param analysisId  ID of the analysis.
     * @param id the id of the partOfSpeechDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/part-of-speeches/{id}")
    public ResponseEntity<Void> deletePartOfSpeech(
        @PathVariable("projectId") Long projectId,
        @PathVariable("textId") Long textId,
        @PathVariable("analysisId") String analysisId,
        @PathVariable String id
    ) {
        log.debug("REST request to delete part-of-speech {} from analysis {} in text {} of project {}", id, analysisId, textId, projectId);
        partOfSpeechService.delete(projectId, textId, analysisId, id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, EntityNames.PART_OF_SPEECH, id))
            .build();
    }
}
