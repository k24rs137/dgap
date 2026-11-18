package com.example.dgap.controller;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dgap.model.Category;
import com.example.dgap.model.Lesson;
import com.example.dgap.service.LessonCatalogService;
import com.example.dgap.service.MyPageService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PageController {
    private static final Set<String> SEARCH_LANGUAGES = Set.of("ja", "ko", "en", "hira");
    private final LessonCatalogService catalog;
    private final MyPageService myPageService;

    public PageController(LessonCatalogService catalog, MyPageService myPageService) {
        this.catalog = catalog;
        this.myPageService = myPageService;
    }

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "ja") String lang, Model model,
            HttpSession session) {
        addLanguage(model, lang);
        model.addAttribute("categories", catalog.getCategories());
        String userId = (String) session.getAttribute(AuthController.SESSION_USER_ID);
        var dashboard = userId == null ? null : myPageService.build(userId, lang).orElse(null);
        model.addAttribute("authenticated", dashboard != null);
        model.addAttribute("homeDashboard", dashboard);
        return "index";
    }

    @GetMapping("/category/{id}")
    public String category(@PathVariable String id,
            @RequestParam(defaultValue = "ja") String lang, Model model) {
        List<Category> categories = catalog.getCategories();
        return categories.stream()
                .filter(item -> item.getUrl().equals("/category/" + id))
                .findFirst()
                .map(category -> {
                    addLanguage(model, lang);
                    model.addAttribute("category", category);
                    model.addAttribute("recommendations", categories.stream()
                            .filter(item -> !item.getUrl().equals(category.getUrl())).toList());
                    return "category";
                })
                .orElse("redirect:/");
    }

    @GetMapping("/search")
    public String search(@RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "ja") String lang, Model model) {
        String normalizedKeyword = normalizeSearchText(keyword);
        List<Category> results = new ArrayList<>();
        if (!normalizedKeyword.isBlank()) {
            results = catalog.getCategories().stream()
                    .filter(category -> matches(category, normalizedKeyword))
                    .toList();
        }
        addLanguage(model, lang);
        model.addAttribute("keyword", keyword);
        model.addAttribute("results", results);
        return "search";
    }

    @GetMapping("/lesson/{id}")
    public String lesson(@PathVariable String id,
            @RequestParam(defaultValue = "ja") String lang, Model model) {
        for (Category category : catalog.getCategories()) {
            List<Lesson> lessons = category.getLessons();
            for (int index = 0; index < lessons.size(); index++) {
                Lesson lesson = lessons.get(index);
                if (lesson.getUrl().equals("/lesson/" + id)) {
                    addLanguage(model, lang);
                    model.addAttribute("lesson", lesson);
                    model.addAttribute("category", category);
                    model.addAttribute("coursePosition", index + 1);
                    model.addAttribute("courseSize", lessons.size());
                    model.addAttribute("previousLesson", index > 0 ? lessons.get(index - 1) : null);
                    model.addAttribute("nextLesson", index + 1 < lessons.size() ? lessons.get(index + 1) : null);
                    return "lesson";
                }
            }
        }
        return "redirect:/";
    }

    @GetMapping({"/login", "/register", "/community", "/diagnosis", "/ai", "/fraud"})
    public String simplePage(@RequestParam(defaultValue = "ja") String lang,
            Model model, HttpServletRequest request) {
        addLanguage(model, lang);
        return request.getRequestURI().substring(1);
    }

    private boolean matches(Category category, String keyword) {
        boolean categoryMatch = SEARCH_LANGUAGES.stream().anyMatch(language ->
                normalizeSearchText(category.getTitleByLang(language)).contains(keyword)
                || normalizeSearchText(category.getDescriptionByLang(language)).contains(keyword));
        return categoryMatch || category.getLessons().stream().anyMatch(lesson ->
                SEARCH_LANGUAGES.stream().anyMatch(language ->
                        normalizeSearchText(lesson.getTitleByLang(language)).contains(keyword)
                        || normalizeSearchText(lesson.getDescriptionByLang(language)).contains(keyword)));
    }

    private String normalizeSearchText(String value) {
        if (value == null) return "";
        return Normalizer.normalize(value, Normalizer.Form.NFKC)
                .toLowerCase(Locale.ROOT).replaceAll("\\s+", " ").trim();
    }

    private void addLanguage(Model model, String lang) {
        model.addAttribute("lang", lang);
    }
}
