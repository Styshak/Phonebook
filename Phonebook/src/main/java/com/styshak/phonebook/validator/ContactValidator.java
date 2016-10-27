package com.styshak.phonebook.validator;

import com.styshak.phonebook.model.Contact;
import com.styshak.phonebook.model.User;
import com.styshak.phonebook.service.ContactService;
import com.styshak.phonebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validator for {@link com.styshak.phonebook.model.User} class,
 * implements {@link Validator} interface.
 *
 * @author Sergey Styshak
 * @version 1.0
 */

@Component
public class ContactValidator implements Validator {

    private String mobilePhonePattern = "^\\+380\\d{9}$";
    private String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";;

    @Autowired
    private ContactService contactService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Contact.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Contact contact = (Contact) o;

        if (contact.getFirstName().length() <= 3) {
            errors.rejectValue("firstName", "Size.contactForm.firstName");
            return;
        }
        if (contact.getMiddleName().length() <= 3) {
            errors.rejectValue("middleName", "Size.contactForm.middleName");
            return;
        }
        if (contact.getLastName().length() <= 3) {
            errors.rejectValue("lastName", "Size.contactForm.lastName");
            return;
        }
        if (contact.getMobilePhone().trim().length() == 0) {
            errors.rejectValue("mobilePhone", "Required");
            return;
        }
        if (!isValid(mobilePhonePattern, contact.getMobilePhone())) {
            errors.rejectValue("mobilePhone", "ContactForm.incorrectPhone");
            return;
        }
        if (contact.getPhone().trim().length() > 0 && !isValid(mobilePhonePattern, contact.getPhone())) {
            errors.rejectValue("phone", "ContactForm.incorrectPhone");
            return;
        }
        if (contact.getEmail().trim().length() > 0 && !isValid(emailPattern, contact.getEmail())) {
            errors.rejectValue("email", "ContactForm.incorrectEmail");
            return;
        }
    }

    private boolean isValid(String pattern, String value) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(value);
        return m.matches();
    }
}
