package git.erpBackend.repository;

import git.erpBackend.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;

public interface CountryRepository extends JpaRepository<Country, Integer> {
}
