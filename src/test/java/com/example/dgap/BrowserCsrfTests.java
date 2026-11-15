package com.example.dgap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import jakarta.servlet.http.Cookie;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"test", "browser-csrf"})
class BrowserCsrfTests {
    @Autowired
    private MockMvc mvc;

    @Test
    void cookieAndHeaderReachJapaneseRegistrationValidation() throws Exception {
        MvcResult page = mvc.perform(get("/register").param("lang", "ja"))
                .andExpect(status().isOk())
                .andReturn();
        Cookie csrfCookie = page.getResponse().getCookie("XSRF-TOKEN");
        org.assertj.core.api.Assertions.assertThat(csrfCookie).isNotNull();

        mvc.perform(post("/api/register")
                .cookie(csrfCookie)
                .header("X-XSRF-TOKEN", csrfCookie.getValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":\"test\",\"displayName\":\"Test User\",\"password\":\"123456\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("パスワードは8〜72文字で入力してください。"));
    }
}
