package vn.iuh.iuh.fit.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.iuh.iuh.fit.models.Customer;
import vn.iuh.iuh.fit.repositories.CustomerRepository;
import vn.iuh.iuh.fit.services.CustomerServices;

import java.util.List;

@Service
public class CustomerServicesImpl implements CustomerServices {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }
}
