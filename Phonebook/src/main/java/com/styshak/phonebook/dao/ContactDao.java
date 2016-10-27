package com.styshak.phonebook.dao;

import com.styshak.phonebook.model.Contact;

import java.util.List;

public interface ContactDao {

    List<Contact> findByFirstName(String usersId, String firstNameExp);

    List<Contact> findByMobilePhone(String usersId, String mobilePhoneExp);

    <S extends Contact> S save(S s);

    void delete(Integer id);

    Contact findOne(Integer id);
}
