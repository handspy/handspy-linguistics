package pt.up.hs.linguistics.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import pt.up.hs.linguistics.domain.enumeration.Status;

/**
 * Linguistic analysis conducted in a text.\n\n@author José Carlos Paiva
 */
@Document(collection = "analysis")
public class Analysis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("text_id")
    private Long textId;

    @Field("text")
    private String text;

    @Field("language")
    private String language;

    @Field("status")
    private Status status;

    @DBRef
    @Field("id")
    private NumericalStatistics id;

    @DBRef
    @Field("wordFrequency")
    private Set<WordFrequency> wordFrequencies = new HashSet<>();

    @DBRef
    @Field("lemmaFrequency")
    private Set<LemmaFrequency> lemmaFrequencies = new HashSet<>();

    @DBRef
    @Field("emotion")
    private Set<Emotion> emotions = new HashSet<>();

    @DBRef
    @Field("partOfSpeech")
    private Set<PartOfSpeech> partOfSpeeches = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getText() {
        return text;
    }

    public Analysis text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
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

    public NumericalStatistics getId() {
        return id;
    }

    public Analysis id(NumericalStatistics numericalStatistics) {
        this.id = numericalStatistics;
        return this;
    }

    public void setId(NumericalStatistics numericalStatistics) {
        this.id = numericalStatistics;
    }

    public Set<WordFrequency> getWordFrequencies() {
        return wordFrequencies;
    }

    public Analysis wordFrequencies(Set<WordFrequency> wordFrequencies) {
        this.wordFrequencies = wordFrequencies;
        return this;
    }

    public Analysis addWordFrequency(WordFrequency wordFrequency) {
        this.wordFrequencies.add(wordFrequency);
        wordFrequency.setAnalysis(this);
        return this;
    }

    public Analysis removeWordFrequency(WordFrequency wordFrequency) {
        this.wordFrequencies.remove(wordFrequency);
        wordFrequency.setAnalysis(null);
        return this;
    }

    public void setWordFrequencies(Set<WordFrequency> wordFrequencies) {
        this.wordFrequencies = wordFrequencies;
    }

    public Set<LemmaFrequency> getLemmaFrequencies() {
        return lemmaFrequencies;
    }

    public Analysis lemmaFrequencies(Set<LemmaFrequency> lemmaFrequencies) {
        this.lemmaFrequencies = lemmaFrequencies;
        return this;
    }

    public Analysis addLemmaFrequency(LemmaFrequency lemmaFrequency) {
        this.lemmaFrequencies.add(lemmaFrequency);
        lemmaFrequency.setAnalysis(this);
        return this;
    }

    public Analysis removeLemmaFrequency(LemmaFrequency lemmaFrequency) {
        this.lemmaFrequencies.remove(lemmaFrequency);
        lemmaFrequency.setAnalysis(null);
        return this;
    }

    public void setLemmaFrequencies(Set<LemmaFrequency> lemmaFrequencies) {
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

    public Set<PartOfSpeech> getPartOfSpeeches() {
        return partOfSpeeches;
    }

    public Analysis partOfSpeeches(Set<PartOfSpeech> partOfSpeeches) {
        this.partOfSpeeches = partOfSpeeches;
        return this;
    }

    public Analysis addPartOfSpeech(PartOfSpeech partOfSpeech) {
        this.partOfSpeeches.add(partOfSpeech);
        partOfSpeech.setAnalysis(this);
        return this;
    }

    public Analysis removePartOfSpeech(PartOfSpeech partOfSpeech) {
        this.partOfSpeeches.remove(partOfSpeech);
        partOfSpeech.setAnalysis(null);
        return this;
    }

    public void setPartOfSpeeches(Set<PartOfSpeech> partOfSpeeches) {
        this.partOfSpeeches = partOfSpeeches;
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
            "id=" + getId() +
            ", textId=" + getTextId() +
            ", text='" + getText() + "'" +
            ", language='" + getLanguage() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
