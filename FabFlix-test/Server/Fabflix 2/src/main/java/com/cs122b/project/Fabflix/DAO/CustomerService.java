package com.cs122b.project.Fabflix.DAO;

import com.cs122b.project.Fabflix.model.Customer;

public class CustomerService {

    private DBService dbService;

    public Customer login(Customer customer) {
        return dbService.login(customer);
    }
}
