package com.glc.managment.dashboard.ui;

import com.glc.managment.security.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by mberhe on 4/13/19.
 */
@Controller
@RequestMapping("/dashboard")
public class DashboardUIController {
    public static final Logger log = LoggerFactory.getLogger(DashboardUIController.class);

    private final AuthenticationService authenticationService;

    @Autowired
    public DashboardUIController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public String
}
