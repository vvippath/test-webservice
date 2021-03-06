package com.dylan.test.springboot.web;

import com.dylan.test.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount) {
        return new HelloResponseDto(name,amount);
    }

    @GetMapping("/hello/hyun")
    public String checkHyun(@RequestParam("name") String name, @RequestParam("amount") int amount) {
        HelloResponseDto dto = new HelloResponseDto(name, amount);
        return "false";
    }
}
