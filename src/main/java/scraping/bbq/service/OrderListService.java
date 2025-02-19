package scraping.bbq.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import scraping.bbq.dto.MappingDto;
import scraping.bbq.dto.OrderDto;
import scraping.bbq.entity.Order;
import scraping.bbq.entity.OrderList;
import scraping.bbq.repository.OrderListRepository;
import scraping.bbq.repository.OrderRepository;

@RequiredArgsConstructor
@Service
public class OrderListService {

    private final RestClient restClient;
    private final OrderListRepository orderListRepository;
    private final OrderRepository orderRepository;
    private final LoginService loginService;

    public void save() {
        MappingDto mappingDto = getList();
        OrderList orderList = orderListRepository.save(OrderList.from(mappingDto));
        for(OrderDto orderDto : mappingDto.getResponseList()) {
            orderRepository.save(Order.from(orderDto, orderList));
        }
    }

    public MappingDto getList() {
        String payload = """
        {
            "branchId": "",
            "mealType": "DELIVERY",
            "latitude": 37.35886358148,
            "longitude": 126.7726331339,
            "legalDongId": "4139012700",
            "administrativeDongId": "4139058100",
            "ecouponList": []
        }""";

        // 로그인 후 쿠키 가져오기
        String loginCookie = loginService.getSessionCookie();

        return restClient.post()
                .uri("/api/delivery/cart/list")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.COOKIE, loginCookie)  // 로그인 후 받은 쿠키 추가
                .body(payload)
                .retrieve()
                .body(MappingDto.class);
    }

}
