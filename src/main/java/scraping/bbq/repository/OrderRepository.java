package scraping.bbq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import scraping.bbq.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
