package com.atpco.audit.model;

/**
 * Created by T440 on 2019/2/8.
 */
public class AreaPartition {
    //
    private String airportCode;

    //
    private String cityCode;

    //
    private String stateCode;

    //
    private String countryCode;

    //
    private String zoneCode;

    //
    private String subzoneCode;

    //
    private String areaCode;

    //
    private String subareaCode;

    //
    private String subcityCode;

    //
    private String us50States;

    //
    private String substateCode;

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    public String getZoneCode() {
        return zoneCode;
    }

    public void setSubzoneCode(String subzoneCode) {
        this.subzoneCode = subzoneCode;
    }

    public String getSubzoneCode() {
        return subzoneCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setSubareaCode(String subareaCode) {
        this.subareaCode = subareaCode;
    }

    public String getSubareaCode() {
        return subareaCode;
    }

    public void setSubcityCode(String subcityCode) {
        this.subcityCode = subcityCode;
    }

    public String getSubcityCode() {
        return subcityCode;
    }

    public void setUs50States(String us50States) {
        this.us50States = us50States;
    }

    public String getUs50States() {
        return us50States;
    }

    public void setSubstateCode(String substateCode) {
        this.substateCode = substateCode;
    }

    public String getSubstateCode() {
        return substateCode;
    }

    public AreaPartition() {
    }

    public AreaPartition(String airportCode) {
        this.airportCode = airportCode;
    }
}
