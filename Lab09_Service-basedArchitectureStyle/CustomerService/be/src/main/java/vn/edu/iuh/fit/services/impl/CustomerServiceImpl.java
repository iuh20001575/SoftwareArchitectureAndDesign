package vn.edu.iuh.fit.services.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.models.Customer;
import vn.edu.iuh.fit.repositories.CustomerRepository;
import vn.edu.iuh.fit.services.CustomerService;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerServiceImpl(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<Customer> add(Customer customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));

        Customer saved = customerRepository.save(customer);

        return Optional.of(saved);
    }
}
