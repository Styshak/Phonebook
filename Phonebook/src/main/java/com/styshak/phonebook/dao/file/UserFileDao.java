package com.styshak.phonebook.dao.file;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.styshak.phonebook.dao.UserDao;
import com.styshak.phonebook.model.Contact;
import com.styshak.phonebook.model.User;
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
public class UserFileDao implements UserDao {

    @Autowired
    ObjectMapper objectMapper;

    final private String FILE_PATH = "src/main/resources/users.json";

    @Override
    public User findByLogin(String login) {
        List<User> users;
        User user = null;
        try {
            users = objectMapper.readValue(new File(FILE_PATH), new TypeReference<List<User>>() {
            });
            for (User element : users) {
                if (element.getLogin().equals(login)) {
                    user = element;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public <S extends User> S save(S s) {
        File file = new File(FILE_PATH);
        List<User> users = getUsers();
        s.setContacts(new ArrayList<>());
        s.setId(Math.abs(UUID.randomUUID().getMostSignificantBits()));
        users.add(s);
        try {
            objectMapper.writeValue(file, users);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    public void addContact(Contact contact, User user) {
        try {
            List<User> users = getUsers();
            int index = users.indexOf(user);
            user.getContacts().add(contact);
            users.set(index, user);
            objectMapper.writeValue(new File(FILE_PATH), users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User findOne(Long id) {
        List<User> users = getUsers();
        User user = null;

        for (User element : users) {
            if (element.getId() == id) {
                user = element;
            }
        }
        return user;
    }

    public void deleteContact(Integer id) {
        List<User> users = getUsers();
        List<Contact> contacts;
        Contact contact = null;
        User user = null;
        for (User u : users
                ) {
            contacts = u.getContacts();
            for (Contact element : contacts
                    ) {
                if (element.getId() == id) {
                    contact = element;
                    user = u;
                }
            }
        }
        users.get(users.indexOf(user)).getContacts().remove(contact);

        try {
            objectMapper.writeValue(new File(FILE_PATH), users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<User> getUsers() {
        File file = new File(FILE_PATH);
        List<User> users = null;
        if (file.exists() && !file.isDirectory()) {
            try {
                users = objectMapper.readValue(file, new TypeReference<List<User>>() {
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            users = new ArrayList<>();
        }
        return users;
    }
}
