package pt.up.hs.linguistics.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import pt.up.hs.linguistics.web.rest.TestUtil;

public class WordFrequencyDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WordFrequencyDTO.class);
        WordFrequencyDTO wordFrequencyDTO1 = new WordFrequencyDTO();
        wordFrequencyDTO1.setId("id1");
        WordFrequencyDTO wordFrequencyDTO2 = new WordFrequencyDTO();
        assertThat(wordFrequencyDTO1).isNotEqualTo(wordFrequencyDTO2);
        wordFrequencyDTO2.setId(wordFrequencyDTO1.getId());
        assertThat(wordFrequencyDTO1).isEqualTo(wordFrequencyDTO2);
        wordFrequencyDTO2.setId("id2");
        assertThat(wordFrequencyDTO1).isNotEqualTo(wordFrequencyDTO2);
        wordFrequencyDTO1.setId(null);
        assertThat(wordFrequencyDTO1).isNotEqualTo(wordFrequencyDTO2);
    }
}
