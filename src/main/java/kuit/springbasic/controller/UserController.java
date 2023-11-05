package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {
    private final MemoryUserRepository memoryUserRepository;

    @RequestMapping("/user/form")
    public String showUserForm() {
        log.info("UserController.showUserForm");
        return "/user/form";
    }

    @RequestMapping("/user/signup")
    public String createUserV1(@RequestParam("userId") String userId, @RequestParam("password") String password, @RequestParam("name") String name, @RequestParam("email") String email) {
        User user = new User(userId, password, name, email);

        memoryUserRepository.insert(user);
        return "redirect:/user/list";
    }

    @RequestMapping("/user/list")
    public String showUserList(HttpServletRequest request) throws Exception {
        if (UserSessionUtils.isLoggedIn(request.getSession())) {
            request.setAttribute("users", memoryUserRepository.findAll());
            return "/user/list";
        }
        return "redirect:/user/loginForm";
    }

    @RequestMapping("/user/updateForm")
    public String showUserUpdateForm() {
        log.info("userController.showUserUpdateForm");
        return "/user/updateForm";
    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
    @RequestMapping("/user/update")
    public String updateUser(@RequestParam("userId") String userId, @RequestParam("password") String password, @RequestParam("name") String name, @RequestParam("email") String email) {
        memoryUserRepository.update(new User(userId, password, name, email));
        return "redirect:/user/list";
    }
}
