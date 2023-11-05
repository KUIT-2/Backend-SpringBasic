package kuit.springbasic.controller;

import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class CreateUserController {

    private final MemoryUserRepository memoryUserRepository;

    @RequestMapping("/form")
    public String loadCreateForm() {
        log.info("CreateUserController.loadCreateForm");
        ForwardController.forwardUrl = "user/form";
        return "redirect:/forward";
    }

    @RequestMapping("/signup")
    public ModelAndView createUser(
            @ModelAttribute User newUser
            ) {
        log.info("CreateUserController.createUser");

        memoryUserRepository.insert(newUser);

        ModelAndView modelAndView = new ModelAndView("redirect:/user/list");

        return modelAndView;
    }
}
