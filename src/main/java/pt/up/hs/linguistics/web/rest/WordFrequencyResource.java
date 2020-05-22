package pt.up.hs.linguistics.web.rest;

import pt.up.hs.linguistics.service.WordFrequencyService;
import pt.up.hs.linguistics.web.rest.errors.BadRequestAlertException;
import pt.up.hs.linguistics.service.dto.WordFrequencyDTO;

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
 * REST controller for managing {@link pt.up.hs.linguistics.domain.WordFrequency}.
 */
@RestController
@RequestMapping("/api")
public class WordFrequencyResource {

    private final Logger log = LoggerFactory.getLogger(WordFrequencyResource.class);

    private static final String ENTITY_NAME = "linguisticsWordFrequency";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WordFrequencyService wordFrequencyService;

    public WordFrequencyResource(WordFrequencyService wordFrequencyService) {
        this.wordFrequencyService = wordFrequencyService;
    }

    /**
     * {@code POST  /word-frequencies} : Create a new wordFrequency.
     *
     * @param wordFrequencyDTO the wordFrequencyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new wordFrequencyDTO, or with status {@code 400 (Bad Request)} if the wordFrequency has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/word-frequencies")
    public ResponseEntity<WordFrequencyDTO> createWordFrequency(@Valid @RequestBody WordFrequencyDTO wordFrequencyDTO) throws URISyntaxException {
        log.debug("REST request to save WordFrequency : {}", wordFrequencyDTO);
        if (wordFrequencyDTO.getId() != null) {
            throw new BadRequestAlertException("A new wordFrequency cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WordFrequencyDTO result = wordFrequencyService.save(wordFrequencyDTO);
        return ResponseEntity.created(new URI("/api/word-frequencies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /word-frequencies} : Updates an existing wordFrequency.
     *
     * @param wordFrequencyDTO the wordFrequencyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated wordFrequencyDTO,
     * or with status {@code 400 (Bad Request)} if the wordFrequencyDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the wordFrequencyDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/word-frequencies")
    public ResponseEntity<WordFrequencyDTO> updateWordFrequency(@Valid @RequestBody WordFrequencyDTO wordFrequencyDTO) throws URISyntaxException {
        log.debug("REST request to update WordFrequency : {}", wordFrequencyDTO);
        if (wordFrequencyDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WordFrequencyDTO result = wordFrequencyService.save(wordFrequencyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, wordFrequencyDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /word-frequencies} : get all the wordFrequencies.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wordFrequencies in body.
     */
    @GetMapping("/word-frequencies")
    public List<WordFrequencyDTO> getAllWordFrequencies() {
        log.debug("REST request to get all WordFrequencies");
        return wordFrequencyService.findAll();
    }

    /**
     * {@code GET  /word-frequencies/:id} : get the "id" wordFrequency.
     *
     * @param id the id of the wordFrequencyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the wordFrequencyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/word-frequencies/{id}")
    public ResponseEntity<WordFrequencyDTO> getWordFrequency(@PathVariable String id) {
        log.debug("REST request to get WordFrequency : {}", id);
        Optional<WordFrequencyDTO> wordFrequencyDTO = wordFrequencyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(wordFrequencyDTO);
    }

    /**
     * {@code DELETE  /word-frequencies/:id} : delete the "id" wordFrequency.
     *
     * @param id the id of the wordFrequencyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/word-frequencies/{id}")
    public ResponseEntity<Void> deleteWordFrequency(@PathVariable String id) {
        log.debug("REST request to delete WordFrequency : {}", id);

        wordFrequencyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
