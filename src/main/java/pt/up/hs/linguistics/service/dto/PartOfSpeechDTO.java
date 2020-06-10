package pt.up.hs.linguistics.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import java.io.Serializable;
import pt.up.hs.linguistics.domain.enumeration.PoSTag;

/**
 * A DTO for the {@link pt.up.hs.linguistics.domain.PartOfSpeech} entity.
 *
 * @author José Carlos Paiva
 */
@ApiModel(description = "Annotation referring to the Part-of-Speech tag.")
public class PartOfSpeechDTO implements Serializable {

    private String id;

    /**
     * Part-of-Speech tag.
     */
    @NotNull
    @ApiModelProperty(value = "Part of Speech tag", required = true)
    private PoSTag tag;

    /**
     * Start position of the tag.
     */
    @NotNull
    @ApiModelProperty(value = "Start position of the tag", required = true)
    private Integer start;

    /**
     * Size of the tag.
     */
    @NotNull
    @ApiModelProperty(value = "Size of the tag", required = true)
    private Integer size;

    /**
     * Lemma of the tagged word.
     */
    @ApiModelProperty(value = "Lemma of the word.")
    private String lemma;

    /**
     * Note about tag.
     */
    @ApiModelProperty(value = "Note about tag")
    private String note;


    private String analysisId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PoSTag getTag() {
        return tag;
    }

    public void setTag(PoSTag tag) {
        this.tag = tag;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getLemma() {
        return lemma;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAnalysisId() {
        return analysisId;
    }

    public void setAnalysisId(String analysisId) {
        this.analysisId = analysisId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PartOfSpeechDTO)) {
            return false;
        }

        return id != null && id.equals(((PartOfSpeechDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PartOfSpeechDTO{" +
            "id='" + id + '\'' +
            ", tag=" + tag +
            ", start=" + start +
            ", size=" + size +
            ", lemma='" + lemma + '\'' +
            ", note='" + note + '\'' +
            ", analysisId='" + analysisId + '\'' +
            '}';
    }
}
