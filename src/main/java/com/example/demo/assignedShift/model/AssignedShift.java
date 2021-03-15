package com.example.demo.assignedShift.model;

import com.example.demo.facebook.model.FacebookUser;
import com.example.demo.shift.model.Shift;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Table(name = "assigned_shifts")
public class AssignedShift {
    @EmbeddedId
    private AssignedShiftKey id = new AssignedShiftKey();

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("userId")
    private FacebookUser facebookUser;

    @ManyToOne
    @MapsId("shiftId")
    private Shift shift;

    @Column(name = "employee_checkin_time")
    private String checkin;

    @Column(name = "employee_checkout_time")
    private String checkout;

    public AssignedShift() {
    }

    public AssignedShift(FacebookUser facebookUser, Shift shift, String checkin, String checkout) {
        this.facebookUser = facebookUser;
        this.shift = shift;
        this.checkin = checkin;
        this.checkout = checkout;
    }

    public FacebookUser getFacebookUser() {
        return facebookUser;
    }

    public void setFacebookUser(FacebookUser facebookUser) {
        this.facebookUser = facebookUser;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    public AssignedShiftKey getId() {
        return id;
    }

    public void setId(AssignedShiftKey id) {
        this.id = id;
    }
}
