package pt.up.hs.linguistics.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import pt.up.hs.linguistics.web.rest.TestUtil;

public class AnalysisTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Analysis.class);
        Analysis analysis1 = new Analysis();
        analysis1.setId("id1");
        Analysis analysis2 = new Analysis();
        analysis2.setId(analysis1.getId());
        assertThat(analysis1).isEqualTo(analysis2);
        analysis2.setId("id2");
        assertThat(analysis1).isNotEqualTo(analysis2);
        analysis1.setId(null);
        assertThat(analysis1).isNotEqualTo(analysis2);
    }
}
