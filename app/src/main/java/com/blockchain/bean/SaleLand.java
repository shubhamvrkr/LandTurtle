package com.blockchain.bean;

/**
 * Created by shubham_verekar on 5/16/2016.
 */
public class SaleLand {
    int imageid;
    String surveyId;
    String propertyid;
    String landtype;
    String areaSize;
    String Price;
    String location;
    String contactNo;

    public SaleLand(int imageid, String surveyid,String propertyid, String landtype, String areaSize, String price, String location, String contactNo) {
        this.imageid = imageid;
       this.surveyId=surveyid;
        this.propertyid = propertyid;
        this.landtype = landtype;
        this.areaSize = areaSize;
        Price = price;
        this.location = location;
        this.contactNo = contactNo;
    }

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }


    public String getPropertyid() {
        return propertyid;
    }

    public void setPropertyid(String propertyid) {
        this.propertyid = propertyid;
    }

    public String getLandtype() {
        return landtype;
    }

    public void setLandtype(String landtype) {
        this.landtype = landtype;
    }

    public String getAreaSize() {
        return areaSize;
    }

    public void setAreaSize(String areaSize) {
        this.areaSize = areaSize;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

}
