package pt.up.hs.linguistics.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import pt.up.hs.linguistics.client.project.ProjectFeignClient;
import pt.up.hs.linguistics.client.project.dto.Participant;
import pt.up.hs.linguistics.client.project.dto.Task;
import pt.up.hs.linguistics.client.sampling.SamplingFeignClient;
import pt.up.hs.linguistics.client.sampling.dto.Text;
import pt.up.hs.linguistics.constants.EntityNames;
import pt.up.hs.linguistics.constants.ErrorKeys;
import pt.up.hs.linguistics.reporting.LinguisticsReportBuilder;
import pt.up.hs.linguistics.repository.FullAnalysis;
import pt.up.hs.linguistics.repository.AnalysisRepository;
import pt.up.hs.linguistics.service.ExportService;
import pt.up.hs.linguistics.service.exceptions.ServiceException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * Service Implementation for managing exports.
 */
@Service
public class ExportServiceImpl implements ExportService {

    private final Logger log = LoggerFactory.getLogger(ExportServiceImpl.class);

    private final MessageSource messageSource;

    private final AnalysisRepository analysisRepository;
    private final SamplingFeignClient samplingFeignClient;
    private final ProjectFeignClient projectFeignClient;

    public ExportServiceImpl(
        MessageSource messageSource,
        AnalysisRepository analysisRepository,
        SamplingFeignClient samplingFeignClient,
        ProjectFeignClient projectFeignClient
    ) {
        this.messageSource = messageSource;
        this.analysisRepository = analysisRepository;
        this.samplingFeignClient = samplingFeignClient;
        this.projectFeignClient = projectFeignClient;
    }

    @Override
    public ByteArrayInputStream export(Long projectId, Long textId) {

        LinguisticsReportBuilder lrBuilder = new LinguisticsReportBuilder(messageSource);

        addReportToAnalysis(lrBuilder, projectId, textId);

        try {
            return lrBuilder.conclude().saveToStream();
        } catch (IOException e) {
            throw new ServiceException(
                EntityNames.ANALYSIS,
                ErrorKeys.ERR_EXPORT_EXCEL,
                "Could not export analysis to Excel."
            );
        }
    }

    @Override
    public ByteArrayInputStream bulkExport(Long projectId, Long[] textIds) {

        LinguisticsReportBuilder lrBuilder = new LinguisticsReportBuilder(messageSource);

        Arrays.stream(textIds)
            .forEach(textId -> addReportToAnalysis(lrBuilder, projectId, textId));

        try {
            return lrBuilder.conclude().saveToStream();
        } catch (IOException e) {
            throw new ServiceException(
                EntityNames.ANALYSIS,
                ErrorKeys.ERR_EXPORT_EXCEL,
                "Could not export analysis to Excel."
            );
        }
    }

    private void addReportToAnalysis(
        LinguisticsReportBuilder lrBuilder, Long projectId, Long textId
    ) {

        FullAnalysis analysis = analysisRepository
            .findFirstFullAnalysisByProjectIdAndTextId(projectId, textId)
            .orElse(null);

        if (analysis == null) {
            return;
        }

        Text text = samplingFeignClient.getText(projectId, textId);

        String code = String.format("T%d_P%d", text.getTaskId(), text.getParticipantId());

        Task task = projectFeignClient.getTask(projectId, text.getTaskId());
        String taskName = task == null ? "??" : task.getName();

        Participant participant = projectFeignClient.getParticipant(projectId, text.getParticipantId());
        String participantName = participant == null ? "??" : participant.getName();

        lrBuilder.newSummaryLine(
            code, taskName, participantName,
            analysis.getCharacterCount(),
            analysis.getNonBlankCharacterCount(),
            analysis.getWordCount(),
            analysis.getDistinctWordCount(),
            analysis.getFunctionalWordCount(),
            analysis.getDistinctFunctionalWordCount(),
            analysis.getContentWordCount(),
            analysis.getDistinctContentWordCount(),
            analysis.getDistinctLemmaCount(),
            analysis.getWordAvgLength(),
            analysis.getFunctionalWordAvgLength(),
            analysis.getContentWordAvgLength(),
            analysis.getSentenceCount(),
            analysis.getLexicalDensity(),
            analysis.getBaseTTR(),
            analysis.getHdd(),
            analysis.getMtld(),
            analysis.getVocd(),
            analysis.getIdeaDensity()
        );

        lrBuilder.newWordFrequencySheet(code, analysis.getContentWordFrequencies(), analysis.getFunctionalWordFrequencies());
        lrBuilder.newPoSTagSheet(code, analysis.getWordsByCategory());
        lrBuilder.newCoOccurrencesSheet(code, analysis.getCoOccurrences());
        lrBuilder.newEmotionalSheet(code, analysis.getEmotions(), text);
    }
}
