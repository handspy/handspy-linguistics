package pt.up.hs.linguistics.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import pt.up.hs.linguistics.domain.enumeration.Status;

/**
 * A DTO for the {@link pt.up.hs.linguistics.domain.Analysis} entity.
 */
@ApiModel(description = "Linguistic analysis conducted in a text.\n\n@author José Carlos Paiva")
public class AnalysisDTO implements Serializable {
    
    private String id;

    private Long textId;

    private String text;

    private String language;

    private Status status;


    private String idId;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTextId() {
        return textId;
    }

    public void setTextId(Long textId) {
        this.textId = textId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getIdId() {
        return idId;
    }

    public void setIdId(String numericalStatisticsId) {
        this.idId = numericalStatisticsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnalysisDTO)) {
            return false;
        }

        return id != null && id.equals(((AnalysisDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnalysisDTO{" +
            "id=" + getId() +
            ", textId=" + getTextId() +
            ", text='" + getText() + "'" +
            ", language='" + getLanguage() + "'" +
            ", status='" + getStatus() + "'" +
            ", idId='" + getIdId() + "'" +
            "}";
    }
}
