package com.fredy.web.rest;

import com.fredy.StockTrackerApp;

import com.fredy.domain.StockList;
import com.fredy.repository.StockListRepository;
import com.fredy.service.StockListService;
import com.fredy.service.dto.StockListDTO;
import com.fredy.service.mapper.StockListMapper;
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
import java.util.List;

import static com.fredy.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the StockListResource REST controller.
 *
 * @see StockListResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StockTrackerApp.class)
public class StockListResourceIntTest {

    private static final String DEFAULT_SYMBOL = "AAAAAAAAAA";
    private static final String UPDATED_SYMBOL = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_SALE = "AAAAAAAAAA";
    private static final String UPDATED_LAST_SALE = "BBBBBBBBBB";

    private static final String DEFAULT_MARKET_CAP = "AAAAAAAAAA";
    private static final String UPDATED_MARKET_CAP = "BBBBBBBBBB";

    private static final String DEFAULT_IPO_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_IPO_YEAR = "BBBBBBBBBB";

    private static final String DEFAULT_SECTOR = "AAAAAAAAAA";
    private static final String UPDATED_SECTOR = "BBBBBBBBBB";

    private static final String DEFAULT_INDUSTRY = "AAAAAAAAAA";
    private static final String UPDATED_INDUSTRY = "BBBBBBBBBB";

    private static final String DEFAULT_ADR = "AAAAAAAAAA";
    private static final String UPDATED_ADR = "BBBBBBBBBB";

    private static final String DEFAULT_SUMMARY_QUOTE = "AAAAAAAAAA";
    private static final String UPDATED_SUMMARY_QUOTE = "BBBBBBBBBB";

    @Autowired
    private StockListRepository stockListRepository;

    @Autowired
    private StockListMapper stockListMapper;

    @Autowired
    private StockListService stockListService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStockListMockMvc;

    private StockList stockList;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StockListResource stockListResource = new StockListResource(stockListService);
        this.restStockListMockMvc = MockMvcBuilders.standaloneSetup(stockListResource)
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
    public static StockList createEntity(EntityManager em) {
        StockList stockList = new StockList()
            .symbol(DEFAULT_SYMBOL)
            .name(DEFAULT_NAME)
            .lastSale(DEFAULT_LAST_SALE)
            .marketCap(DEFAULT_MARKET_CAP)
            .ipoYear(DEFAULT_IPO_YEAR)
            .sector(DEFAULT_SECTOR)
            .industry(DEFAULT_INDUSTRY)
            .adr(DEFAULT_ADR)
            .summaryQuote(DEFAULT_SUMMARY_QUOTE);
        return stockList;
    }

    @Before
    public void initTest() {
        stockList = createEntity(em);
    }

    @Test
    @Transactional
    public void createStockList() throws Exception {
        int databaseSizeBeforeCreate = stockListRepository.findAll().size();

        // Create the StockList
        StockListDTO stockListDTO = stockListMapper.toDto(stockList);
        restStockListMockMvc.perform(post("/api/stock-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockListDTO)))
            .andExpect(status().isCreated());

        // Validate the StockList in the database
        List<StockList> stockListList = stockListRepository.findAll();
        assertThat(stockListList).hasSize(databaseSizeBeforeCreate + 1);
        StockList testStockList = stockListList.get(stockListList.size() - 1);
        assertThat(testStockList.getSymbol()).isEqualTo(DEFAULT_SYMBOL);
        assertThat(testStockList.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStockList.getLastSale()).isEqualTo(DEFAULT_LAST_SALE);
        assertThat(testStockList.getMarketCap()).isEqualTo(DEFAULT_MARKET_CAP);
        assertThat(testStockList.getIpoYear()).isEqualTo(DEFAULT_IPO_YEAR);
        assertThat(testStockList.getSector()).isEqualTo(DEFAULT_SECTOR);
        assertThat(testStockList.getIndustry()).isEqualTo(DEFAULT_INDUSTRY);
        assertThat(testStockList.getAdr()).isEqualTo(DEFAULT_ADR);
        assertThat(testStockList.getSummaryQuote()).isEqualTo(DEFAULT_SUMMARY_QUOTE);
    }

    @Test
    @Transactional
    public void createStockListWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stockListRepository.findAll().size();

        // Create the StockList with an existing ID
        stockList.setId(1L);
        StockListDTO stockListDTO = stockListMapper.toDto(stockList);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStockListMockMvc.perform(post("/api/stock-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockListDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StockList in the database
        List<StockList> stockListList = stockListRepository.findAll();
        assertThat(stockListList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllStockLists() throws Exception {
        // Initialize the database
        stockListRepository.saveAndFlush(stockList);

        // Get all the stockListList
        restStockListMockMvc.perform(get("/api/stock-lists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stockList.getId().intValue())))
            .andExpect(jsonPath("$.[*].symbol").value(hasItem(DEFAULT_SYMBOL.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastSale").value(hasItem(DEFAULT_LAST_SALE.toString())))
            .andExpect(jsonPath("$.[*].marketCap").value(hasItem(DEFAULT_MARKET_CAP.toString())))
            .andExpect(jsonPath("$.[*].ipoYear").value(hasItem(DEFAULT_IPO_YEAR.toString())))
            .andExpect(jsonPath("$.[*].sector").value(hasItem(DEFAULT_SECTOR.toString())))
            .andExpect(jsonPath("$.[*].industry").value(hasItem(DEFAULT_INDUSTRY.toString())))
            .andExpect(jsonPath("$.[*].adr").value(hasItem(DEFAULT_ADR.toString())))
            .andExpect(jsonPath("$.[*].summaryQuote").value(hasItem(DEFAULT_SUMMARY_QUOTE.toString())));
    }

    @Test
    @Transactional
    public void getStockList() throws Exception {
        // Initialize the database
        stockListRepository.saveAndFlush(stockList);

        // Get the stockList
        restStockListMockMvc.perform(get("/api/stock-lists/{id}", stockList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stockList.getId().intValue()))
            .andExpect(jsonPath("$.symbol").value(DEFAULT_SYMBOL.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.lastSale").value(DEFAULT_LAST_SALE.toString()))
            .andExpect(jsonPath("$.marketCap").value(DEFAULT_MARKET_CAP.toString()))
            .andExpect(jsonPath("$.ipoYear").value(DEFAULT_IPO_YEAR.toString()))
            .andExpect(jsonPath("$.sector").value(DEFAULT_SECTOR.toString()))
            .andExpect(jsonPath("$.industry").value(DEFAULT_INDUSTRY.toString()))
            .andExpect(jsonPath("$.adr").value(DEFAULT_ADR.toString()))
            .andExpect(jsonPath("$.summaryQuote").value(DEFAULT_SUMMARY_QUOTE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStockList() throws Exception {
        // Get the stockList
        restStockListMockMvc.perform(get("/api/stock-lists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStockList() throws Exception {
        // Initialize the database
        stockListRepository.saveAndFlush(stockList);
        int databaseSizeBeforeUpdate = stockListRepository.findAll().size();

        // Update the stockList
        StockList updatedStockList = stockListRepository.findOne(stockList.getId());
        // Disconnect from session so that the updates on updatedStockList are not directly saved in db
        em.detach(updatedStockList);
        updatedStockList
            .symbol(UPDATED_SYMBOL)
            .name(UPDATED_NAME)
            .lastSale(UPDATED_LAST_SALE)
            .marketCap(UPDATED_MARKET_CAP)
            .ipoYear(UPDATED_IPO_YEAR)
            .sector(UPDATED_SECTOR)
            .industry(UPDATED_INDUSTRY)
            .adr(UPDATED_ADR)
            .summaryQuote(UPDATED_SUMMARY_QUOTE);
        StockListDTO stockListDTO = stockListMapper.toDto(updatedStockList);

        restStockListMockMvc.perform(put("/api/stock-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockListDTO)))
            .andExpect(status().isOk());

        // Validate the StockList in the database
        List<StockList> stockListList = stockListRepository.findAll();
        assertThat(stockListList).hasSize(databaseSizeBeforeUpdate);
        StockList testStockList = stockListList.get(stockListList.size() - 1);
        assertThat(testStockList.getSymbol()).isEqualTo(UPDATED_SYMBOL);
        assertThat(testStockList.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStockList.getLastSale()).isEqualTo(UPDATED_LAST_SALE);
        assertThat(testStockList.getMarketCap()).isEqualTo(UPDATED_MARKET_CAP);
        assertThat(testStockList.getIpoYear()).isEqualTo(UPDATED_IPO_YEAR);
        assertThat(testStockList.getSector()).isEqualTo(UPDATED_SECTOR);
        assertThat(testStockList.getIndustry()).isEqualTo(UPDATED_INDUSTRY);
        assertThat(testStockList.getAdr()).isEqualTo(UPDATED_ADR);
        assertThat(testStockList.getSummaryQuote()).isEqualTo(UPDATED_SUMMARY_QUOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingStockList() throws Exception {
        int databaseSizeBeforeUpdate = stockListRepository.findAll().size();

        // Create the StockList
        StockListDTO stockListDTO = stockListMapper.toDto(stockList);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStockListMockMvc.perform(put("/api/stock-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stockListDTO)))
            .andExpect(status().isCreated());

        // Validate the StockList in the database
        List<StockList> stockListList = stockListRepository.findAll();
        assertThat(stockListList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteStockList() throws Exception {
        // Initialize the database
        stockListRepository.saveAndFlush(stockList);
        int databaseSizeBeforeDelete = stockListRepository.findAll().size();

        // Get the stockList
        restStockListMockMvc.perform(delete("/api/stock-lists/{id}", stockList.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StockList> stockListList = stockListRepository.findAll();
        assertThat(stockListList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockList.class);
        StockList stockList1 = new StockList();
        stockList1.setId(1L);
        StockList stockList2 = new StockList();
        stockList2.setId(stockList1.getId());
        assertThat(stockList1).isEqualTo(stockList2);
        stockList2.setId(2L);
        assertThat(stockList1).isNotEqualTo(stockList2);
        stockList1.setId(null);
        assertThat(stockList1).isNotEqualTo(stockList2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StockListDTO.class);
        StockListDTO stockListDTO1 = new StockListDTO();
        stockListDTO1.setId(1L);
        StockListDTO stockListDTO2 = new StockListDTO();
        assertThat(stockListDTO1).isNotEqualTo(stockListDTO2);
        stockListDTO2.setId(stockListDTO1.getId());
        assertThat(stockListDTO1).isEqualTo(stockListDTO2);
        stockListDTO2.setId(2L);
        assertThat(stockListDTO1).isNotEqualTo(stockListDTO2);
        stockListDTO1.setId(null);
        assertThat(stockListDTO1).isNotEqualTo(stockListDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(stockListMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(stockListMapper.fromId(null)).isNull();
    }
}
