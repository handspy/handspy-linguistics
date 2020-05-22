package pt.up.hs.linguistics.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LemmaFrequencyMapperTest {

    private LemmaFrequencyMapper lemmaFrequencyMapper;

    @BeforeEach
    public void setUp() {
        lemmaFrequencyMapper = new LemmaFrequencyMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(lemmaFrequencyMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(lemmaFrequencyMapper.fromId(null)).isNull();
    }
}
