package pt.up.hs.linguistics.web.rest;

import pt.up.hs.linguistics.service.LemmaFrequencyService;
import pt.up.hs.linguistics.web.rest.errors.BadRequestAlertException;
import pt.up.hs.linguistics.service.dto.LemmaFrequencyDTO;

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
 * REST controller for managing {@link pt.up.hs.linguistics.domain.LemmaFrequency}.
 */
@RestController
@RequestMapping("/api")
public class LemmaFrequencyResource {

    private final Logger log = LoggerFactory.getLogger(LemmaFrequencyResource.class);

    private static final String ENTITY_NAME = "linguisticsLemmaFrequency";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LemmaFrequencyService lemmaFrequencyService;

    public LemmaFrequencyResource(LemmaFrequencyService lemmaFrequencyService) {
        this.lemmaFrequencyService = lemmaFrequencyService;
    }

    /**
     * {@code POST  /lemma-frequencies} : Create a new lemmaFrequency.
     *
     * @param lemmaFrequencyDTO the lemmaFrequencyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lemmaFrequencyDTO, or with status {@code 400 (Bad Request)} if the lemmaFrequency has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lemma-frequencies")
    public ResponseEntity<LemmaFrequencyDTO> createLemmaFrequency(@Valid @RequestBody LemmaFrequencyDTO lemmaFrequencyDTO) throws URISyntaxException {
        log.debug("REST request to save LemmaFrequency : {}", lemmaFrequencyDTO);
        if (lemmaFrequencyDTO.getId() != null) {
            throw new BadRequestAlertException("A new lemmaFrequency cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LemmaFrequencyDTO result = lemmaFrequencyService.save(lemmaFrequencyDTO);
        return ResponseEntity.created(new URI("/api/lemma-frequencies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /lemma-frequencies} : Updates an existing lemmaFrequency.
     *
     * @param lemmaFrequencyDTO the lemmaFrequencyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lemmaFrequencyDTO,
     * or with status {@code 400 (Bad Request)} if the lemmaFrequencyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lemmaFrequencyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lemma-frequencies")
    public ResponseEntity<LemmaFrequencyDTO> updateLemmaFrequency(@Valid @RequestBody LemmaFrequencyDTO lemmaFrequencyDTO) throws URISyntaxException {
        log.debug("REST request to update LemmaFrequency : {}", lemmaFrequencyDTO);
        if (lemmaFrequencyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LemmaFrequencyDTO result = lemmaFrequencyService.save(lemmaFrequencyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lemmaFrequencyDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /lemma-frequencies} : get all the lemmaFrequencies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lemmaFrequencies in body.
     */
    @GetMapping("/lemma-frequencies")
    public List<LemmaFrequencyDTO> getAllLemmaFrequencies() {
        log.debug("REST request to get all LemmaFrequencies");
        return lemmaFrequencyService.findAll();
    }

    /**
     * {@code GET  /lemma-frequencies/:id} : get the "id" lemmaFrequency.
     *
     * @param id the id of the lemmaFrequencyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lemmaFrequencyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lemma-frequencies/{id}")
    public ResponseEntity<LemmaFrequencyDTO> getLemmaFrequency(@PathVariable String id) {
        log.debug("REST request to get LemmaFrequency : {}", id);
        Optional<LemmaFrequencyDTO> lemmaFrequencyDTO = lemmaFrequencyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lemmaFrequencyDTO);
    }

    /**
     * {@code DELETE  /lemma-frequencies/:id} : delete the "id" lemmaFrequency.
     *
     * @param id the id of the lemmaFrequencyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lemma-frequencies/{id}")
    public ResponseEntity<Void> deleteLemmaFrequency(@PathVariable String id) {
        log.debug("REST request to delete LemmaFrequency : {}", id);

        lemmaFrequencyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
