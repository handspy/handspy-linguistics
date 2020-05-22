package pt.up.hs.linguistics.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class WordFrequencyMapperTest {

    private WordFrequencyMapper wordFrequencyMapper;

    @BeforeEach
    public void setUp() {
        wordFrequencyMapper = new WordFrequencyMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(wordFrequencyMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(wordFrequencyMapper.fromId(null)).isNull();
    }
}
