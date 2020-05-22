package pt.up.hs.linguistics.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import pt.up.hs.linguistics.web.rest.TestUtil;

public class EmotionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmotionDTO.class);
        EmotionDTO emotionDTO1 = new EmotionDTO();
        emotionDTO1.setId("id1");
        EmotionDTO emotionDTO2 = new EmotionDTO();
        assertThat(emotionDTO1).isNotEqualTo(emotionDTO2);
        emotionDTO2.setId(emotionDTO1.getId());
        assertThat(emotionDTO1).isEqualTo(emotionDTO2);
        emotionDTO2.setId("id2");
        assertThat(emotionDTO1).isNotEqualTo(emotionDTO2);
        emotionDTO1.setId(null);
        assertThat(emotionDTO1).isNotEqualTo(emotionDTO2);
    }
}
