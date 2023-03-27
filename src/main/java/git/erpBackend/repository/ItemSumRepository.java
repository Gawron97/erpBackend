package git.erpBackend.repository;

import git.erpBackend.entity.ItemSum;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ItemSumRepository extends JpaRepository<ItemSum, Integer> {

    Optional<ItemSum> findByName(String name);

}
