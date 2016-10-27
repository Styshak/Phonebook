package com.styshak.phonebook.service;

import com.styshak.phonebook.model.User;

public interface UserService {

    User save(User user);

    User findByLogin(String login);
}
