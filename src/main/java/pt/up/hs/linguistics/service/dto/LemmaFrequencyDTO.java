package pt.up.hs.linguistics.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link pt.up.hs.linguistics.domain.LemmaFrequency} entity.
 */
@ApiModel(description = "The Lemma Frequency.\n\n@author José Carlos Paiva")
public class LemmaFrequencyDTO implements Serializable {
    
    private String id;

    /**
     * Lemma obtained from a word in the text
     */
    @ApiModelProperty(value = "Lemma obtained from a word in the text")
    private String lemma;

    /**
     * Frequency of the lemma
     */
    @ApiModelProperty(value = "Frequency of the lemma")
    private Integer frequency;


    private String analysisId;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLemma() {
        return lemma;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
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
        if (!(o instanceof LemmaFrequencyDTO)) {
            return false;
        }

        return id != null && id.equals(((LemmaFrequencyDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LemmaFrequencyDTO{" +
            "id=" + getId() +
            ", lemma='" + getLemma() + "'" +
            ", frequency=" + getFrequency() +
            ", analysisId='" + getAnalysisId() + "'" +
            "}";
    }
}
