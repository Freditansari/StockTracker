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
    @Mapping(source = "instrument.id", target = "instrumentId")
    @Mapping(source = "instrument.symbol", target = "instrumentSymbol")
    PortfolioDTO toDto(Portfolio portfolio);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "instrumentId", target = "instrument")
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
