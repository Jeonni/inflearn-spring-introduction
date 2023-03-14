package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!");
        return "hello";
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-templates";
    }

    @GetMapping("hello-string")
    @ResponseBody // http 의 응답 body 부에 해당 내용을 직접 넣어주겠다는 의미
    public String helloString(@RequestParam("name") String name) {
        return "hello" + name; // "hello (요청한 내용)"
    }


    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    public class Hello {
        private String name;

        // getter setter window 단축키: alt+insert
        // getter, setter 사용하는 이유: 캡슐화, 객체의 무결성, 정보 은닉을 위해
        // - 필드들은 private 접근 제한자로 막아두고, 각 필드의 Getter, Setter로 접근하는 방식
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
