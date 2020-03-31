package com.atpco.audit.model;


import java.math.BigDecimal;

/**
 * Created by T440 on 2019/2/8.
 */
public class CurrencyRegulation {

    private String currencyCode;

    //
    private double fromNuc;

    //
    private double fareRoundUnit;

    //
    private BigDecimal taxOtherRoundUnit;

    //
    private int decimaUnit;

    //
    private String strongWeakFlag;

    //
    private String currencyName;

    //
    private String countryName;

    //
    private String roundingDirection;

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public double getFromNuc() {
        return fromNuc;
    }

    public void setFromNuc(double fromNuc) {
        this.fromNuc = fromNuc;
    }

    public double getFareRoundUnit() {
        return fareRoundUnit;
    }

    public void setFareRoundUnit(double fareRoundUnit) {
        this.fareRoundUnit = fareRoundUnit;
    }

    public BigDecimal getTaxOtherRoundUnit() {
        return taxOtherRoundUnit;
    }

    public void setTaxOtherRoundUnit(BigDecimal taxOtherRoundUnit) {
        this.taxOtherRoundUnit = taxOtherRoundUnit;
    }

    public int getDecimaUnit() {
        return decimaUnit;
    }

    public void setDecimaUnit(int decimaUnit) {
        this.decimaUnit = decimaUnit;
    }

    public void setStrongWeakFlag(String strongWeakFlag) {
        this.strongWeakFlag = strongWeakFlag;
    }

    public String getStrongWeakFlag() {
        return strongWeakFlag;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setRoundingDirection(String roundingDirection) {
        this.roundingDirection = roundingDirection;
    }

    public String getRoundingDirection() {
        return roundingDirection;
    }

    public CurrencyRegulation() {
        super();
    }

}
