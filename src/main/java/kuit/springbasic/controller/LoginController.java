package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;

@Slf4j
@RequestMapping("/user")            // 코드의 중복 줄여줌
@RequiredArgsConstructor            // final이 붙은 필드를 모아서 생성자를 자동 생성
@Controller
public class LoginController {
    private final MemoryUserRepository memoryUserRepository;

    /**
     * TODO: showLoginForm
     */
    @GetMapping("/loginForm")
    // @RequestMapping("/loginForm", method = RequestMethod.GET) == @GetMapping("/loginForm") == @RequestMapping("/loginForm")
    public String showLoginForm(){
        log.info("LoginController.showLoginForm");
        return "user/login";
    }


    /**
     * TODO: login
     * loginV1 : @RequestParam("")
     * loginV2 : @RequestParam
     * loginV3 : @RequestParam 생략(비추천)
     * loginV4 : @ModelAttribute
     */

    // loginV1  -> RequestParam 어노테이션으로 Id, passwd 직접 받아오기 (가독성을 위해서 "~~" 추가)
    @RequestMapping("/loginV1")
    public String loginV1(@RequestParam("Id") String Id, @RequestParam("passwd") String passwd, HttpServletRequest request){
        User loggedInUser = new User(Id, passwd);
        User user = memoryUserRepository.findByUserId(Id);

        if(user != null && user.getUserId().equals(loggedInUser.getUserId()) && user.getPassword().equals(loggedInUser.getPassword())){
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";                // 로그인 실패시

    }

    // loginV2  -> 위의 방식과 동일(RequestParam 어노테이션 뒤의 변수와 Param이 이름이 같을 경우 굳이 "~~" 안써도 됨)
    @RequestMapping("/loginV2")
    public String loginV2(@RequestParam String Id, @RequestParam String passwd, HttpServletRequest request){
        User loggedInUser = new User(Id, passwd);
        User user = memoryUserRepository.findByUserId(Id);

        if(user != null && user.getUserId().equals(loggedInUser.getUserId()) && user.getPassword().equals(loggedInUser.getPassword())){
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";                // 로그인 실패시

    }

    // loginV3  -> 위의 방식과 동일(RequestParam 어노테이션도 생략 -> 팀플시 매너가 아님, 비추천)
    @RequestMapping("/loginV3")
    public String loginV3(String Id, String passwd, HttpServletRequest request){
        User loggedInUser = new User(Id, passwd);
        User user = memoryUserRepository.findByUserId(Id);

        if(user != null && user.getUserId().equals(loggedInUser.getUserId()) && user.getPassword().equals(loggedInUser.getPassword())){
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";                // 로그인 실패시

    }


    // loginV4  -> Id와 passwd를 각각 param으로 받는 것이 아니라 user객체를 인자로 받아서 user의 id, passwd를 꺼내서 사용
    @PostMapping("/login")
    // @RequestMapping("/loginForm", method = RequestMethod.POST) == @POSTMapping("/login") == @RequestMapping("/login")
    public String loginV4(@ModelAttribute User loggedInUser, HttpServletRequest request){
        // 세션값을 가져오려면 Servlet이 필요하므로 인자로 HttpServletRequest 추가
        User user = memoryUserRepository.findByUserId(loggedInUser.getUserId());

        if(user != null && user.getUserId().equals(loggedInUser.getUserId()) && user.getPassword().equals(loggedInUser.getPassword())){
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return "redirect:/";
        }
        return "redirect:/user/loginFailed";                // 로그인 실패시
    }


    /**
     * TODO: showLoginFailed -> 위에 로그인 기능에 포함
     */


    /**
     * TODO: logout
     */
    // logIn과 마찬가지로 HttpRequest로부터 세션값 가져온 후, 세션에서 user 지우기
    @RequestMapping("/logout")
    public String logOut(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        return "redirect:/";
    }
}
