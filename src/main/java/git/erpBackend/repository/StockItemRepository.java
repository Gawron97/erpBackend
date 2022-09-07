package git.erpBackend.repository;

import git.erpBackend.entity.StockItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockItemRepository extends JpaRepository<StockItem, Integer> {

}
