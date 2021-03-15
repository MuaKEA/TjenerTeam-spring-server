package com.example.demo;

import com.example.demo.facebook.model.FacebookUser;
import com.example.demo.facebook.repository.FacebookRepository;
import com.example.demo.notificationService.dto.NotificationRequestDto;
import com.example.demo.notificationService.service.NotificationService;
import com.example.demo.shift.logic.ShiftLogic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.example.demo.facebook.logic.FacebookUserLogic.checkSnoozeStatus;
import static com.example.demo.facebook.logic.FacebookUserLogic.hashUserInfo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
class DemoApplicationTests {
@Autowired
FacebookRepository facebookRepository;
    @Test
    void activieShiftTest() {
        assertEquals(ShiftLogic.isActiveShift("2020-12-12"),true);

    }
    @Test
    void testNotificationService() {

        NotificationService notificationService = new NotificationService();
        NotificationRequestDto notificationRequestDto = new NotificationRequestDto("fAtxXT8j7Ng:APA91bEp1D24soV48xDD3TfgdsJgMw6zwXsQWkHhRVdkODn7iuG374tzMQLFosEFDeWAbTAOI02MzM8LdBYkv_TgE2gqQcpk5-jlWEKfNnFtjP3CtPDIMm1Pox71EMLB47y6N_6r-SN2", "From Server", "Spring unit Test");
        System.out.println(notificationService.sendPnsToDevice(notificationRequestDto));
       // assertNotNull(notificationService.sendPnsToDevice(notificationRequestDto));
    }

    @Test
    void hashingTester() {
    assertNotNull(hashUserInfo("020290-2323"));

    }
    @Test
    void getUserTest(){
       ArrayList<FacebookUser> facebookUserArrayList = (ArrayList<FacebookUser>) facebookRepository.findAll();


       assertEquals(facebookUserArrayList.size(),facebookRepository.count());

    }

   @Test
    void checkSnoozedate(){
        assertEquals(checkSnoozeStatus(LocalDate.of(2020,1,19)),true);


   }


}
