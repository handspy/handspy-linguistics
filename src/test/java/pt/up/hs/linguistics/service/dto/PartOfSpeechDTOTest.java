package pt.up.hs.linguistics.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import pt.up.hs.linguistics.web.rest.TestUtil;

public class PartOfSpeechDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartOfSpeechDTO.class);
        PartOfSpeechDTO partOfSpeechDTO1 = new PartOfSpeechDTO();
        partOfSpeechDTO1.setId("id1");
        PartOfSpeechDTO partOfSpeechDTO2 = new PartOfSpeechDTO();
        assertThat(partOfSpeechDTO1).isNotEqualTo(partOfSpeechDTO2);
        partOfSpeechDTO2.setId(partOfSpeechDTO1.getId());
        assertThat(partOfSpeechDTO1).isEqualTo(partOfSpeechDTO2);
        partOfSpeechDTO2.setId("id2");
        assertThat(partOfSpeechDTO1).isNotEqualTo(partOfSpeechDTO2);
        partOfSpeechDTO1.setId(null);
        assertThat(partOfSpeechDTO1).isNotEqualTo(partOfSpeechDTO2);
    }
}
