package pt.up.hs.linguistics.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PartOfSpeechMapperTest {

    private PartOfSpeechMapper partOfSpeechMapper;

    @BeforeEach
    public void setUp() {
        partOfSpeechMapper = new PartOfSpeechMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(partOfSpeechMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(partOfSpeechMapper.fromId(null)).isNull();
    }
}
