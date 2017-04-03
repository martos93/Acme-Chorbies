
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Cache;

public interface CacheRepository extends JpaRepository<Cache, Integer> {

}
