package miniproject.com.miniproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MiniProjectController {
    
    @GetMapping("/miniproject")
    public String miniproject(){
        return "miniproject";
    }
}