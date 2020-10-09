package pt.up.hs.linguistics.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.up.hs.linguistics.service.ExportService;

import java.io.ByteArrayInputStream;

/**
 * REST controller for exporting linguistic reports.
 */
@RestController
@RequestMapping("/api/projects/{projectId}/texts")
public class ExportResource {

    private final Logger log = LoggerFactory.getLogger(ExportResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExportService exportService;

    public ExportResource(ExportService exportService) {
        this.exportService = exportService;
    }

    /**
     * {@code GET  /{textId}/export} : export analysis of text ID.
     *
     * @param projectId ID of the project.
     * @param textId    ID of the text analyzed.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the excel file.
     */
    @GetMapping("/{textId}/export")
    public ResponseEntity<InputStreamResource> export(
        @PathVariable("projectId") Long projectId,
        @PathVariable("textId") Long textId
    ) {
        log.debug("REST request to export the analysis of text {} of project {}", textId, projectId);
        ByteArrayInputStream bais = exportService.export(projectId, textId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/vnd.ms-excel");
        headers.add("Content-Disposition",
            "attachment; filename=linguistics_text_" + projectId + "_" + textId + ".xls");
        return ResponseEntity.ok()
            .headers(headers)
            .body(new InputStreamResource(bais));
    }

    /**
     * {@code GET  /export} : export analyses of text IDs.
     *
     * @param projectId ID of the project.
     * @param textIds   IDs of the texts analyzed.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the excel file.
     */
    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> bulkExport(
        @PathVariable("projectId") Long projectId,
        @RequestParam(value="textId") Long[] textIds
    ) {
        log.debug("REST request to bulk export the analyses of texts {} of project {}", textIds, projectId);
        ByteArrayInputStream bais = exportService.bulkExport(projectId, textIds);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/vnd.ms-excel");
        headers.add("Content-Disposition",
            "attachment; filename=linguistics_project_" + projectId +".xls");
        return ResponseEntity.ok()
            .headers(headers)
            .body(new InputStreamResource(bais));
    }
}
