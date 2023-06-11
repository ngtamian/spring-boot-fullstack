package com.amigoscode.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class PingPongController{

    record PingPong(String result){}

    @GetMapping("/ping")
    public List<Customer> getPingPong() {

        return new PingPong("Pong");
    }

}
