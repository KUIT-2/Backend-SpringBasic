package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class UserController {
    private final MemoryUserRepository memoryUserRepository;

    /**
     * TODO: showUserForm
     */
    //user/form 으로 이동
    @RequestMapping("/user/form")
    public String showUserForm(){
        return "/user/form";
    }

    /**
     * TODO: createUser
     * createUserV1 : @RequestParam
     * createUserV2 : @ModelAttribute
     */
    //입력받은 user를 레포지토리에 넣고 유저리스트 화면으로 이동
    @RequestMapping("/user/signup")
    public String createUser(@ModelAttribute User newUser){
        memoryUserRepository.insert(newUser);
        return "redirect:/user/list";
    }

    /**
     * TODO: showUserList
     */
    //로그인이 되어 있다면 userlist창으로 안되어있다면 다시 로그인 창으로 이동하게 함
    @RequestMapping("/user/list")
    public String showUserList(HttpServletRequest request, Model model){ //세션을 가져오기 위해 request, 모델에 넣기 위해 model
        HttpSession session = request.getSession();
        if(UserSessionUtils.isLoggedIn(session)){
            model.addAttribute("users",memoryUserRepository.findAll());
            return "/user/list";
        }
        return "redirect:/user/loginForm";
    }

    /**
     * TODO: showUserUpdateForm
     */
    //해당 userId를 이용히야 updateForm으로 이동 없다면 기본화면으로
    @RequestMapping("/user/updateForm")
    public String showUserUpdateForm(@RequestParam("userId")String userId, Model model){
        User user = memoryUserRepository.findByUserId(userId);
        if(user != null){
            model.addAttribute("user",user);
            return "/user/updateForm";
        }
        return "/";
    }

    /**
     * TODO: updateUser
     * updateUserV1 : @RequestParam
     * updateUserV2 : @ModelAttribute
     */
    //user를 없데이트하고 user/list로 화면 이동
    @RequestMapping("/user/update")
    public String updateUser(@ModelAttribute User user){
        memoryUserRepository.update(user);
        return "redirect:/user/list";
    }

}
