package pt.up.hs.linguistics.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pt.up.hs.linguini.Linguini;
import pt.up.hs.linguini.analysis.cooccurrence.CoOccurrence;
import pt.up.hs.linguini.exceptions.LinguiniException;
import pt.up.hs.linguini.models.LinguisticsReport;
import pt.up.hs.linguistics.client.sampling.SamplingFeignClient;
import pt.up.hs.linguistics.client.sampling.dto.Text;
import pt.up.hs.linguistics.constants.EntityNames;
import pt.up.hs.linguistics.constants.ErrorKeys;
import pt.up.hs.linguistics.domain.Analysis;
import pt.up.hs.linguistics.domain.Emotion;
import pt.up.hs.linguistics.domain.PartOfSpeech;
import pt.up.hs.linguistics.domain.enumeration.*;
import pt.up.hs.linguistics.repository.AnalysisRepository;
import pt.up.hs.linguistics.repository.EmotionRepository;
import pt.up.hs.linguistics.repository.PartOfSpeechRepository;
import pt.up.hs.linguistics.service.AnalysisService;
import pt.up.hs.linguistics.service.dto.AnalysisDTO;
import pt.up.hs.linguistics.service.exceptions.ServiceException;
import pt.up.hs.linguistics.service.mapper.AnalysisMapper;
import pt.up.hs.linguistics.utils.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Analysis}.
 */
@Service
public class AnalysisServiceImpl implements AnalysisService {

    private final Logger log = LoggerFactory.getLogger(AnalysisServiceImpl.class);

    private final AnalysisRepository analysisRepository;
    private final AnalysisMapper analysisMapper;

    private final EmotionRepository emotionRepository;
    private final PartOfSpeechRepository partOfSpeechRepository;

    private final SamplingFeignClient samplingFeignClient;

    public AnalysisServiceImpl(
        AnalysisRepository analysisRepository,
        AnalysisMapper analysisMapper,

        EmotionRepository emotionRepository,
        PartOfSpeechRepository partOfSpeechRepository,

        SamplingFeignClient samplingFeignClient
    ) {
        this.analysisRepository = analysisRepository;
        this.analysisMapper = analysisMapper;
        this.emotionRepository = emotionRepository;
        this.partOfSpeechRepository = partOfSpeechRepository;
        this.samplingFeignClient = samplingFeignClient;
    }

    /**
     * Perform linguistic analysis and save its result merged with
     * provided {@link AnalysisDTO} entity.
     *
     * @param projectId   ID of the project to which the analysis belongs.
     * @param textId      ID of the text to which the analysis belongs.
     * @param analysisDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AnalysisDTO analyze(
        Long projectId,
        Long textId,
        AnalysisDTO analysisDTO
    ) {
        log.debug("Request to conduct analysis {} in text {} of project {}", analysisDTO, textId, projectId);

        deleteOldAnalyses(projectId, textId, analysisDTO.getId(), true);

        Text text = samplingFeignClient.getText(projectId, textId);

        Analysis analysis = analysisMapper.toEntity(analysisDTO);
        analysis.setProjectId(projectId);
        analysis.setTextId(textId);

        Locale locale = text.getLanguage() != null
            ? StringUtils.parseLocaleString(text.getLanguage())
            : Locale.getDefault();
        if (locale == null) {
            locale = new Locale("en");
        }

        Linguini linguini = new Linguini(locale);

        try {
            LinguisticsReport report = linguini.analyze(text.getText());
            analysis
                .status(Status.COMPLETED)
                .language(locale.toString())
                .characterCount(report.getCharacterCount())
                .nonBlankCharacterCount(report.getNonBlankCharacterCount())

                .wordCount(report.getWordCount())
                .distinctWordCount(report.getDistinctWordCount())
                .contentWordCount(report.getContentWordCount())
                .distinctContentWordCount(report.getDistinctContentWordCount())
                .functionalWordCount(report.getFunctionalWordCount())
                .distinctFunctionalWordCount(report.getDistinctFunctionalWordCount())
                .distinctLemmaCount(report.getDistinctLemmaCount())

                .wordAvgLength(report.getWordAvgLength())
                .contentWordAvgLength(report.getContentWordAvgLength())
                .functionalWordAvgLength(report.getFunctionalWordAvgLength())
                .sentenceCount(report.getSentenceCount())

                .lexicalDensity(report.getLexicalDensity())

                .baseTTR(report.getBaseTTR())
                .hdd(report.getHdd())
                .mtld(report.getMtld())
                .vocd(report.getVocd())

                .wordsByCategory(report.getWordsByCategory().entrySet()
                    .parallelStream()
                    .collect(Collectors.toMap(e -> PoSTag.valueOf(e.getKey()), Map.Entry::getValue))
                )
                .contentWordFrequencies(report.getContentWordFrequency())
                .functionalWordFrequencies(report.getFunctionalWordFrequency())
                .lemmaFrequencies(report.getLemmaFrequency())

                .coOccurrences(report.getCoOccurrences().parallelStream()
                    .collect(Collectors.toMap(
                        c -> c.getFirstWord() + "--" + c.getSecondWord(),
                        CoOccurrence::getValue
                    )));

            List<Emotion> emotions = report.getEmotionalAnnotations()
                .parallelStream()
                .map(annot -> new Emotion()
                    .start(annot.getToken().getStart())
                    .size(annot.original().length())
                    .primary(PrimaryEmotion.valueOf(annot.getInfo().getGlobal().toString()))
                    .secondary(SecondaryEmotion.valueOf(annot.getInfo().getIntermediate().toString()))
                    .tertiary(TertiaryEmotion.valueOf(annot.getInfo().getSpecific().toString())))
                .collect(Collectors.toList());

            emotionRepository.saveAll(emotions);
            analysis.emotions(new HashSet<>(emotions));

            List<PartOfSpeech> partsOfSpeech = report.getMorphologicalAnnotations()
                .parallelStream()
                .map(annot ->  new PartOfSpeech()
                    .start(annot.getToken().getStart())
                    .size(annot.getToken().size())
                    .tag(PoSTag.valueOf(annot.getInfo().toUpperCase()))
                    .lemma(annot.word()))
                .collect(Collectors.toList());

            partOfSpeechRepository.saveAll(partsOfSpeech);
            analysis.partsOfSpeech(new HashSet<>(partsOfSpeech));

        } catch (LinguiniException e) {
            throw new ServiceException(
                EntityNames.ANALYSIS,
                ErrorKeys.ERR_ANALYSIS,
                "Linguistic analysis failed. Cause: " + e.getMessage()
            );
        }

        analysis = analysisRepository.save(analysis);
        return analysisMapper.toDto(analysis);
    }

    /**
     * Save a analysis.
     *
     * @param projectId   ID of the project to which the analysis belongs.
     * @param textId      ID of the text to which the analysis belongs.
     * @param analysisDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AnalysisDTO save(Long projectId, Long textId, AnalysisDTO analysisDTO) {
        log.debug("Request to save analysis {} of text {} in project {}", analysisDTO, textId, projectId);
        Analysis analysis = analysisMapper.toEntity(analysisDTO);
        analysis.setProjectId(projectId);
        analysis.setTextId(textId);
        deleteOldAnalyses(projectId, textId, analysisDTO.getId(), false);
        analysis
            .partsOfSpeech(new HashSet<>(partOfSpeechRepository.saveAll(analysis.getPartsOfSpeech())));
        analysis
            .emotions(new HashSet<>(emotionRepository.saveAll(analysis.getEmotions())));
        analysis = analysisRepository.save(analysis);
        return analysisMapper.toDto(analysis);
    }

    /**
     * Get all the analyses.
     *
     * @param projectId ID of the project to which the analysis belongs.
     * @param textId    ID of the text to which the analysis belongs.
     * @return the list of entities.
     */
    @Override
    public List<AnalysisDTO> findAll(Long projectId, Long textId) {
        log.debug("Request to get all analyses of text {} in project {}", textId, projectId);
        return analysisRepository.findByProjectIdAndTextId(projectId, textId).stream()
            .map(analysisMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one analysis by id.
     *
     * @param projectId ID of the project to which the analysis belongs.
     * @param textId    ID of the text to which the analysis belongs.
     * @param id        the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<AnalysisDTO> findOne(Long projectId, Long textId, String id) {
        log.debug("Request to get analysis {} of text {} in project {}", id, textId, projectId);
        return analysisRepository
            .findByProjectIdAndTextIdAndId(projectId, textId, id)
            .map(analysisMapper::toDto);
    }

    /**
     * Delete the analysis by id.
     *
     * @param projectId ID of the project to which the analysis belongs.
     * @param textId    ID of the text to which the analysis belongs.
     * @param id        the id of the entity.
     */
    @Override
    public void delete(Long projectId, Long textId, String id) {
        log.debug("Request to delete analysis {} of text {} in project {}", id, textId, projectId);
        analysisRepository.deleteByProjectIdAndTextIdAndId(projectId, textId, id);
        deleteLinkedData(id);
    }

    private void deleteOldAnalyses(
        Long projectId, Long textId,
        String analysisId, boolean currentLinkedData
    ) {
        List<AnalysisDTO> oldAnalyses = findAll(projectId, textId);
        if (!oldAnalyses.isEmpty()) {
            oldAnalyses.forEach(oldAnalysis -> {
                if (!Objects.equals(oldAnalysis.getId(), analysisId)) {
                    analysisRepository.delete(analysisMapper.toEntity(oldAnalysis));
                } else if (currentLinkedData) {
                    deleteLinkedData(oldAnalysis.getId());
                }
            });
        }
    }

    private void deleteLinkedData(String analysisId) {
        emotionRepository.deleteByAnalysisId(analysisId);
        partOfSpeechRepository.deleteByAnalysisId(analysisId);
    }
}
