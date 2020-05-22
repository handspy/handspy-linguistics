package pt.up.hs.linguistics.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link pt.up.hs.linguistics.domain.WordFrequency} entity.
 */
@ApiModel(description = "The Word Frequency.\n\n@author José Carlos Paiva")
public class WordFrequencyDTO implements Serializable {
    
    private String id;

    /**
     * Word in the text
     */
    @ApiModelProperty(value = "Word in the text")
    private String word;

    /**
     * Frequency of the word
     */
    @ApiModelProperty(value = "Frequency of the word")
    private Integer frequency;

    /**
     * Is it a content word?
     */
    @ApiModelProperty(value = "Is it a content word?")
    private Boolean content;

    /**
     * Is it a functional word?
     */
    @ApiModelProperty(value = "Is it a functional word?")
    private Boolean functional;


    private String analysisId;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public Boolean isContent() {
        return content;
    }

    public void setContent(Boolean content) {
        this.content = content;
    }

    public Boolean isFunctional() {
        return functional;
    }

    public void setFunctional(Boolean functional) {
        this.functional = functional;
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
        if (!(o instanceof WordFrequencyDTO)) {
            return false;
        }

        return id != null && id.equals(((WordFrequencyDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WordFrequencyDTO{" +
            "id=" + getId() +
            ", word='" + getWord() + "'" +
            ", frequency=" + getFrequency() +
            ", content='" + isContent() + "'" +
            ", functional='" + isFunctional() + "'" +
            ", analysisId='" + getAnalysisId() + "'" +
            "}";
    }
}
