
package repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {

	@Query("select m from Manager m where m.userAccount.username = ?1")
	public Manager managerByUsername(String username);

	@Query("select m from Manager m where m.userAccount.id=?1")
	public Manager findByPrincipal(int id);

	//dahsboard

	//A listing of managers sorted by the number of events that they organise.
	@Query("select m from Manager m order by m.events.size DESC")
	ArrayList<Manager> sortedManagersByEvents();

	//A listing of managers that includes the amount that they due in fees.
	@Query("select m.name,m.amountDue from Manager m")
	ArrayList<Object> managersAndFees();

}
