package vn.edu.iuh.fit.services.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.models.Customer;
import vn.edu.iuh.fit.repositories.CustomerRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final CustomerRepository customerRepository;

    public UserDetailsServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String phone) {
        Customer customer = customerRepository.findByPhone(phone).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        System.out.println(customer);

        return new User(customer.getPhone(), customer.getPassword(), customer.getAuthorities());
    }
}
