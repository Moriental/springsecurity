package jpabasic.testsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JoinController {
    @GetMapping
    public String join(){
        return "join";
    }
    @PostMapping("/joinProc")
    public String joinProcess(){
        return "redirect:/login";
    }
}
