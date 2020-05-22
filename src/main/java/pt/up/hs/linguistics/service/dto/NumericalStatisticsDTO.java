package pt.up.hs.linguistics.service.dto;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link pt.up.hs.linguistics.domain.NumericalStatistics} entity.
 */
@ApiModel(description = "Numerical statistics.\n\n@author José Carlos Paiva")
public class NumericalStatisticsDTO implements Serializable {
    
    private String id;

    private Integer nrOfCharacters;

    private Integer nrOfNonBlankCharacters;

    private Integer nrOfWords;

    private Integer nrOfDistinctWords;

    private Integer nrOfStopWords;

    private Integer nrOfErrors;

    private Integer nrOfFunctionalWords;

    private Integer nrOfDistinctFunctionalWords;

    private Integer nrOfContentWords;

    private Integer nrOfDistinctContentWords;

    private Integer nrOfDistinctLemmas;

    private Integer nrOfSentences;

    private Double avgWordLength;

    private Double avgNonStopWordLength;

    private Double avgFunctionalWordLength;

    private Double avgContentWordLength;


    private String analysisId;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getNrOfCharacters() {
        return nrOfCharacters;
    }

    public void setNrOfCharacters(Integer nrOfCharacters) {
        this.nrOfCharacters = nrOfCharacters;
    }

    public Integer getNrOfNonBlankCharacters() {
        return nrOfNonBlankCharacters;
    }

    public void setNrOfNonBlankCharacters(Integer nrOfNonBlankCharacters) {
        this.nrOfNonBlankCharacters = nrOfNonBlankCharacters;
    }

    public Integer getNrOfWords() {
        return nrOfWords;
    }

    public void setNrOfWords(Integer nrOfWords) {
        this.nrOfWords = nrOfWords;
    }

    public Integer getNrOfDistinctWords() {
        return nrOfDistinctWords;
    }

    public void setNrOfDistinctWords(Integer nrOfDistinctWords) {
        this.nrOfDistinctWords = nrOfDistinctWords;
    }

    public Integer getNrOfStopWords() {
        return nrOfStopWords;
    }

    public void setNrOfStopWords(Integer nrOfStopWords) {
        this.nrOfStopWords = nrOfStopWords;
    }

    public Integer getNrOfErrors() {
        return nrOfErrors;
    }

    public void setNrOfErrors(Integer nrOfErrors) {
        this.nrOfErrors = nrOfErrors;
    }

    public Integer getNrOfFunctionalWords() {
        return nrOfFunctionalWords;
    }

    public void setNrOfFunctionalWords(Integer nrOfFunctionalWords) {
        this.nrOfFunctionalWords = nrOfFunctionalWords;
    }

    public Integer getNrOfDistinctFunctionalWords() {
        return nrOfDistinctFunctionalWords;
    }

    public void setNrOfDistinctFunctionalWords(Integer nrOfDistinctFunctionalWords) {
        this.nrOfDistinctFunctionalWords = nrOfDistinctFunctionalWords;
    }

    public Integer getNrOfContentWords() {
        return nrOfContentWords;
    }

    public void setNrOfContentWords(Integer nrOfContentWords) {
        this.nrOfContentWords = nrOfContentWords;
    }

    public Integer getNrOfDistinctContentWords() {
        return nrOfDistinctContentWords;
    }

    public void setNrOfDistinctContentWords(Integer nrOfDistinctContentWords) {
        this.nrOfDistinctContentWords = nrOfDistinctContentWords;
    }

    public Integer getNrOfDistinctLemmas() {
        return nrOfDistinctLemmas;
    }

    public void setNrOfDistinctLemmas(Integer nrOfDistinctLemmas) {
        this.nrOfDistinctLemmas = nrOfDistinctLemmas;
    }

    public Integer getNrOfSentences() {
        return nrOfSentences;
    }

    public void setNrOfSentences(Integer nrOfSentences) {
        this.nrOfSentences = nrOfSentences;
    }

    public Double getAvgWordLength() {
        return avgWordLength;
    }

    public void setAvgWordLength(Double avgWordLength) {
        this.avgWordLength = avgWordLength;
    }

    public Double getAvgNonStopWordLength() {
        return avgNonStopWordLength;
    }

    public void setAvgNonStopWordLength(Double avgNonStopWordLength) {
        this.avgNonStopWordLength = avgNonStopWordLength;
    }

    public Double getAvgFunctionalWordLength() {
        return avgFunctionalWordLength;
    }

    public void setAvgFunctionalWordLength(Double avgFunctionalWordLength) {
        this.avgFunctionalWordLength = avgFunctionalWordLength;
    }

    public Double getAvgContentWordLength() {
        return avgContentWordLength;
    }

    public void setAvgContentWordLength(Double avgContentWordLength) {
        this.avgContentWordLength = avgContentWordLength;
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
        if (!(o instanceof NumericalStatisticsDTO)) {
            return false;
        }

        return id != null && id.equals(((NumericalStatisticsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NumericalStatisticsDTO{" +
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
            ", analysisId='" + getAnalysisId() + "'" +
            "}";
    }
}
