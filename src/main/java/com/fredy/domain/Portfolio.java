package com.fredy.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Portfolio.
 */
@Entity
@Table(name = "portfolio")
public class Portfolio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "last_price")
    private Double lastPrice;

    @Column(name = "purchase_price")
    private Double purchasePrice;

    @Column(name = "purchase_date")
    private ZonedDateTime purchaseDate;

    @Column(name = "gain")
    private Double gain;

    @Column(name = "last_updated")
    private ZonedDateTime lastUpdated;

    @ManyToOne
    private User user;

    @ManyToOne
    private StockList instrument;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLastPrice() {
        return lastPrice;
    }

    public Portfolio lastPrice(Double lastPrice) {
        this.lastPrice = lastPrice;
        return this;
    }

    public void setLastPrice(Double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public Portfolio purchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
        return this;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public ZonedDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public Portfolio purchaseDate(ZonedDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
        return this;
    }

    public void setPurchaseDate(ZonedDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Double getGain() {
        return gain;
    }

    public Portfolio gain(Double gain) {
        this.gain = gain;
        return this;
    }

    public void setGain(Double gain) {
        this.gain = gain;
    }

    public ZonedDateTime getLastUpdated() {
        return lastUpdated;
    }

    public Portfolio lastUpdated(ZonedDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    public void setLastUpdated(ZonedDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public User getUser() {
        return user;
    }

    public Portfolio user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public StockList getInstrument() {
        return instrument;
    }

    public Portfolio instrument(StockList stockList) {
        this.instrument = stockList;
        return this;
    }

    public void setInstrument(StockList stockList) {
        this.instrument = stockList;
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
        Portfolio portfolio = (Portfolio) o;
        if (portfolio.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), portfolio.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Portfolio{" +
            "id=" + getId() +
            ", lastPrice=" + getLastPrice() +
            ", purchasePrice=" + getPurchasePrice() +
            ", purchaseDate='" + getPurchaseDate() + "'" +
            ", gain=" + getGain() +
            ", lastUpdated='" + getLastUpdated() + "'" +
            "}";
    }
}
