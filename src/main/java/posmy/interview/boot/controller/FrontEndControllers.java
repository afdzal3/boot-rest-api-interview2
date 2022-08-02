package posmy.interview.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontEndControllers {

    @GetMapping("/loginpage")
    public String getLoginPage(){

        return "loginpage";
    }

    @GetMapping("/bookpage")
    public String getBookPage(){

        return "bookpage";
    }

}
