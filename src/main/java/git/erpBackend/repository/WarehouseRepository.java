package git.erpBackend.repository;

import git.erpBackend.entity.Warehouse;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Repository
@Transactional
public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {


    @EntityGraph(attributePaths = {"name", "address", "items", "address.country", "items.quantityType"})
    @Query("SELECT w FROM Warehouse w WHERE w.idWarehouse = :id")
    Optional<Warehouse> findByIdWithItems(@Param(value = "id") Integer id);

    @EntityGraph(attributePaths = {"name", "address", "itemSums", "address.country"})
    @Query("SELECT w FROM Warehouse w WHERE w.idWarehouse = :id")
    Optional<Warehouse> findByIdWithItemSums(@Param(value = "id") Integer id);

    @EntityGraph(attributePaths = {"name", "address", "address.country"})
    @Override
    Optional<Warehouse> findById(Integer integer);
}
