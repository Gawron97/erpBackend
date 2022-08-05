package git.erpBackend.repository;

import git.erpBackend.entity.QuantityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuantityTypeRepository extends JpaRepository<QuantityType, Integer> {
}
