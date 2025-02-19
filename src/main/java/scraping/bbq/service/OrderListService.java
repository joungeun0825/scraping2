package scraping.bbq.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import scraping.bbq.dto.MappingDto;
import scraping.bbq.dto.OrderDto;
import scraping.bbq.entity.Order;
import scraping.bbq.entity.OrderList;
import scraping.bbq.repository.OrderListRepository;
import scraping.bbq.repository.OrderRepository;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RequiredArgsConstructor
@Service
public class OrderListService {

    private final HttpClient client;
    private final OrderListRepository orderListRepository;
    private final OrderRepository orderRepository;

    public void save() {
        try {
            MappingDto mappingDto = getList();
            OrderList orderList = orderListRepository.save(OrderList.from(mappingDto));
            for(OrderDto orderDto : mappingDto.getResponseList()) {
                orderRepository.save(Order.from(orderDto, orderList));
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public MappingDto getList() throws IOException, InterruptedException {

        // 요청 본문에 보낼 데이터를 준비 (JSON 데이터)
        String payload = "{\"branchId\":\"\",\"mealType\":\"DELIVERY\",\"latitude\":37.35886358148,\"longitude\":126.7726331339,\"legalDongId\":\"4139012700\",\"administrativeDongId\":\"4139058100\",\"ecouponList\":[]}";

        // POST 요청 구성
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://bbq.co.kr/api/delivery/cart/list"))   // 실제 API URL로 변경
                .header("Content-Type", "application/json; charset=utf-8")
                .header("Accept", "application/json, text/plain, */*")
                .header("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7")
                .header("Origin", "https://bbq.co.kr")
                .header("Referer", "https://bbq.co.kr/cart")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36")
                .POST(HttpRequest.BodyPublishers.ofString(payload)) // JSON 데이터를 요청 본문으로 전송
                .build();

        // 요청 보내고 응답 받기
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // 객체 변환
        ObjectMapper objectMapper = new ObjectMapper();
        MappingDto mappingDto = objectMapper.readValue(response.body(), MappingDto.class);

        return mappingDto;
    }
}
