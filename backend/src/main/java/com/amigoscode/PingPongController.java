package com.amigoscode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PingPongController{
    record PingPong(String result){}
    @GetMapping("/ping")
    public PingPong getPingPong() {

        return new PingPong("Pong");
    }

}
