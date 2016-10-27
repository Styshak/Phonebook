package com.styshak.phonebook.dao.sql;


import com.styshak.phonebook.dao.UserDao;
import com.styshak.phonebook.model.User;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Profile("sql")
public interface UserSqlDao extends JpaRepository<User,Long>, UserDao {
    User findByLogin(String login);
}
