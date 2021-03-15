package com.example.demo.facebook.model;


import com.example.demo.assignedShift.model.AssignedShift;
import com.example.demo.shift.model.Shift;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class FacebookUser {
    @Id
    private Long id;
    private String name;
    private String email;
    private String address;
    private String city;
    private Long phoneNumber;
    private String cprNumber;
    private int regNumber;
    private String accountNumber;
    private String dateOfBirth;
    private String fcmToken;
    private LocalDate notificationSnoozeEndDate;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "postcode_id")
    private PostCodes postCodes;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "shift", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<AssignedShift> assignedShifts;

    public FacebookUser() {

    }

    public FacebookUser(String name, String email, String address, String city, Long phoneNumber, PostCodes postCodes, LocalDate notificationSnoozeEndDate, List<AssignedShift> assignedShifts) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.postCodes = postCodes;
        this.notificationSnoozeEndDate = notificationSnoozeEndDate;
        this.assignedShifts = assignedShifts;
    }

    public FacebookUser(Long id, String name, String email, String fcmToken, LocalDate notificationSnoozeEndDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.fcmToken = fcmToken;
        this.notificationSnoozeEndDate = notificationSnoozeEndDate;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public PostCodes postCodes() {
        return postCodes;
    }

    public void setPostCodes(PostCodes postCodes) {
        this.postCodes = postCodes;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<AssignedShift> getAssignedShifts() {
        return assignedShifts;
    }

    public void setAssignedShifts(List<AssignedShift> assignedShifts) {
        this.assignedShifts = assignedShifts;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public PostCodes getPostCodes() {
        return postCodes;
    }

    public LocalDate getNotificationSnoozeEndDate() {
        return notificationSnoozeEndDate;
    }

    public String getCprNumber() {
        return cprNumber;
    }

    public void setCprNumber(String cprNumber) {
        this.cprNumber = cprNumber;
    }

    public int getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(int regNumber) {
        this.regNumber = regNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setNotificationSnoozeEndDate(LocalDate notificationSnoozeEndDate) {
        this.notificationSnoozeEndDate = notificationSnoozeEndDate;
    }

    @Override
    public String toString() {
        return "FacebookUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", cprNumber='" + cprNumber + '\'' +
                ", regNumber=" + regNumber +
                ", accountNumber=" + accountNumber +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", fcmToken='" + fcmToken + '\'' +
                ", notificationSnoozeEndDate=" + notificationSnoozeEndDate +
                ", postCodes=" + postCodes +
                ", assignedShifts=" + assignedShifts +
                '}';
    }
}