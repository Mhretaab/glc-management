package com.glc.managment.security.ui;

import com.glc.managment.security.AuthenticationRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by mberhe on 4/13/19.
 */
@Controller
@RequestMapping("/ui")
public class UIAuthenticationController {

    private static final Logger log = LoggerFactory.getLogger(UIAuthenticationController.class);

    private static final String LOGIN_FORM_PATH = "login/login";



    @Autowired
    public UIAuthenticationController() {

    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLogin() {

        return LOGIN_FORM_PATH;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(Model model, @Validated @ModelAttribute AuthenticationRequestDTO authenticationRequest) {
        return LOGIN_FORM_PATH;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        return LOGIN_FORM_PATH;
    }
}
