package git.erpBackend.repository;

import git.erpBackend.entity.Operator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OperatorRepository extends JpaRepository<Operator, Integer> {

    Optional<Operator> findByLogin(String login);

}
