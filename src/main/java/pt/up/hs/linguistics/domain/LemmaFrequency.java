package pt.up.hs.linguistics.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * The Lemma Frequency.\n\n@author José Carlos Paiva
 */
@Document(collection = "lemma_frequency")
public class LemmaFrequency implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    /**
     * Lemma obtained from a word in the text
     */
    @Field("lemma")
    private String lemma;

    /**
     * Frequency of the lemma
     */
    @Field("frequency")
    private Integer frequency;

    @DBRef
    @Field("analysis")
    @JsonIgnoreProperties(value = "lemmaFrequencies", allowSetters = true)
    private Analysis analysis;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLemma() {
        return lemma;
    }

    public LemmaFrequency lemma(String lemma) {
        this.lemma = lemma;
        return this;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public LemmaFrequency frequency(Integer frequency) {
        this.frequency = frequency;
        return this;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public Analysis getAnalysis() {
        return analysis;
    }

    public LemmaFrequency analysis(Analysis analysis) {
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
        if (!(o instanceof LemmaFrequency)) {
            return false;
        }
        return id != null && id.equals(((LemmaFrequency) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LemmaFrequency{" +
            "id=" + getId() +
            ", lemma='" + getLemma() + "'" +
            ", frequency=" + getFrequency() +
            "}";
    }
}
