package com.example.demo.facebook.logic;

import com.example.demo.assignedShift.model.AssignedShift;
import com.example.demo.facebook.model.FacebookUser;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FacebookUserLogic {

    public static ArrayList<FacebookUser> removeUnavailableEmployees(ArrayList<FacebookUser> facebookUserArrayList) {
        for (int i = 0; i < facebookUserArrayList.size(); i++) {
            if (!checkSnoozeStatus(facebookUserArrayList.get(i).getNotificationSnoozeEndDate())) {
                facebookUserArrayList.remove(facebookUserArrayList.get(i));
            }else{
                for (AssignedShift assignedShift : facebookUserArrayList.get(i).getAssignedShifts()) {
                    if (assignedShift.getFacebookUser().getId().equals(facebookUserArrayList.get(i).getId())){
                        if (checkHoursToCurrentJob(assignedShift.getShift().getJobCreatedDate())) {
                            facebookUserArrayList.remove(facebookUserArrayList.get(i));
                            break;
                        }
                    }
                }
            }
        }
        return facebookUserArrayList;
    }

    public static Boolean checkSnoozeStatus(LocalDate snoozeDate) {
       boolean isSnoozed = true;
        if (ChronoUnit.DAYS.between(LocalDate.now(), snoozeDate) > 0){
            isSnoozed = false;
            return isSnoozed;
        }
        return isSnoozed;
    }

    public static Boolean checkHoursToCurrentJob(Date jobDate) {
        return ChronoUnit.HOURS.between(jobDate.toInstant(), new Date().toInstant()) < -48;
    }

    public static String hashUserInfo(String hashinfo){
        try {
            MessageDigest hashinginstanse = MessageDigest.getInstance("MD5");
            hashinginstanse.reset();
            hashinginstanse.update(hashinfo.getBytes());
            byte[] digest = hashinginstanse.digest();
            BigInteger bigInt = new BigInteger(1,digest);
            String hashText = bigInt.toString(16);

            while(hashText.length() < 32 ){
                hashText = "0"+ hashText;
            }
            return hashText;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        }


        return hashinfo;


    }
}
