package kuit.springbasic.controller.qna;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kuit.springbasic.db.MemoryAnswerRepository;
import kuit.springbasic.db.MemoryQuestionRepository;
import kuit.springbasic.domain.Answer;
import kuit.springbasic.domain.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor            // final이 붙은 필드를 모아서 생성자를 자동 생성
@Controller
public class AnswerController {
    private final MemoryQuestionRepository memoryQuestionRepository;
    private final MemoryAnswerRepository memoryAnswerRepository;

    /**
     * TODO: addAnswer - @PostMapping
     * addAnswerV0 : @RequestParam, HttpServletResponse
     * addAnswerV1 : @RequestParam, Model
     * addAnswerV2 : @RequestParam, @ResponseBody
     * addAnswerV3 : @ModelAttribute, @ResponseBody
     */
    @PostMapping("/api/qna/addAnswerV0")
    public void addAnswerV0(@RequestParam int questionId, @RequestParam String writer, @RequestParam String contents,
                            HttpServletResponse response) throws SQLException, IOException {
        log.info("AnswerController.addAnswerV0");

        Answer answer = new Answer(questionId, writer, contents);
        Answer savedAnswer = memoryAnswerRepository.insert(answer);

        Question question = memoryQuestionRepository.findByQuestionId(answer.getQuestionId());
        question.increaseCountOfAnswer();
        memoryQuestionRepository.updateCountOfAnswer(question);

        Map<String, Object> model = new HashMap<>();
        model.put("answer", savedAnswer);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(model));
    }

    @PostMapping("/api/qna/addAnswerV1")
    public String addAnswerV1(@RequestParam int questionId, @RequestParam String writer, @RequestParam String contents,
                              Model model) throws SQLException {
        log.info("AnswerController.addAnswerV1");

        Answer answer = new Answer(questionId, writer, contents);
        Answer savedAnswer = memoryAnswerRepository.insert(answer);

        Question question = memoryQuestionRepository.findByQuestionId(answer.getQuestionId());
        question.increaseCountOfAnswer();
        memoryQuestionRepository.updateCountOfAnswer(question);

        model.addAttribute("answer", savedAnswer);

        return "jsonView";
    }

    @ResponseBody
    @PostMapping("/api/qna/addAnswerV2")
    public Answer addAnswerV2(@RequestParam int questionId, @RequestParam String writer, @RequestParam String contents){
        log.info("AnswerController.addAnswerV2");

        Answer answer = new Answer(questionId, writer, contents, Date.valueOf(LocalDate.now()));
        Answer savedAnswer = memoryAnswerRepository.insert(answer);

        Question question = memoryQuestionRepository.findByQuestionId(answer.getQuestionId());
        question.increaseCountOfAnswer();
        memoryQuestionRepository.updateCountOfAnswer(question);

        return savedAnswer;
    }

    @PostMapping("/api/qna/addAnswer")
    public Answer addAnswerV3(@ModelAttribute Answer answer) throws SQLException{
        log.info("AnswerController.addAnswerV3");

        Answer savedAnswer = memoryAnswerRepository.insert(answer);

        Question question = memoryQuestionRepository.findByQuestionId(answer.getQuestionId());
        question.increaseCountOfAnswer();
        memoryQuestionRepository.updateCountOfAnswer(question);

        return savedAnswer;
    }
}
