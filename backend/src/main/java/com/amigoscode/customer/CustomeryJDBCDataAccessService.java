package com.amigoscode.customer;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomeryJDBCDataAccessService implements  CustomerDao{

    private final JdbcTemplate jdbcTemplate;

    private final CustomerRowMapper customerRowMapper;

    public CustomeryJDBCDataAccessService(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    @Override
    public List<Customer> selectAllCustomers() {
        var sql = """
                  SELECT id, name, email,password, age, gender
                  FROM customer
                  """;
        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {

        var sql = """
                  SELECT id, name, email,password,age, gender
                  FROM customer
                  WHERE id  = ?
                  """;
        return jdbcTemplate.query(sql, customerRowMapper, id).stream().findFirst();
    }

    @Override
    public void insertCustomer(Customer customer) {

        var sql = """
                  INSERT INTO customer(name,email,password,age, gender)
                  VALUES(?,?,?,?,?)
                  """;
        int result = jdbcTemplate.update(sql,
                customer.getName(),
                customer.getEmail(),
                customer.getPassword(),
                customer.getAge(),
                customer.getGender().name());
        System.out.println(" jdbcTemplate.update : "+result);


    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        var sql = """
                  SELECT count(id)
                  FROM customer
                  WHERE email = ?
                  """;
        Integer  count = jdbcTemplate.queryForObject(sql,Integer.class,email);
        return count !=null && count > 0;
    }

    @Override
    public boolean existsPersonWithId(Integer id) {
        var sql = """
                  SELECT count(id)
                  FROM customer
                  WHERE id = ?
                  """;
        Integer  count = jdbcTemplate.queryForObject(sql,Integer.class,id);
        return count !=null && count > 0;
    }

    @Override
    public void deleleCustomerById(Integer id) {

        var sql = """
                  DELETE 
                  FROM customer
                  WHERE id = ?
                  
                  """;
        int  result = jdbcTemplate.update(sql,id);
        System.out.println(" deleleCustomerById result = " + result);

    }

    @Override
    public void updateCustomer(Customer update) {

    }

    @Override
    public Optional<Customer> selectUserByEmail(String email) {
        var sql = """
                  SELECT id, name, email,password,age, gender
                  FROM customer
                  WHERE email  = ?
                  """;
        return jdbcTemplate.query(sql, customerRowMapper, email).stream().findFirst();
    }
}
