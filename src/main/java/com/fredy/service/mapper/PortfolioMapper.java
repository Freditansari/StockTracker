package com.fredy.service.mapper;

import com.fredy.domain.*;
import com.fredy.service.dto.PortfolioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Portfolio and its DTO PortfolioDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, StockListMapper.class})
public interface PortfolioMapper extends EntityMapper<PortfolioDTO, Portfolio> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "stockList.id", target = "stockListId")
    @Mapping(source = "stockList.symbol", target = "stockListSymbol")
    PortfolioDTO toDto(Portfolio portfolio);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "stockListId", target = "stockList")
    Portfolio toEntity(PortfolioDTO portfolioDTO);

    default Portfolio fromId(Long id) {
        if (id == null) {
            return null;
        }
        Portfolio portfolio = new Portfolio();
        portfolio.setId(id);
        return portfolio;
    }
}
