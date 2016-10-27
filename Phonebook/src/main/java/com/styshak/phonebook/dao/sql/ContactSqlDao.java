package com.styshak.phonebook.dao.sql;

import com.styshak.phonebook.dao.ContactDao;
import com.styshak.phonebook.model.Contact;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Profile("sql")
public interface ContactSqlDao extends JpaRepository<Contact, Integer>, ContactDao {

    @Query(value = "SELECT * FROM Contacts WHERE user_id = :usersId AND first_name LIKE %:firstNameExp%", nativeQuery = true)
    List<Contact> findByFirstName(@Param("usersId") String usersId, @Param("firstNameExp") String firstNameExp);

    @Query(value = "SELECT * FROM Contacts WHERE user_id = :usersId AND mobile_phone LIKE %:mobilePhoneExp%", nativeQuery = true)
    List<Contact> findByMobilePhone(@Param("usersId") String usersId, @Param("mobilePhoneExp") String mobilePhoneExp);
}
