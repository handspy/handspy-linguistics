package pt.up.hs.linguistics.web.rest;

import pt.up.hs.linguistics.LinguisticsApp;
import pt.up.hs.linguistics.config.SecurityBeanOverrideConfiguration;
import pt.up.hs.linguistics.domain.Analysis;
import pt.up.hs.linguistics.repository.AnalysisRepository;
import pt.up.hs.linguistics.service.AnalysisService;
import pt.up.hs.linguistics.service.dto.AnalysisDTO;
import pt.up.hs.linguistics.service.mapper.AnalysisMapper;

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

import pt.up.hs.linguistics.domain.enumeration.Status;
/**
 * Integration tests for the {@link AnalysisResource} REST controller.
 */
@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, LinguisticsApp.class })
@AutoConfigureMockMvc
@WithMockUser
public class AnalysisResourceIT {

    private static final Long DEFAULT_TEXT_ID = 1L;
    private static final Long UPDATED_TEXT_ID = 2L;

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.QUEUED;
    private static final Status UPDATED_STATUS = Status.PROCESSING;

    @Autowired
    private AnalysisRepository analysisRepository;

    @Autowired
    private AnalysisMapper analysisMapper;

    @Autowired
    private AnalysisService analysisService;

    @Autowired
    private MockMvc restAnalysisMockMvc;

    private Analysis analysis;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Analysis createEntity() {
        Analysis analysis = new Analysis()
            .textId(DEFAULT_TEXT_ID)
            .text(DEFAULT_TEXT)
            .language(DEFAULT_LANGUAGE)
            .status(DEFAULT_STATUS);
        return analysis;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Analysis createUpdatedEntity() {
        Analysis analysis = new Analysis()
            .textId(UPDATED_TEXT_ID)
            .text(UPDATED_TEXT)
            .language(UPDATED_LANGUAGE)
            .status(UPDATED_STATUS);
        return analysis;
    }

    @BeforeEach
    public void initTest() {
        analysisRepository.deleteAll();
        analysis = createEntity();
    }

    @Test
    public void createAnalysis() throws Exception {
        int databaseSizeBeforeCreate = analysisRepository.findAll().size();
        // Create the Analysis
        AnalysisDTO analysisDTO = analysisMapper.toDto(analysis);
        restAnalysisMockMvc.perform(post("/api/analyses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(analysisDTO)))
            .andExpect(status().isCreated());

        // Validate the Analysis in the database
        List<Analysis> analysisList = analysisRepository.findAll();
        assertThat(analysisList).hasSize(databaseSizeBeforeCreate + 1);
        Analysis testAnalysis = analysisList.get(analysisList.size() - 1);
        assertThat(testAnalysis.getTextId()).isEqualTo(DEFAULT_TEXT_ID);
        assertThat(testAnalysis.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testAnalysis.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testAnalysis.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    public void createAnalysisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = analysisRepository.findAll().size();

        // Create the Analysis with an existing ID
        analysis.setId("existing_id");
        AnalysisDTO analysisDTO = analysisMapper.toDto(analysis);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnalysisMockMvc.perform(post("/api/analyses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(analysisDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Analysis in the database
        List<Analysis> analysisList = analysisRepository.findAll();
        assertThat(analysisList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllAnalyses() throws Exception {
        // Initialize the database
        analysisRepository.save(analysis);

        // Get all the analysisList
        restAnalysisMockMvc.perform(get("/api/analyses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(analysis.getId())))
            .andExpect(jsonPath("$.[*].textId").value(hasItem(DEFAULT_TEXT_ID.intValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    public void getAnalysis() throws Exception {
        // Initialize the database
        analysisRepository.save(analysis);

        // Get the analysis
        restAnalysisMockMvc.perform(get("/api/analyses/{id}", analysis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(analysis.getId()))
            .andExpect(jsonPath("$.textId").value(DEFAULT_TEXT_ID.intValue()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }
    @Test
    public void getNonExistingAnalysis() throws Exception {
        // Get the analysis
        restAnalysisMockMvc.perform(get("/api/analyses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAnalysis() throws Exception {
        // Initialize the database
        analysisRepository.save(analysis);

        int databaseSizeBeforeUpdate = analysisRepository.findAll().size();

        // Update the analysis
        Analysis updatedAnalysis = analysisRepository.findById(analysis.getId()).get();
        updatedAnalysis
            .textId(UPDATED_TEXT_ID)
            .text(UPDATED_TEXT)
            .language(UPDATED_LANGUAGE)
            .status(UPDATED_STATUS);
        AnalysisDTO analysisDTO = analysisMapper.toDto(updatedAnalysis);

        restAnalysisMockMvc.perform(put("/api/analyses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(analysisDTO)))
            .andExpect(status().isOk());

        // Validate the Analysis in the database
        List<Analysis> analysisList = analysisRepository.findAll();
        assertThat(analysisList).hasSize(databaseSizeBeforeUpdate);
        Analysis testAnalysis = analysisList.get(analysisList.size() - 1);
        assertThat(testAnalysis.getTextId()).isEqualTo(UPDATED_TEXT_ID);
        assertThat(testAnalysis.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testAnalysis.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testAnalysis.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    public void updateNonExistingAnalysis() throws Exception {
        int databaseSizeBeforeUpdate = analysisRepository.findAll().size();

        // Create the Analysis
        AnalysisDTO analysisDTO = analysisMapper.toDto(analysis);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnalysisMockMvc.perform(put("/api/analyses").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(analysisDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Analysis in the database
        List<Analysis> analysisList = analysisRepository.findAll();
        assertThat(analysisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteAnalysis() throws Exception {
        // Initialize the database
        analysisRepository.save(analysis);

        int databaseSizeBeforeDelete = analysisRepository.findAll().size();

        // Delete the analysis
        restAnalysisMockMvc.perform(delete("/api/analyses/{id}", analysis.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Analysis> analysisList = analysisRepository.findAll();
        assertThat(analysisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
