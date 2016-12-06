package com.rollingstone.api.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rollingstone.domain.Contact;
import com.rollingstone.domain.Customer;
import com.rollingstone.exception.HTTP400Exception;
import com.rollingstone.service.RsMortgageCustomerContactService;
/*
 * Demonstrates how to set up RESTful API endpoints using Spring MVC
 */

@RestController
@RequestMapping(value = "/rsmortgage-customer-contact-service/v1/customer-contact")
public class CustomerContactController extends AbstractRestController {

    @Autowired
    private RsMortgageCustomerContactService customerContactService;
  
    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.CREATED)
    public void createCustomerContact(@RequestBody Contact contact,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception {
    	Contact createdContact = this.customerContactService.createContact(contact);
        response.setHeader("Location", request.getRequestURL().append("/").append(createdContact.getId()).toString());
    }

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    Page<Contact> getAllCustomersContactByPage(@RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
                                      @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
                                      HttpServletRequest request, HttpServletResponse response) {
        return this.customerContactService.getAllContactsByPage(page, size);
    }
    
    @RequestMapping(value = "/all",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    List<Contact> getAllCustomerContacts(@RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
                                      @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
                                      HttpServletRequest request, HttpServletResponse response) {
        return this.customerContactService.getAllContacts();
    }
    
    @RequestMapping(value = "/all/{customerId}",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    List<Contact> getAllCustomerContactsForSingleCustomer(@RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
                                      @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
                                      @PathVariable("id") Long id,
                                      HttpServletRequest request, HttpServletResponse response) {
        return this.customerContactService.getAllContactsForCustomer(new Customer());
    }

    
    @RequestMapping("/simple/{id}")
	public Contact getSimpleCustomerContact(@PathVariable("id") Long id) {
    	Contact contact = this.customerContactService.getContact(id);
         checkResourceFound(contact);
         return contact;
	}

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    Contact getContact(@PathVariable("id") Long id,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
    	Contact contact = this.customerContactService.getContact(id);
        checkResourceFound(contact);
        return contact;
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PUT,
            consumes = {"application/json", "application/xml"},
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomerContact(@PathVariable("id") Long id, @RequestBody Contact contact,
                                 HttpServletRequest request, HttpServletResponse response) throws Exception {
        checkResourceFound(this.customerContactService.getContact(id));
        if (id != contact.getId()) throw new HTTP400Exception("ID doesn't match!");
        this.customerContactService.updateContact(contact);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = {"application/json", "application/xml"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomerContact(@PathVariable("id") Long id, HttpServletRequest request,
                                 HttpServletResponse response) {
        checkResourceFound(this.customerContactService.getContact(id));
        this.customerContactService.deleteContact(id);
    }
}
