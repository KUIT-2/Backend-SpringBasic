package kuit.springbasic.controller.qna;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kuit.springbasic.db.MemoryQuestionRepository;
import kuit.springbasic.domain.Question;
import kuit.springbasic.domain.User;
import kuit.springbasic.util.UserSessionUtils;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

@Slf4j
@RequestMapping("/qna")             // 코드의 중복 줄여줌
@RequiredArgsConstructor            // final이 붙은 필드를 모아서 생성자를 자동 생성
@Controller
public class QuestionController {
    private final MemoryQuestionRepository memoryQuestionRepository;

    /**
     * TODO: showQuestionForm   -> FormController
     */
    @RequestMapping("/form")
    public ModelAndView showQuestionForm(HttpServletRequest request){
        HttpSession session = request.getSession();

        if(UserSessionUtils.isLoggedIn(session)){
            return new ModelAndView("/qna/form");
        }
        return new ModelAndView("redirect:/user/loginForm");
    }

    /**
     * TODO: createQuestion
     * createQuestionV1 : @RequestParam
     * createQuestionV2 : @ModelAttribute
     */
    @RequestMapping("/createV1")
    public ModelAndView createQuestionV1(@RequestParam String writer, @RequestParam String title, @RequestParam String contents){
        Question question = new Question(writer, title, contents, Date.valueOf(LocalDate.now()), 0);
        memoryQuestionRepository.insert(question);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping("/create")
    public ModelAndView createQuestionV2(@ModelAttribute Question question){
        memoryQuestionRepository.insert(question);
        return new ModelAndView("redirect:/");
    }

    /**
     * TODO: showUpdateQuestionForm  -> UpdateQuestionFormController
     * showUpdateQuestionFormV1 : @RequestParam, HttpServletRequest, Model
     * showUpdateQuestionFormV2 : @RequestParam, @SessionAttribute, Model
     */
    @RequestMapping("/updateFormV1")
    public String showUpdateQuestionFormV1(@RequestParam int questionId, HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        if(!UserSessionUtils.isLoggedIn(session)){
            return "redirect:/users/loginForm";
        }
        Question question = memoryQuestionRepository.findByQuestionId(questionId);

        if(!question.isSameUser(Objects.requireNonNull(UserSessionUtils.getUserFromSession(session)))){
            throw new IllegalArgumentException();
        }
        model.addAttribute("question", question);           // model에 바로 바인딩

        return "qna/updateForm";
    }

    // @SessionAttribute : 세션에 저장되어있는 값을 컨트롤러의 메소드에 매핑시켜주는 어노테이션
    @RequestMapping("/updateForm")
    public String showUpdateQuestionFormV2(@RequestParam int questionId, @SessionAttribute("user") User user, Model model){
        if(user == null){
            return "redirect:/users/loginForm";
        }
        Question question = memoryQuestionRepository.findByQuestionId(questionId);

        if(!question.isSameUser(user)){
            throw new IllegalArgumentException();
        }
        model.addAttribute("question", question);           // model에 바로 바인딩

        return "qna/updateForm";
    }


    /**
     * TODO: updateQuestion
     */
    @RequestMapping("/update")
    public String updateQuestion(@RequestParam int questionId, @SessionAttribute("user") User user){
        if(user == null){
            return "redirect:/users/loginForm";
        }

        Question question = memoryQuestionRepository.findByQuestionId(questionId);
        if(!question.isSameUser(user)){
            throw new IllegalArgumentException();
        }
        question.updateTitleAndContents(question.getTitle(), question.getContents());
        memoryQuestionRepository.update(question);

        return "redirect:/";
    }

}
