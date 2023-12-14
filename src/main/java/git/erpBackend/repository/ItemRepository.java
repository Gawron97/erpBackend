package git.erpBackend.repository;

import git.erpBackend.entity.Item;
import git.erpBackend.entity.Warehouse;
import git.erpBackend.utils.exception.warehouse.WarehouseExceptionHandler;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    Boolean existsByNameAndWarehouse(String name, Warehouse warehouse);

    Optional<Item> findItemByNameAndWarehouse(String name, Warehouse warehouse);

    Optional<Item> findItemByName(String name);

}
