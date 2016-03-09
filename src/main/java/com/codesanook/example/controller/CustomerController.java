package com.codesanook.example.controller;

import com.codesanook.example.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private MongoTemplate mongoTemplate;

    @Autowired
    public CustomerController(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    //URL : /customers
    //add new customer
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String addCustomer(@RequestBody Customer customer) {
        mongoTemplate.insert(customer);
        return customer.getId();
    }

    //URL : /customers/{id}
    //get customer by id
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Customer getCustomer(@PathVariable String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Customer customer = mongoTemplate.findOne(query, Customer.class);
        return customer;
    }

    //URL : /customers
    //get all customers
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Customer> getAllCustomers() {
        List<Customer> customer = mongoTemplate.findAll(Customer.class);
        return customer;
    }


    //URL : /customers/{id}
    //update customer with id
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void updateCustomer(@PathVariable String id, @RequestBody Customer request) {
        Query query = new Query(Criteria.where("id").is(id));
        Customer customer = mongoTemplate.findOne(query, Customer.class);
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        mongoTemplate.save(customer);
    }

    //URL : /customers/{id}
    //remove customer by id
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void remove(@PathVariable String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        mongoTemplate.remove(query, Customer.class);
    }

    //URL : /customers
    //remove all customers
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public void removeAll() {
        Query query = new Query();
        mongoTemplate.remove(query, Customer.class);
    }

    //URL : /customers/count
    //get count amount of customers
    @RequestMapping("/count")
    public long countCustomer() {
        Query query = new Query();
        long count = mongoTemplate.count(query, Customer.class);
        return count;
    }

}
