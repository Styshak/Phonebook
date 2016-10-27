package com.styshak.phonebook.dao;

import com.styshak.phonebook.model.User;


public interface UserDao {
    User findByLogin(String login);
    <S extends User> S save(S s);
}
