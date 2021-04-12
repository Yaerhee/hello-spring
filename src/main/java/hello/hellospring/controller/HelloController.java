package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello") //http의 GET
    public String hello(Model model) {
        model.addAttribute("data", "yaerhee");
        return "hello";
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(value = "name", required = true) String name, Model model) {
        //"name"부분을 http://localhost:8080/hello-mvc?name=yaerhee!!!! 와 같이 적어서 넘기면 메인에 내용이 출력됨!
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody //응답 body 부분에 바로 직접 넣어주겠다는 내용
    //데이터가 그대로 내려가므로 소스 보기 상으로도 내용만 보임! (viewResolver 사용 X)
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name;
    }

    @GetMapping("hello-api")
    @ResponseBody
    //ResponseBody 사용 후 객체를 반환하면, 객체가 JSON으로 반환된다
    public Hello helloApi(@RequestParam("name") String name) {
        //문자가 아니고 웬 객체가...?
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello {
        private String name;

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

        
    }

}
