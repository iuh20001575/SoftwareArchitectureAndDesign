package vn.edu.iuh.fit.services;

import vn.edu.iuh.fit.models.Customer;

import java.util.Optional;

public interface CustomerService {
    public Optional<Customer> add(Customer customer);
}
