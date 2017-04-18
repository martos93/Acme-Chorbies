
package repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	@Query("select c.location.city from Chorbi c")
	public Set<String> cities();

	@Query("select c.location.country from Chorbi c")
	public Set<String> countries();

	//Dashboard

	//A listing with the number of chorbies per country and city:
	//Country:
	@Query("select c.location.country, count(c) from Chorbi c where c.location.country = ?1")
	public Object[] chorbiesByCountry(String country);
	//City
	@Query("select c.location.city, count(c) from Chorbi c where c.location.city = ?1")
	public Object[] chorbiesByCity(String city);

	//The minimum, the maximum, and the average ages of the chorbies:
	@Query("select min(YEAR(CURRENT_DATE)-YEAR(c.birthDate)), max(YEAR(CURRENT_DATE)-Year(c.birthDate)), avg(YEAR(CURRENT_DATE)-YEAR(c.birthDate)) from Chorbi c")
	public Object[] minMaxAvgAgesChorbies();

	//The ratio of chorbies who have not registered a credit card or have registered an invalid credit card:
	@Query("select (count(c)*1.0)/(select count(cc) from Chorbi cc) from Chorbi c where c.creditCard is null OR(c.creditCard.expirationMonth<=MONTH(CURRENT_DATE) AND(c.creditCard.expirationYear<=YEAR(CURRENT_DATE)))")
	public Double ratioChorbiesInvalidCreditCard();

	//The ratios of chorbies who search for ?activities?, ?friendship?, and ?love?:
	//Activities:
	@Query("select (count(c)*1.0)/(select count(cc) from Chorbi cc) from Chorbi c where c.template.kindRelationship = 'ACTIVITIES'")
	public Double ratioChorbiesSearchActivites();
	//Frienship:
	@Query("select (count(c)*1.0)/(select count(cc) from Chorbi cc) from Chorbi c where c.template.kindRelationship = 'FRIENDSHIP'")
	public Double ratioChorbiesSearchFriendShip();
	//Love:
	@Query("select (count(c)*1.0)/(select count(cc) from Chorbi cc) from Chorbi c where c.template.kindRelationship = 'LOVE'")
	public Double ratioChorbiesSearchLove();

	//The minimum, the maximum, and the average number of likes per chorbi:
	@Query("select min(c.lovedBy.size), max(c.lovedBy.size), avg(c.lovedBy.size) from Chorbi c")
	public Object[] minMaxAvgLikesPerChorbi();

	//The minimum, the maximum, and the average number of chirps that a chorbi receives from other chorbies:
	@Query("select min(c.received.size), max(c.received.size), avg(c.received.size) from Chorbi c")
	public Object[] minMaxAvgChirpsRecieved();

	//The minimum, the maximum, and the average number of chirps that a chorbi sends to other chorbies:
	@Query("select min(c.sended.size), max(c.sended.size), avg(c.sended.size) from Chorbi c")
	public Object[] minMaxAvgChirpsSended();
}
