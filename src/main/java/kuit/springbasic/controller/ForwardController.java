package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.HttpServletBean;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ForwardController {
    private String forwardUrl;

    public ForwardController(String forwardUrl) {
        this.forwardUrl = forwardUrl;
        if (forwardUrl == null) {
            throw new NullPointerException("forwardUrl is null. 이동할 URL을 입력하세요.");
        }
    }

    @RequestMapping("/forwardV1")
    public ModelAndView showForwardV1(HttpServletRequest request, HttpServletResponse response){
        log.info("ForwardController.v1");

        ModelAndView modelAndView = new ModelAndView("forward");
        return modelAndView;
    }

    @RequestMapping("/forwardV2")
    public ModelAndView showForwardV2(){
        log.info("ForwardController.v2");

        ModelAndView modelAndView = new ModelAndView("forward");
        return modelAndView;
    }

    @RequestMapping("/forwardV3")
    public String showForwardV3(Model model){
        log.info("HomeController.homeV3");
        return forwardUrl;
    }

}
