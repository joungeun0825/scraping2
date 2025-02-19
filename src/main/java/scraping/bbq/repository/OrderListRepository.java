package scraping.bbq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import scraping.bbq.entity.OrderList;

public interface OrderListRepository extends JpaRepository<OrderList, Long>  {
}
