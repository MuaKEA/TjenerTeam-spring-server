package com.example.demo.facebook.controller;

import com.example.demo.MailService.SendMail;
import com.example.demo.assignedShift.model.AssignedShift;
import com.example.demo.assignedShift.repository.UserAndShiftRepository;
import com.example.demo.facebook.model.FacebookUser;
import com.example.demo.facebook.model.PostCodes;
import com.example.demo.facebook.repository.FacebookRepository;
import com.example.demo.facebook.repository.IpostCodes;
import com.example.demo.notificationService.dto.NotificationRequestDto;
import com.example.demo.notificationService.service.NotificationService;
import com.example.demo.shift.model.Shift;
import com.example.demo.shift.repository.IshiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static com.example.demo.facebook.logic.FacebookUserLogic.hashUserInfo;


@Controller
public class FacebookUserController {


    @Autowired
    private IpostCodes ipostCodes;
    @Autowired
    private FacebookRepository facebookRepository;
    @Autowired
    private IshiftRepository shiftRepository;
    @Autowired
    private UserAndShiftRepository userAndShiftRepository;
    @Autowired
    SendMail mailsending;
    @Autowired
    NotificationService notificationService;

    @GetMapping("/getUser")
    public ResponseEntity getFacebookUser(@RequestParam(name = "facebook_id") Long facebookid) {
        Optional<FacebookUser> facebookUserOption = facebookRepository.findById(facebookid);

        if (facebookUserOption.isPresent()) {



            return new ResponseEntity<>(facebookUserOption.get(), HttpStatus.ACCEPTED);


        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


    @PostMapping("/saveUser")
    public ResponseEntity<FacebookUser> saveUser(@RequestParam(name = "facebook_id") Long facebook_id,
                                                 @RequestParam(name = "name") String name,
                                                 @RequestParam(name = "email") String email,
                                                 @RequestParam(name = "fcmtoken") String fcmToken  ) {


        FacebookUser facebookUser;
        Optional<FacebookUser> user = facebookRepository.findById(facebook_id);
        LocalDate snoozeDate = LocalDate.now();
        if (user.isPresent()) {
            facebookUser = new FacebookUser(facebook_id, name, email,fcmToken, snoozeDate);


        } else {
            facebookUser = new FacebookUser(facebook_id, name, email,fcmToken, snoozeDate);
            facebookRepository.save(facebookUser);
            mailsending.sendMail(email, " Tillykke " + name + " du er nu oprettet som bruger i Tjener Teamet \n\n\n venligst hilsen TjenerTeamet ");

        }


        return new ResponseEntity<>(facebookUser, HttpStatus.OK);


    }

    @GetMapping("/notificationTest")
    public ResponseEntity notificationTest() {
        String token = "emmTfwRPxhI:APA91bFLpAG1_Jcpip-o06zHadxP9BeEHp_mqpVbqcQdJMJSAxkK-X4sp1Fu-kaf7YPMp2EDgKzeL94aF1Ia6yJNYhQTdklbPD9q3PUHm7wHrVh8empHza8LXeYoq801zNu1AuiMOaTN";
        NotificationRequestDto notificationRequestDto = new NotificationRequestDto(token, "From Server", "Spring kan nu sende notificationer");

        notificationService.sendPnsToDevice(notificationRequestDto);

        return new ResponseEntity(HttpStatus.ACCEPTED);

    }

    @PostMapping("/saveUserRequestedJob")
    public ResponseEntity saveUserRequestedJob(@RequestParam(name = "user_id") Long user_id,
                                               @RequestParam(name = "shift_id") Long shift_id) {
        Optional<FacebookUser> user = facebookRepository.findById(user_id);
        Optional<Shift> shift = shiftRepository.findById(shift_id);

        if (user.isPresent() && shift.isPresent()) {
            AssignedShift assignedShift = new AssignedShift(user.get(), shift.get(), null, null);
            //user.get().getAssignedShift().add(shift.get());
            shift.get().setNumberOfEmployees(shift.get().getNumberOfEmployees() - 1);



            userAndShiftRepository.save(assignedShift);
            //facebookRepository.save(user.get());
            mailsending.sendMail(user.get().getEmail(), "Hej " + user.get().getName() + ".\n\n\nDin anmodning om at dække vagten for " + shift.get().getCustomerName() + ", er godkendt\n\n\nV.h. TjenerTeamet ");
        }


        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PostMapping("/cancelAssignedJob")
    public ResponseEntity cancelAssignedJob(@RequestParam(name = "user_id") Long user_id,
                                            @RequestParam(name = "shift_id") Long shift_id) {
        Optional<FacebookUser> user = facebookRepository.findById(user_id);
        Optional<Shift> shift = shiftRepository.findById(shift_id);

        if (user.isPresent() && shift.isPresent()) {
            user.get().getAssignedShifts().removeIf(assignedShift -> assignedShift.getShift().getId().equals(shift_id));
            //user.get().getAssignedShifts().remove(shift.get());
            shift.get().setNumberOfEmployees(shift.get().getNumberOfEmployees() + 1);
            facebookRepository.save(user.get());
            mailsending.sendMail(user.get().getEmail(), "Hej " + user.get().getName() + ".\n\n\nDin anmodning om at aflyse vagten for " + shift.get().getCustomerName() + ", er godkendt\n\n\nV.h. TjenerTeamet ");
        }


        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @PostMapping("/saveUserSnoozeRequest")
    public ResponseEntity saveUserSnoozeRequest(@RequestParam(name = "user_id") String user_id,
                                                @RequestParam(name = "snooze_value") String snooze_value) {
        Optional<FacebookUser> user = facebookRepository.findById(Long.valueOf(user_id));
        LocalDate currentDate = LocalDate.now();

        if (user.isPresent()) {
            if (snooze_value.equals("Sluk")) {
                user.get().setNotificationSnoozeEndDate(null);
            } else {
                LocalDate notificationSnoozeEndDate = currentDate.plusDays(Long.parseLong(snooze_value));
                user.get().setNotificationSnoozeEndDate(notificationSnoozeEndDate);
            }
            facebookRepository.save(user.get());
        }
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }


    @PostMapping("/updateUser")
    public ResponseEntity<FacebookUser> updateUser(@RequestParam(name = "id") Long id,
                                                   @RequestParam(name = "address", defaultValue = "") String address,
                                                   @RequestParam(name = "postCodes",defaultValue = "0") int postCodes,
                                                   @RequestParam(name = "cprNumber",defaultValue = "0") String cprNumber,
                                                   @RequestParam(name = "regNumber",defaultValue = "0") int regNumber,
                                                   @RequestParam(name = "accountNumber",defaultValue = "0") Long accountNumber,
                                                   @RequestParam(name = "dateOfBirth",defaultValue = "") String dateOfBirth,
                                                   @RequestParam(name = "phoneNumber",defaultValue = "0") Long phoneNumber,
                                                   @RequestParam(name = "city",defaultValue = "") String city) {
        FacebookUser facebookUser = null;
        Optional<FacebookUser> findUser = facebookRepository.findById(id);
        Optional<PostCodes> findpostCode = ipostCodes.findByPostCode(postCodes);
        if (findUser.isPresent()) {
            facebookUser = findUser.get();
            facebookUser.setAddress(address);
if(cprNumber.length() >5) {
    facebookUser.setCprNumber(cprNumber.substring(0, 6) + "-" + hashUserInfo(cprNumber));

}

if(accountNumber.toString().length() > 5 ) {
    String hashaccount = hashUserInfo(accountNumber.toString());
    facebookUser.setAccountNumber(hashaccount);

}

            facebookUser.setRegNumber(regNumber);
            facebookUser.setDateOfBirth(dateOfBirth);
            facebookUser.setPhoneNumber(phoneNumber);
            facebookUser.setCity(city);

            if (findpostCode.isPresent()) {
                facebookUser.setPostCodes(findpostCode.get());

            } else {
                facebookUser.setPostCodes(new PostCodes(postCodes));
            }

            facebookRepository.save(facebookUser);

        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity deleteUser(@RequestParam(name = "facebook_id") Long facebookId) {
        Optional<FacebookUser> facebookUserOptional = facebookRepository.findById(facebookId);
        ArrayList<AssignedShift> assignedShiftOptional = (ArrayList<AssignedShift>) userAndShiftRepository.findAll();
        if (facebookUserOptional.isPresent()) {
            FacebookUser facebookUser = facebookUserOptional.get();

            for (int i = 0; i <assignedShiftOptional.size() ; i++) {
                if (assignedShiftOptional.get(i).getFacebookUser().getId().equals( facebookId)){
                    userAndShiftRepository.delete(assignedShiftOptional.get(i));

                }


            }
            facebookRepository.delete(facebookUser);

            return new ResponseEntity(HttpStatus.ACCEPTED);

        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}


