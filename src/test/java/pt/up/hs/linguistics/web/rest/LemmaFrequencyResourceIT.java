package pt.up.hs.linguistics.web.rest;

import pt.up.hs.linguistics.LinguisticsApp;
import pt.up.hs.linguistics.config.SecurityBeanOverrideConfiguration;
import pt.up.hs.linguistics.domain.LemmaFrequency;
import pt.up.hs.linguistics.domain.Analysis;
import pt.up.hs.linguistics.repository.LemmaFrequencyRepository;
import pt.up.hs.linguistics.service.LemmaFrequencyService;
import pt.up.hs.linguistics.service.dto.LemmaFrequencyDTO;
import pt.up.hs.linguistics.service.mapper.LemmaFrequencyMapper;

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
 * Integration tests for the {@link LemmaFrequencyResource} REST controller.
 */
@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, LinguisticsApp.class })
@AutoConfigureMockMvc
@WithMockUser
public class LemmaFrequencyResourceIT {

    private static final String DEFAULT_LEMMA = "AAAAAAAAAA";
    private static final String UPDATED_LEMMA = "BBBBBBBBBB";

    private static final Integer DEFAULT_FREQUENCY = 1;
    private static final Integer UPDATED_FREQUENCY = 2;

    @Autowired
    private LemmaFrequencyRepository lemmaFrequencyRepository;

    @Autowired
    private LemmaFrequencyMapper lemmaFrequencyMapper;

    @Autowired
    private LemmaFrequencyService lemmaFrequencyService;

    @Autowired
    private MockMvc restLemmaFrequencyMockMvc;

    private LemmaFrequency lemmaFrequency;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LemmaFrequency createEntity() {
        LemmaFrequency lemmaFrequency = new LemmaFrequency()
            .lemma(DEFAULT_LEMMA)
            .frequency(DEFAULT_FREQUENCY);
        // Add required entity
        Analysis analysis;
        analysis = AnalysisResourceIT.createEntity();
        analysis.setId("fixed-id-for-tests");
        lemmaFrequency.setAnalysis(analysis);
        return lemmaFrequency;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LemmaFrequency createUpdatedEntity() {
        LemmaFrequency lemmaFrequency = new LemmaFrequency()
            .lemma(UPDATED_LEMMA)
            .frequency(UPDATED_FREQUENCY);
        // Add required entity
        Analysis analysis;
        analysis = AnalysisResourceIT.createUpdatedEntity();
        analysis.setId("fixed-id-for-tests");
        lemmaFrequency.setAnalysis(analysis);
        return lemmaFrequency;
    }

    @BeforeEach
    public void initTest() {
        lemmaFrequencyRepository.deleteAll();
        lemmaFrequency = createEntity();
    }

    @Test
    public void createLemmaFrequency() throws Exception {
        int databaseSizeBeforeCreate = lemmaFrequencyRepository.findAll().size();
        // Create the LemmaFrequency
        LemmaFrequencyDTO lemmaFrequencyDTO = lemmaFrequencyMapper.toDto(lemmaFrequency);
        restLemmaFrequencyMockMvc.perform(post("/api/lemma-frequencies").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lemmaFrequencyDTO)))
            .andExpect(status().isCreated());

        // Validate the LemmaFrequency in the database
        List<LemmaFrequency> lemmaFrequencyList = lemmaFrequencyRepository.findAll();
        assertThat(lemmaFrequencyList).hasSize(databaseSizeBeforeCreate + 1);
        LemmaFrequency testLemmaFrequency = lemmaFrequencyList.get(lemmaFrequencyList.size() - 1);
        assertThat(testLemmaFrequency.getLemma()).isEqualTo(DEFAULT_LEMMA);
        assertThat(testLemmaFrequency.getFrequency()).isEqualTo(DEFAULT_FREQUENCY);
    }

    @Test
    public void createLemmaFrequencyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lemmaFrequencyRepository.findAll().size();

        // Create the LemmaFrequency with an existing ID
        lemmaFrequency.setId("existing_id");
        LemmaFrequencyDTO lemmaFrequencyDTO = lemmaFrequencyMapper.toDto(lemmaFrequency);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLemmaFrequencyMockMvc.perform(post("/api/lemma-frequencies").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lemmaFrequencyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LemmaFrequency in the database
        List<LemmaFrequency> lemmaFrequencyList = lemmaFrequencyRepository.findAll();
        assertThat(lemmaFrequencyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllLemmaFrequencies() throws Exception {
        // Initialize the database
        lemmaFrequencyRepository.save(lemmaFrequency);

        // Get all the lemmaFrequencyList
        restLemmaFrequencyMockMvc.perform(get("/api/lemma-frequencies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lemmaFrequency.getId())))
            .andExpect(jsonPath("$.[*].lemma").value(hasItem(DEFAULT_LEMMA)))
            .andExpect(jsonPath("$.[*].frequency").value(hasItem(DEFAULT_FREQUENCY)));
    }
    
    @Test
    public void getLemmaFrequency() throws Exception {
        // Initialize the database
        lemmaFrequencyRepository.save(lemmaFrequency);

        // Get the lemmaFrequency
        restLemmaFrequencyMockMvc.perform(get("/api/lemma-frequencies/{id}", lemmaFrequency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lemmaFrequency.getId()))
            .andExpect(jsonPath("$.lemma").value(DEFAULT_LEMMA))
            .andExpect(jsonPath("$.frequency").value(DEFAULT_FREQUENCY));
    }
    @Test
    public void getNonExistingLemmaFrequency() throws Exception {
        // Get the lemmaFrequency
        restLemmaFrequencyMockMvc.perform(get("/api/lemma-frequencies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateLemmaFrequency() throws Exception {
        // Initialize the database
        lemmaFrequencyRepository.save(lemmaFrequency);

        int databaseSizeBeforeUpdate = lemmaFrequencyRepository.findAll().size();

        // Update the lemmaFrequency
        LemmaFrequency updatedLemmaFrequency = lemmaFrequencyRepository.findById(lemmaFrequency.getId()).get();
        updatedLemmaFrequency
            .lemma(UPDATED_LEMMA)
            .frequency(UPDATED_FREQUENCY);
        LemmaFrequencyDTO lemmaFrequencyDTO = lemmaFrequencyMapper.toDto(updatedLemmaFrequency);

        restLemmaFrequencyMockMvc.perform(put("/api/lemma-frequencies").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lemmaFrequencyDTO)))
            .andExpect(status().isOk());

        // Validate the LemmaFrequency in the database
        List<LemmaFrequency> lemmaFrequencyList = lemmaFrequencyRepository.findAll();
        assertThat(lemmaFrequencyList).hasSize(databaseSizeBeforeUpdate);
        LemmaFrequency testLemmaFrequency = lemmaFrequencyList.get(lemmaFrequencyList.size() - 1);
        assertThat(testLemmaFrequency.getLemma()).isEqualTo(UPDATED_LEMMA);
        assertThat(testLemmaFrequency.getFrequency()).isEqualTo(UPDATED_FREQUENCY);
    }

    @Test
    public void updateNonExistingLemmaFrequency() throws Exception {
        int databaseSizeBeforeUpdate = lemmaFrequencyRepository.findAll().size();

        // Create the LemmaFrequency
        LemmaFrequencyDTO lemmaFrequencyDTO = lemmaFrequencyMapper.toDto(lemmaFrequency);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLemmaFrequencyMockMvc.perform(put("/api/lemma-frequencies").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lemmaFrequencyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LemmaFrequency in the database
        List<LemmaFrequency> lemmaFrequencyList = lemmaFrequencyRepository.findAll();
        assertThat(lemmaFrequencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteLemmaFrequency() throws Exception {
        // Initialize the database
        lemmaFrequencyRepository.save(lemmaFrequency);

        int databaseSizeBeforeDelete = lemmaFrequencyRepository.findAll().size();

        // Delete the lemmaFrequency
        restLemmaFrequencyMockMvc.perform(delete("/api/lemma-frequencies/{id}", lemmaFrequency.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LemmaFrequency> lemmaFrequencyList = lemmaFrequencyRepository.findAll();
        assertThat(lemmaFrequencyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
