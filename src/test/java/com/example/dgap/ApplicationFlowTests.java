package com.example.dgap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ApplicationFlowTests {
    @Autowired
    private MockMvc mvc;

    @Test
    void registrationCreatesAuthenticatedSessionAndPersistsUserData() throws Exception {
        mvc.perform(post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":\"x\",\"password\":\"short\"}"))
                .andExpect(status().isBadRequest());

        MockHttpSession session = (MockHttpSession) mvc.perform(post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":\"flow-user\",\"password\":\"password123\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andReturn().getRequest().getSession(false);

        mvc.perform(get("/api/session").session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("flow-user"));

        mvc.perform(post("/api/me/progress").session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"key\":\"/lesson/gmail\",\"completed\":true,\"title\":\"Gmail\"}"))
                .andExpect(status().isOk());
        mvc.perform(post("/api/me/favorite").session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"key\":\"/lesson/gmail\",\"favorite\":true}"))
                .andExpect(status().isOk());
        mvc.perform(get("/api/me/learning").session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completed[0]").value("/lesson/gmail"))
                .andExpect(jsonPath("$.favorites[0]").value("/lesson/gmail"));
    }

    @Test
    void communityRequiresSessionAndReturnsPostTextAsJsonData() throws Exception {
        mvc.perform(post("/api/community/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"text\":\"hello\"}"))
                .andExpect(status().isUnauthorized());

        MockHttpSession session = (MockHttpSession) mvc.perform(post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":\"community-user\",\"password\":\"password123\"}"))
                .andExpect(status().isCreated())
                .andReturn().getRequest().getSession(false);

        mvc.perform(post("/api/community/posts").session(session)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"text\":\"<script>alert(1)</script>\"}"))
                .andExpect(status().isCreated());
        mvc.perform(get("/api/community/posts").session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].text").value("<script>alert(1)</script>"))
                .andExpect(jsonPath("$[0].owned").value(true));
    }

    @Test
    void searchSupportsCaseInsensitiveEnglishLessonTerms() throws Exception {
        mvc.perform(get("/search").param("keyword", "EMAIL").param("lang", "en"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Gmail")));
    }

    @Test
    void lessonIncludesPracticalMissionOutcomeAndSafetyGuidance() throws Exception {
        mvc.perform(get("/lesson/gmail").param("lang", "ja"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("実践ミッション")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("自分宛てに件名『送信練習』")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("安全・注意ポイント")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("操作練習モード")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("data-lesson-key=\"/lesson/gmail\"")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("practice.js?v=20261001-2")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("トップページへ戻る")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("href=\"/?lang=ja\"")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("12分")));
    }

    @Test
    void primaryMenusIncludeLocalizedFraudCheckLink() throws Exception {
        mvc.perform(get("/").param("lang", "ja"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("href=\"/fraud?lang=ja\"")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("詐欺メール診断")));

        mvc.perform(get("/search").param("lang", "en"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Scam email check")));
    }
}
