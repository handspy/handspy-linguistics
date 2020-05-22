package pt.up.hs.linguistics.web.rest;

import pt.up.hs.linguistics.LinguisticsApp;
import pt.up.hs.linguistics.config.SecurityBeanOverrideConfiguration;
import pt.up.hs.linguistics.domain.WordFrequency;
import pt.up.hs.linguistics.domain.Analysis;
import pt.up.hs.linguistics.repository.WordFrequencyRepository;
import pt.up.hs.linguistics.service.WordFrequencyService;
import pt.up.hs.linguistics.service.dto.WordFrequencyDTO;
import pt.up.hs.linguistics.service.mapper.WordFrequencyMapper;

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
 * Integration tests for the {@link WordFrequencyResource} REST controller.
 */
@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, LinguisticsApp.class })
@AutoConfigureMockMvc
@WithMockUser
public class WordFrequencyResourceIT {

    private static final String DEFAULT_WORD = "AAAAAAAAAA";
    private static final String UPDATED_WORD = "BBBBBBBBBB";

    private static final Integer DEFAULT_FREQUENCY = 1;
    private static final Integer UPDATED_FREQUENCY = 2;

    private static final Boolean DEFAULT_CONTENT = false;
    private static final Boolean UPDATED_CONTENT = true;

    private static final Boolean DEFAULT_FUNCTIONAL = false;
    private static final Boolean UPDATED_FUNCTIONAL = true;

    @Autowired
    private WordFrequencyRepository wordFrequencyRepository;

    @Autowired
    private WordFrequencyMapper wordFrequencyMapper;

    @Autowired
    private WordFrequencyService wordFrequencyService;

    @Autowired
    private MockMvc restWordFrequencyMockMvc;

    private WordFrequency wordFrequency;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WordFrequency createEntity() {
        WordFrequency wordFrequency = new WordFrequency()
            .word(DEFAULT_WORD)
            .frequency(DEFAULT_FREQUENCY)
            .content(DEFAULT_CONTENT)
            .functional(DEFAULT_FUNCTIONAL);
        // Add required entity
        Analysis analysis;
        analysis = AnalysisResourceIT.createEntity();
        analysis.setId("fixed-id-for-tests");
        wordFrequency.setAnalysis(analysis);
        return wordFrequency;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WordFrequency createUpdatedEntity() {
        WordFrequency wordFrequency = new WordFrequency()
            .word(UPDATED_WORD)
            .frequency(UPDATED_FREQUENCY)
            .content(UPDATED_CONTENT)
            .functional(UPDATED_FUNCTIONAL);
        // Add required entity
        Analysis analysis;
        analysis = AnalysisResourceIT.createUpdatedEntity();
        analysis.setId("fixed-id-for-tests");
        wordFrequency.setAnalysis(analysis);
        return wordFrequency;
    }

    @BeforeEach
    public void initTest() {
        wordFrequencyRepository.deleteAll();
        wordFrequency = createEntity();
    }

    @Test
    public void createWordFrequency() throws Exception {
        int databaseSizeBeforeCreate = wordFrequencyRepository.findAll().size();
        // Create the WordFrequency
        WordFrequencyDTO wordFrequencyDTO = wordFrequencyMapper.toDto(wordFrequency);
        restWordFrequencyMockMvc.perform(post("/api/word-frequencies").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wordFrequencyDTO)))
            .andExpect(status().isCreated());

        // Validate the WordFrequency in the database
        List<WordFrequency> wordFrequencyList = wordFrequencyRepository.findAll();
        assertThat(wordFrequencyList).hasSize(databaseSizeBeforeCreate + 1);
        WordFrequency testWordFrequency = wordFrequencyList.get(wordFrequencyList.size() - 1);
        assertThat(testWordFrequency.getWord()).isEqualTo(DEFAULT_WORD);
        assertThat(testWordFrequency.getFrequency()).isEqualTo(DEFAULT_FREQUENCY);
        assertThat(testWordFrequency.isContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testWordFrequency.isFunctional()).isEqualTo(DEFAULT_FUNCTIONAL);
    }

    @Test
    public void createWordFrequencyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = wordFrequencyRepository.findAll().size();

        // Create the WordFrequency with an existing ID
        wordFrequency.setId("existing_id");
        WordFrequencyDTO wordFrequencyDTO = wordFrequencyMapper.toDto(wordFrequency);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWordFrequencyMockMvc.perform(post("/api/word-frequencies").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wordFrequencyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WordFrequency in the database
        List<WordFrequency> wordFrequencyList = wordFrequencyRepository.findAll();
        assertThat(wordFrequencyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllWordFrequencies() throws Exception {
        // Initialize the database
        wordFrequencyRepository.save(wordFrequency);

        // Get all the wordFrequencyList
        restWordFrequencyMockMvc.perform(get("/api/word-frequencies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wordFrequency.getId())))
            .andExpect(jsonPath("$.[*].word").value(hasItem(DEFAULT_WORD)))
            .andExpect(jsonPath("$.[*].frequency").value(hasItem(DEFAULT_FREQUENCY)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.booleanValue())))
            .andExpect(jsonPath("$.[*].functional").value(hasItem(DEFAULT_FUNCTIONAL.booleanValue())));
    }
    
    @Test
    public void getWordFrequency() throws Exception {
        // Initialize the database
        wordFrequencyRepository.save(wordFrequency);

        // Get the wordFrequency
        restWordFrequencyMockMvc.perform(get("/api/word-frequencies/{id}", wordFrequency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(wordFrequency.getId()))
            .andExpect(jsonPath("$.word").value(DEFAULT_WORD))
            .andExpect(jsonPath("$.frequency").value(DEFAULT_FREQUENCY))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.booleanValue()))
            .andExpect(jsonPath("$.functional").value(DEFAULT_FUNCTIONAL.booleanValue()));
    }
    @Test
    public void getNonExistingWordFrequency() throws Exception {
        // Get the wordFrequency
        restWordFrequencyMockMvc.perform(get("/api/word-frequencies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateWordFrequency() throws Exception {
        // Initialize the database
        wordFrequencyRepository.save(wordFrequency);

        int databaseSizeBeforeUpdate = wordFrequencyRepository.findAll().size();

        // Update the wordFrequency
        WordFrequency updatedWordFrequency = wordFrequencyRepository.findById(wordFrequency.getId()).get();
        updatedWordFrequency
            .word(UPDATED_WORD)
            .frequency(UPDATED_FREQUENCY)
            .content(UPDATED_CONTENT)
            .functional(UPDATED_FUNCTIONAL);
        WordFrequencyDTO wordFrequencyDTO = wordFrequencyMapper.toDto(updatedWordFrequency);

        restWordFrequencyMockMvc.perform(put("/api/word-frequencies").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wordFrequencyDTO)))
            .andExpect(status().isOk());

        // Validate the WordFrequency in the database
        List<WordFrequency> wordFrequencyList = wordFrequencyRepository.findAll();
        assertThat(wordFrequencyList).hasSize(databaseSizeBeforeUpdate);
        WordFrequency testWordFrequency = wordFrequencyList.get(wordFrequencyList.size() - 1);
        assertThat(testWordFrequency.getWord()).isEqualTo(UPDATED_WORD);
        assertThat(testWordFrequency.getFrequency()).isEqualTo(UPDATED_FREQUENCY);
        assertThat(testWordFrequency.isContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testWordFrequency.isFunctional()).isEqualTo(UPDATED_FUNCTIONAL);
    }

    @Test
    public void updateNonExistingWordFrequency() throws Exception {
        int databaseSizeBeforeUpdate = wordFrequencyRepository.findAll().size();

        // Create the WordFrequency
        WordFrequencyDTO wordFrequencyDTO = wordFrequencyMapper.toDto(wordFrequency);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWordFrequencyMockMvc.perform(put("/api/word-frequencies").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(wordFrequencyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WordFrequency in the database
        List<WordFrequency> wordFrequencyList = wordFrequencyRepository.findAll();
        assertThat(wordFrequencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteWordFrequency() throws Exception {
        // Initialize the database
        wordFrequencyRepository.save(wordFrequency);

        int databaseSizeBeforeDelete = wordFrequencyRepository.findAll().size();

        // Delete the wordFrequency
        restWordFrequencyMockMvc.perform(delete("/api/word-frequencies/{id}", wordFrequency.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WordFrequency> wordFrequencyList = wordFrequencyRepository.findAll();
        assertThat(wordFrequencyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
