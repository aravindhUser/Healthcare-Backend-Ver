package com.cts.AuthService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

//import com.cts.AuthService.config.FeignClientConfig;
import com.cts.AuthService.dto.UserInfo;
import com.cts.AuthService.dto.UserResponse;
import com.cts.AuthService.model.User;
import com.netflix.discovery.EurekaClientConfig;

@FeignClient(name="AUTH")
//,configuration=FeignClientConfig.class)
public interface UserClient {

	@PostMapping("/doctor/register")
	UserInfo saveDoctor(@RequestBody UserInfo info);

    @PostMapping("/patient/register")
    UserInfo savePatient(@RequestBody UserInfo info);
    
    @GetMapping("/doctor/getDoctor/{userId}")
    UserResponse getDoctor(@PathVariable int userId);
    
    @GetMapping("/patient/getPatient/{userId}")
    UserResponse getPatient(@PathVariable int userId);
}

