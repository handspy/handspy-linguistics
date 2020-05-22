package pt.up.hs.linguistics.service.mapper;


import pt.up.hs.linguistics.domain.*;
import pt.up.hs.linguistics.service.dto.NumericalStatisticsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link NumericalStatistics} and its DTO {@link NumericalStatisticsDTO}.
 */
@Mapper(componentModel = "spring", uses = {AnalysisMapper.class})
public interface NumericalStatisticsMapper extends EntityMapper<NumericalStatisticsDTO, NumericalStatistics> {

    @Mapping(source = "analysis.id", target = "analysisId")
    NumericalStatisticsDTO toDto(NumericalStatistics numericalStatistics);

    @Mapping(source = "analysisId", target = "analysis")
    @Mapping(target = "analysisId", ignore = true)
    NumericalStatistics toEntity(NumericalStatisticsDTO numericalStatisticsDTO);

    default NumericalStatistics fromId(String id) {
        if (id == null) {
            return null;
        }
        NumericalStatistics numericalStatistics = new NumericalStatistics();
        numericalStatistics.setId(id);
        return numericalStatistics;
    }
}
