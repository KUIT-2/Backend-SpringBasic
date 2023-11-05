package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final MemoryUserRepository memoryUserRepository;


    /**
     * TODO: showLoginForm
     */
    @RequestMapping("user/loginForm")
    public String showLoginForm(){
        return "/user/login";
    }

    /**
     * TODO: showLoginFailed
     */

    /**
     * TODO: login
     * loginV1 : @RequestParam("")
     * loginV2 : @RequestParam
     * loginV3 : @RequestParam 생략(비추천)
     * loginV4 : @ModelAttribute
     */

    @RequestMapping("/user/login")
    public String login(@RequestParam("userId")String userId, @RequestParam("password")String password
    , HttpServletRequest request){
        User loggedInUser = new User(userId, password);
        User user = memoryUserRepository.findByUserId(userId);

        if(user != null && user.getUserId().equals(loggedInUser.getUserId()) && user.getPassword().equals(loggedInUser.getPassword())){
            HttpSession session = request.getSession();
            session.setAttribute("user",user);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";
    }

    /**
     * TODO: logout
     */
    //logout기능으로 유저의 정보를 삭제하고 기본 페이지로 이동
    @RequestMapping("/user/logout")
    public String logOut(HttpServletRequest request){ //세션을 가져오기 위해 request
        HttpSession session = request.getSession();
        session.removeAttribute("user"); //유저 정보를 삭제
        return "redirect:/";
    }

    //로그인 실패하면 다시 로그인페이지로
    @RequestMapping("/user/loginFailed")
    public String loginFailed(){
        return "user/login";
    }

}
