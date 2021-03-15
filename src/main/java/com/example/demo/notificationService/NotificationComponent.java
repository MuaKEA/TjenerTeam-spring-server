package com.example.demo.notificationService;

import com.example.demo.facebook.logic.FacebookUserLogic;
import com.example.demo.facebook.model.FacebookUser;
import com.example.demo.facebook.repository.FacebookRepository;
import com.example.demo.notificationService.dto.NotificationRequestDto;
import com.example.demo.notificationService.service.NotificationService;
import com.example.demo.shift.model.Shift;
import com.example.demo.shift.repository.IshiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class NotificationComponent {
    @Autowired
    private FacebookRepository facebookRepository;
    @Autowired
    NotificationService notificationService;
    @Autowired
    IshiftRepository ishiftRepository;


    @Scheduled(cron = "0 09 13 * * *")
    public void sendNotificationsDaily() {
        ArrayList<FacebookUser> facebookUserArrayList = (ArrayList<FacebookUser>) facebookRepository.findAll();
        List<Shift> shiftArrayList = ishiftRepository.findAll();
        FacebookUserLogic.removeUnavailableEmployees(facebookUserArrayList);
        int numberOfshits=0;
            for (Shift shift : shiftArrayList) {
                if (ChronoUnit.HOURS.between(shift.getJobCreatedDate().toInstant(), new Date().toInstant()) >= -24) {
                    numberOfshits++;
                }

                for (FacebookUser facebookUser : facebookUserArrayList) {
                    notificationService.sendPnsToDevice(new NotificationRequestDto(facebookUser.getFcmToken(), "Tjenerteamet",numberOfshits+ " nye vagter tilføjet inden for de sidste 24 timer"));
                }
            }
    }
}