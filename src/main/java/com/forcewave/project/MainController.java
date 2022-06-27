package com.forcewave.project;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Controller
public class MainController {

    @GetMapping("/main")
    public String main() {
        return "map";
    }

    @GetMapping("/kakao")
    public String kakao() {
        return "kakao2";
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
