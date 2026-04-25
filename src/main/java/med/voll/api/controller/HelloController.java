package med.voll.api.controller;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/hello")
public class HelloController {
    @GetMapping("/saludo")
    public String helloWorld(){
        return "Hello World!!!";
    }
}
