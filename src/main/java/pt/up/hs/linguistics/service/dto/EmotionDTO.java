package pt.up.hs.linguistics.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import java.io.Serializable;
import pt.up.hs.linguistics.domain.enumeration.PrimaryEmotion;
import pt.up.hs.linguistics.domain.enumeration.SecondaryEmotion;
import pt.up.hs.linguistics.domain.enumeration.TertiaryEmotion;

/**
 * A DTO for the {@link pt.up.hs.linguistics.domain.Emotion} entity.
 */
@ApiModel(description = "An emotion present in the text.\n\n@author José Carlos Paiva")
public class EmotionDTO implements Serializable {

    private String id;

    private String analysisId;

    /**
     * Primary emotion
     */
    @NotNull
    @ApiModelProperty(value = "Primary emotion", required = true)
    private PrimaryEmotion primary;

    /**
     * Secondary emotion
     */
    @ApiModelProperty(value = "Secondary emotion")
    private SecondaryEmotion secondary;

    /**
     * Tertiary emotion
     */
    @ApiModelProperty(value = "Tertiary emotion")
    private TertiaryEmotion tertiary;

    /**
     * Start position of the emotion
     */
    @NotNull
    @ApiModelProperty(value = "Start position of the emotion", required = true)
    private Integer start;

    /**
     * Size of the emotion
     */
    @NotNull
    @ApiModelProperty(value = "Size of the emotion", required = true)
    private Integer size;

    /**
     * Note about emotion
     */
    @ApiModelProperty(value = "Note about emotion")
    private String note;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PrimaryEmotion getPrimary() {
        return primary;
    }

    public void setPrimary(PrimaryEmotion primary) {
        this.primary = primary;
    }

    public SecondaryEmotion getSecondary() {
        return secondary;
    }

    public void setSecondary(SecondaryEmotion secondary) {
        this.secondary = secondary;
    }

    public TertiaryEmotion getTertiary() {
        return tertiary;
    }

    public void setTertiary(TertiaryEmotion tertiary) {
        this.tertiary = tertiary;
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
        if (!(o instanceof EmotionDTO)) {
            return false;
        }

        return id != null && id.equals(((EmotionDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmotionDTO{" +
            "id=" + getId() +
            ", analysisId='" + getAnalysisId() + "'" +
            ", primary='" + getPrimary() + "'" +
            ", secondary='" + getSecondary() + "'" +
            ", tertiary='" + getTertiary() + "'" +
            ", start=" + getStart() +
            ", size=" + getSize() +
            ", note='" + getNote() + "'" +
            "}";
    }
}
