package scraping.bbq.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping
    public ResponseEntity<?> getOrderList() throws IOException, InterruptedException {
        if (loginService.login() == SUCCESS_CODE) {
            return  ResponseEntity.status(HttpStatus.CREATED).body(orderListService.getList());
        }
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @GetMapping("/save")
    public ResponseEntity<?> saveOrderList() throws IOException, InterruptedException {
        if (loginService.login() == SUCCESS_CODE) {
            orderListService.save();
            return  ResponseEntity.status(HttpStatus.CREATED).body("구매한 리스트가 저장되었습니다.");
        }
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
}
