
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Cache;

@Repository
public interface ChirpRepository extends JpaRepository<Cache, Integer> {

}
