package pt.up.hs.linguistics.service.mapper;


import pt.up.hs.linguistics.domain.*;
import pt.up.hs.linguistics.service.dto.WordFrequencyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link WordFrequency} and its DTO {@link WordFrequencyDTO}.
 */
@Mapper(componentModel = "spring", uses = {AnalysisMapper.class})
public interface WordFrequencyMapper extends EntityMapper<WordFrequencyDTO, WordFrequency> {

    @Mapping(source = "analysis.id", target = "analysisId")
    WordFrequencyDTO toDto(WordFrequency wordFrequency);

    @Mapping(source = "analysisId", target = "analysis")
    WordFrequency toEntity(WordFrequencyDTO wordFrequencyDTO);

    default WordFrequency fromId(String id) {
        if (id == null) {
            return null;
        }
        WordFrequency wordFrequency = new WordFrequency();
        wordFrequency.setId(id);
        return wordFrequency;
    }
}
