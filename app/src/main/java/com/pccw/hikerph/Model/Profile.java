package com.pccw.hikerph.Model;

import java.util.List;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Profile {


    @PrimaryKey(autoGenerate = true)
    private long id;

    private String firstName;

    private String middleName;

    private String lastName;

    private String bday;

    private String email;

    private String contactNo;

    private String motto;

    private String gender;

    private String profilePic_bitMap;

    public Profile(String firstName, String middleName, String lastName, String bday, String email,
                   String contactNo, String motto, String profilePic_bitMap, String gender) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.bday = bday;
        this.email = email;
        this.contactNo = contactNo;
        this.motto = motto;
        this.profilePic_bitMap = profilePic_bitMap;
        this.gender = gender;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBday() {
        return bday;
    }

    public void setBday(String bday) {
        this.bday = bday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }


    public String getProfilePic_bitMap() {
        return profilePic_bitMap;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setProfilePic_bitMap(String profilePic_bitMap) {
        this.profilePic_bitMap = profilePic_bitMap;
    }
}
