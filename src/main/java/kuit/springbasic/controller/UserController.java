package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.net.http.HttpRequest;

@Slf4j
@RequestMapping("/user")            // 코드의 중복 줄여줌
@RequiredArgsConstructor            // final이 붙은 필드를 모아서 생성자를 자동 생성
@Controller
public class UserController {
    private final MemoryUserRepository memoryUserRepository;

    /**
     * TODO: showUserForm
     */
    @RequestMapping("/form")
    public String form(){
        return "/user/form";
    }


    /**
     * TODO: createUser -> CreateUserController
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
    // createUserV1 -> id, passwd, name, email 4가지 정보 필요
    @RequestMapping("/signupv1")
    public String CreateUserV1(@RequestParam String Id, @RequestParam String passwd, @RequestParam String name, @RequestParam String email){
        User user = new User(Id, passwd, name, email);
        memoryUserRepository.insert(user);          // user 등록
        return "redirect:/user/list";
    }

    // createUserV2 -> User 객체 받아오기
    @RequestMapping("/signup")
    public String CreateUserV2(@ModelAttribute User user){
        memoryUserRepository.insert(user);
        return "redirect:/user/list";
    }

    /**
     * TODO: showUserList -> ListUserController
     */
    @RequestMapping("/list")
    public ModelAndView userList(HttpServletRequest request){
        if(UserSessionUtils.isLoggedIn(request.getSession())){
            return new ModelAndView("/user/list").addObject("user", memoryUserRepository.findAll());
        }
        return new ModelAndView("redirect:/user/loginForm");
    }

    /**
     * TODO: showUserUpdateForm -> UpdateUserFormController
     */
    @RequestMapping("/updateForm")
    public ModelAndView updateUserForm(@ModelAttribute User loginedUser){
        User user = memoryUserRepository.findByUserId(loginedUser.getUserId());
        if(user != null){
            return new ModelAndView("/user/updateForm").addObject("user", user);
        }
        return new ModelAndView("redirect:/");
    }


    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
    // updateUserV1 방식 -> 위의 createUser 방식과 동일
    @RequestMapping("/updateV1")
    public String updateUserV1(@RequestParam String Id, @RequestParam String passwd, @RequestParam String name, @RequestParam String email){
        User user = new User(Id, passwd, name, email);
        memoryUserRepository.findByUserId(Id).update(user);         // Id로 update target user 찾아서 정보 update
        return "redirect:/user/list";
    }

    // updateUserV2 방식 : ModelAttribute 어노테이션을 통해서 User 모델을 인자로 넘김
    @RequestMapping("/update")
    public String updateUserV2(@ModelAttribute User targetUser){
        memoryUserRepository.findByUserId(targetUser.getUserId()).update(targetUser);       // update
        return "redirect:/user/list";
    }

}
