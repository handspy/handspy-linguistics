package pt.up.hs.linguistics.repository;

import org.springframework.beans.factory.annotation.Value;
import pt.up.hs.linguistics.domain.Emotion;
import pt.up.hs.linguistics.domain.PartOfSpeech;
import pt.up.hs.linguistics.domain.enumeration.PoSTag;
import pt.up.hs.linguistics.domain.enumeration.Status;

import java.time.Instant;
import java.util.Map;
import java.util.Set;

// @Projection(name = "full", types = Analysis.class)
public interface FullAnalysis {

    String getId();
    Long getProjectId();
    Long getTextId();
    String getLanguage();
    Status getStatus();
    Integer getCharacterCount();
    Integer getNonBlankCharacterCount();
    Integer getWordCount();
    Integer getDistinctWordCount();
    Integer getFunctionalWordCount();
    Integer getDistinctFunctionalWordCount();
    Integer getContentWordCount();
    Integer getDistinctContentWordCount();
    Integer getDistinctLemmaCount();
    Double getWordAvgLength();
    Double getFunctionalWordAvgLength();
    Double getContentWordAvgLength();
    Integer getSentenceCount();
    Double getLexicalDensity();
    Double getBaseTTR();
    Double getHdd();
    Double getMtld();
    Double getVocd();
    Map<PoSTag, Set<String>> getWordsByCategory();
    Map<String, Integer> getContentWordFrequencies();
    Map<String, Integer> getFunctionalWordFrequencies();
    Map<String, Integer> getLemmaFrequencies();
    Map<String, Double> getCoOccurrences();
    Double getIdeaDensity();

    String getCreatedBy();
    Instant getCreatedDate();
    String getLastModifiedBy();
    Instant getLastModifiedDate();

    @Value("#{@emotionRepository.findByAnalysisId(target.id)}")
    Set<Emotion> getEmotions();

    @Value("#{@partOfSpeechRepository.findByAnalysisId(target.id)}")
    Set<PartOfSpeech> getPartsOfSpeech();
}
