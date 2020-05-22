package pt.up.hs.linguistics.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * The Word Frequency.\n\n@author José Carlos Paiva
 */
@Document(collection = "word_frequency")
public class WordFrequency implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    /**
     * Word in the text
     */
    @Field("word")
    private String word;

    /**
     * Frequency of the word
     */
    @Field("frequency")
    private Integer frequency;

    /**
     * Is it a content word?
     */
    @Field("content")
    private Boolean content;

    /**
     * Is it a functional word?
     */
    @Field("functional")
    private Boolean functional;

    @DBRef
    @Field("analysis")
    @JsonIgnoreProperties(value = "wordFrequencies", allowSetters = true)
    private Analysis analysis;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public WordFrequency word(String word) {
        this.word = word;
        return this;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public WordFrequency frequency(Integer frequency) {
        this.frequency = frequency;
        return this;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public Boolean isContent() {
        return content;
    }

    public WordFrequency content(Boolean content) {
        this.content = content;
        return this;
    }

    public void setContent(Boolean content) {
        this.content = content;
    }

    public Boolean isFunctional() {
        return functional;
    }

    public WordFrequency functional(Boolean functional) {
        this.functional = functional;
        return this;
    }

    public void setFunctional(Boolean functional) {
        this.functional = functional;
    }

    public Analysis getAnalysis() {
        return analysis;
    }

    public WordFrequency analysis(Analysis analysis) {
        this.analysis = analysis;
        return this;
    }

    public void setAnalysis(Analysis analysis) {
        this.analysis = analysis;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WordFrequency)) {
            return false;
        }
        return id != null && id.equals(((WordFrequency) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WordFrequency{" +
            "id=" + getId() +
            ", word='" + getWord() + "'" +
            ", frequency=" + getFrequency() +
            ", content='" + isContent() + "'" +
            ", functional='" + isFunctional() + "'" +
            "}";
    }
}
