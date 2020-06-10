package pt.up.hs.linguistics.client.sampling.dto;

import java.io.Serializable;

/**
 * A text.
 *
 * @author José Carlos Paiva
 */
public class Text implements Serializable {

    private String text;
    private String language;

    public Text() {
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
