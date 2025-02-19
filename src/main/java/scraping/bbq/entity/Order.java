package scraping.bbq.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import scraping.bbq.dto.OrderDto;

@Setter
@Getter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "order_id")
    private Long id;

    @Column(name = "menu_type")
    private String menuType;

    @Column(name = "main_menu_name")
    private String mainMenuName;

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_list_id", nullable = false)
    private OrderList orderList;

    public Order(){}

    public Order(OrderDto orderDto, OrderList orderList) {
        this.id = orderDto.getId();
        this.menuType = orderDto.getMenuType();
        this.mainMenuName = orderDto.getMainMenuName();
        this.quantity = orderDto.getQuantity();
        this.orderList = orderList;
    }

    public static Order from(OrderDto orderDto, OrderList orderList) {
        return new Order(orderDto, orderList);
    }
}
