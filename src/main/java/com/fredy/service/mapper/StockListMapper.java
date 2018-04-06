package com.fredy.service.mapper;

import com.fredy.domain.*;
import com.fredy.service.dto.StockListDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity StockList and its DTO StockListDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StockListMapper extends EntityMapper<StockListDTO, StockList> {


    @Mapping(target = "portfolios", ignore = true)
    StockList toEntity(StockListDTO stockListDTO);

    default StockList fromId(Long id) {
        if (id == null) {
            return null;
        }
        StockList stockList = new StockList();
        stockList.setId(id);
        return stockList;
    }
}
