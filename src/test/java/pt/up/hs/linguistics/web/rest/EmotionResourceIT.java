package pt.up.hs.linguistics.web.rest;

import pt.up.hs.linguistics.LinguisticsApp;
import pt.up.hs.linguistics.config.SecurityBeanOverrideConfiguration;
import pt.up.hs.linguistics.domain.Emotion;
import pt.up.hs.linguistics.domain.Analysis;
import pt.up.hs.linguistics.repository.AnalysisRepository;
import pt.up.hs.linguistics.repository.EmotionRepository;
import pt.up.hs.linguistics.service.EmotionService;
import pt.up.hs.linguistics.service.dto.EmotionDTO;
import pt.up.hs.linguistics.service.mapper.EmotionMapper;

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

import pt.up.hs.linguistics.domain.enumeration.PrimaryEmotion;
import pt.up.hs.linguistics.domain.enumeration.SecondaryEmotion;
import pt.up.hs.linguistics.domain.enumeration.TertiaryEmotion;

/**
 * Integration tests for the {@link EmotionResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, LinguisticsApp.class})
@AutoConfigureMockMvc
@WithMockUser
public class EmotionResourceIT {

    private static final Long DEFAULT_PROJECT_ID = 1L;
    private static final Long DEFAULT_TEXT_ID = 1001L;
    private static final String DEFAULT_ANALYSIS_ID = "fixed-id-for-tests";

    private static final PrimaryEmotion DEFAULT_PRIMARY = PrimaryEmotion.SAFETY;
    private static final PrimaryEmotion UPDATED_PRIMARY = PrimaryEmotion.COMFORT;

    private static final SecondaryEmotion DEFAULT_SECONDARY = SecondaryEmotion.COURAGE;
    private static final SecondaryEmotion UPDATED_SECONDARY = SecondaryEmotion.CALM;

    private static final TertiaryEmotion DEFAULT_TERTIARY = TertiaryEmotion.AUDACITY;
    private static final TertiaryEmotion UPDATED_TERTIARY = TertiaryEmotion.EXTROVERSION;

    private static final Integer DEFAULT_START = 1;
    private static final Integer UPDATED_START = 2;

    private static final Integer DEFAULT_SIZE = 1;
    private static final Integer UPDATED_SIZE = 2;

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private AnalysisRepository analysisRepository;

    @Autowired
    private EmotionRepository emotionRepository;

    @Autowired
    private EmotionMapper emotionMapper;

    @Autowired
    private EmotionService emotionService;

    @Autowired
    private MockMvc restEmotionMockMvc;

    private Analysis analysis;
    private Emotion emotion;

    public static Analysis createAnalysisEntity() {
        Analysis analysis = AnalysisResourceIT.createEntity();
        analysis.setId(DEFAULT_ANALYSIS_ID);
        analysis.setProjectId(DEFAULT_PROJECT_ID);
        analysis.setTextId(DEFAULT_TEXT_ID);
        return analysis;
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     * @param analysis required entity
     */
    public static Emotion createEntity(Analysis analysis) {
        return new Emotion()
            .analysis(analysis)
            .primary(DEFAULT_PRIMARY)
            .secondary(DEFAULT_SECONDARY)
            .tertiary(DEFAULT_TERTIARY)
            .start(DEFAULT_START)
            .size(DEFAULT_SIZE)
            .note(DEFAULT_NOTE);
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     * @param analysis required entity
     */
    public static Emotion createUpdatedEntity(Analysis analysis) {
        return new Emotion()
            .analysis(analysis)
            .primary(UPDATED_PRIMARY)
            .secondary(UPDATED_SECONDARY)
            .tertiary(UPDATED_TERTIARY)
            .start(UPDATED_START)
            .size(UPDATED_SIZE)
            .note(UPDATED_NOTE);
    }

    @BeforeEach
    public void initTest() {
        analysisRepository.deleteAll();
        emotionRepository.deleteAll();
        analysis = analysisRepository.save(createAnalysisEntity());
        emotion = createEntity(analysis);
    }

    @Test
    public void createEmotion() throws Exception {
        int databaseSizeBeforeCreate = emotionRepository.findAll().size();
        // Create the Emotion
        EmotionDTO emotionDTO = emotionMapper.toDto(emotion);
        restEmotionMockMvc.perform(
            post("/api/projects/{projectId}/texts/{textId}/analyses/{analysisId}/emotions", DEFAULT_PROJECT_ID, DEFAULT_TEXT_ID, DEFAULT_ANALYSIS_ID)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(emotionDTO)))
            .andExpect(status().isCreated());

        // Validate the Emotion in the database
        List<Emotion> emotionList = emotionRepository.findAll();
        assertThat(emotionList).hasSize(databaseSizeBeforeCreate + 1);
        Emotion testEmotion = emotionList.get(emotionList.size() - 1);
        assertThat(testEmotion.getPrimary()).isEqualTo(DEFAULT_PRIMARY);
        assertThat(testEmotion.getSecondary()).isEqualTo(DEFAULT_SECONDARY);
        assertThat(testEmotion.getTertiary()).isEqualTo(DEFAULT_TERTIARY);
        assertThat(testEmotion.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testEmotion.getSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(testEmotion.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    public void createEmotionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emotionRepository.findAll().size();

        // Create the Emotion with an existing ID
        emotion.setId("existing_id");
        EmotionDTO emotionDTO = emotionMapper.toDto(emotion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmotionMockMvc.perform(
            post("/api/projects/{projectId}/texts/{textId}/analyses/{analysisId}/emotions", DEFAULT_PROJECT_ID, DEFAULT_TEXT_ID, DEFAULT_ANALYSIS_ID)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(emotionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Emotion in the database
        List<Emotion> emotionList = emotionRepository.findAll();
        assertThat(emotionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkPrimaryIsRequired() throws Exception {
        int databaseSizeBeforeTest = emotionRepository.findAll().size();
        // set the field null
        emotion.setPrimary(null);

        // Create the Emotion, which fails.
        EmotionDTO emotionDTO = emotionMapper.toDto(emotion);


        restEmotionMockMvc.perform(
            post("/api/projects/{projectId}/texts/{textId}/analyses/{analysisId}/emotions", DEFAULT_PROJECT_ID, DEFAULT_TEXT_ID, DEFAULT_ANALYSIS_ID)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(emotionDTO)))
            .andExpect(status().isBadRequest());

        List<Emotion> emotionList = emotionRepository.findAll();
        assertThat(emotionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkStartIsRequired() throws Exception {
        int databaseSizeBeforeTest = emotionRepository.findAll().size();
        // set the field null
        emotion.setStart(null);

        // Create the Emotion, which fails.
        EmotionDTO emotionDTO = emotionMapper.toDto(emotion);


        restEmotionMockMvc.perform(
            post("/api/projects/{projectId}/texts/{textId}/analyses/{analysisId}/emotions", DEFAULT_PROJECT_ID, DEFAULT_TEXT_ID, DEFAULT_ANALYSIS_ID)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(emotionDTO)))
            .andExpect(status().isBadRequest());

        List<Emotion> emotionList = emotionRepository.findAll();
        assertThat(emotionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkSizeIsRequired() throws Exception {
        int databaseSizeBeforeTest = emotionRepository.findAll().size();
        // set the field null
        emotion.setSize(null);

        // Create the Emotion, which fails.
        EmotionDTO emotionDTO = emotionMapper.toDto(emotion);


        restEmotionMockMvc.perform(
            post("/api/projects/{projectId}/texts/{textId}/analyses/{analysisId}/emotions", DEFAULT_PROJECT_ID, DEFAULT_TEXT_ID, DEFAULT_ANALYSIS_ID)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(emotionDTO))
        ).andExpect(status().isBadRequest());

        List<Emotion> emotionList = emotionRepository.findAll();
        assertThat(emotionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllEmotions() throws Exception {
        // Initialize the database
        emotionRepository.save(emotion);

        // Get all the emotionList
        restEmotionMockMvc.perform(
            get("/api/projects/{projectId}/texts/{textId}/analyses/{analysisId}/emotions?sort=id,desc", DEFAULT_PROJECT_ID, DEFAULT_TEXT_ID, DEFAULT_ANALYSIS_ID)
        )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emotion.getId())))
            .andExpect(jsonPath("$.[*].primary").value(hasItem(DEFAULT_PRIMARY.toString())))
            .andExpect(jsonPath("$.[*].secondary").value(hasItem(DEFAULT_SECONDARY.toString())))
            .andExpect(jsonPath("$.[*].tertiary").value(hasItem(DEFAULT_TERTIARY.toString())))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START)))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));
    }

    @Test
    public void getEmotion() throws Exception {
        // Initialize the database
        emotionRepository.save(emotion);

        // Get the emotion
        restEmotionMockMvc.perform(
            get("/api/projects/{projectId}/texts/{textId}/analyses/{analysisId}/emotions/{id}", DEFAULT_PROJECT_ID, DEFAULT_TEXT_ID, DEFAULT_ANALYSIS_ID, emotion.getId())
        )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(emotion.getId()))
            .andExpect(jsonPath("$.primary").value(DEFAULT_PRIMARY.toString()))
            .andExpect(jsonPath("$.secondary").value(DEFAULT_SECONDARY.toString()))
            .andExpect(jsonPath("$.tertiary").value(DEFAULT_TERTIARY.toString()))
            .andExpect(jsonPath("$.start").value(DEFAULT_START))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE));
    }

    @Test
    public void getNonExistingEmotion() throws Exception {
        // Get the emotion
        restEmotionMockMvc.perform(
            get("/api/projects/{projectId}/texts/{textId}/analyses/{analysisId}/emotions/{id}", DEFAULT_PROJECT_ID, DEFAULT_TEXT_ID, DEFAULT_ANALYSIS_ID, Long.MAX_VALUE)
        ).andExpect(status().isNotFound());
    }

    @Test
    public void updateEmotion() throws Exception {
        // Initialize the database
        emotionRepository.save(emotion);

        int databaseSizeBeforeUpdate = emotionRepository.findAll().size();

        // Update the emotion
        Emotion updatedEmotion = emotionRepository.findById(emotion.getId()).get();
        updatedEmotion
            .primary(UPDATED_PRIMARY)
            .secondary(UPDATED_SECONDARY)
            .tertiary(UPDATED_TERTIARY)
            .start(UPDATED_START)
            .size(UPDATED_SIZE)
            .note(UPDATED_NOTE);
        EmotionDTO emotionDTO = emotionMapper.toDto(updatedEmotion);

        restEmotionMockMvc.perform(
            put("/api/projects/{projectId}/texts/{textId}/analyses/{analysisId}/emotions", DEFAULT_PROJECT_ID, DEFAULT_TEXT_ID, DEFAULT_ANALYSIS_ID)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(emotionDTO))
        ).andExpect(status().isOk());

        // Validate the Emotion in the database
        List<Emotion> emotionList = emotionRepository.findAll();
        assertThat(emotionList).hasSize(databaseSizeBeforeUpdate);
        Emotion testEmotion = emotionList.get(emotionList.size() - 1);
        assertThat(testEmotion.getPrimary()).isEqualTo(UPDATED_PRIMARY);
        assertThat(testEmotion.getSecondary()).isEqualTo(UPDATED_SECONDARY);
        assertThat(testEmotion.getTertiary()).isEqualTo(UPDATED_TERTIARY);
        assertThat(testEmotion.getStart()).isEqualTo(UPDATED_START);
        assertThat(testEmotion.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testEmotion.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    public void updateNonExistingEmotion() throws Exception {
        int databaseSizeBeforeUpdate = emotionRepository.findAll().size();

        // Create the Emotion
        EmotionDTO emotionDTO = emotionMapper.toDto(emotion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmotionMockMvc.perform(
            put("/api/projects/{projectId}/texts/{textId}/analyses/{analysisId}/emotions", DEFAULT_PROJECT_ID, DEFAULT_TEXT_ID, DEFAULT_ANALYSIS_ID)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(emotionDTO))
        ).andExpect(status().isBadRequest());

        // Validate the Emotion in the database
        List<Emotion> emotionList = emotionRepository.findAll();
        assertThat(emotionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteEmotion() throws Exception {
        // Initialize the database
        emotionRepository.save(emotion);

        int databaseSizeBeforeDelete = emotionRepository.findAll().size();

        // Delete the emotion
        restEmotionMockMvc.perform(
            delete("/api/projects/{projectId}/texts/{textId}/analyses/{analysisId}/emotions/{id}", DEFAULT_PROJECT_ID, DEFAULT_TEXT_ID, DEFAULT_ANALYSIS_ID, emotion.getId()).with(csrf())
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Emotion> emotionList = emotionRepository.findAll();
        assertThat(emotionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
