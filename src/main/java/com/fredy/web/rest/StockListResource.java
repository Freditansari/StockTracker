package com.fredy.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fredy.service.StockListService;
import com.fredy.web.rest.errors.BadRequestAlertException;
import com.fredy.web.rest.util.HeaderUtil;
import com.fredy.web.rest.util.PaginationUtil;
import com.fredy.service.dto.StockListDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing StockList.
 */
@RestController
@RequestMapping("/api")
public class StockListResource {

    private final Logger log = LoggerFactory.getLogger(StockListResource.class);

    private static final String ENTITY_NAME = "stockList";

    private final StockListService stockListService;

    public StockListResource(StockListService stockListService) {
        this.stockListService = stockListService;
    }

    /**
     * POST  /stock-lists : Create a new stockList.
     *
     * @param stockListDTO the stockListDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new stockListDTO, or with status 400 (Bad Request) if the stockList has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/stock-lists")
    @Timed
    public ResponseEntity<StockListDTO> createStockList(@RequestBody StockListDTO stockListDTO) throws URISyntaxException {
        log.debug("REST request to save StockList : {}", stockListDTO);
        if (stockListDTO.getId() != null) {
            throw new BadRequestAlertException("A new stockList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StockListDTO result = stockListService.save(stockListDTO);
        return ResponseEntity.created(new URI("/api/stock-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /stock-lists : Updates an existing stockList.
     *
     * @param stockListDTO the stockListDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated stockListDTO,
     * or with status 400 (Bad Request) if the stockListDTO is not valid,
     * or with status 500 (Internal Server Error) if the stockListDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/stock-lists")
    @Timed
    public ResponseEntity<StockListDTO> updateStockList(@RequestBody StockListDTO stockListDTO) throws URISyntaxException {
        log.debug("REST request to update StockList : {}", stockListDTO);
        if (stockListDTO.getId() == null) {
            return createStockList(stockListDTO);
        }
        StockListDTO result = stockListService.save(stockListDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, stockListDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stock-lists : get all the stockLists.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of stockLists in body
     */
    @GetMapping("/stock-lists")
    @Timed
    public ResponseEntity<List<StockListDTO>> getAllStockLists(Pageable pageable) {
        log.debug("REST request to get a page of StockLists");
        Page<StockListDTO> page = stockListService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/stock-lists");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /stock-lists/:id : get the "id" stockList.
     *
     * @param id the id of the stockListDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stockListDTO, or with status 404 (Not Found)
     */
    @GetMapping("/stock-lists/{id}")
    @Timed
    public ResponseEntity<StockListDTO> getStockList(@PathVariable Long id) {
        log.debug("REST request to get StockList : {}", id);
        StockListDTO stockListDTO = stockListService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(stockListDTO));
    }

    /**
     * DELETE  /stock-lists/:id : delete the "id" stockList.
     *
     * @param id the id of the stockListDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/stock-lists/{id}")
    @Timed
    public ResponseEntity<Void> deleteStockList(@PathVariable Long id) {
        log.debug("REST request to delete StockList : {}", id);
        stockListService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
