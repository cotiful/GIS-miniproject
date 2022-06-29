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
}
