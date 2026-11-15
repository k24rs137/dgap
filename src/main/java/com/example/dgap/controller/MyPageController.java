package com.example.dgap.controller;

import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dgap.service.MyPageService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MyPageController {
    private static final Set<String> SUPPORTED_LANGUAGES = Set.of("ja", "ko", "en", "hira");
    private final MyPageService myPageService;

    public MyPageController(MyPageService myPageService) {
        this.myPageService = myPageService;
    }

    @GetMapping("/mypage")
    public String myPage(@RequestParam(defaultValue = "ja") String lang,
            HttpSession session, Model model) {
        String selectedLanguage = SUPPORTED_LANGUAGES.contains(lang) ? lang : "ja";
        String userId = (String) session.getAttribute(AuthController.SESSION_USER_ID);
        if (userId == null) return "redirect:/login?lang=" + selectedLanguage;

        return myPageService.build(userId, selectedLanguage)
                .map(view -> {
                    model.addAttribute("lang", selectedLanguage);
                    model.addAttribute("myPage", view);
                    return "mypage";
                })
                .orElse("redirect:/login?lang=" + selectedLanguage);
    }
}
