package com.rollingstone.dao.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.rollingstone.domain.Contact;
import com.rollingstone.domain.Customer;



public interface RsMortgageCustomerContactRepository extends PagingAndSortingRepository<Contact, Long> {
    Contact findCustomerContactByEmailAddress(String emailAddress);
    List<Contact> findCustomerContactByCustomer(Customer customer);

    Page findAll(Pageable pageable);
}
