package pt.up.hs.linguistics.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * Numerical statistics.\n\n@author José Carlos Paiva
 */
@Document(collection = "numerical_statistics")
public class NumericalStatistics implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("nr_of_characters")
    private Integer nrOfCharacters;

    @Field("nr_of_non_blank_characters")
    private Integer nrOfNonBlankCharacters;

    @Field("nr_of_words")
    private Integer nrOfWords;

    @Field("nr_of_distinct_words")
    private Integer nrOfDistinctWords;

    @Field("nr_of_stop_words")
    private Integer nrOfStopWords;

    @Field("nr_of_errors")
    private Integer nrOfErrors;

    @Field("nr_of_functional_words")
    private Integer nrOfFunctionalWords;

    @Field("nr_of_distinct_functional_words")
    private Integer nrOfDistinctFunctionalWords;

    @Field("nr_of_content_words")
    private Integer nrOfContentWords;

    @Field("nr_of_distinct_content_words")
    private Integer nrOfDistinctContentWords;

    @Field("nr_of_distinct_lemmas")
    private Integer nrOfDistinctLemmas;

    @Field("nr_of_sentences")
    private Integer nrOfSentences;

    @Field("avg_word_length")
    private Double avgWordLength;

    @Field("avg_non_stop_word_length")
    private Double avgNonStopWordLength;

    @Field("avg_functional_word_length")
    private Double avgFunctionalWordLength;

    @Field("avg_content_word_length")
    private Double avgContentWordLength;

    @DBRef
    @Field("analysis")
    private Analysis analysis;

    @DBRef
    @Field("analysisId")
    @com.fasterxml.jackson.annotation.JsonBackReference
    private Analysis analysisId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getNrOfCharacters() {
        return nrOfCharacters;
    }

    public NumericalStatistics nrOfCharacters(Integer nrOfCharacters) {
        this.nrOfCharacters = nrOfCharacters;
        return this;
    }

    public void setNrOfCharacters(Integer nrOfCharacters) {
        this.nrOfCharacters = nrOfCharacters;
    }

    public Integer getNrOfNonBlankCharacters() {
        return nrOfNonBlankCharacters;
    }

    public NumericalStatistics nrOfNonBlankCharacters(Integer nrOfNonBlankCharacters) {
        this.nrOfNonBlankCharacters = nrOfNonBlankCharacters;
        return this;
    }

    public void setNrOfNonBlankCharacters(Integer nrOfNonBlankCharacters) {
        this.nrOfNonBlankCharacters = nrOfNonBlankCharacters;
    }

    public Integer getNrOfWords() {
        return nrOfWords;
    }

    public NumericalStatistics nrOfWords(Integer nrOfWords) {
        this.nrOfWords = nrOfWords;
        return this;
    }

    public void setNrOfWords(Integer nrOfWords) {
        this.nrOfWords = nrOfWords;
    }

    public Integer getNrOfDistinctWords() {
        return nrOfDistinctWords;
    }

    public NumericalStatistics nrOfDistinctWords(Integer nrOfDistinctWords) {
        this.nrOfDistinctWords = nrOfDistinctWords;
        return this;
    }

    public void setNrOfDistinctWords(Integer nrOfDistinctWords) {
        this.nrOfDistinctWords = nrOfDistinctWords;
    }

    public Integer getNrOfStopWords() {
        return nrOfStopWords;
    }

    public NumericalStatistics nrOfStopWords(Integer nrOfStopWords) {
        this.nrOfStopWords = nrOfStopWords;
        return this;
    }

    public void setNrOfStopWords(Integer nrOfStopWords) {
        this.nrOfStopWords = nrOfStopWords;
    }

    public Integer getNrOfErrors() {
        return nrOfErrors;
    }

    public NumericalStatistics nrOfErrors(Integer nrOfErrors) {
        this.nrOfErrors = nrOfErrors;
        return this;
    }

    public void setNrOfErrors(Integer nrOfErrors) {
        this.nrOfErrors = nrOfErrors;
    }

    public Integer getNrOfFunctionalWords() {
        return nrOfFunctionalWords;
    }

    public NumericalStatistics nrOfFunctionalWords(Integer nrOfFunctionalWords) {
        this.nrOfFunctionalWords = nrOfFunctionalWords;
        return this;
    }

    public void setNrOfFunctionalWords(Integer nrOfFunctionalWords) {
        this.nrOfFunctionalWords = nrOfFunctionalWords;
    }

    public Integer getNrOfDistinctFunctionalWords() {
        return nrOfDistinctFunctionalWords;
    }

    public NumericalStatistics nrOfDistinctFunctionalWords(Integer nrOfDistinctFunctionalWords) {
        this.nrOfDistinctFunctionalWords = nrOfDistinctFunctionalWords;
        return this;
    }

    public void setNrOfDistinctFunctionalWords(Integer nrOfDistinctFunctionalWords) {
        this.nrOfDistinctFunctionalWords = nrOfDistinctFunctionalWords;
    }

    public Integer getNrOfContentWords() {
        return nrOfContentWords;
    }

    public NumericalStatistics nrOfContentWords(Integer nrOfContentWords) {
        this.nrOfContentWords = nrOfContentWords;
        return this;
    }

    public void setNrOfContentWords(Integer nrOfContentWords) {
        this.nrOfContentWords = nrOfContentWords;
    }

    public Integer getNrOfDistinctContentWords() {
        return nrOfDistinctContentWords;
    }

    public NumericalStatistics nrOfDistinctContentWords(Integer nrOfDistinctContentWords) {
        this.nrOfDistinctContentWords = nrOfDistinctContentWords;
        return this;
    }

    public void setNrOfDistinctContentWords(Integer nrOfDistinctContentWords) {
        this.nrOfDistinctContentWords = nrOfDistinctContentWords;
    }

    public Integer getNrOfDistinctLemmas() {
        return nrOfDistinctLemmas;
    }

    public NumericalStatistics nrOfDistinctLemmas(Integer nrOfDistinctLemmas) {
        this.nrOfDistinctLemmas = nrOfDistinctLemmas;
        return this;
    }

    public void setNrOfDistinctLemmas(Integer nrOfDistinctLemmas) {
        this.nrOfDistinctLemmas = nrOfDistinctLemmas;
    }

    public Integer getNrOfSentences() {
        return nrOfSentences;
    }

    public NumericalStatistics nrOfSentences(Integer nrOfSentences) {
        this.nrOfSentences = nrOfSentences;
        return this;
    }

    public void setNrOfSentences(Integer nrOfSentences) {
        this.nrOfSentences = nrOfSentences;
    }

    public Double getAvgWordLength() {
        return avgWordLength;
    }

    public NumericalStatistics avgWordLength(Double avgWordLength) {
        this.avgWordLength = avgWordLength;
        return this;
    }

    public void setAvgWordLength(Double avgWordLength) {
        this.avgWordLength = avgWordLength;
    }

    public Double getAvgNonStopWordLength() {
        return avgNonStopWordLength;
    }

    public NumericalStatistics avgNonStopWordLength(Double avgNonStopWordLength) {
        this.avgNonStopWordLength = avgNonStopWordLength;
        return this;
    }

    public void setAvgNonStopWordLength(Double avgNonStopWordLength) {
        this.avgNonStopWordLength = avgNonStopWordLength;
    }

    public Double getAvgFunctionalWordLength() {
        return avgFunctionalWordLength;
    }

    public NumericalStatistics avgFunctionalWordLength(Double avgFunctionalWordLength) {
        this.avgFunctionalWordLength = avgFunctionalWordLength;
        return this;
    }

    public void setAvgFunctionalWordLength(Double avgFunctionalWordLength) {
        this.avgFunctionalWordLength = avgFunctionalWordLength;
    }

    public Double getAvgContentWordLength() {
        return avgContentWordLength;
    }

    public NumericalStatistics avgContentWordLength(Double avgContentWordLength) {
        this.avgContentWordLength = avgContentWordLength;
        return this;
    }

    public void setAvgContentWordLength(Double avgContentWordLength) {
        this.avgContentWordLength = avgContentWordLength;
    }

    public Analysis getAnalysis() {
        return analysis;
    }

    public NumericalStatistics analysis(Analysis analysis) {
        this.analysis = analysis;
        return this;
    }

    public void setAnalysis(Analysis analysis) {
        this.analysis = analysis;
    }

    public Analysis getAnalysisId() {
        return analysisId;
    }

    public NumericalStatistics analysisId(Analysis analysis) {
        this.analysisId = analysis;
        return this;
    }

    public void setAnalysisId(Analysis analysis) {
        this.analysisId = analysis;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NumericalStatistics)) {
            return false;
        }
        return id != null && id.equals(((NumericalStatistics) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NumericalStatistics{" +
            "id=" + getId() +
            ", nrOfCharacters=" + getNrOfCharacters() +
            ", nrOfNonBlankCharacters=" + getNrOfNonBlankCharacters() +
            ", nrOfWords=" + getNrOfWords() +
            ", nrOfDistinctWords=" + getNrOfDistinctWords() +
            ", nrOfStopWords=" + getNrOfStopWords() +
            ", nrOfErrors=" + getNrOfErrors() +
            ", nrOfFunctionalWords=" + getNrOfFunctionalWords() +
            ", nrOfDistinctFunctionalWords=" + getNrOfDistinctFunctionalWords() +
            ", nrOfContentWords=" + getNrOfContentWords() +
            ", nrOfDistinctContentWords=" + getNrOfDistinctContentWords() +
            ", nrOfDistinctLemmas=" + getNrOfDistinctLemmas() +
            ", nrOfSentences=" + getNrOfSentences() +
            ", avgWordLength=" + getAvgWordLength() +
            ", avgNonStopWordLength=" + getAvgNonStopWordLength() +
            ", avgFunctionalWordLength=" + getAvgFunctionalWordLength() +
            ", avgContentWordLength=" + getAvgContentWordLength() +
            "}";
    }
}
