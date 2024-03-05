package app.netlify.maav.passportvalidation.Controller;

import org.springframework.web.bind.annotation.RestController;

import app.netlify.maav.passportvalidation.Model.Passport;
import app.netlify.maav.passportvalidation.Model.Zml;
import app.netlify.maav.passportvalidation.Service.PassportService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("api/v1/passport-validation")
public class PassportController {

    @Autowired
    PassportService service;

    @PostMapping("/zlm")
    public Zml generateZLM(@Valid @RequestBody Passport passport) {

        return service.generateZml(passport);
    }
    
}
