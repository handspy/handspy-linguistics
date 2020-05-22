package pt.up.hs.linguistics.web.rest;

import pt.up.hs.linguistics.LinguisticsApp;
import pt.up.hs.linguistics.config.SecurityBeanOverrideConfiguration;
import pt.up.hs.linguistics.domain.NumericalStatistics;
import pt.up.hs.linguistics.domain.Analysis;
import pt.up.hs.linguistics.repository.NumericalStatisticsRepository;
import pt.up.hs.linguistics.service.NumericalStatisticsService;
import pt.up.hs.linguistics.service.dto.NumericalStatisticsDTO;
import pt.up.hs.linguistics.service.mapper.NumericalStatisticsMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link NumericalStatisticsResource} REST controller.
 */
@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, LinguisticsApp.class })
@AutoConfigureMockMvc
@WithMockUser
public class NumericalStatisticsResourceIT {

    private static final Integer DEFAULT_NR_OF_CHARACTERS = 1;
    private static final Integer UPDATED_NR_OF_CHARACTERS = 2;

    private static final Integer DEFAULT_NR_OF_NON_BLANK_CHARACTERS = 1;
    private static final Integer UPDATED_NR_OF_NON_BLANK_CHARACTERS = 2;

    private static final Integer DEFAULT_NR_OF_WORDS = 1;
    private static final Integer UPDATED_NR_OF_WORDS = 2;

    private static final Integer DEFAULT_NR_OF_DISTINCT_WORDS = 1;
    private static final Integer UPDATED_NR_OF_DISTINCT_WORDS = 2;

    private static final Integer DEFAULT_NR_OF_STOP_WORDS = 1;
    private static final Integer UPDATED_NR_OF_STOP_WORDS = 2;

    private static final Integer DEFAULT_NR_OF_ERRORS = 1;
    private static final Integer UPDATED_NR_OF_ERRORS = 2;

    private static final Integer DEFAULT_NR_OF_FUNCTIONAL_WORDS = 1;
    private static final Integer UPDATED_NR_OF_FUNCTIONAL_WORDS = 2;

    private static final Integer DEFAULT_NR_OF_DISTINCT_FUNCTIONAL_WORDS = 1;
    private static final Integer UPDATED_NR_OF_DISTINCT_FUNCTIONAL_WORDS = 2;

    private static final Integer DEFAULT_NR_OF_CONTENT_WORDS = 1;
    private static final Integer UPDATED_NR_OF_CONTENT_WORDS = 2;

    private static final Integer DEFAULT_NR_OF_DISTINCT_CONTENT_WORDS = 1;
    private static final Integer UPDATED_NR_OF_DISTINCT_CONTENT_WORDS = 2;

    private static final Integer DEFAULT_NR_OF_DISTINCT_LEMMAS = 1;
    private static final Integer UPDATED_NR_OF_DISTINCT_LEMMAS = 2;

    private static final Integer DEFAULT_NR_OF_SENTENCES = 1;
    private static final Integer UPDATED_NR_OF_SENTENCES = 2;

    private static final Double DEFAULT_AVG_WORD_LENGTH = 1D;
    private static final Double UPDATED_AVG_WORD_LENGTH = 2D;

    private static final Double DEFAULT_AVG_NON_STOP_WORD_LENGTH = 1D;
    private static final Double UPDATED_AVG_NON_STOP_WORD_LENGTH = 2D;

    private static final Double DEFAULT_AVG_FUNCTIONAL_WORD_LENGTH = 1D;
    private static final Double UPDATED_AVG_FUNCTIONAL_WORD_LENGTH = 2D;

    private static final Double DEFAULT_AVG_CONTENT_WORD_LENGTH = 1D;
    private static final Double UPDATED_AVG_CONTENT_WORD_LENGTH = 2D;

    @Autowired
    private NumericalStatisticsRepository numericalStatisticsRepository;

    @Autowired
    private NumericalStatisticsMapper numericalStatisticsMapper;

    @Autowired
    private NumericalStatisticsService numericalStatisticsService;

    @Autowired
    private MockMvc restNumericalStatisticsMockMvc;

    private NumericalStatistics numericalStatistics;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NumericalStatistics createEntity() {
        NumericalStatistics numericalStatistics = new NumericalStatistics()
            .nrOfCharacters(DEFAULT_NR_OF_CHARACTERS)
            .nrOfNonBlankCharacters(DEFAULT_NR_OF_NON_BLANK_CHARACTERS)
            .nrOfWords(DEFAULT_NR_OF_WORDS)
            .nrOfDistinctWords(DEFAULT_NR_OF_DISTINCT_WORDS)
            .nrOfStopWords(DEFAULT_NR_OF_STOP_WORDS)
            .nrOfErrors(DEFAULT_NR_OF_ERRORS)
            .nrOfFunctionalWords(DEFAULT_NR_OF_FUNCTIONAL_WORDS)
            .nrOfDistinctFunctionalWords(DEFAULT_NR_OF_DISTINCT_FUNCTIONAL_WORDS)
            .nrOfContentWords(DEFAULT_NR_OF_CONTENT_WORDS)
            .nrOfDistinctContentWords(DEFAULT_NR_OF_DISTINCT_CONTENT_WORDS)
            .nrOfDistinctLemmas(DEFAULT_NR_OF_DISTINCT_LEMMAS)
            .nrOfSentences(DEFAULT_NR_OF_SENTENCES)
            .avgWordLength(DEFAULT_AVG_WORD_LENGTH)
            .avgNonStopWordLength(DEFAULT_AVG_NON_STOP_WORD_LENGTH)
            .avgFunctionalWordLength(DEFAULT_AVG_FUNCTIONAL_WORD_LENGTH)
            .avgContentWordLength(DEFAULT_AVG_CONTENT_WORD_LENGTH);
        // Add required entity
        Analysis analysis;
        analysis = AnalysisResourceIT.createEntity();
        analysis.setId("fixed-id-for-tests");
        numericalStatistics.setAnalysisId(analysis);
        return numericalStatistics;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NumericalStatistics createUpdatedEntity() {
        NumericalStatistics numericalStatistics = new NumericalStatistics()
            .nrOfCharacters(UPDATED_NR_OF_CHARACTERS)
            .nrOfNonBlankCharacters(UPDATED_NR_OF_NON_BLANK_CHARACTERS)
            .nrOfWords(UPDATED_NR_OF_WORDS)
            .nrOfDistinctWords(UPDATED_NR_OF_DISTINCT_WORDS)
            .nrOfStopWords(UPDATED_NR_OF_STOP_WORDS)
            .nrOfErrors(UPDATED_NR_OF_ERRORS)
            .nrOfFunctionalWords(UPDATED_NR_OF_FUNCTIONAL_WORDS)
            .nrOfDistinctFunctionalWords(UPDATED_NR_OF_DISTINCT_FUNCTIONAL_WORDS)
            .nrOfContentWords(UPDATED_NR_OF_CONTENT_WORDS)
            .nrOfDistinctContentWords(UPDATED_NR_OF_DISTINCT_CONTENT_WORDS)
            .nrOfDistinctLemmas(UPDATED_NR_OF_DISTINCT_LEMMAS)
            .nrOfSentences(UPDATED_NR_OF_SENTENCES)
            .avgWordLength(UPDATED_AVG_WORD_LENGTH)
            .avgNonStopWordLength(UPDATED_AVG_NON_STOP_WORD_LENGTH)
            .avgFunctionalWordLength(UPDATED_AVG_FUNCTIONAL_WORD_LENGTH)
            .avgContentWordLength(UPDATED_AVG_CONTENT_WORD_LENGTH);
        // Add required entity
        Analysis analysis;
        analysis = AnalysisResourceIT.createUpdatedEntity();
        analysis.setId("fixed-id-for-tests");
        numericalStatistics.setAnalysisId(analysis);
        return numericalStatistics;
    }

    @BeforeEach
    public void initTest() {
        numericalStatisticsRepository.deleteAll();
        numericalStatistics = createEntity();
    }

    @Test
    public void createNumericalStatistics() throws Exception {
        int databaseSizeBeforeCreate = numericalStatisticsRepository.findAll().size();
        // Create the NumericalStatistics
        NumericalStatisticsDTO numericalStatisticsDTO = numericalStatisticsMapper.toDto(numericalStatistics);
        restNumericalStatisticsMockMvc.perform(post("/api/numerical-statistics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(numericalStatisticsDTO)))
            .andExpect(status().isCreated());

        // Validate the NumericalStatistics in the database
        List<NumericalStatistics> numericalStatisticsList = numericalStatisticsRepository.findAll();
        assertThat(numericalStatisticsList).hasSize(databaseSizeBeforeCreate + 1);
        NumericalStatistics testNumericalStatistics = numericalStatisticsList.get(numericalStatisticsList.size() - 1);
        assertThat(testNumericalStatistics.getNrOfCharacters()).isEqualTo(DEFAULT_NR_OF_CHARACTERS);
        assertThat(testNumericalStatistics.getNrOfNonBlankCharacters()).isEqualTo(DEFAULT_NR_OF_NON_BLANK_CHARACTERS);
        assertThat(testNumericalStatistics.getNrOfWords()).isEqualTo(DEFAULT_NR_OF_WORDS);
        assertThat(testNumericalStatistics.getNrOfDistinctWords()).isEqualTo(DEFAULT_NR_OF_DISTINCT_WORDS);
        assertThat(testNumericalStatistics.getNrOfStopWords()).isEqualTo(DEFAULT_NR_OF_STOP_WORDS);
        assertThat(testNumericalStatistics.getNrOfErrors()).isEqualTo(DEFAULT_NR_OF_ERRORS);
        assertThat(testNumericalStatistics.getNrOfFunctionalWords()).isEqualTo(DEFAULT_NR_OF_FUNCTIONAL_WORDS);
        assertThat(testNumericalStatistics.getNrOfDistinctFunctionalWords()).isEqualTo(DEFAULT_NR_OF_DISTINCT_FUNCTIONAL_WORDS);
        assertThat(testNumericalStatistics.getNrOfContentWords()).isEqualTo(DEFAULT_NR_OF_CONTENT_WORDS);
        assertThat(testNumericalStatistics.getNrOfDistinctContentWords()).isEqualTo(DEFAULT_NR_OF_DISTINCT_CONTENT_WORDS);
        assertThat(testNumericalStatistics.getNrOfDistinctLemmas()).isEqualTo(DEFAULT_NR_OF_DISTINCT_LEMMAS);
        assertThat(testNumericalStatistics.getNrOfSentences()).isEqualTo(DEFAULT_NR_OF_SENTENCES);
        assertThat(testNumericalStatistics.getAvgWordLength()).isEqualTo(DEFAULT_AVG_WORD_LENGTH);
        assertThat(testNumericalStatistics.getAvgNonStopWordLength()).isEqualTo(DEFAULT_AVG_NON_STOP_WORD_LENGTH);
        assertThat(testNumericalStatistics.getAvgFunctionalWordLength()).isEqualTo(DEFAULT_AVG_FUNCTIONAL_WORD_LENGTH);
        assertThat(testNumericalStatistics.getAvgContentWordLength()).isEqualTo(DEFAULT_AVG_CONTENT_WORD_LENGTH);
    }

    @Test
    public void createNumericalStatisticsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = numericalStatisticsRepository.findAll().size();

        // Create the NumericalStatistics with an existing ID
        numericalStatistics.setId("existing_id");
        NumericalStatisticsDTO numericalStatisticsDTO = numericalStatisticsMapper.toDto(numericalStatistics);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNumericalStatisticsMockMvc.perform(post("/api/numerical-statistics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(numericalStatisticsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NumericalStatistics in the database
        List<NumericalStatistics> numericalStatisticsList = numericalStatisticsRepository.findAll();
        assertThat(numericalStatisticsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllNumericalStatistics() throws Exception {
        // Initialize the database
        numericalStatisticsRepository.save(numericalStatistics);

        // Get all the numericalStatisticsList
        restNumericalStatisticsMockMvc.perform(get("/api/numerical-statistics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(numericalStatistics.getId())))
            .andExpect(jsonPath("$.[*].nrOfCharacters").value(hasItem(DEFAULT_NR_OF_CHARACTERS)))
            .andExpect(jsonPath("$.[*].nrOfNonBlankCharacters").value(hasItem(DEFAULT_NR_OF_NON_BLANK_CHARACTERS)))
            .andExpect(jsonPath("$.[*].nrOfWords").value(hasItem(DEFAULT_NR_OF_WORDS)))
            .andExpect(jsonPath("$.[*].nrOfDistinctWords").value(hasItem(DEFAULT_NR_OF_DISTINCT_WORDS)))
            .andExpect(jsonPath("$.[*].nrOfStopWords").value(hasItem(DEFAULT_NR_OF_STOP_WORDS)))
            .andExpect(jsonPath("$.[*].nrOfErrors").value(hasItem(DEFAULT_NR_OF_ERRORS)))
            .andExpect(jsonPath("$.[*].nrOfFunctionalWords").value(hasItem(DEFAULT_NR_OF_FUNCTIONAL_WORDS)))
            .andExpect(jsonPath("$.[*].nrOfDistinctFunctionalWords").value(hasItem(DEFAULT_NR_OF_DISTINCT_FUNCTIONAL_WORDS)))
            .andExpect(jsonPath("$.[*].nrOfContentWords").value(hasItem(DEFAULT_NR_OF_CONTENT_WORDS)))
            .andExpect(jsonPath("$.[*].nrOfDistinctContentWords").value(hasItem(DEFAULT_NR_OF_DISTINCT_CONTENT_WORDS)))
            .andExpect(jsonPath("$.[*].nrOfDistinctLemmas").value(hasItem(DEFAULT_NR_OF_DISTINCT_LEMMAS)))
            .andExpect(jsonPath("$.[*].nrOfSentences").value(hasItem(DEFAULT_NR_OF_SENTENCES)))
            .andExpect(jsonPath("$.[*].avgWordLength").value(hasItem(DEFAULT_AVG_WORD_LENGTH.doubleValue())))
            .andExpect(jsonPath("$.[*].avgNonStopWordLength").value(hasItem(DEFAULT_AVG_NON_STOP_WORD_LENGTH.doubleValue())))
            .andExpect(jsonPath("$.[*].avgFunctionalWordLength").value(hasItem(DEFAULT_AVG_FUNCTIONAL_WORD_LENGTH.doubleValue())))
            .andExpect(jsonPath("$.[*].avgContentWordLength").value(hasItem(DEFAULT_AVG_CONTENT_WORD_LENGTH.doubleValue())));
    }
    
    @Test
    public void getNumericalStatistics() throws Exception {
        // Initialize the database
        numericalStatisticsRepository.save(numericalStatistics);

        // Get the numericalStatistics
        restNumericalStatisticsMockMvc.perform(get("/api/numerical-statistics/{id}", numericalStatistics.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(numericalStatistics.getId()))
            .andExpect(jsonPath("$.nrOfCharacters").value(DEFAULT_NR_OF_CHARACTERS))
            .andExpect(jsonPath("$.nrOfNonBlankCharacters").value(DEFAULT_NR_OF_NON_BLANK_CHARACTERS))
            .andExpect(jsonPath("$.nrOfWords").value(DEFAULT_NR_OF_WORDS))
            .andExpect(jsonPath("$.nrOfDistinctWords").value(DEFAULT_NR_OF_DISTINCT_WORDS))
            .andExpect(jsonPath("$.nrOfStopWords").value(DEFAULT_NR_OF_STOP_WORDS))
            .andExpect(jsonPath("$.nrOfErrors").value(DEFAULT_NR_OF_ERRORS))
            .andExpect(jsonPath("$.nrOfFunctionalWords").value(DEFAULT_NR_OF_FUNCTIONAL_WORDS))
            .andExpect(jsonPath("$.nrOfDistinctFunctionalWords").value(DEFAULT_NR_OF_DISTINCT_FUNCTIONAL_WORDS))
            .andExpect(jsonPath("$.nrOfContentWords").value(DEFAULT_NR_OF_CONTENT_WORDS))
            .andExpect(jsonPath("$.nrOfDistinctContentWords").value(DEFAULT_NR_OF_DISTINCT_CONTENT_WORDS))
            .andExpect(jsonPath("$.nrOfDistinctLemmas").value(DEFAULT_NR_OF_DISTINCT_LEMMAS))
            .andExpect(jsonPath("$.nrOfSentences").value(DEFAULT_NR_OF_SENTENCES))
            .andExpect(jsonPath("$.avgWordLength").value(DEFAULT_AVG_WORD_LENGTH.doubleValue()))
            .andExpect(jsonPath("$.avgNonStopWordLength").value(DEFAULT_AVG_NON_STOP_WORD_LENGTH.doubleValue()))
            .andExpect(jsonPath("$.avgFunctionalWordLength").value(DEFAULT_AVG_FUNCTIONAL_WORD_LENGTH.doubleValue()))
            .andExpect(jsonPath("$.avgContentWordLength").value(DEFAULT_AVG_CONTENT_WORD_LENGTH.doubleValue()));
    }
    @Test
    public void getNonExistingNumericalStatistics() throws Exception {
        // Get the numericalStatistics
        restNumericalStatisticsMockMvc.perform(get("/api/numerical-statistics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateNumericalStatistics() throws Exception {
        // Initialize the database
        numericalStatisticsRepository.save(numericalStatistics);

        int databaseSizeBeforeUpdate = numericalStatisticsRepository.findAll().size();

        // Update the numericalStatistics
        NumericalStatistics updatedNumericalStatistics = numericalStatisticsRepository.findById(numericalStatistics.getId()).get();
        updatedNumericalStatistics
            .nrOfCharacters(UPDATED_NR_OF_CHARACTERS)
            .nrOfNonBlankCharacters(UPDATED_NR_OF_NON_BLANK_CHARACTERS)
            .nrOfWords(UPDATED_NR_OF_WORDS)
            .nrOfDistinctWords(UPDATED_NR_OF_DISTINCT_WORDS)
            .nrOfStopWords(UPDATED_NR_OF_STOP_WORDS)
            .nrOfErrors(UPDATED_NR_OF_ERRORS)
            .nrOfFunctionalWords(UPDATED_NR_OF_FUNCTIONAL_WORDS)
            .nrOfDistinctFunctionalWords(UPDATED_NR_OF_DISTINCT_FUNCTIONAL_WORDS)
            .nrOfContentWords(UPDATED_NR_OF_CONTENT_WORDS)
            .nrOfDistinctContentWords(UPDATED_NR_OF_DISTINCT_CONTENT_WORDS)
            .nrOfDistinctLemmas(UPDATED_NR_OF_DISTINCT_LEMMAS)
            .nrOfSentences(UPDATED_NR_OF_SENTENCES)
            .avgWordLength(UPDATED_AVG_WORD_LENGTH)
            .avgNonStopWordLength(UPDATED_AVG_NON_STOP_WORD_LENGTH)
            .avgFunctionalWordLength(UPDATED_AVG_FUNCTIONAL_WORD_LENGTH)
            .avgContentWordLength(UPDATED_AVG_CONTENT_WORD_LENGTH);
        NumericalStatisticsDTO numericalStatisticsDTO = numericalStatisticsMapper.toDto(updatedNumericalStatistics);

        restNumericalStatisticsMockMvc.perform(put("/api/numerical-statistics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(numericalStatisticsDTO)))
            .andExpect(status().isOk());

        // Validate the NumericalStatistics in the database
        List<NumericalStatistics> numericalStatisticsList = numericalStatisticsRepository.findAll();
        assertThat(numericalStatisticsList).hasSize(databaseSizeBeforeUpdate);
        NumericalStatistics testNumericalStatistics = numericalStatisticsList.get(numericalStatisticsList.size() - 1);
        assertThat(testNumericalStatistics.getNrOfCharacters()).isEqualTo(UPDATED_NR_OF_CHARACTERS);
        assertThat(testNumericalStatistics.getNrOfNonBlankCharacters()).isEqualTo(UPDATED_NR_OF_NON_BLANK_CHARACTERS);
        assertThat(testNumericalStatistics.getNrOfWords()).isEqualTo(UPDATED_NR_OF_WORDS);
        assertThat(testNumericalStatistics.getNrOfDistinctWords()).isEqualTo(UPDATED_NR_OF_DISTINCT_WORDS);
        assertThat(testNumericalStatistics.getNrOfStopWords()).isEqualTo(UPDATED_NR_OF_STOP_WORDS);
        assertThat(testNumericalStatistics.getNrOfErrors()).isEqualTo(UPDATED_NR_OF_ERRORS);
        assertThat(testNumericalStatistics.getNrOfFunctionalWords()).isEqualTo(UPDATED_NR_OF_FUNCTIONAL_WORDS);
        assertThat(testNumericalStatistics.getNrOfDistinctFunctionalWords()).isEqualTo(UPDATED_NR_OF_DISTINCT_FUNCTIONAL_WORDS);
        assertThat(testNumericalStatistics.getNrOfContentWords()).isEqualTo(UPDATED_NR_OF_CONTENT_WORDS);
        assertThat(testNumericalStatistics.getNrOfDistinctContentWords()).isEqualTo(UPDATED_NR_OF_DISTINCT_CONTENT_WORDS);
        assertThat(testNumericalStatistics.getNrOfDistinctLemmas()).isEqualTo(UPDATED_NR_OF_DISTINCT_LEMMAS);
        assertThat(testNumericalStatistics.getNrOfSentences()).isEqualTo(UPDATED_NR_OF_SENTENCES);
        assertThat(testNumericalStatistics.getAvgWordLength()).isEqualTo(UPDATED_AVG_WORD_LENGTH);
        assertThat(testNumericalStatistics.getAvgNonStopWordLength()).isEqualTo(UPDATED_AVG_NON_STOP_WORD_LENGTH);
        assertThat(testNumericalStatistics.getAvgFunctionalWordLength()).isEqualTo(UPDATED_AVG_FUNCTIONAL_WORD_LENGTH);
        assertThat(testNumericalStatistics.getAvgContentWordLength()).isEqualTo(UPDATED_AVG_CONTENT_WORD_LENGTH);
    }

    @Test
    public void updateNonExistingNumericalStatistics() throws Exception {
        int databaseSizeBeforeUpdate = numericalStatisticsRepository.findAll().size();

        // Create the NumericalStatistics
        NumericalStatisticsDTO numericalStatisticsDTO = numericalStatisticsMapper.toDto(numericalStatistics);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNumericalStatisticsMockMvc.perform(put("/api/numerical-statistics").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(numericalStatisticsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NumericalStatistics in the database
        List<NumericalStatistics> numericalStatisticsList = numericalStatisticsRepository.findAll();
        assertThat(numericalStatisticsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteNumericalStatistics() throws Exception {
        // Initialize the database
        numericalStatisticsRepository.save(numericalStatistics);

        int databaseSizeBeforeDelete = numericalStatisticsRepository.findAll().size();

        // Delete the numericalStatistics
        restNumericalStatisticsMockMvc.perform(delete("/api/numerical-statistics/{id}", numericalStatistics.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NumericalStatistics> numericalStatisticsList = numericalStatisticsRepository.findAll();
        assertThat(numericalStatisticsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
