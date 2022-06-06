package com.diamondjewelry.api.controller;

import com.diamondjewelry.api.model.Contact;
import com.diamondjewelry.api.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("api/v1/contacts")
public class ContactController {
    @Autowired
    private ContactService service;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Contact>> getAllContact() {
        return new ResponseEntity<>(service.getAllContacts(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<?> getContactById(@PathVariable("id") String id) {
        if (!service.getContactById(id).isPresent()) {
            return new ResponseEntity<>("Contact is not found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(service.getContactById(id).get(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Contact> createNewContact(@RequestBody Contact contact) {
        service.addContact(contact);
        return new ResponseEntity<>(contact, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Contact> updateContact(@RequestBody Contact contact) {
        if (!service.getContactById(contact.getId()).isPresent()) {
            service.addContact(contact);
            return new ResponseEntity<>(contact, HttpStatus.CREATED);
        }
        service.updateContact(contact);
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<?> deleteContactById(@PathVariable("id") String id) {
        Optional<Contact> contact = service.getContactById(id);
        if (!contact.isPresent()) {
            return new ResponseEntity<>("Contact is not found.", HttpStatus.NOT_FOUND);
        }
        service.removeContactById(id);
        return new ResponseEntity<>(contact.get(), HttpStatus.OK);
    }
}
