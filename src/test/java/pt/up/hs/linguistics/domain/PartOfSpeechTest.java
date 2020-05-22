package pt.up.hs.linguistics.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import pt.up.hs.linguistics.web.rest.TestUtil;

public class PartOfSpeechTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartOfSpeech.class);
        PartOfSpeech partOfSpeech1 = new PartOfSpeech();
        partOfSpeech1.setId("id1");
        PartOfSpeech partOfSpeech2 = new PartOfSpeech();
        partOfSpeech2.setId(partOfSpeech1.getId());
        assertThat(partOfSpeech1).isEqualTo(partOfSpeech2);
        partOfSpeech2.setId("id2");
        assertThat(partOfSpeech1).isNotEqualTo(partOfSpeech2);
        partOfSpeech1.setId(null);
        assertThat(partOfSpeech1).isNotEqualTo(partOfSpeech2);
    }
}
