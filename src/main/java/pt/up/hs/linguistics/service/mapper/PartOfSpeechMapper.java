package pt.up.hs.linguistics.service.mapper;


import pt.up.hs.linguistics.domain.*;
import pt.up.hs.linguistics.service.dto.PartOfSpeechDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PartOfSpeech} and its DTO {@link PartOfSpeechDTO}.
 */
@Mapper(componentModel = "spring", uses = {AnalysisMapper.class})
public interface PartOfSpeechMapper extends EntityMapper<PartOfSpeechDTO, PartOfSpeech> {

    @Mapping(source = "analysis.id", target = "analysisId")
    PartOfSpeechDTO toDto(PartOfSpeech partOfSpeech);

    @Mapping(source = "analysisId", target = "analysis")
    PartOfSpeech toEntity(PartOfSpeechDTO partOfSpeechDTO);

    default PartOfSpeech fromId(String id) {
        if (id == null) {
            return null;
        }
        PartOfSpeech partOfSpeech = new PartOfSpeech();
        partOfSpeech.setId(id);
        return partOfSpeech;
    }
}
