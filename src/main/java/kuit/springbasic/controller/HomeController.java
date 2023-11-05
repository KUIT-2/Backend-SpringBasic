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

@Slf4j                  // log
@Controller
@RequiredArgsConstructor            // lombok library 사용 -> final이 붙은 필드를 모아서 생성자를 자동으로 생성
public class HomeController {

    private final MemoryQuestionRepository memoryQuestionRepository;

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

    @RequestMapping("/")            // showhomeV3 방식 채택
                                    // -> localhost:8080/ 으로 넘어오는 내용에 대해서는 아래의 메서드로 매핑
    public String showHomeV3(Model model) {
        log.info("HomeController.homeV3");

        List<Question> questions = memoryQuestionRepository.findAll();
        model.addAttribute("questions", questions);             // model에 바로 바인딩

        return "home";              // Spring MVC 에서 MAV로 변환해서 화면에 출력해줌
    }

}
