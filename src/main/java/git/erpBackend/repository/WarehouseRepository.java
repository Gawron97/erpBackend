package git.erpBackend.repository;

import git.erpBackend.entity.Warehouse;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {

    @EntityGraph(attributePaths = "items")
    Optional<Warehouse> findById(Integer id);

}
