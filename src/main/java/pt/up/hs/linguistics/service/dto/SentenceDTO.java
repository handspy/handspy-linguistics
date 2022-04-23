package pt.up.hs.linguistics.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link pt.up.hs.linguistics.domain.PartOfSpeech} entity.
 *
 * @author José Carlos Paiva
 */
@ApiModel(description = "Annotation referring to a sentence.")
public class SentenceDTO implements Serializable {

    private String id;

    /**
     * Start position of the sentence.
     */
    @NotNull
    @ApiModelProperty(value = "Start position of the sentence", required = true)
    private Integer start;

    /**
     * Size of the sentence.
     */
    @NotNull
    @ApiModelProperty(value = "Size of the sentence", required = true)
    private Integer size;

    /**
     * Lemma of the tagged word.
     */
    @NotNull
    @ApiModelProperty(value = "Number of words")
    private Integer nrOfWords;

    private String analysisId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getNrOfWords() {
        return nrOfWords;
    }

    public void setNrOfWords(Integer nrOfWords) {
        this.nrOfWords = nrOfWords;
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
        if (!(o instanceof SentenceDTO)) {
            return false;
        }

        return id != null && id.equals(((SentenceDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SentenceDTO{" +
            "id='" + id + '\'' +
            ", start=" + start +
            ", size=" + size +
            ", nrOfWords=" + nrOfWords +
            ", analysisId='" + analysisId + '\'' +
            '}';
    }
}
