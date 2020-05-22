package pt.up.hs.linguistics.web.rest;

import pt.up.hs.linguistics.LinguisticsApp;
import pt.up.hs.linguistics.config.SecurityBeanOverrideConfiguration;
import pt.up.hs.linguistics.domain.PartOfSpeech;
import pt.up.hs.linguistics.domain.Analysis;
import pt.up.hs.linguistics.repository.PartOfSpeechRepository;
import pt.up.hs.linguistics.service.PartOfSpeechService;
import pt.up.hs.linguistics.service.dto.PartOfSpeechDTO;
import pt.up.hs.linguistics.service.mapper.PartOfSpeechMapper;

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

import pt.up.hs.linguistics.domain.enumeration.PoSTag;
/**
 * Integration tests for the {@link PartOfSpeechResource} REST controller.
 */
@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, LinguisticsApp.class })
@AutoConfigureMockMvc
@WithMockUser
public class PartOfSpeechResourceIT {

    private static final PoSTag DEFAULT_TAG = PoSTag.ADJECTIVE;
    private static final PoSTag UPDATED_TAG = PoSTag.PREPOSITION;

    private static final Integer DEFAULT_START = 1;
    private static final Integer UPDATED_START = 2;

    private static final Integer DEFAULT_SIZE = 1;
    private static final Integer UPDATED_SIZE = 2;

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private PartOfSpeechRepository partOfSpeechRepository;

    @Autowired
    private PartOfSpeechMapper partOfSpeechMapper;

    @Autowired
    private PartOfSpeechService partOfSpeechService;

    @Autowired
    private MockMvc restPartOfSpeechMockMvc;

    private PartOfSpeech partOfSpeech;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartOfSpeech createEntity() {
        PartOfSpeech partOfSpeech = new PartOfSpeech()
            .tag(DEFAULT_TAG)
            .start(DEFAULT_START)
            .size(DEFAULT_SIZE)
            .note(DEFAULT_NOTE);
        // Add required entity
        Analysis analysis;
        analysis = AnalysisResourceIT.createEntity();
        analysis.setId("fixed-id-for-tests");
        partOfSpeech.setAnalysis(analysis);
        return partOfSpeech;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartOfSpeech createUpdatedEntity() {
        PartOfSpeech partOfSpeech = new PartOfSpeech()
            .tag(UPDATED_TAG)
            .start(UPDATED_START)
            .size(UPDATED_SIZE)
            .note(UPDATED_NOTE);
        // Add required entity
        Analysis analysis;
        analysis = AnalysisResourceIT.createUpdatedEntity();
        analysis.setId("fixed-id-for-tests");
        partOfSpeech.setAnalysis(analysis);
        return partOfSpeech;
    }

    @BeforeEach
    public void initTest() {
        partOfSpeechRepository.deleteAll();
        partOfSpeech = createEntity();
    }

    @Test
    public void createPartOfSpeech() throws Exception {
        int databaseSizeBeforeCreate = partOfSpeechRepository.findAll().size();
        // Create the PartOfSpeech
        PartOfSpeechDTO partOfSpeechDTO = partOfSpeechMapper.toDto(partOfSpeech);
        restPartOfSpeechMockMvc.perform(post("/api/part-of-speeches").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partOfSpeechDTO)))
            .andExpect(status().isCreated());

        // Validate the PartOfSpeech in the database
        List<PartOfSpeech> partOfSpeechList = partOfSpeechRepository.findAll();
        assertThat(partOfSpeechList).hasSize(databaseSizeBeforeCreate + 1);
        PartOfSpeech testPartOfSpeech = partOfSpeechList.get(partOfSpeechList.size() - 1);
        assertThat(testPartOfSpeech.getTag()).isEqualTo(DEFAULT_TAG);
        assertThat(testPartOfSpeech.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testPartOfSpeech.getSize()).isEqualTo(DEFAULT_SIZE);
        assertThat(testPartOfSpeech.getNote()).isEqualTo(DEFAULT_NOTE);
    }

    @Test
    public void createPartOfSpeechWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partOfSpeechRepository.findAll().size();

        // Create the PartOfSpeech with an existing ID
        partOfSpeech.setId("existing_id");
        PartOfSpeechDTO partOfSpeechDTO = partOfSpeechMapper.toDto(partOfSpeech);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartOfSpeechMockMvc.perform(post("/api/part-of-speeches").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partOfSpeechDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PartOfSpeech in the database
        List<PartOfSpeech> partOfSpeechList = partOfSpeechRepository.findAll();
        assertThat(partOfSpeechList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkTagIsRequired() throws Exception {
        int databaseSizeBeforeTest = partOfSpeechRepository.findAll().size();
        // set the field null
        partOfSpeech.setTag(null);

        // Create the PartOfSpeech, which fails.
        PartOfSpeechDTO partOfSpeechDTO = partOfSpeechMapper.toDto(partOfSpeech);


        restPartOfSpeechMockMvc.perform(post("/api/part-of-speeches").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partOfSpeechDTO)))
            .andExpect(status().isBadRequest());

        List<PartOfSpeech> partOfSpeechList = partOfSpeechRepository.findAll();
        assertThat(partOfSpeechList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkStartIsRequired() throws Exception {
        int databaseSizeBeforeTest = partOfSpeechRepository.findAll().size();
        // set the field null
        partOfSpeech.setStart(null);

        // Create the PartOfSpeech, which fails.
        PartOfSpeechDTO partOfSpeechDTO = partOfSpeechMapper.toDto(partOfSpeech);


        restPartOfSpeechMockMvc.perform(post("/api/part-of-speeches").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partOfSpeechDTO)))
            .andExpect(status().isBadRequest());

        List<PartOfSpeech> partOfSpeechList = partOfSpeechRepository.findAll();
        assertThat(partOfSpeechList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkSizeIsRequired() throws Exception {
        int databaseSizeBeforeTest = partOfSpeechRepository.findAll().size();
        // set the field null
        partOfSpeech.setSize(null);

        // Create the PartOfSpeech, which fails.
        PartOfSpeechDTO partOfSpeechDTO = partOfSpeechMapper.toDto(partOfSpeech);


        restPartOfSpeechMockMvc.perform(post("/api/part-of-speeches").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partOfSpeechDTO)))
            .andExpect(status().isBadRequest());

        List<PartOfSpeech> partOfSpeechList = partOfSpeechRepository.findAll();
        assertThat(partOfSpeechList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllPartOfSpeeches() throws Exception {
        // Initialize the database
        partOfSpeechRepository.save(partOfSpeech);

        // Get all the partOfSpeechList
        restPartOfSpeechMockMvc.perform(get("/api/part-of-speeches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partOfSpeech.getId())))
            .andExpect(jsonPath("$.[*].tag").value(hasItem(DEFAULT_TAG.toString())))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START)))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));
    }
    
    @Test
    public void getPartOfSpeech() throws Exception {
        // Initialize the database
        partOfSpeechRepository.save(partOfSpeech);

        // Get the partOfSpeech
        restPartOfSpeechMockMvc.perform(get("/api/part-of-speeches/{id}", partOfSpeech.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partOfSpeech.getId()))
            .andExpect(jsonPath("$.tag").value(DEFAULT_TAG.toString()))
            .andExpect(jsonPath("$.start").value(DEFAULT_START))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE));
    }
    @Test
    public void getNonExistingPartOfSpeech() throws Exception {
        // Get the partOfSpeech
        restPartOfSpeechMockMvc.perform(get("/api/part-of-speeches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updatePartOfSpeech() throws Exception {
        // Initialize the database
        partOfSpeechRepository.save(partOfSpeech);

        int databaseSizeBeforeUpdate = partOfSpeechRepository.findAll().size();

        // Update the partOfSpeech
        PartOfSpeech updatedPartOfSpeech = partOfSpeechRepository.findById(partOfSpeech.getId()).get();
        updatedPartOfSpeech
            .tag(UPDATED_TAG)
            .start(UPDATED_START)
            .size(UPDATED_SIZE)
            .note(UPDATED_NOTE);
        PartOfSpeechDTO partOfSpeechDTO = partOfSpeechMapper.toDto(updatedPartOfSpeech);

        restPartOfSpeechMockMvc.perform(put("/api/part-of-speeches").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partOfSpeechDTO)))
            .andExpect(status().isOk());

        // Validate the PartOfSpeech in the database
        List<PartOfSpeech> partOfSpeechList = partOfSpeechRepository.findAll();
        assertThat(partOfSpeechList).hasSize(databaseSizeBeforeUpdate);
        PartOfSpeech testPartOfSpeech = partOfSpeechList.get(partOfSpeechList.size() - 1);
        assertThat(testPartOfSpeech.getTag()).isEqualTo(UPDATED_TAG);
        assertThat(testPartOfSpeech.getStart()).isEqualTo(UPDATED_START);
        assertThat(testPartOfSpeech.getSize()).isEqualTo(UPDATED_SIZE);
        assertThat(testPartOfSpeech.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    public void updateNonExistingPartOfSpeech() throws Exception {
        int databaseSizeBeforeUpdate = partOfSpeechRepository.findAll().size();

        // Create the PartOfSpeech
        PartOfSpeechDTO partOfSpeechDTO = partOfSpeechMapper.toDto(partOfSpeech);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartOfSpeechMockMvc.perform(put("/api/part-of-speeches").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partOfSpeechDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PartOfSpeech in the database
        List<PartOfSpeech> partOfSpeechList = partOfSpeechRepository.findAll();
        assertThat(partOfSpeechList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deletePartOfSpeech() throws Exception {
        // Initialize the database
        partOfSpeechRepository.save(partOfSpeech);

        int databaseSizeBeforeDelete = partOfSpeechRepository.findAll().size();

        // Delete the partOfSpeech
        restPartOfSpeechMockMvc.perform(delete("/api/part-of-speeches/{id}", partOfSpeech.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartOfSpeech> partOfSpeechList = partOfSpeechRepository.findAll();
        assertThat(partOfSpeechList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
