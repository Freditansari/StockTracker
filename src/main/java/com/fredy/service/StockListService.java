package com.fredy.service;

import com.fredy.service.dto.StockListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing StockList.
 */
public interface StockListService {

    /**
     * Save a stockList.
     *
     * @param stockListDTO the entity to save
     * @return the persisted entity
     */
    StockListDTO save(StockListDTO stockListDTO);

    /**
     * Get all the stockLists.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<StockListDTO> findAll(Pageable pageable);

    /**
     * Get the "id" stockList.
     *
     * @param id the id of the entity
     * @return the entity
     */
    StockListDTO findOne(Long id);

    /**
     * Delete the "id" stockList.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
