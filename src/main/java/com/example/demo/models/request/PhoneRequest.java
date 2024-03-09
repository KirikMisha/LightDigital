package com.example.demo.models.request;

public class PhoneRequest {
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String phone;
    public PhoneRequest(String phone){

        this.phone = phone;
    }

}
