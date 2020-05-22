package pt.up.hs.linguistics.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import pt.up.hs.linguistics.web.rest.TestUtil;

public class LemmaFrequencyDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LemmaFrequencyDTO.class);
        LemmaFrequencyDTO lemmaFrequencyDTO1 = new LemmaFrequencyDTO();
        lemmaFrequencyDTO1.setId("id1");
        LemmaFrequencyDTO lemmaFrequencyDTO2 = new LemmaFrequencyDTO();
        assertThat(lemmaFrequencyDTO1).isNotEqualTo(lemmaFrequencyDTO2);
        lemmaFrequencyDTO2.setId(lemmaFrequencyDTO1.getId());
        assertThat(lemmaFrequencyDTO1).isEqualTo(lemmaFrequencyDTO2);
        lemmaFrequencyDTO2.setId("id2");
        assertThat(lemmaFrequencyDTO1).isNotEqualTo(lemmaFrequencyDTO2);
        lemmaFrequencyDTO1.setId(null);
        assertThat(lemmaFrequencyDTO1).isNotEqualTo(lemmaFrequencyDTO2);
    }
}
