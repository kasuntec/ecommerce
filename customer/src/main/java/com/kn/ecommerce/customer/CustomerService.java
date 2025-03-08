package com.kn.ecommerce.customer;

import ch.qos.logback.core.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    public CustomerResponse save(CustomerRequest customerRequest) {
        Customer savedCustomer = customerMapper.toCustomer(customerRequest);
        return customerMapper.toCustomerResponse(customerRepository.save(savedCustomer));
    }

    public CustomerResponse findById(String id) {
        Customer customer = customerRepository
                .findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer not found, id" + id));
        return customerMapper.toCustomerResponse(customer);
    }

    public CustomerResponse update(String id, CustomerRequest customerRequest) {
        Customer customer = customerRepository
                .findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer not found, id" + id));
        mergeCustomer(customer, customerRequest);
        customerRepository.save(customer);
        return customerMapper.toCustomerResponse(customer);
    }

    private void mergeCustomer(Customer customer, CustomerRequest request) {
        if (StringUtil.notNullNorEmpty(request.firstName())) {
            customer.setFirstName(request.firstName());
        }
        if (StringUtil.notNullNorEmpty(request.lastName())) {
            customer.setLastName(request.lastName());
        }
        if (StringUtil.notNullNorEmpty(request.firstName())) {
            customer.setEmail(request.email());
        }
        if (StringUtil.notNullNorEmpty(request.firstName())) {
            customer.setMobile(request.mobile());
        }
        if (request.address() != null) {
            customer.setAddress(request.address());
        }
    }

    public Collection<CustomerResponse> findAll() {
        return customerRepository
                .findAll()
                .stream()
                .map(customerMapper::toCustomerResponse)
                .collect(Collectors.toList());
    }

    public void deleteById(String id) {
        customerRepository.deleteById(id);
    }
}
