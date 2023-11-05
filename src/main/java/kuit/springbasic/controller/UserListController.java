package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Setter
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserListController {

    private final MemoryUserRepository memoryUserRepository;

    @RequestMapping("/list")
    public ModelAndView userList(HttpServletRequest request) {
        log.info("UserListController.userList");
        HttpSession session = request.getSession();

        if (UserSessionUtils.isLoggedIn(session)) {
            ModelAndView modelAndView = new ModelAndView("user/list");
            Collection<User> users = memoryUserRepository.findAll();
            modelAndView.addObject("users", users);
            return modelAndView;
        }
        return new ModelAndView("redirect:/user/loginForm");
    }
}
