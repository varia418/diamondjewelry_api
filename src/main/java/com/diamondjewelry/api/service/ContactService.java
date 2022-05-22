package com.diamondjewelry.api.service;

import com.diamondjewelry.api.model.Contact;
import com.diamondjewelry.api.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {
    @Autowired
    private ContactRepository repository;

    public List<Contact> getAllContacts() {
        return repository.findAll();
    }

    public Optional<Contact> getContactById(String id) {
        return repository.findById(id);
    }

    public void addContact(Contact contact) {
        repository.insert(contact);
    }

    public void updateContact(Contact contact) {
        repository.save(contact);
    }

    public void removeContactById(String id) {
        repository.deleteById(id);
    }
}
