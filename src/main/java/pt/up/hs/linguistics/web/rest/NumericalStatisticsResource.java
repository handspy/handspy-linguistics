package pt.up.hs.linguistics.web.rest;

import pt.up.hs.linguistics.service.NumericalStatisticsService;
import pt.up.hs.linguistics.web.rest.errors.BadRequestAlertException;
import pt.up.hs.linguistics.service.dto.NumericalStatisticsDTO;

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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link pt.up.hs.linguistics.domain.NumericalStatistics}.
 */
@RestController
@RequestMapping("/api")
public class NumericalStatisticsResource {

    private final Logger log = LoggerFactory.getLogger(NumericalStatisticsResource.class);

    private static final String ENTITY_NAME = "linguisticsNumericalStatistics";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NumericalStatisticsService numericalStatisticsService;

    public NumericalStatisticsResource(NumericalStatisticsService numericalStatisticsService) {
        this.numericalStatisticsService = numericalStatisticsService;
    }

    /**
     * {@code POST  /numerical-statistics} : Create a new numericalStatistics.
     *
     * @param numericalStatisticsDTO the numericalStatisticsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new numericalStatisticsDTO, or with status {@code 400 (Bad Request)} if the numericalStatistics has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/numerical-statistics")
    public ResponseEntity<NumericalStatisticsDTO> createNumericalStatistics(@Valid @RequestBody NumericalStatisticsDTO numericalStatisticsDTO) throws URISyntaxException {
        log.debug("REST request to save NumericalStatistics : {}", numericalStatisticsDTO);
        if (numericalStatisticsDTO.getId() != null) {
            throw new BadRequestAlertException("A new numericalStatistics cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NumericalStatisticsDTO result = numericalStatisticsService.save(numericalStatisticsDTO);
        return ResponseEntity.created(new URI("/api/numerical-statistics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /numerical-statistics} : Updates an existing numericalStatistics.
     *
     * @param numericalStatisticsDTO the numericalStatisticsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated numericalStatisticsDTO,
     * or with status {@code 400 (Bad Request)} if the numericalStatisticsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the numericalStatisticsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/numerical-statistics")
    public ResponseEntity<NumericalStatisticsDTO> updateNumericalStatistics(@Valid @RequestBody NumericalStatisticsDTO numericalStatisticsDTO) throws URISyntaxException {
        log.debug("REST request to update NumericalStatistics : {}", numericalStatisticsDTO);
        if (numericalStatisticsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NumericalStatisticsDTO result = numericalStatisticsService.save(numericalStatisticsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, numericalStatisticsDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /numerical-statistics} : get all the numericalStatistics.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of numericalStatistics in body.
     */
    @GetMapping("/numerical-statistics")
    public List<NumericalStatisticsDTO> getAllNumericalStatistics(@RequestParam(required = false) String filter) {
        if ("analysisid-is-null".equals(filter)) {
            log.debug("REST request to get all NumericalStatisticss where analysisId is null");
            return numericalStatisticsService.findAllWhereAnalysisIdIsNull();
        }
        log.debug("REST request to get all NumericalStatistics");
        return numericalStatisticsService.findAll();
    }

    /**
     * {@code GET  /numerical-statistics/:id} : get the "id" numericalStatistics.
     *
     * @param id the id of the numericalStatisticsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the numericalStatisticsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/numerical-statistics/{id}")
    public ResponseEntity<NumericalStatisticsDTO> getNumericalStatistics(@PathVariable String id) {
        log.debug("REST request to get NumericalStatistics : {}", id);
        Optional<NumericalStatisticsDTO> numericalStatisticsDTO = numericalStatisticsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(numericalStatisticsDTO);
    }

    /**
     * {@code DELETE  /numerical-statistics/:id} : delete the "id" numericalStatistics.
     *
     * @param id the id of the numericalStatisticsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/numerical-statistics/{id}")
    public ResponseEntity<Void> deleteNumericalStatistics(@PathVariable String id) {
        log.debug("REST request to delete NumericalStatistics : {}", id);

        numericalStatisticsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
