package pt.up.hs.linguistics.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class NumericalStatisticsMapperTest {

    private NumericalStatisticsMapper numericalStatisticsMapper;

    @BeforeEach
    public void setUp() {
        numericalStatisticsMapper = new NumericalStatisticsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(numericalStatisticsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(numericalStatisticsMapper.fromId(null)).isNull();
    }
}
