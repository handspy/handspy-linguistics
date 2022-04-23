package pt.up.hs.linguistics.service.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pt.up.hs.linguistics.domain.Sentence;
import pt.up.hs.linguistics.service.dto.SentenceDTO;

/**
 * Mapper for the entity {@link Sentence} and its DTO {@link SentenceDTO}.
 */
@Mapper(componentModel = "spring", uses = {AnalysisMapper.class})
public interface SentenceMapper extends EntityMapper<SentenceDTO, Sentence> {

    @Mapping(source = "analysis.id", target = "analysisId")
    SentenceDTO toDto(Sentence sentence);

    @Mapping(source = "analysisId", target = "analysis")
    Sentence toEntity(SentenceDTO sentenceDTO);

    default Sentence fromId(String id) {
        if (id == null) {
            return null;
        }
        Sentence sentence = new Sentence();
        sentence.setId(id);
        return sentence;
    }
}
