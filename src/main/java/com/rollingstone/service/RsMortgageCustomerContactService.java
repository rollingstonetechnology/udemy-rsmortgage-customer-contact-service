package com.rollingstone.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.rollingstone.dao.jpa.RsMortgageCustomerContactRepository;
import com.rollingstone.domain.Contact;
import com.rollingstone.domain.Customer;

/*
 * Service class to do CRUD for Customer Contact through JPS Repository
 */
@Service
public class RsMortgageCustomerContactService {

    private static final Logger log = LoggerFactory.getLogger(RsMortgageCustomerContactService.class);

    @Autowired
    private RsMortgageCustomerContactRepository customerContactRepository;

    @Autowired
    CounterService counterService;

    @Autowired
    GaugeService gaugeService;

    public RsMortgageCustomerContactService() {
    }

    public Contact createContact(Contact contact) {
        return customerContactRepository.save(contact);
    }

    public Contact getContact(long id) {
        return customerContactRepository.findOne(id);
    }

    public void updateContact(Contact contact) {
    	customerContactRepository.save(contact);
    }

    public void deleteContact(Long id) {
    	customerContactRepository.delete(id);
    }

    public Page<Contact> getAllContactsByPage(Integer page, Integer size) {
        Page pageOfContacts = customerContactRepository.findAll(new PageRequest(page, size));
        
        // example of adding to the /metrics
        if (size > 50) {
            counterService.increment("com.rollingstone.getAll.largePayload");
        }
        return pageOfContacts;
    }
    
    public List<Contact> getAllContacts() {
        Iterable<Contact> pageOfContacts = customerContactRepository.findAll();
        
        List<Contact> customers = new ArrayList<Contact>();
        
        for (Contact contact : pageOfContacts){
        	customers.add(contact);
        }
    	log.info("In Real Service getAllContacts  size :"+customers.size());

    	
        return customers;
    }
    
    public List<Contact> getAllContactsForCustomer(Customer customer) {
        Iterable<Contact> pageOfContacts = customerContactRepository.findCustomerContactByCustomer(customer);
        
        List<Contact> customers = new ArrayList<Contact>();
        
        for (Contact contact : pageOfContacts){
        	customers.add(contact);
        }
    	log.info("In Real Service getAllContacts  size :"+customers.size());

    	
        return customers;
    }
}
