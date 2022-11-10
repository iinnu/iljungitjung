package com.iljungitjung.domain.notification;

import com.iljungitjung.domain.category.service.CategoryServiceImpl;
import com.iljungitjung.domain.notification.dto.NotificationMessageDto;
import com.iljungitjung.domain.notification.dto.NotificationMessageRequestDto;
import com.iljungitjung.domain.notification.dto.NotificationRequestDto;
import com.iljungitjung.domain.notification.dto.NotificationResponseDto;
import com.iljungitjung.domain.notification.service.NotificationService;
import com.iljungitjung.domain.notification.service.NotificationServiceImpl;
import com.iljungitjung.domain.schedule.entity.Schedule;
import com.iljungitjung.domain.schedule.entity.Type;
import com.iljungitjung.domain.user.entity.User;
import com.iljungitjung.global.scheduler.NotificationNcloud;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("알림 서비스")
@ExtendWith(SpringExtension.class)
public class NotificationServiceTest {

    private NotificationService notificationService;

    @MockBean
    private NotificationNcloud notificationNcloud;

    @BeforeEach
    public void init(){
        notificationService = new NotificationServiceImpl(notificationNcloud);
    }

    @Test
    @DisplayName("메시지 전송")
    public void A() throws Exception {
        String message = "하이";
        String phone = "01000000000";

        NotificationMessageDto notificationMessageDto = new NotificationMessageDto(phone, message);
        List<NotificationMessageDto> messageList = new ArrayList<>();
        messageList.add(notificationMessageDto);
        NotificationRequestDto notificationRequestDto = new NotificationRequestDto(messageList);
        HttpEntity<NotificationMessageRequestDto> body = notificationService.makeBody(notificationRequestDto);

        when(notificationNcloud.sendNcloud(body)).thenReturn(new NotificationResponseDto("202"));

        NotificationResponseDto responseDto = notificationService.sendMessage(notificationRequestDto);

        Assertions.assertEquals(responseDto.getStatusCode(), "202");
    }

    @Test
    @DisplayName("예약 신청 알림 문자 전송")
    public void B() throws Exception {

        String categoryName = "파마";
        String color = "#000000";

        Long scheduleId = 2L;
        Long userToId = 1L;
        Long userFromId = 2L;
        String userFromNickname = "1";
        String userToNickname = "2";
        String phone = "01000000000";
        String email = "email";

        User userFrom = User.builder().nickname(userFromNickname).email(email).build();
        User userTo = User.builder().nickname(userToNickname).phonenum(phone).build();
        userTo.setId(userToId);
        userFrom.setId(userFromId);
        Schedule schedule = Schedule.builder().type(Type.REQUEST).userFrom(userFrom).userTo(userTo)
                .endDate(new Date()).startDate(new Date()).categoryName(categoryName).phonenum(phone).color(color).build();
        schedule.setId(scheduleId);

        notificationService.buildTemplate(schedule);
    }

    @Test
    @DisplayName("예약 승인 문자 전송")
    public void C() throws Exception {
        String categoryName = "파마";
        String color = "#000000";

        Long scheduleId = 2L;
        Long userToId = 1L;
        Long userFromId = 2L;
        String userFromNickname = "1";
        String userToNickname = "2";
        String phone = "01000000000";
        String email = "email";

        User userFrom = User.builder().nickname(userFromNickname).email(email).build();
        User userTo = User.builder().nickname(userToNickname).phonenum(phone).build();
        userTo.setId(userToId);
        userFrom.setId(userFromId);
        Schedule schedule = Schedule.builder().type(Type.REQUEST).userFrom(userFrom).userTo(userTo)
                .endDate(new Date()).startDate(new Date()).categoryName(categoryName).phonenum(phone).color(color).build();
        schedule.setId(scheduleId);
        schedule.accpeted();

        notificationService.buildTemplate(schedule);
    }

    @Test
    @DisplayName("예약 신청 거절 문자 전송")
    public void D() throws Exception {
        String categoryName = "파마";
        String color = "#000000";

        Long scheduleId = 2L;
        Long userToId = 1L;
        Long userFromId = 2L;
        String userFromNickname = "1";
        String userToNickname = "2";
        String phone = "01000000000";
        String email = "email";

        User userFrom = User.builder().nickname(userFromNickname).email(email).build();
        User userTo = User.builder().nickname(userToNickname).phonenum(phone).build();
        userTo.setId(userToId);
        userFrom.setId(userFromId);
        Schedule schedule = Schedule.builder().type(Type.REQUEST).userFrom(userFrom).userTo(userTo)
                .endDate(new Date()).startDate(new Date()).categoryName(categoryName).phonenum(phone).color(color).build();
        schedule.setId(scheduleId);
        String cancelFrom = "제공자";
        schedule.canceled(cancelFrom, "");

        notificationService.buildTemplate(schedule);

    }

    @Test
    @DisplayName("예약 신청 취소 문자 전송")
    public void E() throws Exception {
        String categoryName = "파마";
        String color = "#000000";

        Long scheduleId = 2L;
        Long userToId = 1L;
        Long userFromId = 2L;
        String userFromNickname = "1";
        String userToNickname = "2";
        String phone = "01000000000";
        String email = "email";

        User userFrom = User.builder().nickname(userFromNickname).email(email).build();
        User userTo = User.builder().nickname(userToNickname).phonenum(phone).build();
        userTo.setId(userToId);
        userFrom.setId(userFromId);
        Schedule schedule = Schedule.builder().type(Type.REQUEST).userFrom(userFrom).userTo(userTo)
                .endDate(new Date()).startDate(new Date()).categoryName(categoryName).phonenum(phone).color(color).build();
        schedule.setId(scheduleId);
        String cancelFrom = "사용자";
        schedule.canceled(cancelFrom, "");

        notificationService.buildTemplate(schedule);
    }

    @Test
    @DisplayName("예약 취소 문자 전송")
    public void F() throws Exception {
        String categoryName = "파마";
        String color = "#000000";

        Long scheduleId = 2L;
        Long userToId = 1L;
        Long userFromId = 2L;
        String userFromNickname = "1";
        String userToNickname = "2";
        String phone = "01000000000";
        String email = "email";

        User userFrom = User.builder().nickname(userFromNickname).email(email).build();
        User userTo = User.builder().nickname(userToNickname).phonenum(phone).build();
        userTo.setId(userToId);
        userFrom.setId(userFromId);
        Schedule schedule = Schedule.builder().type(Type.ACCEPT).userFrom(userFrom).userTo(userTo)
                .endDate(new Date()).startDate(new Date()).categoryName(categoryName).phonenum(phone).color(color).build();
        schedule.setId(scheduleId);
        schedule.deleted();

        notificationService.buildTemplate(schedule);
    }

}
