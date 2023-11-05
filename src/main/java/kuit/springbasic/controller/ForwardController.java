package kuit.springbasic.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@Getter
@Setter
public class ForwardController {
    public static String forwardUrl;
    @GetMapping("/forward")
    public String forward() {
        log.info("ForwardController.forward:"+forwardUrl);
        return forwardUrl;
    }
}
