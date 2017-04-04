
package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

	@Query("select a from Actor a where a.userAccount.username=?1")
	Actor findActorByUsername(String username);

	@Query("select a from Actor a where a.sended.size >=ALL(select a.sended.size from Actor a)")
	List<Actor> actorWithMoreMessagesSent();

	@Query("select a from Actor a where a.received.size >=ALL(select a.received.size from Actor a)")
	List<Actor> actorWithMoreMessagesReceived();

	@Query("select a from Actor a where (select count(c) from Comment c where c.author=a.userAccount.username) > (select avg(ac.comments.size)*0.8 from Actor ac) and (select count(c) from Comment c where c.author=a.userAccount.username)< (select avg(ac.comments.size)*1.2 from Actor ac)")
	List<Actor> actorWhoPosted10PercentageFromComments();

}
