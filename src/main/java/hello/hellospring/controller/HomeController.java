package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // 도메인의 첫 번째
    // localhost8080 들어가면 처음으로 보이는 화면
    @GetMapping("/")
    public String home() {
        return "home";
    }
}
