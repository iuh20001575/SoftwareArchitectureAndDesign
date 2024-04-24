package vn.edu.iuh.fit.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customer/quoting")
public class QuotingController {
    @GetMapping
    public ResponseEntity<?> index() {
        return ResponseEntity.ok("OK");
    }
}
