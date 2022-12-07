package com.shree.treasurer;

public class storingdata {
    String pan,dob,gender,employment_type,address,alternate_mo;

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmployment_type() {
        return employment_type;
    }

    public void setEmployment_type(String employment_type) {
        this.employment_type = employment_type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAlternate_mo() {
        return alternate_mo;
    }

    public void setAlternate_mo(String alternate_mo) {
        this.alternate_mo = alternate_mo;
    }

    public storingdata() {
    }

    public storingdata(String pan, String dob, String gender, String employment_type, String address, String alternate_mo) {
        this.pan = pan;
        this.dob = dob;
        this.gender = gender;
        this.employment_type = employment_type;
        this.address = address;
        this.alternate_mo = alternate_mo;
    }

}
