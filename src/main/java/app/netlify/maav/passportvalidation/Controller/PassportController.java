package app.netlify.maav.passportvalidation.Controller;

import org.springframework.web.bind.annotation.RestController;

import app.netlify.maav.passportvalidation.Model.Passport;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("api/v1/passport-validation")
public class PassportController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @PostMapping("/zlm")
    public ResponseEntity<String> generateZLM(@Valid @RequestBody Passport passport) {
        
        return ResponseEntity.ok("Valid request");
    }
    
}
