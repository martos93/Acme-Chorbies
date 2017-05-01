
package repositories;

import java.util.ArrayList;
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

	@Query("select l.loved from Love l where l.lover.id=?1")
	public Collection<Chorbi> findAllLovedByChorbiId(int id);

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

	//A listing of chorbies sorted by the number of events to which they have registered.
	@Query("select c from Chorbi c order by c.events.size DESC")
	public Collection<Chorbi> sortedChorbiesByEvents();

	//A listing of chorbies that includes the amount that they due in fees.
	@Query("select c.name,c.amountDue from Chorbi c")
	ArrayList<Object> chorbiesAndFees();

	//The minimum, the maximum, and the average number of stars per chorbi.
	@Query("select min(l.stars),avg(l.stars),max(l.stars) from Love l")
	public Object[] minAvgMaxStarsPerChorbi();

	//The list of chorbies, sorted by the average number of stars that they've got.
	@Query("select c from Chorbi c order by c.lovedBy.size DESC")
	ArrayList<Chorbi> sortedChorbiesByStars();

}
