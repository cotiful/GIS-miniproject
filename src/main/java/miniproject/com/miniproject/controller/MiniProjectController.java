package miniproject.com.miniproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MiniProjectController {

    @GetMapping("/mini")
    public String home(){
        return "mini";
    }

    @GetMapping("/main")
    public String forestFire(){
        return "main";
    }
}
