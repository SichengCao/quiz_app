package com.quizapp.service;

import com.quizapp.model.ContactMessage;
import com.quizapp.repository.ContactRepository;
import org.springframework.stereotype.Service;

@Service
public class ContactService {
    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public void saveMessage(ContactMessage message) {
        contactRepository.saveMessage(message);
    }
}
