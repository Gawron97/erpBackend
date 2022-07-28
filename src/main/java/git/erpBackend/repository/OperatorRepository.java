package git.erpBackend.repository;

import git.erpBackend.entity.Operator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperatorRepository extends JpaRepository<Operator, Integer> {
}
