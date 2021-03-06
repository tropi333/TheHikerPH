package com.pccw.hikerph.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "hike")
public class Hike implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String eventName;
    private String mtName;
    private String location;
    private String startDate;
    private String endDate;
    private Long dtStartDate;
    private Long dtEndDate;
    private String tourGuide;
    private Double estBudget;
    private long elevation;

    private String itinerary;

    private String path_banner;

    private long profileId;

    public Hike(String eventName, String mtName, String location, String startDate,
                String endDate, String tourGuide, Double estBudget, long elevation,
                long profileId, String path_banner, Long dtStartDate, Long dtEndDate) {

        this.eventName = eventName;
        this.mtName = mtName;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tourGuide = tourGuide;
        this.estBudget = estBudget;
        this.elevation = elevation;
        this.profileId = profileId;
        this.path_banner = path_banner;
        this.dtStartDate = dtStartDate;
        this.dtEndDate = dtEndDate;
    }


    protected Hike(Parcel in) {

        id = in.readLong();
        eventName = in.readString();
        mtName = in.readString();
        location = in.readString();
        startDate = in.readString();
        endDate = in.readString();
        tourGuide = in.readString();
        if (in.readByte() == 0) {
            estBudget = null;
        } else {
            estBudget = in.readDouble();
        }
        elevation = in.readLong();
        profileId = in.readLong();
        itinerary = in.readString();
        path_banner = in.readString();

        dtStartDate = in.readLong();
        dtEndDate = in.readLong();
    }


    public static final Creator<Hike> CREATOR = new Creator<Hike>() {
        @Override
        public Hike createFromParcel(Parcel in) {
            return new Hike(in);
        }

        @Override
        public Hike[] newArray(int size) {
            return new Hike[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getMtName() {
        return mtName;
    }

    public void setMtName(String mtName) {
        this.mtName = mtName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getTourGuide() {
        return tourGuide;
    }

    public void setTourGuide(String tourGuide) {
        this.tourGuide = tourGuide;
    }


    public Double getEstBudget() {
        return estBudget;
    }

    public void setEstBudget(Double estBudget) {
        this.estBudget = estBudget;
    }

    public long getElevation() {
        return elevation;
    }

    public void setElevation(long elevation) {
        this.elevation = elevation;
    }

    public long getProfileId() {
        return profileId;
    }

    public void setProfileId(long profileId) {
        this.profileId = profileId;
    }


    public String getItinerary() {
        return itinerary;
    }

    public void setItinerary(String itinerary) {
        this.itinerary = itinerary;
    }

    public String getPath_banner() {
        return path_banner;
    }

    public void setPath_banner(String path_banner) {
        this.path_banner = path_banner;
    }

    public Long getDtStartDate() {
        return dtStartDate;
    }

    public void setDtStartDate(Long dtStartDate) {
        this.dtStartDate = dtStartDate;
    }

    public Long getDtEndDate() {
        return dtEndDate;
    }

    public void setDtEndDate(Long dtEndDate) {
        this.dtEndDate = dtEndDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(eventName);
        parcel.writeString(mtName);
        parcel.writeString(location);
        parcel.writeString(startDate);
        parcel.writeString(endDate);
        parcel.writeString(tourGuide);
        if (estBudget == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(estBudget);
        }
        parcel.writeLong(elevation);
        parcel.writeLong(profileId);
        parcel.writeString(itinerary);
        parcel.writeString(path_banner);
        parcel.writeLong(dtStartDate);
        parcel.writeLong(dtEndDate);


    }
}
