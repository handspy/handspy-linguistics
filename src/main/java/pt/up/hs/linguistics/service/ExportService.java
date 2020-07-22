package pt.up.hs.linguistics.service;

import java.io.ByteArrayInputStream;

/**
 * Service Interface for managing reports.
 *
 * @author José Carlos Paiva
 */
public interface ExportService {

    /**
     * Export an analysis of a text.
     *
     * @param projectId {@link Long} ID of the project.
     * @param textId {@link Long} ID of the text from which the analysis is to export.
     * @return {@link ByteArrayInputStream} of the created workbook.
     */
    ByteArrayInputStream export(Long projectId, Long textId);

    /**
     * Bulk export the analyses of texts.
     *
     * @param projectId {@link Long} ID of the project.
     * @param textIds {@link Long[]} ID of the texts from which the analyses are to export.
     * @return {@link ByteArrayInputStream} of the created workbook.
     */
    ByteArrayInputStream bulkExport(Long projectId, Long[] textIds);
}
