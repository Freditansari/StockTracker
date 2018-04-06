package com.fredy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A StockList.
 */
@Entity
@Table(name = "stock_list")
public class StockList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "name")
    private String name;

    @Column(name = "last_sale")
    private String lastSale;

    @Column(name = "market_cap")
    private String marketCap;

    @Column(name = "ipo_year")
    private String ipoYear;

    @Column(name = "sector")
    private String sector;

    @Column(name = "industry")
    private String industry;

    @Column(name = "adr")
    private String adr;

    @Column(name = "summary_quote")
    private String summaryQuote;

    @OneToMany(mappedBy = "instrument")
    @JsonIgnore
    private Set<Portfolio> portfolioFKS = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public StockList symbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public StockList name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastSale() {
        return lastSale;
    }

    public StockList lastSale(String lastSale) {
        this.lastSale = lastSale;
        return this;
    }

    public void setLastSale(String lastSale) {
        this.lastSale = lastSale;
    }

    public String getMarketCap() {
        return marketCap;
    }

    public StockList marketCap(String marketCap) {
        this.marketCap = marketCap;
        return this;
    }

    public void setMarketCap(String marketCap) {
        this.marketCap = marketCap;
    }

    public String getIpoYear() {
        return ipoYear;
    }

    public StockList ipoYear(String ipoYear) {
        this.ipoYear = ipoYear;
        return this;
    }

    public void setIpoYear(String ipoYear) {
        this.ipoYear = ipoYear;
    }

    public String getSector() {
        return sector;
    }

    public StockList sector(String sector) {
        this.sector = sector;
        return this;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getIndustry() {
        return industry;
    }

    public StockList industry(String industry) {
        this.industry = industry;
        return this;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getAdr() {
        return adr;
    }

    public StockList adr(String adr) {
        this.adr = adr;
        return this;
    }

    public void setAdr(String adr) {
        this.adr = adr;
    }

    public String getSummaryQuote() {
        return summaryQuote;
    }

    public StockList summaryQuote(String summaryQuote) {
        this.summaryQuote = summaryQuote;
        return this;
    }

    public void setSummaryQuote(String summaryQuote) {
        this.summaryQuote = summaryQuote;
    }

    public Set<Portfolio> getPortfolioFKS() {
        return portfolioFKS;
    }

    public StockList portfolioFKS(Set<Portfolio> portfolios) {
        this.portfolioFKS = portfolios;
        return this;
    }

    public StockList addPortfolioFK(Portfolio portfolio) {
        this.portfolioFKS.add(portfolio);
        portfolio.setInstrument(this);
        return this;
    }

    public StockList removePortfolioFK(Portfolio portfolio) {
        this.portfolioFKS.remove(portfolio);
        portfolio.setInstrument(null);
        return this;
    }

    public void setPortfolioFKS(Set<Portfolio> portfolios) {
        this.portfolioFKS = portfolios;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StockList stockList = (StockList) o;
        if (stockList.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stockList.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StockList{" +
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
