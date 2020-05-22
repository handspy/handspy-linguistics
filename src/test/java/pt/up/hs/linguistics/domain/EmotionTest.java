package pt.up.hs.linguistics.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import pt.up.hs.linguistics.web.rest.TestUtil;

public class EmotionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Emotion.class);
        Emotion emotion1 = new Emotion();
        emotion1.setId("id1");
        Emotion emotion2 = new Emotion();
        emotion2.setId(emotion1.getId());
        assertThat(emotion1).isEqualTo(emotion2);
        emotion2.setId("id2");
        assertThat(emotion1).isNotEqualTo(emotion2);
        emotion1.setId(null);
        assertThat(emotion1).isNotEqualTo(emotion2);
    }
}
