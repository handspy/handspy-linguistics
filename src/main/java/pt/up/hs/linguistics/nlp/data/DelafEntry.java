package pt.up.hs.linguistics.nlp.data;

import java.io.Serializable;
import java.util.Objects;

public class DelafEntry implements Serializable {
    private String inflectedForm;
    private String lemma;
    private String partOfSpeech;
    private String subcategory;
    private String morphAttributes;

    public DelafEntry(
        String inflectedForm, String lemma,
        String partOfSpeech, String subcategory,
        String morphAttributes
    ) {
        this.inflectedForm = inflectedForm;
        this.lemma = lemma;
        this.partOfSpeech = partOfSpeech;
        this.subcategory = subcategory;
        this.morphAttributes = morphAttributes;
    }

    public String getInflectedForm() {
        return inflectedForm;
    }

    public void setInflectedForm(String inflectedForm) {
        this.inflectedForm = inflectedForm;
    }

    public String getLemma() {
        return lemma;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getMorphAttributes() {
        return morphAttributes;
    }

    public void setMorphAttributes(String morphAttributes) {
        this.morphAttributes = morphAttributes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DelafEntry that = (DelafEntry) o;
        return Objects.equals(getInflectedForm(), that.getInflectedForm()) &&
            Objects.equals(getLemma(), that.getLemma()) &&
            Objects.equals(getPartOfSpeech(), that.getPartOfSpeech()) &&
            Objects.equals(getSubcategory(), that.getSubcategory()) &&
            Objects.equals(getMorphAttributes(), that.getMorphAttributes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInflectedForm(), getLemma(), getPartOfSpeech(), getSubcategory(), getMorphAttributes());
    }
}
