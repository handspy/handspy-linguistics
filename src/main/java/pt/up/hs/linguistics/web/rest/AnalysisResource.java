package pt.up.hs.linguistics.web.rest;

import pt.up.hs.linguistics.constants.EntityNames;
import pt.up.hs.linguistics.service.AnalysisService;
import pt.up.hs.linguistics.web.rest.errors.BadRequestAlertException;
import pt.up.hs.linguistics.service.dto.AnalysisDTO;

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
 * REST controller for managing {@link pt.up.hs.linguistics.domain.Analysis}.
 */
@RestController
@RequestMapping("/api/projects/{projectId}/texts/{textId}")
public class AnalysisResource {

    private final Logger log = LoggerFactory.getLogger(AnalysisResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnalysisService analysisService;

    public AnalysisResource(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    /**
     * {@code POST  /analyses} : Create a new analysis.
     *
     * @param projectId   ID of the project.
     * @param textId      ID of the text analyzed.
     * @param analysisDTO the analysisDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new analysisDTO, or with status {@code 400 (Bad Request)} if the analysis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/analyses")
    public ResponseEntity<AnalysisDTO> createAnalysis(
        @PathVariable("projectId") Long projectId,
        @PathVariable("textId") Long textId,
        @RequestBody AnalysisDTO analysisDTO,
        @RequestParam(name = "analyze", required = false, defaultValue = "true") boolean analyze
    ) throws URISyntaxException {
        log.debug("REST request to save analysis {} of project {} of text {}", analysisDTO, projectId, textId);
        if (analysisDTO.getId() != null) {
            throw new BadRequestAlertException("A new analysis cannot already have an ID", EntityNames.ANALYSIS, "idexists");
        }
        AnalysisDTO result;
        if (analyze) {
            result = analysisService.analyze(projectId, textId, analysisDTO);
        } else {
            result = analysisService.save(projectId, textId, analysisDTO);
        }
        return ResponseEntity.created(new URI("/api/projects/" + projectId + "/texts/" + textId + "/analyses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, EntityNames.ANALYSIS, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /analyses} : Updates an existing analysis.
     *
     * @param projectId   ID of the project.
     * @param textId      ID of the text analyzed.
     * @param analysisDTO the analysisDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated analysisDTO,
     * or with status {@code 400 (Bad Request)} if the analysisDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the analysisDTO couldn't be updated.
     */
    @PutMapping("/analyses")
    public ResponseEntity<AnalysisDTO> updateAnalysis(
        @PathVariable("projectId") Long projectId,
        @PathVariable("textId") Long textId,
        @RequestParam(name = "analyze", required = false, defaultValue = "false") boolean analyze,
        @Valid @RequestBody AnalysisDTO analysisDTO
    ) {
        log.debug("REST request to update analysis {} of project {} of text {}", analysisDTO, projectId, textId);
        if (analysisDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", EntityNames.ANALYSIS, "idnull");
        }
        AnalysisDTO result;
        if (analyze) {
            result = analysisService.analyze(projectId, textId, analysisDTO);
        } else {
            result = analysisService.save(projectId, textId, analysisDTO);
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, EntityNames.ANALYSIS, analysisDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /analyses} : get all the analyses.
     *
     * @param projectId ID of the project.
     * @param textId    ID of the text analyzed.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of analyses in body.
     */
    @GetMapping("/analyses")
    public List<AnalysisDTO> getAllAnalyses(
        @PathVariable("projectId") Long projectId,
        @PathVariable("textId") Long textId
    ) {
        log.debug("REST request to get all analyses of text {} of project {}", textId, projectId);
        return analysisService.findAll(projectId, textId);
    }

    /**
     * {@code GET  /analyses/:id} : get the "id" analysis.
     *
     * @param projectId ID of the project.
     * @param textId    ID of the text analyzed.
     * @param id        the ID of the analysisDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the analysisDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/analyses/{id}")
    public ResponseEntity<AnalysisDTO> getAnalysis(
        @PathVariable("projectId") Long projectId,
        @PathVariable("textId") Long textId,
        @PathVariable String id
    ) {
        log.debug("REST request to get analysis {} of text {} of project {}", id, textId, projectId);
        Optional<AnalysisDTO> analysisDTO = analysisService.findOne(projectId, textId, id);
        return ResponseUtil.wrapOrNotFound(analysisDTO);
    }

    /**
     * {@code DELETE  /analyses/:id} : delete the "id" analysis.
     *
     * @param projectId ID of the project.
     * @param textId    ID of the text analyzed.
     * @param id        the id of the analysisDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/analyses/{id}")
    public ResponseEntity<Void> deleteAnalysis(
        @PathVariable("projectId") Long projectId,
        @PathVariable("textId") Long textId,
        @PathVariable String id
    ) {
        log.debug("REST request to delete analysis {} of text {} of project {}", id, textId, projectId);
        analysisService.delete(projectId, textId, id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, EntityNames.ANALYSIS, id))
            .build();
    }
}
