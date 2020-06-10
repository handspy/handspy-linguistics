package pt.up.hs.linguistics.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.*;

import pt.up.hs.linguistics.domain.enumeration.Status;

/**
 * Linguistic analysis conducted in a text.
 *
 * @author José Carlos Paiva
 */
@Document(collection = "analysis")
public class Analysis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("project_id")
    private Long projectId;

    @Field("text_id")
    private Long textId;

    @Field("language")
    private String language;

    @Field("status")
    private Status status;

    @Field("character_count")
    private Integer characterCount;

    @Field("non_blank_character_count")
    private Integer nonBlankCharacterCount;

    @Field("word_count")
    private Integer wordCount;

    @Field("distinct_word_count")
    private Integer distinctWordCount;

    @Field("functional_word_count")
    private Integer functionalWordCount;

    @Field("distinct_functional_word_count")
    private Integer distinctFunctionalWordCount;

    @Field("content_word_count")
    private Integer contentWordCount;

    @Field("distinct_content_word_count")
    private Integer distinctContentWordCount;

    @Field("distinct_lemma_count")
    private Integer distinctLemmaCount;

    @Field("word_avg_length")
    private Double wordAvgLength;

    @Field("functional_word_avg_count")
    private Double functionalWordAvgLength;

    @Field("content_word_avg_count")
    private Double contentWordAvgLength;

    @Field("sentence_count")
    private Integer sentenceCount;

    @Field("lexical_density")
    private Double lexicalDensity;

    @Field("base_ttr")
    private Double baseTTR;

    @Field("hdd")
    private Double hdd;

    @Field("mtld")
    private Double mtld;

    @Field("vocd")
    private Double vocd;

    @Field("words_by_category")
    private Map<String, Set<String>> wordsByCategory;

    @Field("content_word_frequencies")
    private Map<String, Integer> contentWordFrequencies;

    @Field("functional_word_frequencies")
    private Map<String, Integer> functionalWordFrequencies;

    @Field("lemma_frequencies")
    private Map<String, Integer> lemmaFrequencies;

    @Field("cooccurrences")
    private Map<String, Double> coOccurrences;

    @DBRef
    @Field("emotion")
    private Set<Emotion> emotions = new HashSet<>();

    @DBRef
    @Field("part_of_speech")
    private Set<PartOfSpeech> partsOfSpeech = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public Analysis projectId(Long projectId) {
        this.projectId = projectId;
        return this;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getTextId() {
        return textId;
    }

    public Analysis textId(Long textId) {
        this.textId = textId;
        return this;
    }

    public void setTextId(Long textId) {
        this.textId = textId;
    }

    public String getLanguage() {
        return language;
    }

    public Analysis language(String language) {
        this.language = language;
        return this;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Status getStatus() {
        return status;
    }

    public Analysis status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getCharacterCount() {
        return characterCount;
    }

    public Analysis characterCount(Integer characterCount) {
        this.characterCount = characterCount;
        return this;
    }

    public void setCharacterCount(Integer characterCount) {
        this.characterCount = characterCount;
    }

    public Integer getNonBlankCharacterCount() {
        return nonBlankCharacterCount;
    }

    public Analysis nonBlankCharacterCount(Integer nonBlankCharacterCount) {
        this.nonBlankCharacterCount = nonBlankCharacterCount;
        return this;
    }

    public void setNonBlankCharacterCount(Integer nonBlankCharacterCount) {
        this.nonBlankCharacterCount = nonBlankCharacterCount;
    }

    public Integer getWordCount() {
        return wordCount;
    }

    public Analysis wordCount(Integer wordCount) {
        this.wordCount = wordCount;
        return this;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }

    public Integer getDistinctWordCount() {
        return distinctWordCount;
    }

    public Analysis distinctWordCount(Integer distinctWordCount) {
        this.distinctWordCount = distinctWordCount;
        return this;
    }

    public void setDistinctWordCount(Integer distinctWordCount) {
        this.distinctWordCount = distinctWordCount;
    }

    public Integer getFunctionalWordCount() {
        return functionalWordCount;
    }

    public Analysis functionalWordCount(Integer functionalWordCount) {
        this.functionalWordCount = functionalWordCount;
        return this;
    }

    public void setFunctionalWordCount(Integer functionalWordCount) {
        this.functionalWordCount = functionalWordCount;
    }

    public Integer getDistinctFunctionalWordCount() {
        return distinctFunctionalWordCount;
    }

    public Analysis distinctFunctionalWordCount(Integer distinctFunctionalWordCount) {
        this.distinctFunctionalWordCount = distinctFunctionalWordCount;
        return this;
    }

    public void setDistinctFunctionalWordCount(Integer distinctFunctionalWordCount) {
        this.distinctFunctionalWordCount = distinctFunctionalWordCount;
    }

    public Integer getContentWordCount() {
        return contentWordCount;
    }

    public Analysis contentWordCount(Integer contentWordCount) {
        this.contentWordCount = contentWordCount;
        return this;
    }

    public void setContentWordCount(Integer contentWordCount) {
        this.contentWordCount = contentWordCount;
    }

    public Integer getDistinctContentWordCount() {
        return distinctContentWordCount;
    }

    public Analysis distinctContentWordCount(Integer distinctContentWordCount) {
        this.distinctContentWordCount = distinctContentWordCount;
        return this;
    }

    public void setDistinctContentWordCount(Integer distinctContentWordCount) {
        this.distinctContentWordCount = distinctContentWordCount;
    }

    public Integer getDistinctLemmaCount() {
        return distinctLemmaCount;
    }

    public Analysis distinctLemmaCount(Integer distinctLemmaCount) {
        this.distinctLemmaCount = distinctLemmaCount;
        return this;
    }

    public void setDistinctLemmaCount(Integer distinctLemmaCount) {
        this.distinctLemmaCount = distinctLemmaCount;
    }

    public Double getWordAvgLength() {
        return wordAvgLength;
    }

    public Analysis wordAvgLength(Double wordAvgLength) {
        this.wordAvgLength = wordAvgLength;
        return this;
    }

    public void setWordAvgLength(Double wordAvgLength) {
        this.wordAvgLength = wordAvgLength;
    }

    public Double getFunctionalWordAvgLength() {
        return functionalWordAvgLength;
    }

    public Analysis functionalWordAvgLength(Double functionalWordAvgLength) {
        this.functionalWordAvgLength = functionalWordAvgLength;
        return this;
    }

    public void setFunctionalWordAvgLength(Double functionalWordAvgLength) {
        this.functionalWordAvgLength = functionalWordAvgLength;
    }

    public Double getContentWordAvgLength() {
        return contentWordAvgLength;
    }

    public Analysis contentWordAvgLength(Double contentWordAvgLength) {
        this.contentWordAvgLength = contentWordAvgLength;
        return this;
    }

    public void setContentWordAvgLength(Double contentWordAvgLength) {
        this.contentWordAvgLength = contentWordAvgLength;
    }

    public Integer getSentenceCount() {
        return sentenceCount;
    }

    public Analysis sentenceCount(Integer sentenceCount) {
        this.sentenceCount = sentenceCount;
        return this;
    }

    public void setSentenceCount(Integer sentenceCount) {
        this.sentenceCount = sentenceCount;
    }

    public Double getLexicalDensity() {
        return lexicalDensity;
    }

    public Analysis lexicalDensity(Double lexicalDensity) {
        this.lexicalDensity = lexicalDensity;
        return this;
    }

    public void setLexicalDensity(Double lexicalDensity) {
        this.lexicalDensity = lexicalDensity;
    }

    public Double getBaseTTR() {
        return baseTTR;
    }

    public Analysis baseTTR(Double baseTTR) {
        this.baseTTR = baseTTR;
        return this;
    }

    public void setBaseTTR(Double baseTTR) {
        this.baseTTR = baseTTR;
    }

    public Double getHdd() {
        return hdd;
    }

    public Analysis hdd(Double hdd) {
        this.hdd = hdd;
        return this;
    }

    public void setHdd(Double hdd) {
        this.hdd = hdd;
    }

    public Double getMtld() {
        return mtld;
    }

    public Analysis mtld(Double mtld) {
        this.mtld = mtld;
        return this;
    }

    public void setMtld(Double mtld) {
        this.mtld = mtld;
    }

    public Double getVocd() {
        return vocd;
    }

    public Analysis vocd(Double vocd) {
        this.vocd = vocd;
        return this;
    }

    public void setVocd(Double vocd) {
        this.vocd = vocd;
    }

    public Map<String, Set<String>> getWordsByCategory() {
        return wordsByCategory;
    }

    public Analysis wordsByCategory(Map<String, Set<String>> wordsByCategory) {
        this.wordsByCategory = wordsByCategory;
        return this;
    }

    public void setWordsByCategory(Map<String, Set<String>> wordsByCategory) {
        this.wordsByCategory = wordsByCategory;
    }

    public Map<String, Integer> getContentWordFrequencies() {
        return contentWordFrequencies;
    }

    public Analysis contentWordFrequencies(Map<String, Integer> contentWordFrequencies) {
        this.contentWordFrequencies = contentWordFrequencies;
        return this;
    }

    public Analysis addContentWordFrequency(String word, Integer frequency) {
        if (contentWordFrequencies == null) {
            contentWordFrequencies = new HashMap<>();
        }
        contentWordFrequencies.put(word, frequency);
        return this;
    }

    public Analysis removeContentWordFrequency(String word) {
        if (contentWordFrequencies == null) {
            return this;
        }
        contentWordFrequencies.remove(word);
        return this;
    }

    public void setContentWordFrequencies(Map<String, Integer> contentWordFrequencies) {
        this.contentWordFrequencies = contentWordFrequencies;
    }

    public Map<String, Integer> getFunctionalWordFrequencies() {
        return functionalWordFrequencies;
    }

    public Analysis functionalWordFrequencies(Map<String, Integer> functionalWordFrequencies) {
        this.functionalWordFrequencies = functionalWordFrequencies;
        return this;
    }

    public Analysis addFunctionalWordFrequency(String word, Integer frequency) {
        if (functionalWordFrequencies == null) {
            functionalWordFrequencies = new HashMap<>();
        }
        functionalWordFrequencies.put(word, frequency);
        return this;
    }

    public Analysis removeFunctionalWordFrequency(String word) {
        if (functionalWordFrequencies == null) {
            return this;
        }
        functionalWordFrequencies.remove(word);
        return this;
    }

    public void setFunctionalWordFrequencies(Map<String, Integer> functionalWordFrequencies) {
        this.functionalWordFrequencies = functionalWordFrequencies;
    }

    public Map<String, Double> getCoOccurrences() {
        return coOccurrences;
    }

    public Analysis coOccurrences(Map<String, Double> coOccurrences) {
        this.coOccurrences = coOccurrences;
        return this;
    }

    public Analysis addCoOccurrence(String words, Double frequency) {
        if (coOccurrences == null) {
            coOccurrences = new HashMap<>();
        }
        coOccurrences.put(words, frequency);
        return this;
    }

    public Analysis removeCoOccurrence(String words) {
        if (coOccurrences == null) {
            return this;
        }
        coOccurrences.remove(words);
        return this;
    }

    public void setCoOccurrences(Map<String, Double> coOccurrences) {
        this.coOccurrences = coOccurrences;
    }

    public Map<String, Integer> getLemmaFrequencies() {
        return lemmaFrequencies;
    }

    public Analysis lemmaFrequencies(Map<String, Integer> lemmaFrequencies) {
        this.lemmaFrequencies = lemmaFrequencies;
        return this;
    }

    public Analysis addLemmaFrequency(String word, Integer frequency) {
        if (lemmaFrequencies == null) {
            lemmaFrequencies = new HashMap<>();
        }
        lemmaFrequencies.put(word, frequency);
        return this;
    }

    public Analysis removeLemmaFrequency(String word) {
        if (lemmaFrequencies == null) {
            return this;
        }
        lemmaFrequencies.remove(word);
        return this;
    }

    public void setLemmaFrequencies(Map<String, Integer> lemmaFrequencies) {
        this.lemmaFrequencies = lemmaFrequencies;
    }

    public Set<Emotion> getEmotions() {
        return emotions;
    }

    public Analysis emotions(Set<Emotion> emotions) {
        this.emotions = emotions;
        return this;
    }

    public Analysis addEmotion(Emotion emotion) {
        this.emotions.add(emotion);
        emotion.setAnalysis(this);
        return this;
    }

    public Analysis removeEmotion(Emotion emotion) {
        this.emotions.remove(emotion);
        emotion.setAnalysis(null);
        return this;
    }

    public void setEmotions(Set<Emotion> emotions) {
        this.emotions = emotions;
    }

    public Set<PartOfSpeech> getPartsOfSpeech() {
        return partsOfSpeech;
    }

    public Analysis partOfSpeeches(Set<PartOfSpeech> partOfSpeeches) {
        this.partsOfSpeech = partOfSpeeches;
        return this;
    }

    public Analysis addPartOfSpeech(PartOfSpeech partOfSpeech) {
        this.partsOfSpeech.add(partOfSpeech);
        partOfSpeech.setAnalysis(this);
        return this;
    }

    public Analysis removePartOfSpeech(PartOfSpeech partOfSpeech) {
        this.partsOfSpeech.remove(partOfSpeech);
        partOfSpeech.setAnalysis(null);
        return this;
    }

    public void setPartsOfSpeech(Set<PartOfSpeech> partsOfSpeech) {
        this.partsOfSpeech = partsOfSpeech;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Analysis)) {
            return false;
        }
        return id != null && id.equals(((Analysis) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Analysis{" +
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
            ", emotions=" + emotions +
            ", partsOfSpeech=" + partsOfSpeech +
            '}';
    }
}
