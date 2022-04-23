package pt.up.hs.linguistics.service.dto;

import io.swagger.annotations.ApiModel;
import pt.up.hs.linguistics.domain.enumeration.PoSTag;
import pt.up.hs.linguistics.domain.enumeration.Status;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A DTO for the {@link pt.up.hs.linguistics.domain.Analysis} entity.
 *
 * @author José Carlos Paiva
 */
@ApiModel(description = "Linguistic analysis conducted in a text.")
public class AnalysisDTO extends AbstractAuditingDTO {

    private String id;
    private Long projectId;
    private Long textId;

    private String language;
    private Status status;

    private Integer characterCount;
    private Integer nonBlankCharacterCount;

    private Integer wordCount;
    private Integer distinctWordCount;
    private Integer functionalWordCount;
    private Integer distinctFunctionalWordCount;
    private Integer contentWordCount;
    private Integer distinctContentWordCount;

    private Integer distinctLemmaCount;

    private Double wordAvgLength;
    private Double functionalWordAvgLength;
    private Double contentWordAvgLength;

    private Integer sentenceCount;

    private Double lexicalDensity;

    private Double baseTTR;
    private Double hdd;
    private Double mtld;
    private Double vocd;

    private Map<PoSTag, Set<String>> wordsByCategory;
    private Map<String, Integer> contentWordFrequencies;
    private Map<String, Integer> functionalWordFrequencies;
    private Map<String, Integer> lemmaFrequencies;

    private Map<String, Double> coOccurrences;

    private Double ideaDensity;

    private Set<EmotionDTO> emotions = new HashSet<>();

    private Set<PartOfSpeechDTO> partsOfSpeech = new HashSet<>();

    private Set<SentenceDTO> sentenceSummaries = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getTextId() {
        return textId;
    }

    public void setTextId(Long textId) {
        this.textId = textId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getCharacterCount() {
        return characterCount;
    }

    public void setCharacterCount(Integer characterCount) {
        this.characterCount = characterCount;
    }

    public Integer getNonBlankCharacterCount() {
        return nonBlankCharacterCount;
    }

    public void setNonBlankCharacterCount(Integer nonBlankCharacterCount) {
        this.nonBlankCharacterCount = nonBlankCharacterCount;
    }

    public Integer getWordCount() {
        return wordCount;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }

    public Integer getDistinctWordCount() {
        return distinctWordCount;
    }

    public void setDistinctWordCount(Integer distinctWordCount) {
        this.distinctWordCount = distinctWordCount;
    }

    public Integer getFunctionalWordCount() {
        return functionalWordCount;
    }

    public void setFunctionalWordCount(Integer functionalWordCount) {
        this.functionalWordCount = functionalWordCount;
    }

    public Integer getDistinctFunctionalWordCount() {
        return distinctFunctionalWordCount;
    }

    public void setDistinctFunctionalWordCount(Integer distinctFunctionalWordCount) {
        this.distinctFunctionalWordCount = distinctFunctionalWordCount;
    }

    public Integer getContentWordCount() {
        return contentWordCount;
    }

    public void setContentWordCount(Integer contentWordCount) {
        this.contentWordCount = contentWordCount;
    }

    public Integer getDistinctContentWordCount() {
        return distinctContentWordCount;
    }

    public void setDistinctContentWordCount(Integer distinctContentWordCount) {
        this.distinctContentWordCount = distinctContentWordCount;
    }

    public Integer getDistinctLemmaCount() {
        return distinctLemmaCount;
    }

    public void setDistinctLemmaCount(Integer distinctLemmaCount) {
        this.distinctLemmaCount = distinctLemmaCount;
    }

    public Double getWordAvgLength() {
        return wordAvgLength;
    }

    public void setWordAvgLength(Double wordAvgLength) {
        this.wordAvgLength = wordAvgLength;
    }

    public Double getFunctionalWordAvgLength() {
        return functionalWordAvgLength;
    }

    public void setFunctionalWordAvgLength(Double functionalWordAvgLength) {
        this.functionalWordAvgLength = functionalWordAvgLength;
    }

    public Double getContentWordAvgLength() {
        return contentWordAvgLength;
    }

    public void setContentWordAvgLength(Double contentWordAvgLength) {
        this.contentWordAvgLength = contentWordAvgLength;
    }

    public Integer getSentenceCount() {
        return sentenceCount;
    }

    public void setSentenceCount(Integer sentenceCount) {
        this.sentenceCount = sentenceCount;
    }

    public Double getLexicalDensity() {
        return lexicalDensity;
    }

    public void setLexicalDensity(Double lexicalDensity) {
        this.lexicalDensity = lexicalDensity;
    }

    public Double getBaseTTR() {
        return baseTTR;
    }

    public void setBaseTTR(Double baseTTR) {
        this.baseTTR = baseTTR;
    }

    public Double getHdd() {
        return hdd;
    }

    public void setHdd(Double hdd) {
        this.hdd = hdd;
    }

    public Double getMtld() {
        return mtld;
    }

    public void setMtld(Double mtld) {
        this.mtld = mtld;
    }

    public Double getVocd() {
        return vocd;
    }

    public void setVocd(Double vocd) {
        this.vocd = vocd;
    }

    public Map<PoSTag, Set<String>> getWordsByCategory() {
        return wordsByCategory;
    }

    public void setWordsByCategory(Map<PoSTag, Set<String>> wordsByCategory) {
        this.wordsByCategory = wordsByCategory;
    }

    public Map<String, Integer> getContentWordFrequencies() {
        return contentWordFrequencies;
    }

    public void setContentWordFrequencies(Map<String, Integer> contentWordFrequencies) {
        this.contentWordFrequencies = contentWordFrequencies;
    }

    public Map<String, Integer> getFunctionalWordFrequencies() {
        return functionalWordFrequencies;
    }

    public void setFunctionalWordFrequencies(Map<String, Integer> functionalWordFrequencies) {
        this.functionalWordFrequencies = functionalWordFrequencies;
    }

    public Map<String, Integer> getLemmaFrequencies() {
        return lemmaFrequencies;
    }

    public void setLemmaFrequencies(Map<String, Integer> lemmaFrequencies) {
        this.lemmaFrequencies = lemmaFrequencies;
    }

    public Map<String, Double> getCoOccurrences() {
        return coOccurrences;
    }

    public void setCoOccurrences(Map<String, Double> coOccurrences) {
        this.coOccurrences = coOccurrences;
    }

    public Double getIdeaDensity() {
        return ideaDensity;
    }

    public void setIdeaDensity(Double ideaDensity) {
        this.ideaDensity = ideaDensity;
    }

    public Set<EmotionDTO> getEmotions() {
        return emotions;
    }

    public void setEmotions(Set<EmotionDTO> emotions) {
        this.emotions = emotions;
    }

    public Set<PartOfSpeechDTO> getPartsOfSpeech() {
        return partsOfSpeech;
    }

    public void setPartsOfSpeech(Set<PartOfSpeechDTO> partsOfSpeech) {
        this.partsOfSpeech = partsOfSpeech;
    }

    public Set<SentenceDTO> getSentenceSummaries() {
        return sentenceSummaries;
    }

    public void setSentenceSummaries(Set<SentenceDTO> sentenceSummaries) {
        this.sentenceSummaries = sentenceSummaries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnalysisDTO)) {
            return false;
        }

        return id != null && id.equals(((AnalysisDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnalysisDTO{" +
            "id='" + id + '\'' +
            ", projectId=" + projectId +
            ", textId=" + textId +
            ", language='" + language + '\'' +
            ", status=" + status +
            ", characterCount=" + characterCount +
            ", nonBlankCharacterCount=" + nonBlankCharacterCount +
            ", wordCount=" + wordCount +
            ", distinctWordCount=" + distinctWordCount +
            ", functionalWordCount=" + functionalWordCount +
            ", distinctFunctionalWordCount=" + distinctFunctionalWordCount +
            ", contentWordCount=" + contentWordCount +
            ", distinctContentWordCount=" + distinctContentWordCount +
            ", distinctLemmaCount=" + distinctLemmaCount +
            ", wordAvgLength=" + wordAvgLength +
            ", functionalWordAvgLength=" + functionalWordAvgLength +
            ", contentWordAvgLength=" + contentWordAvgLength +
            ", sentenceCount=" + sentenceCount +
            ", lexicalDensity=" + lexicalDensity +
            ", baseTTR=" + baseTTR +
            ", hdd=" + hdd +
            ", mtld=" + mtld +
            ", vocd=" + vocd +
            ", wordsByCategory=" + wordsByCategory +
            ", contentWordFrequencies=" + contentWordFrequencies +
            ", functionalWordFrequencies=" + functionalWordFrequencies +
            ", lemmaFrequencies=" + lemmaFrequencies +
            ", coOccurrences=" + coOccurrences +
            ", ideaDensity=" + ideaDensity +
            ", emotions=" + emotions +
            ", partsOfSpeech=" + partsOfSpeech +
            ", sentenceSummaries=" + sentenceSummaries +
            '}';
    }
}
