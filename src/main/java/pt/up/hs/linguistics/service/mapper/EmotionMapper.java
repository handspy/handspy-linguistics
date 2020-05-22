package pt.up.hs.linguistics.service.mapper;


import pt.up.hs.linguistics.domain.*;
import pt.up.hs.linguistics.service.dto.EmotionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Emotion} and its DTO {@link EmotionDTO}.
 */
@Mapper(componentModel = "spring", uses = {AnalysisMapper.class})
public interface EmotionMapper extends EntityMapper<EmotionDTO, Emotion> {

    @Mapping(source = "analysis.id", target = "analysisId")
    EmotionDTO toDto(Emotion emotion);

    @Mapping(source = "analysisId", target = "analysis")
    Emotion toEntity(EmotionDTO emotionDTO);

    default Emotion fromId(String id) {
        if (id == null) {
            return null;
        }
        Emotion emotion = new Emotion();
        emotion.setId(id);
        return emotion;
    }
}
