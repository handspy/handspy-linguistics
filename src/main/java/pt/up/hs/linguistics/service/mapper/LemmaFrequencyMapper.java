package pt.up.hs.linguistics.service.mapper;


import pt.up.hs.linguistics.domain.*;
import pt.up.hs.linguistics.service.dto.LemmaFrequencyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LemmaFrequency} and its DTO {@link LemmaFrequencyDTO}.
 */
@Mapper(componentModel = "spring", uses = {AnalysisMapper.class})
public interface LemmaFrequencyMapper extends EntityMapper<LemmaFrequencyDTO, LemmaFrequency> {

    @Mapping(source = "analysis.id", target = "analysisId")
    LemmaFrequencyDTO toDto(LemmaFrequency lemmaFrequency);

    @Mapping(source = "analysisId", target = "analysis")
    LemmaFrequency toEntity(LemmaFrequencyDTO lemmaFrequencyDTO);

    default LemmaFrequency fromId(String id) {
        if (id == null) {
            return null;
        }
        LemmaFrequency lemmaFrequency = new LemmaFrequency();
        lemmaFrequency.setId(id);
        return lemmaFrequency;
    }
}
