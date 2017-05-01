
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

	@Query("select e from Event e where e.moment > CURRENT_DATE and e.moment <?1 and e.seatsOffered>0 order by e.seatsOffered")
	public Collection<Event> findByMonthToStartAndSeats(Date now);

	@Query("select e from Event e where e.moment < CURRENT_DATE")
	public Collection<Event> findByPastEvents();

	@Query("select e from Event e where e.moment >?1 or (e.moment > CURRENT_DATE and e.moment <?1 and e.seatsOffered=0)")
	public Collection<Event> findFutureEvents(Date now);

	@Query("select e from Event e where e.manager.id=?1")
	public Collection<Event> findByManager(int id);

}
