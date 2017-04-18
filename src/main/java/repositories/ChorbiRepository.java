
package repositories;

import java.util.Collection;
import java.util.List;

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

	@Query("select c from Chorbi c where c.userAccount.enabled=true")
	public Collection<Chorbi> findAllNotBanned();

	@Query("select c from Chorbi c where c.userAccount.enabled=false")
	public Collection<Chorbi> findAllBanned();

	@Query("select a from Chorbi a where a.userAccount.username=?1")
	public Chorbi findChorbiByUsername(String username);

	//Dashboard:
	//The list of chorbies, sorted by the number of likes they have got:
	@Query("select c from Chorbi c order by c.lovedBy.size DESC")
	public List<Chorbi> chorbiesSortedByLikes();

	//The chorbies who have got more chirps:
	@Query("select c from Chorbi c where c.received.size >=ALL(select c.received.size from Chorbi c)")
	public Collection<Chorbi> chorbiMoreChirpsRecieved();

	//The chorbies who have sent more chirps:
	@Query("select c from Chorbi c where c.sended.size >=ALL(select c.sended.size from Chorbi c)")
	public Collection<Chorbi> chorbiMoreChirpsSended();
}
