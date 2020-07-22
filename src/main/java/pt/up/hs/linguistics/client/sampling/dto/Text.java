package pt.up.hs.linguistics.client.sampling.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * A text.
 *
 * @author José Carlos Paiva
 */
public class Text implements Serializable {

    private Long taskId;
    private Long participantId;
    private String text;
    private String language;

    public Text() {
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Long participantId) {
        this.participantId = participantId;
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
}
