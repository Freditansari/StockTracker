package com.fredy.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the StockList entity.
 */
public class StockListDTO implements Serializable {

    private Long id;

    private String symbol;

    private String name;

    private String lastSale;

    private String marketCap;

    private String ipoYear;

    private String sector;

    private String industry;

    private String adr;

    private String summaryQuote;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastSale() {
        return lastSale;
    }

    public void setLastSale(String lastSale) {
        this.lastSale = lastSale;
    }

    public String getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(String marketCap) {
        this.marketCap = marketCap;
    }

    public String getIpoYear() {
        return ipoYear;
    }

    public void setIpoYear(String ipoYear) {
        this.ipoYear = ipoYear;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getAdr() {
        return adr;
    }

    public void setAdr(String adr) {
        this.adr = adr;
    }

    public String getSummaryQuote() {
        return summaryQuote;
    }

    public void setSummaryQuote(String summaryQuote) {
        this.summaryQuote = summaryQuote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StockListDTO stockListDTO = (StockListDTO) o;
        if(stockListDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stockListDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StockListDTO{" +
            "id=" + getId() +
            ", symbol='" + getSymbol() + "'" +
            ", name='" + getName() + "'" +
            ", lastSale='" + getLastSale() + "'" +
            ", marketCap='" + getMarketCap() + "'" +
            ", ipoYear='" + getIpoYear() + "'" +
            ", sector='" + getSector() + "'" +
            ", industry='" + getIndustry() + "'" +
            ", adr='" + getAdr() + "'" +
            ", summaryQuote='" + getSummaryQuote() + "'" +
            "}";
    }
}
