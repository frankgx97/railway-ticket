package cn.guoduhao.TicketSystem.Controllers;

import cn.guoduhao.TicketSystem.Models.UserCreateForm;
import cn.guoduhao.TicketSystem.Models.Validator.UserCreateFormValidator;
import cn.guoduhao.TicketSystem.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(cn.guoduhao.TicketSystem.Controllers.AuthController.class);
    private final UserService userService;
    private final UserCreateFormValidator userCreateFormValidator;

    @Autowired
    public AuthController(UserService userService, UserCreateFormValidator userCreateFormValidator) {
        this.userService = userService;
        this.userCreateFormValidator = userCreateFormValidator;
    }

    @InitBinder("form")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userCreateFormValidator);
    }

//    @RequestMapping(value = "/login_register", method = RequestMethod.GET)
//    public String getRegisterPage() {
//        return "login_register";
//    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getRegisterPage(Model model) {
        model.addAttribute("form", new UserCreateForm());
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String handleUserCreateForm(Model model, @Valid @ModelAttribute("form") UserCreateForm form, BindingResult bindingResult) {
        LOGGER.debug("用户提交的表单：{}, 验证结果：{}", form, bindingResult);
        if(bindingResult.hasErrors()) {
            model.addAttribute("error", "出错啦");
            return "register";
        }
        try{
            userService.create(form);
        } catch (DataIntegrityViolationException e) {
            LOGGER.warn("保存时出错，邮箱已经注册了！", e);
            bindingResult.reject("email.exists", "Email already exists");
            return "register";
        }
        return "redirect:/home";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage(Model model, @RequestParam Optional<String> error) {
//        return new ModelAndView("login", "error", error);
        model.addAttribute("error", error);
        return "login";
    }
}

