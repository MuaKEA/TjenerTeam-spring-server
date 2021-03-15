package com.example.demo.shift.controller;



import com.example.demo.MailService.SendMail;
import com.example.demo.assignedShift.model.AssignedShift;
import com.example.demo.assignedShift.repository.UserAndShiftRepository;
import com.example.demo.facebook.logic.FacebookUserLogic;
import com.example.demo.facebook.model.FacebookUser;
import com.example.demo.facebook.model.PostCodes;
import com.example.demo.facebook.repository.FacebookRepository;
import com.example.demo.facebook.repository.IpostCodes;
import com.example.demo.notificationService.dto.NotificationRequestDto;
import com.example.demo.notificationService.service.NotificationService;
import com.example.demo.shift.logic.ShiftLogic;
import com.example.demo.shift.model.*;
import com.example.demo.shift.repository.IshiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static com.example.demo.facebook.logic.FacebookUserLogic.removeUnavailableEmployees;


@Controller
public class ShiftController {
    @Autowired
    private IshiftRepository shiftRepository;
    @Autowired
    private FacebookRepository facebookRepository;
    @Autowired
    private UserAndShiftRepository userAndShiftRepository;
    @Autowired
    SendMail mailsending;
    @Autowired
    private IpostCodes ipostCodes;
    @Autowired
    NotificationService notificationService;

    @GetMapping("/getAllShifts")
    public ResponseEntity getAllShifts(@RequestParam(name = "facebook_id") Long facebook_id) {
        ShiftWrapper shiftWrapper = new ShiftWrapper();
        shiftWrapper.shiftWrapper = (ArrayList<Shift>) shiftRepository.findAll();
        Optional<FacebookUser> user = facebookRepository.findById(facebook_id);
        Iterable<AssignedShift> assignedShifts = userAndShiftRepository.findAll();

        if (user.isPresent()) {
            shiftWrapper.shiftWrapper.removeIf(shift -> shift.getNumberOfEmployees() == 0 || !ShiftLogic.isActiveShift(shift.getEventDate()));
            for (AssignedShift assignedShift : assignedShifts) {
                if (assignedShift.getFacebookUser().getId().equals(facebook_id)) {
                    shiftWrapper.shiftWrapper.remove(assignedShift.getShift());
                }
            }
        }
        return new ResponseEntity<>(shiftWrapper, HttpStatus.ACCEPTED);
    }

    @GetMapping("/getUserActiveShifts")
    public ResponseEntity getUserActiveShifts(@RequestParam(name = "facebook_id") Long facebook_id) {
        ShiftWrapper shiftWrapper = new ShiftWrapper();
        Iterable<AssignedShift> assignedShifts = userAndShiftRepository.findAll();
        Optional<FacebookUser> user = facebookRepository.findById(facebook_id);

        if (user.isPresent()) {
            for (AssignedShift assignedShift : assignedShifts) {
                if (assignedShift.getFacebookUser().getId().equals(facebook_id)) {
                    String eventDate = assignedShift.getShift().getEventDate();
                    if (ShiftLogic.isActiveShift(eventDate)) {
                        shiftWrapper.shiftWrapper.add(assignedShift.getShift());
                    }
                }
            }
        }
        return new ResponseEntity<>(shiftWrapper, HttpStatus.ACCEPTED);
    }

    @GetMapping("/getUserInactiveShifts")
    public ResponseEntity getUserInactiveShifts(@RequestParam(name = "facebook_id") Long facebook_id) {
        ShiftWrapper shiftWrapper = new ShiftWrapper();
        Iterable<AssignedShift> assignedShifts = userAndShiftRepository.findAll();
        Optional<FacebookUser> user = facebookRepository.findById(facebook_id);

        if (user.isPresent()) {
            for (AssignedShift assignedShift : assignedShifts) {
                if (assignedShift.getFacebookUser().getId().equals(facebook_id)) {
                    String eventDate = assignedShift.getShift().getEventDate();
                    if (!ShiftLogic.isActiveShift(eventDate)) {
                        shiftWrapper.shiftWrapper.add(assignedShift.getShift());
                    }
                }
            }
        }
        return new ResponseEntity<>(shiftWrapper, HttpStatus.ACCEPTED);
    }


    @PostMapping("/addShift")
    public ResponseEntity checkUserStatus(
            @RequestParam(name = "address") String address,
            @RequestParam(name = "salary") double salary,
            @RequestParam(name = "employeeType") String employeeType,
            @RequestParam(name = "customerName") String customerName,
            @RequestParam(name = "eventName") String eventName,
            @RequestParam(name = "numberOfEmployees") int numberOfEmployees,
            @RequestParam(name = "eventDescription") String eventDescription,
            @RequestParam(name = "dressCode") String dressCode,
            @RequestParam(name = "paymentDate") String paymentDate,
            @RequestParam(name = "eventDate") String eventDate,
            @RequestParam(name = "startTime") String startTime,
            @RequestParam(name = "endTime") String endTime,
            @RequestParam(name = "staffFood") String staffFood,
            @RequestParam(name = "transportSupplements") boolean transportSupplements,
            @RequestParam(name = "city") String city,
            @RequestParam(name = "overtime") int overtime,
            @RequestParam(name = "status") String status,
            @RequestParam(name = "postcode") int postcode) {

        ArrayList<FacebookUser> facebookUserArrayList = (ArrayList<FacebookUser>) facebookRepository.findAll();
        Shift shift;
        Optional<PostCodes> postCodes = ipostCodes.findByPostCode(postcode);


        if (postCodes.isPresent()) {
            shift = new Shift(address, salary, employeeType, customerName, eventName, numberOfEmployees, eventDescription, dressCode, paymentDate, eventDate, startTime, endTime, staffFood, transportSupplements, overtime, city, status, new Date(), postCodes.get());

        } else {

            shift = new Shift(address, salary, employeeType, customerName, eventName, numberOfEmployees, eventDescription, dressCode, paymentDate, eventDate, startTime, endTime, staffFood, transportSupplements, overtime, city, status, new Date(), new PostCodes(postcode));

        }
        if (shift.getStatus().equals("AKUT")) {
            ArrayList<FacebookUser> facebookUserList = (ArrayList<FacebookUser>) removeUnavailableEmployees(facebookUserArrayList);
            for (int i = 0; i < facebookUserList.size(); i++) {
                notificationService.sendPnsToDevice(new NotificationRequestDto(facebookUserArrayList.get(i).getFcmToken(), "Tjenerteamet", "1 ny Akut Vagt tilføjet"));
            }
        }
        shiftRepository.save(shift);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


}

