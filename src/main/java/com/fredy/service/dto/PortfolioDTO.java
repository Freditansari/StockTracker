package com.fredy.service.dto;


import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Portfolio entity.
 */
public class PortfolioDTO implements Serializable {

    private Long id;

    private Double lastPrice;

    private Double purchasePrice;

    private ZonedDateTime purchaseDate;

    private Double gain;

    private ZonedDateTime lastUpdated;

    private Long userId;

    private String userLogin;

    private Long instrumentId;

    private String instrumentSymbol;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(Double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public ZonedDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(ZonedDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Double getGain() {
        return gain;
    }

    public void setGain(Double gain) {
        this.gain = gain;
    }

    public ZonedDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(ZonedDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Long getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(Long stockListId) {
        this.instrumentId = stockListId;
    }

    public String getInstrumentSymbol() {
        return instrumentSymbol;
    }

    public void setInstrumentSymbol(String stockListSymbol) {
        this.instrumentSymbol = stockListSymbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PortfolioDTO portfolioDTO = (PortfolioDTO) o;
        if(portfolioDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), portfolioDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PortfolioDTO{" +
            "id=" + getId() +
            ", lastPrice=" + getLastPrice() +
            ", purchasePrice=" + getPurchasePrice() +
            ", purchaseDate='" + getPurchaseDate() + "'" +
            ", gain=" + getGain() +
            ", lastUpdated='" + getLastUpdated() + "'" +
            "}";
    }
}
