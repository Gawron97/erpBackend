package git.erpBackend.repository;

import git.erpBackend.entity.ItemSum;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ItemSumRepository extends JpaRepository<ItemSum, Integer> {

    @EntityGraph(attributePaths = {"name", "quantity", "quantityType", "warehouses"})
    @Query("SELECT i FROM ItemSum i WHERE i.name = :name")
    Optional<ItemSum> findByNameWithWarehouses(@Param(value = "name") String name);

    @EntityGraph(attributePaths = {"name", "quantity", "quantityType", "warehouses"})
    @Query("SELECT i FROM ItemSum i WHERE i.idItemSum = :id")
    Optional<ItemSum> findByIdWithWarehouses(@Param(value = "id") Integer id);

}
