package com.amigoscode.customer;

import com.amigoscode.exception.DuplicateResourceNotFoundException;
import com.amigoscode.exception.RequestValidationException;
import com.amigoscode.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private  final CustomerDao customerDao;

    public CustomerService(@Qualifier("jpa") CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> getAllCustomers(){
       return customerDao.selectAllCustomers() ;
    }

    public Customer getCustomer(Integer id){
        return customerDao.selectCustomerById(id).orElseThrow(() -> new ResourceNotFoundException("customer with id [%s] not found".formatted(id)));
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest){

        // Check if email exist
        String email = customerRegistrationRequest.email();
        if (customerDao.existsPersonWithEmail(email)){
            throw new DuplicateResourceNotFoundException("email already taken".formatted(email));
        }
        //add
        Customer customer = new Customer(customerRegistrationRequest.name(),customerRegistrationRequest.email(),customerRegistrationRequest.age());
        customerDao.insertCustomer(customer);
    }

    public void deleteCustomerById(Integer id){
        if(!customerDao.existsPersonWithId(id)){
            throw new ResourceNotFoundException("customer with id [%s] not found".formatted(id));
        }
        customerDao.deleleCustomerById(id);
    }

    public void updateCustomer(Integer id,CustomerUpdateRequest customerUpdateRequest){

        Customer customer = getCustomer(id);

        boolean changes = false;
        if(customerUpdateRequest.name()!=null && !customerUpdateRequest.name().equals(customer.getName())){
            customer.setName(customerUpdateRequest.name());
            customerDao.insertCustomer(customer);
            changes = true;
        }
        if(customerUpdateRequest.age()!=null && !customerUpdateRequest.age().equals(customer.getAge())){
            customer.setAge(customerUpdateRequest.age());
            changes = true;
        }

        if(customerUpdateRequest.email()!=null && !customerUpdateRequest.email().equals(customer.getEmail())){
            if (customerDao.existsPersonWithEmail(customerUpdateRequest.email())){
                throw new DuplicateResourceNotFoundException("email already taken".formatted(customerUpdateRequest.email()));
            }
            customer.setEmail(customerUpdateRequest.email());
            changes = true;
        }
        if (!changes){
            throw  new RequestValidationException("no data changes found");
        }
        customerDao.updateCustomer(customer);
    }
}
