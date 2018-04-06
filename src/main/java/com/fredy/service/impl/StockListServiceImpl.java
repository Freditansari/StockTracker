package com.fredy.service.impl;

import com.fredy.service.StockListService;
import com.fredy.domain.StockList;
import com.fredy.repository.StockListRepository;
import com.fredy.service.dto.StockListDTO;
import com.fredy.service.mapper.StockListMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing StockList.
 */
@Service
@Transactional
public class StockListServiceImpl implements StockListService {

    private final Logger log = LoggerFactory.getLogger(StockListServiceImpl.class);

    private final StockListRepository stockListRepository;

    private final StockListMapper stockListMapper;

    public StockListServiceImpl(StockListRepository stockListRepository, StockListMapper stockListMapper) {
        this.stockListRepository = stockListRepository;
        this.stockListMapper = stockListMapper;
    }

    /**
     * Save a stockList.
     *
     * @param stockListDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public StockListDTO save(StockListDTO stockListDTO) {
        log.debug("Request to save StockList : {}", stockListDTO);
        StockList stockList = stockListMapper.toEntity(stockListDTO);
        stockList = stockListRepository.save(stockList);
        return stockListMapper.toDto(stockList);
    }

    /**
     * Get all the stockLists.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StockListDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StockLists");
        return stockListRepository.findAll(pageable)
            .map(stockListMapper::toDto);
    }

    /**
     * Get one stockList by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public StockListDTO findOne(Long id) {
        log.debug("Request to get StockList : {}", id);
        StockList stockList = stockListRepository.findOne(id);
        return stockListMapper.toDto(stockList);
    }

    /**
     * Delete the stockList by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StockList : {}", id);
        stockListRepository.delete(id);
    }
}
