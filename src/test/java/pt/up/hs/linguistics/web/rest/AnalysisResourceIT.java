package pt.up.hs.linguistics.web.rest;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;
import pt.up.hs.linguistics.LinguisticsApp;
import pt.up.hs.linguistics.client.sampling.SamplingFeignClient;
import pt.up.hs.linguistics.client.sampling.dto.Text;
import pt.up.hs.linguistics.config.SecurityBeanOverrideConfiguration;
import pt.up.hs.linguistics.domain.Analysis;
import pt.up.hs.linguistics.domain.enumeration.Status;
import pt.up.hs.linguistics.repository.AnalysisRepository;
import pt.up.hs.linguistics.service.AnalysisService;
import pt.up.hs.linguistics.service.dto.AnalysisDTO;
import pt.up.hs.linguistics.service.mapper.AnalysisMapper;
import pt.up.hs.linguistics.web.rest.errors.ExceptionTranslator;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pt.up.hs.linguistics.web.rest.TestUtil.createFormattingConversionService;

/**
 * Integration tests for the {@link AnalysisResource} REST controller.
 */
@SpringBootTest(
    classes = { SecurityBeanOverrideConfiguration.class, LinguisticsApp.class },
    properties = {"feign.hystrix.enabled=true"}
)
//@AutoConfigureMockMvc@WithMockUser
public class AnalysisResourceIT {

    private static final Long DEFAULT_PROJECT_ID = 1L;
    private static final Long DEFAULT_TEXT_ID = 1001L;

    private static final String DEFAULT_LANGUAGE = "pt_PT";
    private static final String UPDATED_LANGUAGE = "en";

    private static final Status DEFAULT_STATUS = Status.QUEUED;
    private static final Status UPDATED_STATUS = Status.PROCESSING;

    private static final String TEXT =
            "Um porta-voz de passageiros que chegaram à capital da Guiné-Bis" +
            "sau nos últimos dias, providentes de Lisboa, anunciou que vão b" +
            "loquear, com corrente e cadeado, as portas da agência da TAP em" +
            " Bissau em protesto pelo \"péssimo serviço\" da companhia. \n" +
            "Olívio Barreto indicou que a \"paciência já está quase no lim" +
            "ite\" por parte de cerca de 200 passageiros da TAP que chegaram" +
            " à Bissau nas últimas semanas, cujas malas ficaram retidas em L" +
            "isboa.\nCerca de duas dezenas de passageiros manifestaram hoje " +
            "a sua indignação na sede agência da companhia portuguesa na cap" +
            "ital guineense e, de acordo com Olívio Barreto, receberam a pro" +
            "messa dos funcionários em como a situação estava a ser resolvid" +
            "a.\nO serviço da TAP é péssimo. O Governo da Guiné-Bissau devia" +
            " ter chamado já o delegado da TAP para lhe pedir satisfação\", " +
            "declarou o porta-voz dos passageiros indignados.\nUm passageiro" +
            ", que não se quis identificar, disse à Lusa que \"é ridícula, a" +
            " resposta da TAP\", que mandou uma mensagem eletrónica aos afet" +
            "ados pedindo-lhes que indiquem os valores contidos nas malas re" +
            "tidas em Lisboa.\n\"Nem sequer respondi a essa mensagem, pois p" +
            "refiro os meus pertences naquela mala a dez mil euros em dinhei" +
            "ro\", observou o passageiro.\nVários passageiros, que protestav" +
            "am esta manhã de forma ruidosa na sede da agência da TAP, ao po" +
            "nto de interromperem o serviço, disseram à Lusa que estavam sem" +
            " os medicamentos que ficaram nas malas retidas em Lisboa.";

    @Autowired
    private AnalysisRepository analysisRepository;

    @Autowired
    private AnalysisMapper analysisMapper;

    @Autowired
    private AnalysisService analysisService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restAnalysisMockMvc;

    @MockBean
    private SamplingFeignClient samplingFeignClient;

    private Analysis analysis;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnalysisResource analysisResource = new AnalysisResource(analysisService);
        this.restAnalysisMockMvc = MockMvcBuilders.standaloneSetup(analysisResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator)
            .build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Analysis createEntity() {
        Analysis analysis = new Analysis()
            .projectId(DEFAULT_PROJECT_ID)
            .textId(DEFAULT_TEXT_ID)
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
            .projectId(DEFAULT_PROJECT_ID)
            .textId(DEFAULT_TEXT_ID)
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
        restAnalysisMockMvc.perform(post("/api/projects/{projectId}/texts/{textId}/analyses?analyze=false", DEFAULT_PROJECT_ID, DEFAULT_TEXT_ID)
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(analysisDTO)))
            .andExpect(status().isCreated());

        // Validate the Analysis in the database
        List<Analysis> analysisList = analysisRepository.findAll();
        assertThat(analysisList).hasSize(databaseSizeBeforeCreate + 1);
        Analysis testAnalysis = analysisList.get(analysisList.size() - 1);
        assertThat(testAnalysis.getProjectId()).isEqualTo(DEFAULT_PROJECT_ID);
        assertThat(testAnalysis.getTextId()).isEqualTo(DEFAULT_TEXT_ID);
        assertThat(testAnalysis.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testAnalysis.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    public void createAnalysisWithAnalyze() throws Exception {

        when(
            samplingFeignClient.getText(DEFAULT_PROJECT_ID, DEFAULT_TEXT_ID)
        )
            .thenReturn(getText());

        int databaseSizeBeforeCreate = analysisRepository.findAll().size();

        // Create the Linguistic Analysis
        AnalysisDTO analysisDTO = analysisMapper.toDto(analysis);
        restAnalysisMockMvc.perform(post("/api/projects/{projectId}/texts/{textId}/analyses", DEFAULT_PROJECT_ID, DEFAULT_TEXT_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(analysisDTO)))
            .andExpect(status().isCreated());

        // Validate the Linguistic Analysis in the database
        List<Analysis> analysisList = analysisRepository.findAll();
        assertThat(analysisList).hasSize(databaseSizeBeforeCreate + 1);
        Analysis testAnalysis = analysisList.get(analysisList.size() - 1);
        assertThat(testAnalysis.getProjectId()).isEqualTo(DEFAULT_PROJECT_ID);
        assertThat(testAnalysis.getTextId()).isEqualTo(DEFAULT_TEXT_ID);
        assertThat(testAnalysis.getCharacterCount()).isEqualTo(1424);
        assertThat(testAnalysis.getNonBlankCharacterCount()).isEqualTo(1192);
        assertThat(testAnalysis.getWordCount()).isEqualTo(265);
        assertThat(testAnalysis.getDistinctWordCount()).isEqualTo(115);
        assertThat(testAnalysis.getFunctionalWordCount()).isEqualTo(134);
        assertThat(testAnalysis.getDistinctFunctionalWordCount()).isEqualTo(29);
        assertThat(testAnalysis.getContentWordCount()).isEqualTo(131);
        assertThat(testAnalysis.getDistinctContentWordCount()).isEqualTo(89);
        assertThat(testAnalysis.getDistinctLemmaCount()).isEqualTo(115);
        assertThat(testAnalysis.getSentenceCount()).isEqualTo(8);
        assertThat(testAnalysis.getWordAvgLength()).isCloseTo(4.249, Offset.offset(TestUtil.EPSILON));
        assertThat(testAnalysis.getContentWordAvgLength()).isCloseTo(6.519, Offset.offset(TestUtil.EPSILON));
        assertThat(testAnalysis.getFunctionalWordAvgLength()).isCloseTo(2.029, Offset.offset(TestUtil.EPSILON));
        assertThat(testAnalysis.getWordsByCategory()).hasSize(12);
        assertThat(testAnalysis.getContentWordFrequencies()).hasSize(89);
        assertThat(testAnalysis.getFunctionalWordFrequencies()).hasSize(29);
        assertThat(testAnalysis.getLemmaFrequencies()).hasSize(115);
        assertThat(testAnalysis.getEmotions()).hasSize(7);
        assertThat(testAnalysis.getPartsOfSpeech()).hasSize(265);
    }

    private Text getText() {
        Text text = new Text();
        text.setLanguage(DEFAULT_LANGUAGE);
        text.setText(TEXT);
        return text;
    }

    @Test
    public void createAnalysisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = analysisRepository.findAll().size();

        // Create the Analysis with an existing ID
        analysis.setId("existing_id");
        AnalysisDTO analysisDTO = analysisMapper.toDto(analysis);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnalysisMockMvc.perform(post("/api/projects/{projectId}/texts/{textId}/analyses", DEFAULT_PROJECT_ID, DEFAULT_TEXT_ID)
            .with(csrf())
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
        restAnalysisMockMvc.perform(get("/api/projects/{projectId}/texts/{textId}/analyses?sort=id,desc", DEFAULT_PROJECT_ID, DEFAULT_TEXT_ID))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(analysis.getId())))
            .andExpect(jsonPath("$.[*].projectId").value(hasItem(DEFAULT_PROJECT_ID.intValue())))
            .andExpect(jsonPath("$.[*].textId").value(hasItem(DEFAULT_TEXT_ID.intValue())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    public void getAnalysis() throws Exception {
        // Initialize the database
        analysisRepository.save(analysis);

        // Get the analysis
        restAnalysisMockMvc.perform(get("/api/projects/{projectId}/texts/{textId}/analyses/{id}", DEFAULT_PROJECT_ID, DEFAULT_TEXT_ID, analysis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(analysis.getId()))
            .andExpect(jsonPath("$.projectId").value(DEFAULT_PROJECT_ID.intValue()))
            .andExpect(jsonPath("$.textId").value(DEFAULT_TEXT_ID.intValue()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }
    @Test
    public void getNonExistingAnalysis() throws Exception {
        // Get the analysis
        restAnalysisMockMvc.perform(get("/api/projects/{projectId}/texts/{textId}/analyses/{id}", DEFAULT_PROJECT_ID, DEFAULT_TEXT_ID, Long.MAX_VALUE))
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
            .projectId(DEFAULT_PROJECT_ID)
            .textId(DEFAULT_TEXT_ID)
            .language(UPDATED_LANGUAGE)
            .status(UPDATED_STATUS);
        AnalysisDTO analysisDTO = analysisMapper.toDto(updatedAnalysis);

        restAnalysisMockMvc.perform(put("/api/projects/{projectId}/texts/{textId}/analyses", DEFAULT_PROJECT_ID, DEFAULT_TEXT_ID).with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(analysisDTO)))
            .andExpect(status().isOk());

        // Validate the Analysis in the database
        List<Analysis> analysisList = analysisRepository.findAll();
        assertThat(analysisList).hasSize(databaseSizeBeforeUpdate);
        Analysis testAnalysis = analysisList.get(analysisList.size() - 1);
        assertThat(testAnalysis.getProjectId()).isEqualTo(DEFAULT_PROJECT_ID);
        assertThat(testAnalysis.getTextId()).isEqualTo(DEFAULT_TEXT_ID);
        assertThat(testAnalysis.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testAnalysis.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    public void updateNonExistingAnalysis() throws Exception {
        int databaseSizeBeforeUpdate = analysisRepository.findAll().size();

        // Create the Analysis
        AnalysisDTO analysisDTO = analysisMapper.toDto(analysis);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnalysisMockMvc.perform(put("/api/projects/{projectId}/texts/{textId}/analyses", DEFAULT_PROJECT_ID, DEFAULT_TEXT_ID).with(csrf())
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
        restAnalysisMockMvc.perform(delete("/api/projects/{projectId}/texts/{textId}/analyses/{id}", DEFAULT_PROJECT_ID, DEFAULT_TEXT_ID, analysis.getId())
            .with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Analysis> analysisList = analysisRepository.findAll();
        assertThat(analysisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
