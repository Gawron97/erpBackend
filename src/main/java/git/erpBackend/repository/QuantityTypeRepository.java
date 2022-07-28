package git.erpBackend.repository;

import git.erpBackend.entity.QuantityType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuantityTypeRepository extends JpaRepository<QuantityType, Integer> {
}
