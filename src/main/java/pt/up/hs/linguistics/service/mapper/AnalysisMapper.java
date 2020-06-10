package pt.up.hs.linguistics.service.mapper;

import pt.up.hs.linguistics.domain.*;
import pt.up.hs.linguistics.service.dto.AnalysisDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Analysis} and its DTO {@link AnalysisDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmotionMapper.class, PartOfSpeechMapper.class})
public interface AnalysisMapper extends EntityMapper<AnalysisDTO, Analysis> {

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    AnalysisDTO toDto(Analysis analysis);

    @Mapping(target = "removeContentWordFrequency", ignore = true)
    @Mapping(target = "removeFunctionalWordFrequency", ignore = true)
    @Mapping(target = "removeLemmaFrequency", ignore = true)
    @Mapping(target = "removeCoOccurrence", ignore = true)
    @Mapping(target = "emotions", ignore = true)
    @Mapping(target = "removeEmotion", ignore = true)
    @Mapping(target = "partsOfSpeech", ignore = true)
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
