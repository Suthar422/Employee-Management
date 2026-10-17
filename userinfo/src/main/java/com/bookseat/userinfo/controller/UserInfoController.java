package com.bookseat.userinfo.controller;

import com.bookseat.userinfo.dto.UserResponse;
import com.bookseat.userinfo.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("userinfo")
@Slf4j
public class UserInfoController {

    private final UserInfoService userInfoService;


    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @GetMapping("/{userId}")
    public UserResponse getUserInfoById(@PathVariable Long userId) throws InterruptedException {
        log.info("getUserInfoById userId:{}", userId);
        userInfoService.Even();
        userInfoService.Odd();
        return userInfoService.getUserInfoById(userId);
    }

    @GetMapping("/name/{userId}")
    public String getUserName(@PathVariable Long userId) {
        log.info("getUserName for userId:{}", userId);
        return "User Name: " + userId;
    }

}
