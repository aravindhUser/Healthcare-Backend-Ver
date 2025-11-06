package com.cts.AuthService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cts.AuthService.config.FeignClientConfig;
//import com.cts.AuthService.config.FeignClientConfig;
import com.cts.AuthService.dto.UserInfo;
import com.cts.AuthService.dto.UserResponse;
import com.cts.AuthService.model.User;
import com.netflix.discovery.EurekaClientConfig;

@FeignClient(name="APIGATEWAY" ,configuration=FeignClientConfig.class)
public interface UserClient {

	@PostMapping("user/doctor/register")
	UserInfo saveDoctor(@RequestBody UserInfo info);

    @PostMapping("user/patient/register")
    UserInfo savePatient(@RequestBody UserInfo info);
    
    @GetMapping("user/doctor/getDoctor/{userId}")
    UserResponse getDoctor(@PathVariable int userId);
    
    @GetMapping("user/patient/getPatient/{userId}")
    UserResponse getPatient(@PathVariable int userId);
}

