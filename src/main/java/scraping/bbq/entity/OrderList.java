package scraping.bbq.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import scraping.bbq.dto.MappingDto;
import scraping.bbq.dto.OrderDto;

import java.util.List;

@Setter
@Getter
@Entity
public class OrderList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer totalPrice;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderList", orphanRemoval = true)
    private List<Order> order;

    public OrderList(MappingDto mappingDto) {
        this.totalPrice = mappingDto.getTotalPrice();
    }

    public static OrderList from(MappingDto mappingDto) {
        return new OrderList(mappingDto);
    }
}
