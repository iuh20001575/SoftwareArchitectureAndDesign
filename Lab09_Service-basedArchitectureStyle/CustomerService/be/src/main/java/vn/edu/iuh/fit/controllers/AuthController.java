package vn.edu.iuh.fit.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.fit.dto.UserCredentialsDTO;
import vn.edu.iuh.fit.dto.response.AuthenticationResponse;
import vn.edu.iuh.fit.models.Customer;
import vn.edu.iuh.fit.repositories.CustomerRepository;
import vn.edu.iuh.fit.services.CustomerService;
import vn.edu.iuh.fit.utils.JwtUtil;

import java.util.Optional;

@RestController
@RequestMapping("/api/customer/auth")
public class AuthController {
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(CustomerService customerService,
                          CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerService = customerService;
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Customer customer) {
        Optional<Customer> oCustomer = customerService.add(customer);

        if (oCustomer.isEmpty())
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(oCustomer.get());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserCredentialsDTO userCredentials) {
        Optional<Customer> oCustomer = customerRepository.findByPhone(userCredentials.getPhone());

        if (oCustomer.isEmpty())
            return ResponseEntity.notFound().build();

        String encode = passwordEncoder.encode(userCredentials.getPassword());
        System.out.println("Encode password: " + encode);
        System.out.println("Password: " + oCustomer.get().getPassword());

        Customer customer = oCustomer.get();
        if (!passwordEncoder.matches(userCredentials.getPassword(), customer.getPassword()))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        String token = JwtUtil.generateToken(userCredentials);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse(token);
        return ResponseEntity.ok(authenticationResponse);
    }
}
