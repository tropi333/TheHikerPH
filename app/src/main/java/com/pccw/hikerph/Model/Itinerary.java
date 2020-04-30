package com.pccw.hikerph.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Itinerary {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String startDate;
    private String endDate;
    private String remarks;
    private long hikeId;

    public Itinerary(String startDate, String endDate, String remarks, long hikeId ) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.remarks = remarks;
        this.hikeId = hikeId;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public long getHikeId() {
        return hikeId;
    }

    public void setHikeId(long hikeId) {
        this.hikeId = hikeId;
    }
}
