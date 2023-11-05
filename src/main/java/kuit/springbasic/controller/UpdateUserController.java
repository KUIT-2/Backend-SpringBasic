package kuit.springbasic.controller;

import kuit.springbasic.db.MemoryUserRepository;
import kuit.springbasic.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UpdateUserController {

    private final MemoryUserRepository memoryUserRepository;

    @RequestMapping("/updateForm")
    public ModelAndView loadUpdateUserForm(@RequestParam("userId") String userId) {
        log.info("UpdateUserController.loadUpdateUserForm");
        log.info(userId);
        User user = memoryUserRepository.findByUserId(userId);

        if (user != null) {
            ModelAndView modelAndView = new ModelAndView("user/updateForm");
            modelAndView.addObject("user", user);
            return modelAndView;
        }
        return new ModelAndView("redirect:/");
    }

    @RequestMapping("/update")
    public String updateUser(@ModelAttribute User user) {
        log.info("UpdateUserController.updateUser");
        memoryUserRepository.update(user);
        return "redirect:/user/list";
    }
}
