package jets.spring_boot.client;

import jets.spring_boot.model.dto.HelloClassDTO;
import jets.spring_boot.service.HelloService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HelloServiceProxy implements HelloService {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public HelloServiceProxy(RestTemplate restTemplate, @Value("${hello.service.url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    @Override
    public String hello() {
        return restTemplate.getForObject(baseUrl + "/hello", String.class);
    }

    @Override
    public HelloClassDTO helloJson() {
        return restTemplate.getForObject(baseUrl + "/helloJson", HelloClassDTO.class);
    }
}
