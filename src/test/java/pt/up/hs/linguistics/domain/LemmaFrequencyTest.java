package pt.up.hs.linguistics.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import pt.up.hs.linguistics.web.rest.TestUtil;

public class LemmaFrequencyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LemmaFrequency.class);
        LemmaFrequency lemmaFrequency1 = new LemmaFrequency();
        lemmaFrequency1.setId("id1");
        LemmaFrequency lemmaFrequency2 = new LemmaFrequency();
        lemmaFrequency2.setId(lemmaFrequency1.getId());
        assertThat(lemmaFrequency1).isEqualTo(lemmaFrequency2);
        lemmaFrequency2.setId("id2");
        assertThat(lemmaFrequency1).isNotEqualTo(lemmaFrequency2);
        lemmaFrequency1.setId(null);
        assertThat(lemmaFrequency1).isNotEqualTo(lemmaFrequency2);
    }
}
