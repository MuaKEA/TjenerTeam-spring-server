package com.example.demo.assignedShift.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AssignedShiftKey implements Serializable {

    private Long userId;
    private Long shiftId;

    public AssignedShiftKey() {
    }

    public AssignedShiftKey(Long userId, Long shiftId) {
        this.userId = userId;
        this.shiftId = shiftId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getShiftId() {
        return shiftId;
    }

    public void setShiftId(Long shiftId) {
        this.shiftId = shiftId;
    }
}