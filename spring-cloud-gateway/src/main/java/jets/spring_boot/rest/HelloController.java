package jets.spring_boot.rest;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import jets.spring_boot.model.dto.HelloClassDTO;
import jets.spring_boot.service.HelloService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    
    private final HelloService helloService;
    
    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/hello")
    void sayHello(HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();

        writer.write(helloService.hello());
    }
    
    @GetMapping(value = "/helloJson", produces = MediaType.APPLICATION_JSON_VALUE)
    HelloClassDTO sayHelloJson() {
        return helloService.helloJson();
    }
}
