package pt.up.hs.linguistics.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.up.hs.linguistics.constants.EntityNames;
import pt.up.hs.linguistics.service.SentenceService;
import pt.up.hs.linguistics.service.dto.SentenceDTO;
import pt.up.hs.linguistics.web.rest.errors.BadRequestAlertException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link pt.up.hs.linguistics.domain.Sentence}.
 */
@RestController
@RequestMapping("/api/projects/{projectId}/texts/{textId}/analyses/{analysisId}")
public class SentenceResource {

    private final Logger log = LoggerFactory.getLogger(SentenceResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SentenceService sentenceService;

    public SentenceResource(SentenceService sentenceService) {
        this.sentenceService = sentenceService;
    }

    /**
     * {@code POST  /sentences} : Create a new Sentence.
     *
     * @param projectId   ID of the project.
     * @param textId      ID of the text analyzed.
     * @param analysisId  ID of the analysis.
     * @param SentenceDTO the SentenceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new SentenceDTO, or with status {@code 400 (Bad Request)} if the Sentence has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sentences")
    public ResponseEntity<SentenceDTO> createSentence(
        @PathVariable("projectId") Long projectId,
        @PathVariable("textId") Long textId,
        @PathVariable("analysisId") String analysisId,
        @Valid @RequestBody SentenceDTO SentenceDTO
    ) throws URISyntaxException {
        log.debug("REST request to save Sentence {} from analysis {} in text {} of project {}", SentenceDTO, analysisId, textId, projectId);
        if (SentenceDTO.getId() != null) {
            throw new BadRequestAlertException("A new sentence cannot already have an ID", EntityNames.SENTENCE, "idexists");
        }
        SentenceDTO result = sentenceService.save(projectId, textId, analysisId, SentenceDTO);
        return ResponseEntity.created(new URI("/api/projects/" + projectId + "/texts/" + textId + "/analyses/" + analysisId + "/Sentences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, EntityNames.SENTENCE, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /sentences} : Updates an existing Sentence.
     *
     * @param projectId   ID of the project.
     * @param textId      ID of the text analyzed.
     * @param analysisId  ID of the analysis.
     * @param SentenceDTO the SentenceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated SentenceDTO,
     * or with status {@code 400 (Bad Request)} if the SentenceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the SentenceDTO couldn't be updated.
     */
    @PutMapping("/sentences")
    public ResponseEntity<SentenceDTO> updateSentence(
        @PathVariable("projectId") Long projectId,
        @PathVariable("textId") Long textId,
        @PathVariable("analysisId") String analysisId,
        @Valid @RequestBody SentenceDTO SentenceDTO
    ) {
        log.debug("REST request to update Sentence {} from analysis {} in text {} of project {}", SentenceDTO, analysisId, textId, projectId);
        if (SentenceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", EntityNames.SENTENCE, "idnull");
        }
        SentenceDTO result = sentenceService.save(projectId, textId, analysisId, SentenceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, EntityNames.SENTENCE, SentenceDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /sentences} : get all the Sentences.
     *
     * @param projectId   ID of the project.
     * @param textId      ID of the text analyzed.
     * @param analysisId  ID of the analysis.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Sentences in body.
     */
    @GetMapping("/sentences")
    public List<SentenceDTO> getAllSentences(
        @PathVariable("projectId") Long projectId,
        @PathVariable("textId") Long textId,
        @PathVariable("analysisId") String analysisId
    ) {
        log.debug("REST request to get all Sentences from analysis {} in text {} of project {}", analysisId, textId, projectId);
        return sentenceService.findAll(projectId, textId, analysisId);
    }

    /**
     * {@code GET  /sentences/:id} : get the "id" Sentence.
     *
     * @param projectId   ID of the project.
     * @param textId      ID of the text analyzed.
     * @param analysisId  ID of the analysis.
     * @param id the id of the SentenceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the SentenceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sentences/{id}")
    public ResponseEntity<SentenceDTO> getSentence(
        @PathVariable("projectId") Long projectId,
        @PathVariable("textId") Long textId,
        @PathVariable("analysisId") String analysisId,
        @PathVariable String id
    ) {
        log.debug("REST request to get Sentence {} from analysis {} in text {} of project {}", id, analysisId, textId, projectId);
        Optional<SentenceDTO> SentenceDTO = sentenceService.findOne(projectId, textId, analysisId, id);
        return ResponseUtil.wrapOrNotFound(SentenceDTO);
    }

    /**
     * {@code DELETE  /sentences/:id} : delete the "id" Sentence.
     *
     * @param projectId   ID of the project.
     * @param textId      ID of the text analyzed.
     * @param analysisId  ID of the analysis.
     * @param id the id of the SentenceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sentences/{id}")
    public ResponseEntity<Void> deleteSentence(
        @PathVariable("projectId") Long projectId,
        @PathVariable("textId") Long textId,
        @PathVariable("analysisId") String analysisId,
        @PathVariable String id
    ) {
        log.debug("REST request to delete Sentence {} from analysis {} in text {} of project {}", id, analysisId, textId, projectId);
        sentenceService.delete(projectId, textId, analysisId, id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, EntityNames.SENTENCE, id))
            .build();
    }
}
