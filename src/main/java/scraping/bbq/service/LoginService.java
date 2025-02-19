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

import java.util.List;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final RestClient restClient;
    @Getter
    private String sessionCookie;

    public int login(String username, String password) {
        // 폼 데이터 설정
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("username", username); // 아이디
        formData.add("password", password); // 비밀번호
        formData.add("redirect", "false");
        formData.add("csrfToken", "1bc198b047ca87351c17239442c24551728d996c1cddf390da54449e741d4738");
        formData.add("callbackUrl", "https://bbq.co.kr/member/login");
        formData.add("json", "true");

        // 로그인
        ResponseEntity<Void> response = restClient.post()
                .uri("/api/auth/callback/member")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header(HttpHeaders.ACCEPT, "*/*")
                .header(HttpHeaders.ORIGIN, "https://bbq.co.kr")
                .header(HttpHeaders.REFERER, "https://bbq.co.kr/member/login")
                .header(HttpHeaders.COOKIE, "__Host-next-auth.csrf-token=1bc198b047ca87351c17239442c24551728d996c1cddf390da54449e741d4738%7C48d144b5106da48fa68290ded1c3e34fc87f93613e8c1e99d85409e89d4c1af5; _ga=GA1.1.372589340.1739860869")
                .body(formData)
                .retrieve()
                .toBodilessEntity();

        // Cookie 추출
        List<String> cookies = response.getHeaders().get(HttpHeaders.SET_COOKIE);
        if (cookies != null && !cookies.isEmpty()) {
            sessionCookie = String.join("; ", cookies);
        }

        return response.getStatusCode().value();
    }

}
