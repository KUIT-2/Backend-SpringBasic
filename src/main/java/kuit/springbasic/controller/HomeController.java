package kuit.springbasic.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kuit.springbasic.db.MemoryQuestionRepository;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemoryQuestionRepository memoryQuestionRepository;

    /*@RequestMapping("/")
    public ModelAndView showHome(HttpServletRequest request, HttpServletResponse response) {
        log.info("HomeController.showHome");

        List<Question> questions = memoryQuestionRepository.findAll();
        ModelAndView mav = new ModelAndView("home");
        //mav.getModel().put("questions", questions);
        mav.addObject("questions", questions);
        return mav;
    }*/

    @RequestMapping("/homeV1")
//    @RequestMapping("/")
    public ModelAndView showHomeV1(HttpServletRequest request, HttpServletResponse response) {
        log.info("HomeController.homeV1");

        ModelAndView modelAndView = new ModelAndView("home");

        List<Question> questions = memoryQuestionRepository.findAll();
        modelAndView.addObject("questions", questions);

        return modelAndView;
    }

    @RequestMapping("/homeV2")
//    @RequestMapping("/")
    public ModelAndView showHomeV2() {
        log.info("HomeController.homeV2");

        ModelAndView modelAndView = new ModelAndView("home");

        List<Question> questions = memoryQuestionRepository.findAll();
        modelAndView.addObject("questions", questions);

        return modelAndView;
    }

    @RequestMapping("/")
    public String showHomeV3(Model model) {
        log.info("HomeController.homeV3");

        List<Question> questions = memoryQuestionRepository.findAll();
        model.addAttribute("questions", questions);

        return "home";
    }

}
