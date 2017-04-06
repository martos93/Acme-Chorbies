
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Chorbi;

@Repository
public interface ChorbiRepository extends JpaRepository<Chorbi, Integer> {

	@Query("select l.lover from Love l where l.loved.id = ?1")
	public Collection<Chorbi> LikersByChorbyId(int id);

	@Query("select c from Chorbi c where c.userAccount.id=?1")
	public Chorbi findByPrincipal(int id);

}
