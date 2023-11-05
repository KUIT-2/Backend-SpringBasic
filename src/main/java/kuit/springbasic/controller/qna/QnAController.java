package kuit.springbasic.controller.qna;

import jakarta.servlet.http.HttpServletRequest;
import kuit.springbasic.db.MemoryAnswerRepository;
import kuit.springbasic.db.MemoryQuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@RequestMapping("/qna")             // 코드의 중복 줄여줌
@RequiredArgsConstructor            // final이 붙은 필드를 모아서 생성자를 자동 생성
@Controller
public class QnAController {
    private final MemoryQuestionRepository memoryQuestionRepository;
    private final MemoryAnswerRepository memoryAnswerRepository;

    /**
     * TODO: showQnA
     */
    @RequestMapping("/show")
    public String showQnA(Model model, @RequestParam("questionId") int questionId){
        Question question = memoryQuestionRepository.findByQuestionId(questionId);
        List<Answer> answers = memoryAnswerRepository.findAllByQuestionId(questionId);

        model.addAttribute("question", question);       // model에 바로 바인딩
        model.addAttribute("answers", answers);         // model에 바로 바인딩

        return "/qna/show";
    }

}
