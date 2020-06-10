package pt.up.hs.linguistics.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;

import pt.up.hs.linguistics.domain.enumeration.PoSTag;

/**
 * Annotation refering to the Part-of-Speech tag.\n\n@author José Carlos Paiva
 */
@Document(collection = "part_of_speech")
public class PartOfSpeech implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    /**
     * Part of Speech tag
     */
    @NotNull
    @Field("tag")
    private PoSTag tag;

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
     * Lemma of the word.
     */
    @Field("lemma")
    private String lemma;

    /**
     * Note about tag
     */
    @Field("note")
    private String note;

    @DBRef
    @Field("analysis")
    @JsonIgnoreProperties(value = "partOfSpeeches", allowSetters = true)
    private Analysis analysis;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PoSTag getTag() {
        return tag;
    }

    public PartOfSpeech tag(PoSTag tag) {
        this.tag = tag;
        return this;
    }

    public void setTag(PoSTag tag) {
        this.tag = tag;
    }

    public Integer getStart() {
        return start;
    }

    public PartOfSpeech start(Integer start) {
        this.start = start;
        return this;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getSize() {
        return size;
    }

    public PartOfSpeech size(Integer size) {
        this.size = size;
        return this;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getLemma() {
        return lemma;
    }

    public PartOfSpeech lemma(String lemma) {
        this.lemma = lemma;
        return this;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    public String getNote() {
        return note;
    }

    public PartOfSpeech note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Analysis getAnalysis() {
        return analysis;
    }

    public PartOfSpeech analysis(Analysis analysis) {
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
        if (!(o instanceof PartOfSpeech)) {
            return false;
        }
        return id != null && id.equals(((PartOfSpeech) o).id);
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
            ", tag=" + tag +
            ", start=" + start +
            ", size=" + size +
            ", lemma='" + lemma + '\'' +
            ", note='" + note + '\'' +
            ", analysis=" + analysis +
            '}';
    }
}
