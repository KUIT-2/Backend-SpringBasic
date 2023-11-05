package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequestMapping("/user")
@Controller
@RequiredArgsConstructor
public class UserController {

    private final MemoryUserRepository memoryUserRepository;

    /**
     * TODO: showUserForm
     */
    @RequestMapping("/form")
    String showUserForm(Model model){
        log.info("showUserForm");
        ForwardController forwardController = new ForwardController("/user/form");
        return forwardController.showForwardV3(model);
    }


    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
    @RequestMapping("/signupV1")
    String createUserV1(@RequestParam("userId")String userId, @RequestParam("password")
    String password, @RequestParam("name") String name, @RequestParam("email") String email){
        log.info("CreateUserV1");
        User user = new User(userId, password, name, email);
        memoryUserRepository.insert(user);
        return "/user/list";
    }
    @RequestMapping("/signup")
    String createUserV2(@ModelAttribute User newuser){
        log.info("CreateUserV2");
        User user = newuser;
        memoryUserRepository.insert(user);
        return "redirect:/user/list";
    }

    /**
     * TODO: showUserList
     */
    @RequestMapping("/list")
    String showUserList(HttpServletRequest request){
        log.info("showUserList");
        if(UserSessionUtils.isLoggedIn(request.getSession())){
            request.setAttribute("users", memoryUserRepository.findAll());
            return "/user/list";
        }
        return "redirect:/user/loginForm";
    }



    /**
     * TODO: showUserUpdateForm
     */
    @RequestMapping("/updateForm")
    String showUserUpdateForm(@RequestParam("userId") String userId, HttpServletRequest request){
        User user = memoryUserRepository.findByUserId(userId);
        if (user != null) {
            request.setAttribute("user",user);
            //return new JspView("/user/updateForm.jsp");
            return "/user/updateForm";
        }
        //return new JspView("redirect:/");
        return "redirect:/";
    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */

    @RequestMapping("/update")
    String updateUser(@ModelAttribute User user){
        memoryUserRepository.update(user);
        return "redirect:/user/list";
    }

}
