
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Love;

@Repository
public interface LoveRepository extends JpaRepository<Love, Integer> {

}
