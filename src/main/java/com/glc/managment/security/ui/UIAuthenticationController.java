package com.glc.managment.security.ui;

import com.glc.managment.common.util.MessageSourceHolder;
import com.glc.managment.member.CredentialsTypeEnum;
import com.glc.managment.security.AuthenticationRequestDTO;
import com.glc.managment.security.AuthenticationService;
import com.glc.managment.security.AuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * Created by mberhe on 4/13/19.
 */
@Controller
@RequestMapping("/ui")
public class UIAuthenticationController {

    private static final Logger log = LoggerFactory.getLogger(UIAuthenticationController.class);

    private static final String LOGIN_FORM_PATH = "login/login";

    private final AuthenticationService authenticationService;
    private final HttpSession httpSession;
    private MessageSource messageSource = MessageSourceHolder.messageSource;

    @Autowired
    public UIAuthenticationController(AuthenticationService authenticationService, HttpSession httpSession) {
        this.authenticationService = authenticationService;
        this.httpSession = httpSession;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLogin() {

        return LOGIN_FORM_PATH;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(Model model, @Validated @ModelAttribute AuthenticationRequestDTO authenticationRequest) {

            if(log.isDebugEnabled())
                log.debug(String.format("Authenticating using principal: %s", authenticationRequest.getPrincipal()));

            try {

                AuthenticationToken authenticationToken = authenticationService.loginByEmail(authenticationRequest.getPrincipal(), authenticationRequest.getCredentials(), CredentialsTypeEnum.PASSWORD);

                if (log.isDebugEnabled())
                    log.debug(String.format("Authenticated using principal: %s", authenticationRequest.getPrincipal()));

                if (null == authenticationToken.getMember().getRoles() || authenticationToken.getMember().getRoles().isEmpty()) {

                    log.info(String.format("Authenticated principal: %s does not have access to the web portal", authenticationRequest.getPrincipal()));

                    authenticationService.logout();

                    return "web_app_access_not_allowed";
                }

                return "redirect:/dashboard";

            }catch (AuthenticationException ex) {

                if (log.isInfoEnabled())
                    log.info(String.format("check if user exist with principal: %s failed. Error: %s", authenticationRequest.getPrincipal(), ex.getMessage()));

                model.addAttribute("error", messageSource.getMessage("security.ui.login.authentication.error", new String[]{}, "Invalid username or password", LocaleContextHolder.getLocale()));

                return LOGIN_FORM_PATH;

            } catch (Exception ex) {

                if(log.isInfoEnabled())
                    log.info(String.format("Authentication using principal: %s failed. Error: %s", authenticationRequest.getPrincipal(), ex.getMessage()));

                model.addAttribute("error", messageSource.getMessage("security.ui.login.generic.error", new String[]{}, "Unable to process your login. Please contact customer service.", LocaleContextHolder.getLocale()));

                return LOGIN_FORM_PATH;
            }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        authenticationService.logout();
        return LOGIN_FORM_PATH;
    }
}
