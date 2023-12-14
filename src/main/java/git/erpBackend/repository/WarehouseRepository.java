package git.erpBackend.repository;

import git.erpBackend.entity.Item;
import git.erpBackend.entity.Warehouse;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@Transactional
public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {

}
