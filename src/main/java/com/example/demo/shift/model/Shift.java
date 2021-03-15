package com.example.demo.shift.model;

import com.example.demo.assignedShift.model.AssignedShift;
import com.example.demo.facebook.model.FacebookUser;
import com.example.demo.facebook.model.PostCodes;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.api.client.util.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String address;
    private Double salary;
    private String employeeType;
    private String customerName;
    private String eventName;
    private int numberOfEmployees;
    private String eventDescription;
    private String dressCode;
    private String paymentDate;
    private String eventDate;
    private String startTime;
    private String endTime;
    private String staffFood;
    private Boolean transportSupplements;
    private int overtime;
    private String city;
    private String status;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date jobCreatedDate;
    @OneToOne(cascade = CascadeType.ALL)
    private PostCodes postCodes;
    @OneToMany(mappedBy = "shift", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<AssignedShift> assignedShifts;

    public Shift(String address, Double salary, String employeeType, String customerName, String eventName, int numberOfEmployees, String eventDescription, String dressCode, String paymentDate, String eventDate, String startTime, String endTime, String staffFood, Boolean transportSupplements, int overtime, String city, String status, Date jobCreatedDate, PostCodes postCodes, List<AssignedShift> assignedShifts) {
        this.address = address;
        this.salary = salary;
        this.employeeType = employeeType;
        this.customerName = customerName;
        this.eventName = eventName;
        this.numberOfEmployees = numberOfEmployees;
        this.eventDescription = eventDescription;
        this.dressCode = dressCode;
        this.paymentDate = paymentDate;
        this.eventDate = eventDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.staffFood = staffFood;
        this.transportSupplements = transportSupplements;
        this.overtime = overtime;
        this.city = city;
        this.status = status;
        this.jobCreatedDate = jobCreatedDate;
        this.postCodes = postCodes;
        this.assignedShifts = assignedShifts;
    }

    public Shift(String address, Double salary, String employeeType, String customerName, String eventName, int numberOfEmployees, String eventDescription, String dressCode, String paymentDate, String eventDate, String startTime, String endTime, String staffFood, Boolean transportSupplements, int overtime, String city, String status, Date jobCreatedDate, PostCodes postCodes) {
        this.address = address;
        this.salary = salary;
        this.employeeType = employeeType;
        this.customerName = customerName;
        this.eventName = eventName;
        this.numberOfEmployees = numberOfEmployees;
        this.eventDescription = eventDescription;
        this.dressCode = dressCode;
        this.paymentDate = paymentDate;
        this.eventDate = eventDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.staffFood = staffFood;
        this.transportSupplements = transportSupplements;
        this.overtime = overtime;
        this.city = city;
        this.status = status;
        this.jobCreatedDate = jobCreatedDate;
        this.postCodes = postCodes;
    }

    public Shift(Long id) {
        this.id = id;
    }

    public Shift() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(int numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getDressCode() {
        return dressCode;
    }

    public void setDressCode(String dressCode) {
        this.dressCode = dressCode;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getStaffFood() {
        return staffFood;
    }

    public void setStaffFood(String staffFood) {
        this.staffFood = staffFood;
    }

    public Boolean getTransportSupplements() {
        return transportSupplements;
    }

    public void setTransportSupplements(Boolean transportSupplements) {
        this.transportSupplements = transportSupplements;
    }

    public int getOvertime() {
        return overtime;
    }

    public void setOvertime(int overtime) {
        this.overtime = overtime;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public PostCodes getPostCodes() {
        return postCodes;
    }

    public void setPostCodes(PostCodes postCodes) {
        this.postCodes = postCodes;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<AssignedShift> getAssignedShifts() {
        return assignedShifts;
    }

    public void setAssignedShifts(List<AssignedShift> assignedShifts) {
        this.assignedShifts = assignedShifts;
    }

    public String getStatus() {
        return status;
    }

    public Date getJobCreatedDate() {
        return jobCreatedDate;
    }

    public void setJobCreatedDate(Date jobCreatedDate) {
        this.jobCreatedDate = jobCreatedDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "Job: " + eventName + "\n"
                + "Løn: " + salary + "\n" +
                "Stilling: " + employeeType + "\n" +
                "Antal: " + numberOfEmployees + "\n" +
                "Event Dato: " + eventDate + "\n" +
                "Start Tid: " + startTime + '\n' +
                "Slut Tid: " + endTime + "\n" +
                "Adresse: " + address + "\n" +
                "By: " + city + "\n" +
                postCodes.toString();
    }
}