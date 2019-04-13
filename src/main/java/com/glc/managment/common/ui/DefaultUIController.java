package com.glc.managment.common.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by mberhe on 4/13/19.
 */

@Controller
public class DefaultUIController implements ErrorController {

    private static final String ERROR_PAGE_PATH = "error";

    @Autowired
    public DefaultUIController(){

    }

    @RequestMapping("/")
    public String index(){

        return "redirect:/dashboard";
    }

    @RequestMapping("/error")
    public String error(){

        return ERROR_PAGE_PATH;
    }

    public String getErrorPath() {
        return ERROR_PAGE_PATH;
    }
}
