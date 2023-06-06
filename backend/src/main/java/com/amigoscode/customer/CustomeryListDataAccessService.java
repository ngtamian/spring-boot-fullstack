package com.amigoscode.customer;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("list")
public class CustomeryListDataAccessService implements CustomerDao{

    //db
    private static  List<Customer> customers;

    static {
        customers = new ArrayList<>();
        Customer customer1 = new Customer(1,"Alex","alex.jean@gmail.com",30);
        customers.add(customer1);
        Customer customer2 = new Customer(2,"Jemila","jemila.jean@gmail.com",23);
        customers.add(customer2);
    }
    @Override
    public List<Customer> selectAllCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        Optional<Customer> customer = customers.stream().filter(c-> c.getId().equals(id)).findFirst();
        return customer;
    }

    @Override
    public void insertCustomer(Customer customer) {

        customers.add(customer);

    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        return customers.stream().anyMatch(c -> c.getEmail().equals(email));
    }

    @Override
    public boolean existsPersonWithId(Integer id) {
        return customers.stream().anyMatch(c -> c.getId().equals(id));
    }

    @Override
    public void deleleCustomerById(Integer id) {
        customers.stream().filter(c -> c.getId().equals(id)).findFirst().ifPresent(customers::remove);
    }

    @Override
    public void updateCustomer(Customer update) {

        customers.add(update);

    }
}
