package pt.up.hs.linguistics.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import pt.up.hs.linguistics.web.rest.TestUtil;

public class AnalysisDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnalysisDTO.class);
        AnalysisDTO analysisDTO1 = new AnalysisDTO();
        analysisDTO1.setId("id1");
        AnalysisDTO analysisDTO2 = new AnalysisDTO();
        assertThat(analysisDTO1).isNotEqualTo(analysisDTO2);
        analysisDTO2.setId(analysisDTO1.getId());
        assertThat(analysisDTO1).isEqualTo(analysisDTO2);
        analysisDTO2.setId("id2");
        assertThat(analysisDTO1).isNotEqualTo(analysisDTO2);
        analysisDTO1.setId(null);
        assertThat(analysisDTO1).isNotEqualTo(analysisDTO2);
    }
}
