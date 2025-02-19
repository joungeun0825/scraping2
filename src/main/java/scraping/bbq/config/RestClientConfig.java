package scraping.bbq.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.http.HttpHeaders;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl("https://bbq.co.kr")
                .defaultHeader(HttpHeaders.ACCEPT, "*/*")
                .defaultHeader(HttpHeaders.ORIGIN, "https://bbq.co.kr")
                .defaultHeader(HttpHeaders.REFERER, "https://bbq.co.kr/member/login")
                .build();
    }
}
