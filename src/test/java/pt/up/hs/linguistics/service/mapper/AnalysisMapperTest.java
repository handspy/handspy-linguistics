package pt.up.hs.linguistics.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AnalysisMapperTest {

    private AnalysisMapper analysisMapper;

    @BeforeEach
    public void setUp() {
        analysisMapper = new AnalysisMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(analysisMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(analysisMapper.fromId(null)).isNull();
    }
}
