package scraping.bbq.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final HttpClient client;

    public int login() throws IOException, InterruptedException {

        Map<String, String> data = new HashMap<>();
        data.put("username", "ll001122"); // 아이디
        data.put("password", "infotech@30"); // 비밀번호
        data.put("redirect", "false");
        data.put("csrfToken", "1bc198b047ca87351c17239442c24551728d996c1cddf390da54449e741d4738");
        data.put("callbackUrl", "https://bbq.co.kr/member/login");
        data.put("json", "true");

        // URL 인코딩 방식으로 데이터 변환
        StringJoiner sj = new StringJoiner("&");
        for (Map.Entry<String, String> entry : data.entrySet()) {
            sj.add(entry.getKey() + "=" + entry.getValue());
        }
        String formData = sj.toString();

        // POST 요청 구성
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://bbq.co.kr/api/auth/callback/member"))
                .header("Content-Type", "application/x-www-form-urlencoded") // 폼 데이터로 요청
                .header("Accept", "*/*")
                .header("Origin", "https://bbq.co.kr")
                .header("Referer", "https://bbq.co.kr/member/login")
                .header("Cookie", "__Host-next-auth.csrf-token=1bc198b047ca87351c17239442c24551728d996c1cddf390da54449e741d4738%7C48d144b5106da48fa68290ded1c3e34fc87f93613e8c1e99d85409e89d4c1af5; _ga=GA1.1.372589340.1739860869")
                .POST(HttpRequest.BodyPublishers.ofString(formData)) // 폼 데이터를 요청 본문으로 전송
                .build();

        // 요청 보내고 응답 받기
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode();
    }
}
