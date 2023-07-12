package com.amigoscode.customer;

import com.amigoscode.exception.DuplicateResourceNotFoundException;
import com.amigoscode.exception.RequestValidationException;
import com.amigoscode.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private  final CustomerDao customerDao;
    private final CustomerDTOMapper customerDTOMapper;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(@Qualifier("jpa") CustomerDao customerDao, CustomerDTOMapper customerDTOMapper, PasswordEncoder passwordEncoder) {
        this.customerDao = customerDao;
        this.customerDTOMapper = customerDTOMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<CustomerDTO> getAllCustomers(){
       return customerDao.selectAllCustomers().stream().map(customerDTOMapper)
       .collect(Collectors.toList());
    }

    public CustomerDTO getCustomer(Integer id){
        return customerDao.selectCustomerById(id).map(customerDTOMapper).orElseThrow(() -> new ResourceNotFoundException("customer with id [%s] not found".formatted(id)));
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest){

        // Check if email exist
        String email = customerRegistrationRequest.email();
        if (customerDao.existsPersonWithEmail(email)){
            throw new DuplicateResourceNotFoundException("email already taken".formatted(email));
        }
        //add
        Customer customer = new Customer(
                customerRegistrationRequest.name(),
                customerRegistrationRequest.email(),
                passwordEncoder.encode(customerRegistrationRequest.password()),
                customerRegistrationRequest.age(),
                customerRegistrationRequest.gender());
        customerDao.insertCustomer(customer);
    }

    public void deleteCustomerById(Integer id){
        if(!customerDao.existsPersonWithId(id)){
            throw new ResourceNotFoundException("customer with id [%s] not found".formatted(id));
        }
        customerDao.deleleCustomerById(id);
    }

    public void updateCustomer(Integer id,CustomerUpdateRequest customerUpdateRequest){

        Customer customer = customerDao.selectCustomerById(id).orElseThrow(() -> new ResourceNotFoundException("customer with id [%s] not found".formatted(id)));

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
