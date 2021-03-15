package com.example.demo.assignedShift.controller;

import com.example.demo.assignedShift.model.AssignedShift;
import com.example.demo.assignedShift.repository.UserAndShiftRepository;
import com.example.demo.facebook.model.FacebookUser;
import com.example.demo.facebook.repository.FacebookRepository;
import com.example.demo.shift.model.Shift;
import com.example.demo.shift.repository.IshiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AssignedShiftController {

    @Autowired
    private FacebookRepository facebookRepository;
    @Autowired
    private IshiftRepository shiftRepository;
    @Autowired
    private UserAndShiftRepository userAndShiftRepository;

    @PostMapping("/saveUserCheckout")
    public ResponseEntity saveUserCheckout (@RequestParam(name = "user_id") String user_id,
                                            @RequestParam(name = "employee_checkin_time") String employee_checkin_time,
                                            @RequestParam(name = "employee_checkout_time") String employee_checkout_time,
                                            @RequestParam(name = "shift_id") String shift_id){

        Optional<FacebookUser> user = facebookRepository.findById(Long.parseLong(user_id));
        Optional<Shift> shift = shiftRepository.findById(Long.parseLong(shift_id));
        Iterable<AssignedShift> assignedShifts = userAndShiftRepository.findAll();

        if (user.isPresent() && shift.isPresent()) {
            for (AssignedShift assignedShift : assignedShifts) {
                if (assignedShift.getShift().getId().equals(Long.parseLong(shift_id)) &&
                        assignedShift.getFacebookUser().getId().equals(Long.parseLong(user_id))){
                    assignedShift.setCheckin(employee_checkin_time);
                    assignedShift.setCheckout(employee_checkout_time);
                    userAndShiftRepository.save(assignedShift);
                }
            }
        }

        return new ResponseEntity(HttpStatus.ACCEPTED);

    }
}
