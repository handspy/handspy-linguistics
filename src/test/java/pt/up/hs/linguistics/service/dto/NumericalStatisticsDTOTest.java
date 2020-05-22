package pt.up.hs.linguistics.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import pt.up.hs.linguistics.web.rest.TestUtil;

public class NumericalStatisticsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NumericalStatisticsDTO.class);
        NumericalStatisticsDTO numericalStatisticsDTO1 = new NumericalStatisticsDTO();
        numericalStatisticsDTO1.setId("id1");
        NumericalStatisticsDTO numericalStatisticsDTO2 = new NumericalStatisticsDTO();
        assertThat(numericalStatisticsDTO1).isNotEqualTo(numericalStatisticsDTO2);
        numericalStatisticsDTO2.setId(numericalStatisticsDTO1.getId());
        assertThat(numericalStatisticsDTO1).isEqualTo(numericalStatisticsDTO2);
        numericalStatisticsDTO2.setId("id2");
        assertThat(numericalStatisticsDTO1).isNotEqualTo(numericalStatisticsDTO2);
        numericalStatisticsDTO1.setId(null);
        assertThat(numericalStatisticsDTO1).isNotEqualTo(numericalStatisticsDTO2);
    }
}
