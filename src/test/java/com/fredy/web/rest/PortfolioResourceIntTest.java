package com.fredy.web.rest;

import com.fredy.StockTrackerApp;

import com.fredy.domain.Portfolio;
import com.fredy.repository.PortfolioRepository;
import com.fredy.service.PortfolioService;
import com.fredy.service.dto.PortfolioDTO;
import com.fredy.service.mapper.PortfolioMapper;
import com.fredy.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.fredy.web.rest.TestUtil.sameInstant;
import static com.fredy.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PortfolioResource REST controller.
 *
 * @see PortfolioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StockTrackerApp.class)
public class PortfolioResourceIntTest {

    private static final Double DEFAULT_LAST_PRICE = 1D;
    private static final Double UPDATED_LAST_PRICE = 2D;

    private static final Double DEFAULT_PURCHASE_PRICE = 1D;
    private static final Double UPDATED_PURCHASE_PRICE = 2D;

    private static final ZonedDateTime DEFAULT_PURCHASE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PURCHASE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Double DEFAULT_GAIN = 1D;
    private static final Double UPDATED_GAIN = 2D;

    private static final ZonedDateTime DEFAULT_LAST_UPDATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private PortfolioMapper portfolioMapper;

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPortfolioMockMvc;

    private Portfolio portfolio;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PortfolioResource portfolioResource = new PortfolioResource(portfolioService);
        this.restPortfolioMockMvc = MockMvcBuilders.standaloneSetup(portfolioResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Portfolio createEntity(EntityManager em) {
        Portfolio portfolio = new Portfolio()
            .lastPrice(DEFAULT_LAST_PRICE)
            .purchasePrice(DEFAULT_PURCHASE_PRICE)
            .purchaseDate(DEFAULT_PURCHASE_DATE)
            .gain(DEFAULT_GAIN)
            .lastUpdated(DEFAULT_LAST_UPDATED);
        return portfolio;
    }

    @Before
    public void initTest() {
        portfolio = createEntity(em);
    }

    @Test
    @Transactional
    public void createPortfolio() throws Exception {
        int databaseSizeBeforeCreate = portfolioRepository.findAll().size();

        // Create the Portfolio
        PortfolioDTO portfolioDTO = portfolioMapper.toDto(portfolio);
        restPortfolioMockMvc.perform(post("/api/portfolios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portfolioDTO)))
            .andExpect(status().isCreated());

        // Validate the Portfolio in the database
        List<Portfolio> portfolioList = portfolioRepository.findAll();
        assertThat(portfolioList).hasSize(databaseSizeBeforeCreate + 1);
        Portfolio testPortfolio = portfolioList.get(portfolioList.size() - 1);
        assertThat(testPortfolio.getLastPrice()).isEqualTo(DEFAULT_LAST_PRICE);
        assertThat(testPortfolio.getPurchasePrice()).isEqualTo(DEFAULT_PURCHASE_PRICE);
        assertThat(testPortfolio.getPurchaseDate()).isEqualTo(DEFAULT_PURCHASE_DATE);
        assertThat(testPortfolio.getGain()).isEqualTo(DEFAULT_GAIN);
        assertThat(testPortfolio.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
    }

    @Test
    @Transactional
    public void createPortfolioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = portfolioRepository.findAll().size();

        // Create the Portfolio with an existing ID
        portfolio.setId(1L);
        PortfolioDTO portfolioDTO = portfolioMapper.toDto(portfolio);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPortfolioMockMvc.perform(post("/api/portfolios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portfolioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Portfolio in the database
        List<Portfolio> portfolioList = portfolioRepository.findAll();
        assertThat(portfolioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPortfolios() throws Exception {
        // Initialize the database
        portfolioRepository.saveAndFlush(portfolio);

        // Get all the portfolioList
        restPortfolioMockMvc.perform(get("/api/portfolios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(portfolio.getId().intValue())))
            .andExpect(jsonPath("$.[*].lastPrice").value(hasItem(DEFAULT_LAST_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].purchasePrice").value(hasItem(DEFAULT_PURCHASE_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].purchaseDate").value(hasItem(sameInstant(DEFAULT_PURCHASE_DATE))))
            .andExpect(jsonPath("$.[*].gain").value(hasItem(DEFAULT_GAIN.doubleValue())))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED))));
    }

    @Test
    @Transactional
    public void getPortfolio() throws Exception {
        // Initialize the database
        portfolioRepository.saveAndFlush(portfolio);

        // Get the portfolio
        restPortfolioMockMvc.perform(get("/api/portfolios/{id}", portfolio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(portfolio.getId().intValue()))
            .andExpect(jsonPath("$.lastPrice").value(DEFAULT_LAST_PRICE.doubleValue()))
            .andExpect(jsonPath("$.purchasePrice").value(DEFAULT_PURCHASE_PRICE.doubleValue()))
            .andExpect(jsonPath("$.purchaseDate").value(sameInstant(DEFAULT_PURCHASE_DATE)))
            .andExpect(jsonPath("$.gain").value(DEFAULT_GAIN.doubleValue()))
            .andExpect(jsonPath("$.lastUpdated").value(sameInstant(DEFAULT_LAST_UPDATED)));
    }

    @Test
    @Transactional
    public void getNonExistingPortfolio() throws Exception {
        // Get the portfolio
        restPortfolioMockMvc.perform(get("/api/portfolios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePortfolio() throws Exception {
        // Initialize the database
        portfolioRepository.saveAndFlush(portfolio);
        int databaseSizeBeforeUpdate = portfolioRepository.findAll().size();

        // Update the portfolio
        Portfolio updatedPortfolio = portfolioRepository.findOne(portfolio.getId());
        // Disconnect from session so that the updates on updatedPortfolio are not directly saved in db
        em.detach(updatedPortfolio);
        updatedPortfolio
            .lastPrice(UPDATED_LAST_PRICE)
            .purchasePrice(UPDATED_PURCHASE_PRICE)
            .purchaseDate(UPDATED_PURCHASE_DATE)
            .gain(UPDATED_GAIN)
            .lastUpdated(UPDATED_LAST_UPDATED);
        PortfolioDTO portfolioDTO = portfolioMapper.toDto(updatedPortfolio);

        restPortfolioMockMvc.perform(put("/api/portfolios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portfolioDTO)))
            .andExpect(status().isOk());

        // Validate the Portfolio in the database
        List<Portfolio> portfolioList = portfolioRepository.findAll();
        assertThat(portfolioList).hasSize(databaseSizeBeforeUpdate);
        Portfolio testPortfolio = portfolioList.get(portfolioList.size() - 1);
        assertThat(testPortfolio.getLastPrice()).isEqualTo(UPDATED_LAST_PRICE);
        assertThat(testPortfolio.getPurchasePrice()).isEqualTo(UPDATED_PURCHASE_PRICE);
        assertThat(testPortfolio.getPurchaseDate()).isEqualTo(UPDATED_PURCHASE_DATE);
        assertThat(testPortfolio.getGain()).isEqualTo(UPDATED_GAIN);
        assertThat(testPortfolio.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingPortfolio() throws Exception {
        int databaseSizeBeforeUpdate = portfolioRepository.findAll().size();

        // Create the Portfolio
        PortfolioDTO portfolioDTO = portfolioMapper.toDto(portfolio);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPortfolioMockMvc.perform(put("/api/portfolios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(portfolioDTO)))
            .andExpect(status().isCreated());

        // Validate the Portfolio in the database
        List<Portfolio> portfolioList = portfolioRepository.findAll();
        assertThat(portfolioList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePortfolio() throws Exception {
        // Initialize the database
        portfolioRepository.saveAndFlush(portfolio);
        int databaseSizeBeforeDelete = portfolioRepository.findAll().size();

        // Get the portfolio
        restPortfolioMockMvc.perform(delete("/api/portfolios/{id}", portfolio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Portfolio> portfolioList = portfolioRepository.findAll();
        assertThat(portfolioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Portfolio.class);
        Portfolio portfolio1 = new Portfolio();
        portfolio1.setId(1L);
        Portfolio portfolio2 = new Portfolio();
        portfolio2.setId(portfolio1.getId());
        assertThat(portfolio1).isEqualTo(portfolio2);
        portfolio2.setId(2L);
        assertThat(portfolio1).isNotEqualTo(portfolio2);
        portfolio1.setId(null);
        assertThat(portfolio1).isNotEqualTo(portfolio2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PortfolioDTO.class);
        PortfolioDTO portfolioDTO1 = new PortfolioDTO();
        portfolioDTO1.setId(1L);
        PortfolioDTO portfolioDTO2 = new PortfolioDTO();
        assertThat(portfolioDTO1).isNotEqualTo(portfolioDTO2);
        portfolioDTO2.setId(portfolioDTO1.getId());
        assertThat(portfolioDTO1).isEqualTo(portfolioDTO2);
        portfolioDTO2.setId(2L);
        assertThat(portfolioDTO1).isNotEqualTo(portfolioDTO2);
        portfolioDTO1.setId(null);
        assertThat(portfolioDTO1).isNotEqualTo(portfolioDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(portfolioMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(portfolioMapper.fromId(null)).isNull();
    }
}
