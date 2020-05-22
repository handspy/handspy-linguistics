package pt.up.hs.linguistics.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import pt.up.hs.linguistics.web.rest.TestUtil;

public class WordFrequencyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WordFrequency.class);
        WordFrequency wordFrequency1 = new WordFrequency();
        wordFrequency1.setId("id1");
        WordFrequency wordFrequency2 = new WordFrequency();
        wordFrequency2.setId(wordFrequency1.getId());
        assertThat(wordFrequency1).isEqualTo(wordFrequency2);
        wordFrequency2.setId("id2");
        assertThat(wordFrequency1).isNotEqualTo(wordFrequency2);
        wordFrequency1.setId(null);
        assertThat(wordFrequency1).isNotEqualTo(wordFrequency2);
    }
}
