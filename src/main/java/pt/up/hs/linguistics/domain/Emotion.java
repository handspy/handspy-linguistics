package pt.up.hs.linguistics.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;

import pt.up.hs.linguistics.domain.enumeration.PrimaryEmotion;

import pt.up.hs.linguistics.domain.enumeration.SecondaryEmotion;

import pt.up.hs.linguistics.domain.enumeration.TertiaryEmotion;

/**
 * An emotion present in the text.\n\n@author José Carlos Paiva
 */
@Document(collection = "emotion")
public class Emotion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    /**
     * Primary emotion
     */
    @NotNull
    @Field("primary")
    private PrimaryEmotion primary;

    /**
     * Secondary emotion
     */
    @Field("secondary")
    private SecondaryEmotion secondary;

    /**
     * Tertiary emotion
     */
    @Field("tertiary")
    private TertiaryEmotion tertiary;

    /**
     * Start position of the emotion
     */
    @NotNull
    @Field("start")
    private Integer start;

    /**
     * Size of the emotion
     */
    @NotNull
    @Field("size")
    private Integer size;

    /**
     * Note about emotion
     */
    @Field("note")
    private String note;

    @DBRef
    @Field("analysis")
    @JsonIgnoreProperties(value = "emotions", allowSetters = true)
    private Analysis analysis;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PrimaryEmotion getPrimary() {
        return primary;
    }

    public Emotion primary(PrimaryEmotion primary) {
        this.primary = primary;
        return this;
    }

    public void setPrimary(PrimaryEmotion primary) {
        this.primary = primary;
    }

    public SecondaryEmotion getSecondary() {
        return secondary;
    }

    public Emotion secondary(SecondaryEmotion secondary) {
        this.secondary = secondary;
        return this;
    }

    public void setSecondary(SecondaryEmotion secondary) {
        this.secondary = secondary;
    }

    public TertiaryEmotion getTertiary() {
        return tertiary;
    }

    public Emotion tertiary(TertiaryEmotion tertiary) {
        this.tertiary = tertiary;
        return this;
    }

    public void setTertiary(TertiaryEmotion tertiary) {
        this.tertiary = tertiary;
    }

    public Integer getStart() {
        return start;
    }

    public Emotion start(Integer start) {
        this.start = start;
        return this;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getSize() {
        return size;
    }

    public Emotion size(Integer size) {
        this.size = size;
        return this;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getNote() {
        return note;
    }

    public Emotion note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Analysis getAnalysis() {
        return analysis;
    }

    public Emotion analysis(Analysis analysis) {
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
        if (!(o instanceof Emotion)) {
            return false;
        }
        return id != null && id.equals(((Emotion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Emotion{" +
            "id=" + getId() +
            ", primary='" + getPrimary() + "'" +
            ", secondary='" + getSecondary() + "'" +
            ", tertiary='" + getTertiary() + "'" +
            ", start=" + getStart() +
            ", size=" + getSize() +
            ", note='" + getNote() + "'" +
            "}";
    }
}
