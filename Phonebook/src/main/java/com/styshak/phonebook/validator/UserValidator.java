package com.styshak.phonebook.validator;

import com.styshak.phonebook.model.User;
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
public class UserValidator implements Validator {

    private String loginPattern = "^[a-zA-Z]+$";

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        if (user.getLogin().length() <= 2) {
            errors.rejectValue("login", "Size.userForm.login");
            return;
        }
        if (!isValid(loginPattern, user.getLogin())) {
            errors.rejectValue("login", "UserForm.incorrect");
            return;
        }

        if (userService.findByLogin(user.getLogin()) != null) {
            errors.rejectValue("login", "Duplicate.userForm.login");
            return;
        }

        if (user.getPassword().length() <= 4) {
            errors.rejectValue("password", "Size.userForm.password");
            return;
        }

        if (!user.getConfirmPassword().equals(user.getPassword())) {
            errors.rejectValue("confirmPassword", "Different.userForm.password");
            return;
        }

        if (user.getFirstName().length() <= 4) {
            errors.rejectValue("firstName", "Size.userForm.firstName");
            return;
        }

        if (user.getMiddleName().length() <= 4) {
            errors.rejectValue("middleName", "Size.userForm.middleName");
            return;
        }

        if (user.getLastName().length() <= 4) {
            errors.rejectValue("lastName", "Size.userForm.lastName");
            return;
        }
    }

    private boolean isValid(String pattern, String value) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(value);
        return m.matches();
    }
}
