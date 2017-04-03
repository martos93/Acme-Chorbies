
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Coordinates;

@Repository
public interface CoordinateRepository extends JpaRepository<Coordinates, Integer> {

}
