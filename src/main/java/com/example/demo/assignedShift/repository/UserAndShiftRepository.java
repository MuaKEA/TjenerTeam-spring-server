package com.example.demo.assignedShift.repository;

import com.example.demo.assignedShift.model.AssignedShift;
import com.example.demo.facebook.model.FacebookUser;
import com.example.demo.shift.model.Shift;
import org.springframework.data.repository.CrudRepository;

public interface UserAndShiftRepository extends CrudRepository<AssignedShift,Long> {


}

