package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class LoginController {

    private final MemoryUserRepository memoryUserRepository;

    /**
     * TODO: showLoginForm
     */
    @RequestMapping("/loginForm")
    String showLoginForm(Model model){
        log.info("showLoginForm");
        ForwardController forwardController = new ForwardController("/user/login");
        return forwardController.showForwardV3(model);
    }

    /**
     * TODO: showLoginFailed
     */
    @RequestMapping("/loginFailed")
    String showLoginFailed(Model model){
        log.info("showLoginFailed");
        ForwardController forwardController = new ForwardController("/user/loginFailed");
        return forwardController.showForwardV3(model);
    }


    /**
     * TODO: login
     * loginV1 : @RequestParam("")
     * loginV2 : @RequestParam
     * loginV3 : @RequestParam 생략(비추천)
     * loginV4 : @ModelAttribute
     */
    @RequestMapping("/login")
    String Login(@RequestParam("userId") String userId, @RequestParam("password") String password,
                 HttpServletRequest request){
        HttpSession session = request.getSession();
        User logInUser = new User(userId, password);
        User user = memoryUserRepository.findByUserId(userId);

        if(user != null && user.isSameUser(logInUser)){
            session.setAttribute("user",user);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }


    /**
     * TODO: logout
     */

    @RequestMapping("/logout")
    String LogOut(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        return "redirect:/";
    }

}
