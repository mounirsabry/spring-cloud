package jets.spring_boot.rest;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    void sayHello(HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        
        writer.write("<div>Hello</div>");
    }
    
    @GetMapping(value = "/helloJson", produces = MediaType.APPLICATION_JSON_VALUE)
    HelloClassDTO sayHelloJson() {
        return new HelloClassDTO("Hello from a message passed to constructor");
    }

    private record HelloClassDTO(String message) {
    }
}
