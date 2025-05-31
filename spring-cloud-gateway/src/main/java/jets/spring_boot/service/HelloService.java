package jets.spring_boot.service;

import jets.spring_boot.model.dto.HelloClassDTO;

public interface HelloService {

    String hello();

    HelloClassDTO helloJson();
}
