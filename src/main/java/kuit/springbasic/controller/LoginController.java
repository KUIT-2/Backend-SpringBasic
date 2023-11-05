package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class LoginController {

    private final MemoryUserRepository memoryUserRepository;

    /**
     * TODO: showLoginForm
     */
    //세 가지 방식
    //1. 그냥 RequestMapping("/loginForm")
    //2. 인자로 RequestMapping(value = "/loginForm", method = RequestMethod.GET)
    //3. 다른 annotation으로 GetMapping("/loginForm")
    @RequestMapping("/loginForm")
    public String showLoginForm() {
        log.info("LoginController.showLoginForm");
        return "user/login";
    }

    /**
     * TODO: showLoginFailed
     */
    @RequestMapping("/loginFailed")
    public String loginFailed() {
        log.info("LoginController.loginFailed");
        ForwardController.forwardUrl = "user/loginFailed";
        return "redirect:/forward";
    }

    /**
     * TODO: login
     * loginV1 : @RequestParam("")
     * loginV2 : @RequestParam
     * loginV3 : @RequestParam 생략(비추천)
     * loginV4 : @ModelAttribute
     */
    @RequestMapping("/login")
    public String login(
            @ModelAttribute User loggedInUser,
            HttpServletRequest request) {
        User user = memoryUserRepository.findByUserId(loggedInUser.getUserId());

        if (user != null && user.getUserId().equals(loggedInUser.getUserId()) && user.getPassword().equals(loggedInUser.getPassword())) {
            log.info("LoginController.loginSuccess");
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return "redirect:/";
        }
        log.info("LoginController.loginFailed");
        return "redirect:/user/loginFailed";
    }

    /**
     * TODO: logout
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        log.info("LoginController.logout");
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        return "redirect:/";
    }
}
