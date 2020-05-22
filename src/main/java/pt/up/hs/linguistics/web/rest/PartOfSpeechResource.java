package pt.up.hs.linguistics.web.rest;

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
@RequestMapping("/api")
public class PartOfSpeechResource {

    private final Logger log = LoggerFactory.getLogger(PartOfSpeechResource.class);

    private static final String ENTITY_NAME = "linguisticsPartOfSpeech";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PartOfSpeechService partOfSpeechService;

    public PartOfSpeechResource(PartOfSpeechService partOfSpeechService) {
        this.partOfSpeechService = partOfSpeechService;
    }

    /**
     * {@code POST  /part-of-speeches} : Create a new partOfSpeech.
     *
     * @param partOfSpeechDTO the partOfSpeechDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new partOfSpeechDTO, or with status {@code 400 (Bad Request)} if the partOfSpeech has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/part-of-speeches")
    public ResponseEntity<PartOfSpeechDTO> createPartOfSpeech(@Valid @RequestBody PartOfSpeechDTO partOfSpeechDTO) throws URISyntaxException {
        log.debug("REST request to save PartOfSpeech : {}", partOfSpeechDTO);
        if (partOfSpeechDTO.getId() != null) {
            throw new BadRequestAlertException("A new partOfSpeech cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PartOfSpeechDTO result = partOfSpeechService.save(partOfSpeechDTO);
        return ResponseEntity.created(new URI("/api/part-of-speeches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /part-of-speeches} : Updates an existing partOfSpeech.
     *
     * @param partOfSpeechDTO the partOfSpeechDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated partOfSpeechDTO,
     * or with status {@code 400 (Bad Request)} if the partOfSpeechDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the partOfSpeechDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/part-of-speeches")
    public ResponseEntity<PartOfSpeechDTO> updatePartOfSpeech(@Valid @RequestBody PartOfSpeechDTO partOfSpeechDTO) throws URISyntaxException {
        log.debug("REST request to update PartOfSpeech : {}", partOfSpeechDTO);
        if (partOfSpeechDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PartOfSpeechDTO result = partOfSpeechService.save(partOfSpeechDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, partOfSpeechDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /part-of-speeches} : get all the partOfSpeeches.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of partOfSpeeches in body.
     */
    @GetMapping("/part-of-speeches")
    public List<PartOfSpeechDTO> getAllPartOfSpeeches() {
        log.debug("REST request to get all PartOfSpeeches");
        return partOfSpeechService.findAll();
    }

    /**
     * {@code GET  /part-of-speeches/:id} : get the "id" partOfSpeech.
     *
     * @param id the id of the partOfSpeechDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the partOfSpeechDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/part-of-speeches/{id}")
    public ResponseEntity<PartOfSpeechDTO> getPartOfSpeech(@PathVariable String id) {
        log.debug("REST request to get PartOfSpeech : {}", id);
        Optional<PartOfSpeechDTO> partOfSpeechDTO = partOfSpeechService.findOne(id);
        return ResponseUtil.wrapOrNotFound(partOfSpeechDTO);
    }

    /**
     * {@code DELETE  /part-of-speeches/:id} : delete the "id" partOfSpeech.
     *
     * @param id the id of the partOfSpeechDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/part-of-speeches/{id}")
    public ResponseEntity<Void> deletePartOfSpeech(@PathVariable String id) {
        log.debug("REST request to delete PartOfSpeech : {}", id);

        partOfSpeechService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
