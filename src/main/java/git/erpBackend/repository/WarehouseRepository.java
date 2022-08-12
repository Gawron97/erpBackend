package git.erpBackend.repository;

import git.erpBackend.entity.Item;
import git.erpBackend.entity.Warehouse;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Repository
@Transactional
public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {

    @EntityGraph(attributePaths = {"name", "address", "items", "address.country", "items.quantityType"})
    @Query("SELECT w FROM Warehouse w WHERE w.name = :name")
    Optional<Warehouse> findByNameWithItems(@Param(value = "name") String name);

    @EntityGraph(attributePaths = {"name", "address", "itemSums", "address.country"})
    @Query("SELECT w FROM Warehouse w WHERE w.name = :name")
    Optional<Warehouse> findByNameWithItemSums(@Param(value = "name") String name);
}
