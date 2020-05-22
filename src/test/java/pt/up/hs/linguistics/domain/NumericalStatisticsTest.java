package pt.up.hs.linguistics.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import pt.up.hs.linguistics.web.rest.TestUtil;

public class NumericalStatisticsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NumericalStatistics.class);
        NumericalStatistics numericalStatistics1 = new NumericalStatistics();
        numericalStatistics1.setId("id1");
        NumericalStatistics numericalStatistics2 = new NumericalStatistics();
        numericalStatistics2.setId(numericalStatistics1.getId());
        assertThat(numericalStatistics1).isEqualTo(numericalStatistics2);
        numericalStatistics2.setId("id2");
        assertThat(numericalStatistics1).isNotEqualTo(numericalStatistics2);
        numericalStatistics1.setId(null);
        assertThat(numericalStatistics1).isNotEqualTo(numericalStatistics2);
    }
}
