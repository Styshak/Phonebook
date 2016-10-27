package com.styshak.phonebook.dao.file;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.styshak.phonebook.dao.ContactDao;
import com.styshak.phonebook.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@Profile("file")
public class ContactFileDao implements ContactDao {

    final private String FILE_PATH = "src/main/resources/contacts.json";

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserFileDao userFileDao;

    @Override
    public List<Contact> findByFirstName(String usersId, String firstNameExp) {
        List<Contact> contacts = userFileDao.findOne(Long.valueOf(usersId)).getContacts();
        List<Contact> result = new ArrayList<>();
        for (Contact contact : contacts) {
            if (contact.getFirstName().contains(firstNameExp)) {
                result.add(contact);
            }
        }
        return result;
    }

    @Override
    public List<Contact> findByMobilePhone(String usersId, String mobileNumberExp) {
        List<Contact> contacts = userFileDao.findOne(Long.valueOf(usersId)).getContacts();
        List<Contact> result = new ArrayList<>();
        for (Contact contact : contacts) {
            if (contact.getMobilePhone().contains(mobileNumberExp)) {
                result.add(contact);
            }
        }
        return result;
    }

    @Override
    public <S extends Contact> S save(S s) {
        List<Contact> contacts = getContacts();
        boolean isUnique = true;
        for (Contact contact : contacts) {
            if (contact.getId() == s.getId())
                isUnique = false;
                break;
        }
        if (isUnique) {
            s.setId(Math.abs((int) UUID.randomUUID().getMostSignificantBits()));
        } else {
            delete(s.getId());
        }
        try {
            if (!isUnique) {
                s.getUser().getContacts().remove(contacts.indexOf(s));
                contacts = getContacts();
            }
            contacts.add(s);
            objectMapper.writeValue(new File(FILE_PATH), contacts);
            userFileDao.addContact(s, s.getUser());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    @Override
    public void delete(Integer id) {
        List<Contact> contacts = getContacts();
        Contact contact = findOne(id);
        contacts.remove(contacts.indexOf(contact));
        try {
            objectMapper.writeValue(new File(FILE_PATH), contacts);
        } catch (IOException e) {
            e.printStackTrace();
        }
        userFileDao.deleteContact(id);
    }

    @Override
    public Contact findOne(Integer id) {
        List<Contact> contacts = getContacts();
        Contact contact = null;
        for (Contact element : contacts) {
            if (element.getId() == id) {
                contact = element;
            }
        }
        return contact;
    }

    public List<Contact> getContacts() {
        File file = new File(FILE_PATH);
        List<Contact> contacts = null;
        if (file.exists() && !file.isDirectory()) {
            try {
                contacts = objectMapper.readValue(file, new TypeReference<List<Contact>>() {
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            contacts = new ArrayList<>();
        }
        return contacts;
    }
}
