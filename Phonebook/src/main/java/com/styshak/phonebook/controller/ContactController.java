package com.styshak.phonebook.controller;

import com.styshak.phonebook.model.Contact;
import com.styshak.phonebook.service.ContactService;
import com.styshak.phonebook.service.UserService;
import com.styshak.phonebook.validator.ContactValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ContactController {

    @Autowired
    private UserService userService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private ContactValidator contactValidator;

    @RequestMapping(value = {"/contacts"}, method = RequestMethod.GET)
    public String getContacts(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Contact> contactList = userService.findByLogin(userDetails.getUsername()).getContacts();
        model.addAttribute("contactList", contactList);
        return "contacts";
    }

    @RequestMapping(value = "/contactDetail/{id}", method = RequestMethod.GET)
    public String getEditView(@PathVariable("id") int id, Model model) {
        if(id > 0) {
            Contact contact = contactService.findOne(id);
            if(contact != null) {
                model.addAttribute("contactForm", contact);
                model.addAttribute("contactId", contact.getId());
            } else {
                model.addAttribute("contactForm", new Contact());
            }
        } else {
            Contact contact = new Contact();
            contact.setId(id);
            model.addAttribute("contactForm", contact);
        }
        return "contactDetail";
    }

    @RequestMapping(value = "/contactDetail", method = RequestMethod.POST)
    public String registration(@ModelAttribute("contactForm") Contact contact, BindingResult bindingResult, Model model) {
        contactValidator.validate(contact, bindingResult);
        if (bindingResult.hasErrors()) {
            return "contactDetail";
        }
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        contact.setUser(userService.findByLogin(userDetails.getUsername()));
        contactService.save(contact);
        return "redirect:/contacts";
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
    public String deleteContact(@PathVariable("id") int id) {
        contactService.delete(id);
        return "redirect:/contacts";
    }

    @RequestMapping(value = "/searchContact", method = RequestMethod.POST)
    public String searchContactByParam(@RequestParam(value = "searchValue", required = false) String searchValue,
                                       @RequestParam(value = "searchType", required = false) String searchType,
                                        Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Contact> contactList = null;
        if(!searchValue.trim().isEmpty() && !searchType.trim().isEmpty()) {
            switch (searchType) {
                case "First name":
                    contactList = contactService.findByFirstName(String.valueOf(userService.findByLogin(userDetails.getUsername()).getId()), searchValue);
                    break;
                case "Mobile phone":
                    contactList = contactService.findByMobilePhone(String.valueOf(userService.findByLogin(userDetails.getUsername()).getId()), searchValue);
                    break;
            }
        } else if (searchValue.trim().isEmpty()){
            contactList = userService.findByLogin(userDetails.getUsername()).getContacts();
        }
        model.addAttribute("contactList", contactList);
        return "contacts";
    }
}
