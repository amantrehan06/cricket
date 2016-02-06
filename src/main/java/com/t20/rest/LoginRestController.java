package com.t20.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginRestController {
	@RequestMapping(value="/rest/login",method=RequestMethod.GET,produces={"text/html"})
    public @ResponseBody String sayHello(@RequestParam(value="name", required=false, defaultValue="Stranger") String name) {
        return "Hello";
    }
}
