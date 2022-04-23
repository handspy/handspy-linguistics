package pt.up.hs.linguistics.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Annotation referring to the sentence.\n\n@author José Carlos Paiva
 */
@Document(collection = "sentence")
public class Sentence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    /**
     * Start position of the tag
     */
    @NotNull
    @Field("start")
    private Integer start;

    /**
     * Size of the tag
     */
    @NotNull
    @Field("size")
    private Integer size;

    /**
     * Number of words in sentence.
     */
    @Field("nrOfWords")
    private Integer nrOfWords;

    @DBRef(lazy = true)
    @Field("analysis")
    @JsonIgnoreProperties(value = {"emotions", "partsOfSpeech", "sentenceSummaries"}, allowSetters = true)
    private Analysis analysis;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getStart() {
        return start;
    }

    public Sentence start(Integer start) {
        this.start = start;
        return this;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getSize() {
        return size;
    }

    public Sentence size(Integer size) {
        this.size = size;
        return this;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getNrOfWords() {
        return nrOfWords;
    }

    public Sentence nrOfWords(Integer nrOfWords) {
        this.nrOfWords = nrOfWords;
        return this;
    }

    public void setNrOfWords(Integer nrOfWords) {
        this.nrOfWords = nrOfWords;
    }

    public Analysis getAnalysis() {
        return analysis;
    }

    public Sentence analysis(Analysis analysis) {
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
        if (!(o instanceof Sentence)) {
            return false;
        }
        return id != null && id.equals(((Sentence) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PartOfSpeech{" +
            "id='" + id + '\'' +
            ", start=" + start +
            ", size=" + size +
            ", nrOfWords=" + nrOfWords +
            ", analysis=" + analysis +
            '}';
    }
}
