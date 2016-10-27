package com.styshak.phonebook.controller;

import com.styshak.phonebook.Application;
import com.styshak.phonebook.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Sergey on 25.10.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={Application.class})
@SpringBootTest
public class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).dispatchOptions(true).build();
    }

    @Test
    public void testRegistrationGet() throws Exception {
        mockMvc.perform(get("/registration")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
        .andExpect(view().name("registration"))
        .andExpect(forwardedUrl("/WEB-INF/views/registration.jsp"));
    }

    @Test
    public void testRegistrationPost() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setLogin("testlogin");
        user.setPassword("testpassword");
        user.setConfirmPassword("testpassword");
        user.setFirstName("testname");
        user.setMiddleName("testMiddleName");
        user.setLastName("testlastname");
        mockMvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("login", user.getLogin())
                .param("password", user.getPassword())
                .param("confirmPassword", user.getConfirmPassword())
                .param("firstName", user.getFirstName())
                .param("middleName", user.getMiddleName())
                .param("lastName", user.getLastName())
        )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/contacts"));
    }
}
