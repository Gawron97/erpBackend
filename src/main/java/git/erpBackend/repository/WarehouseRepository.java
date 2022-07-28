package git.erpBackend.repository;

import git.erpBackend.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {
}
