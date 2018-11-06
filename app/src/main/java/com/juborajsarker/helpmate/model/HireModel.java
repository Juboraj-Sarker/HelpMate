package com.juborajsarker.helpmate.model;

public class HireModel {

    String hireId;
    String hireExpertName;
    String hireExpertUserId;
    String hireDate;
    String hireTime;

    public HireModel() {
    }

    public HireModel(String hireId, String hireExpertName, String hireExpertUserId, String hireDate, String hireTime) {
        this.hireId = hireId;
        this.hireExpertName = hireExpertName;
        this.hireExpertUserId = hireExpertUserId;
        this.hireDate = hireDate;
        this.hireTime = hireTime;
    }

    public String getHireId() {
        return hireId;
    }

    public void setHireId(String hireId) {
        this.hireId = hireId;
    }

    public String getHireExpertName() {
        return hireExpertName;
    }

    public void setHireExpertName(String hireExpertName) {
        this.hireExpertName = hireExpertName;
    }

    public String getHireExpertUserId() {
        return hireExpertUserId;
    }

    public void setHireExpertUserId(String hireExpertUserId) {
        this.hireExpertUserId = hireExpertUserId;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public String getHireTime() {
        return hireTime;
    }

    public void setHireTime(String hireTime) {
        this.hireTime = hireTime;
    }
}
