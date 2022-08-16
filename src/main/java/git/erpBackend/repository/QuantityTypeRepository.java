package git.erpBackend.repository;

import git.erpBackend.entity.QuantityType;
import git.erpBackend.enums.QuantityEnum;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuantityTypeRepository extends JpaRepository<QuantityType, Integer> {

    @EntityGraph(attributePaths = "items")
    QuantityType findByQuantityType(QuantityEnum quantityType);

    @Override
    @EntityGraph(attributePaths = "items")
    Optional<QuantityType> findById(Integer integer);
}
