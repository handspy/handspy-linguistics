package pt.up.hs.linguistics.service.mapper;


import pt.up.hs.linguistics.domain.*;
import pt.up.hs.linguistics.service.dto.AnalysisDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Analysis} and its DTO {@link AnalysisDTO}.
 */
@Mapper(componentModel = "spring", uses = {NumericalStatisticsMapper.class})
public interface AnalysisMapper extends EntityMapper<AnalysisDTO, Analysis> {

    @Mapping(source = "id.id", target = "idId")
    AnalysisDTO toDto(Analysis analysis);

    @Mapping(source = "idId", target = "id")
    @Mapping(target = "wordFrequencies", ignore = true)
    @Mapping(target = "removeWordFrequency", ignore = true)
    @Mapping(target = "lemmaFrequencies", ignore = true)
    @Mapping(target = "removeLemmaFrequency", ignore = true)
    @Mapping(target = "emotions", ignore = true)
    @Mapping(target = "removeEmotion", ignore = true)
    @Mapping(target = "partOfSpeeches", ignore = true)
    @Mapping(target = "removePartOfSpeech", ignore = true)
    Analysis toEntity(AnalysisDTO analysisDTO);

    default Analysis fromId(String id) {
        if (id == null) {
            return null;
        }
        Analysis analysis = new Analysis();
        analysis.setId(id);
        return analysis;
    }
}
