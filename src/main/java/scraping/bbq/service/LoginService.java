package scraping.bbq.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import scraping.bbq.dto.CsrfDto;
import scraping.bbq.util.CookieUtil;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final String CSRF_URI = "/api/auth/csrf";

    private final RestClient restClient;
    @Getter
    private String sessionCookie;
    private String csrfToken;

    public int login(String username, String password) {
        getCsrfToken();
        // 폼 데이터 설정
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("username", username); // 아이디
        formData.add("password", password); // 비밀번호
        formData.add("redirect", "false");
        formData.add("csrfToken", csrfToken);
        formData.add("callbackUrl", "https://bbq.co.kr/member/login");
        formData.add("json", "true");

        // 로그인
        ResponseEntity<Void> response = restClient.post()
                .uri("/api/auth/callback/member")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header(HttpHeaders.ACCEPT, "*/*")
                .header(HttpHeaders.ORIGIN, "https://bbq.co.kr")
                .header(HttpHeaders.REFERER, "https://bbq.co.kr/member/login")
                .header(HttpHeaders.COOKIE, sessionCookie)
                .body(formData)
                .retrieve()
                .toBodilessEntity();

        sessionCookie = CookieUtil.getCookie(response);

        return response.getStatusCode().value();
    }

    private void getCsrfToken() {
        ResponseEntity<CsrfDto> response = restClient.get()
                .uri(CSRF_URI)
                .retrieve()
                .toEntity(CsrfDto.class);

        sessionCookie = CookieUtil.getCookie(response);
        csrfToken = response.getBody().getCsrfToken();
    }

}
