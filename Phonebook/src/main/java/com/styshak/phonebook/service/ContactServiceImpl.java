package com.styshak.phonebook.service;

import com.styshak.phonebook.dao.ContactDao;
import com.styshak.phonebook.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactDao contactDao;

    @Override
    public List<Contact> findByFirstName(String usersId, String firstNameExp) {
        return contactDao.findByFirstName(usersId, firstNameExp);
    }

    @Override
    public List<Contact> findByMobilePhone(String usersId, String mobilePhoneExp) {
        return contactDao.findByMobilePhone(usersId, mobilePhoneExp);
    }

    @Override
    public <S extends Contact> S save(S s) {
        return contactDao.save(s);
    }

    @Override
    public void delete(Integer id) {
        contactDao.delete(id);
    }

    @Override
    public Contact findOne(Integer id) {
        return contactDao.findOne(id);
    }
}
