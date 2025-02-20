package scraping.bbq.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scraping.bbq.dto.LoginRequest;
import scraping.bbq.service.LoginService;
import scraping.bbq.service.OrderListService;

import java.io.IOException;

@RestController
@RequestMapping("/api/orderList")
@AllArgsConstructor
public class OrderListController {
    private final int SUCCESS_CODE = 200;
    private final OrderListService orderListService;
    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<?> getOrderList(@RequestBody LoginRequest loginRequest) {
        if (loginService.login(loginRequest) == SUCCESS_CODE) {
            return  ResponseEntity.status(HttpStatus.CREATED).body(orderListService.getList());
        }
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveOrderList(@RequestBody LoginRequest loginRequest) {
        if (loginService.login(loginRequest) == SUCCESS_CODE) {
            orderListService.save();
            return  ResponseEntity.status(HttpStatus.CREATED).body("구매한 리스트가 저장되었습니다.");
        }
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
}
