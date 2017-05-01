
package controllers.administrator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import domain.Chorbi;
import domain.Manager;
import services.AdministratorService;
import services.ChorbiService;
import services.ManagerService;

@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController {

	//Services
	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ChorbiService			chorbiService;

	@Autowired
	private ManagerService			managerService;


	//Dashboard
	@RequestMapping("")
	public ModelAndView dashboard() {
		final ModelAndView res = new ModelAndView("administrator/dashboard");

		final List<String> chorbiesByCity = this.administratorService.chorbiesByCity();
		res.addObject("chorbiesByCity", chorbiesByCity);

		final List<String> chorbiesByCountry = this.administratorService.chorbiesByCountry();
		res.addObject("chorbiesByCountry", chorbiesByCountry);

		final Object[] minMaxAvgAgesChorbies = this.administratorService.minMaxAvgAgesChorbies();
		res.addObject("minMaxAvgAgesChorbies", minMaxAvgAgesChorbies);

		final Double ratioChorbiesInvalidCreditCard = this.administratorService.ratioChorbiesInvalidCreditCard();
		res.addObject("ratioChorbiesInvalidCreditCard", ratioChorbiesInvalidCreditCard);

		final Double ratioChorbiesSearchActivites = this.administratorService.ratioChorbiesSearchActivites();
		res.addObject("ratioChorbiesSearchActivites", ratioChorbiesSearchActivites);

		final Double ratioChorbiesSearchFriendShip = this.administratorService.ratioChorbiesSearchFriendShip();
		res.addObject("ratioChorbiesSearchFriendShip", ratioChorbiesSearchFriendShip);

		final Double ratioChorbiesSearchLove = this.administratorService.ratioChorbiesSearchLove();
		res.addObject("ratioChorbiesSearchLove", ratioChorbiesSearchLove);

		final List<Chorbi> chorbiesSortedByLikes = this.chorbiService.chorbiesSortedByLikes();
		res.addObject("chorbiesSortedByLikes", chorbiesSortedByLikes);

		final Object[] minMaxAvgLikesPerChorbi = this.administratorService.minMaxAvgLikesPerChorbi();
		res.addObject("minMaxAvgLikesPerChorbi", minMaxAvgLikesPerChorbi);

		final Object[] minMaxAvgChirpsRecieved = this.administratorService.minMaxAvgChirpsRecieved();
		res.addObject("minMaxAvgChirpsRecieved", minMaxAvgChirpsRecieved);

		final Object[] minMaxAvgChirpsSended = this.administratorService.minMaxAvgChirpsSended();
		res.addObject("minMaxAvgChirpsSended", minMaxAvgChirpsSended);

		final Collection<Chorbi> chorbiMoreChirpsRecieved = this.chorbiService.chorbiMoreChirpsRecieved();
		res.addObject("chorbiMoreChirpsRecieved", chorbiMoreChirpsRecieved);

		final Collection<Chorbi> chorbiMoreChirpsSended = this.chorbiService.chorbiMoreChirpsSended();
		res.addObject("chorbiMoreChirpsSended", chorbiMoreChirpsSended);

		final List<Manager> sortedManagersByEvents = this.managerService.sortedManagersByEvents();
		res.addObject("sortedManagersByEvents", sortedManagersByEvents);

		final ArrayList<Object> obj = this.managerService.managersAndFees();
		final List<Object> result = obj;
		final ArrayList<String> aux = new ArrayList<String>();
		final java.util.Iterator<Object> itr = result.iterator();
		while (itr.hasNext()) {

			final Object[] obje = (Object[]) itr.next();
			// now you have one array of Object for each row
			final String aa = String.valueOf(obje[0]) + " - " + String.valueOf(obje[1]);
			aux.add(aa);
		}
		res.addObject("managersAndFees", aux);

		final Collection<Chorbi> sortedChorbiesByEvents = this.chorbiService.sortedChorbiesByEvents();
		res.addObject("sortedChorbiesByEvents", sortedChorbiesByEvents);

		final ArrayList<Object> obj2 = this.chorbiService.chorbiesAndFees();
		final List<Object> result2 = obj2;
		final ArrayList<String> aux2 = new ArrayList<String>();
		final java.util.Iterator<Object> itr2 = result2.iterator();
		while (itr2.hasNext()) {

			final Object[] obje = (Object[]) itr2.next();
			// now you have one array of Object for each row
			final String aa = String.valueOf(obje[0]) + " - " + String.valueOf(obje[1]);
			aux2.add(aa);
		}
		res.addObject("chorbiesAndFees", aux2);

		final Object[] minAvgMaxStarsPerChorbi = this.chorbiService.minAvgMaxStarsPerChorbi();
		res.addObject("minAvgMaxStarsPerChorbi", minAvgMaxStarsPerChorbi);

		final List<Chorbi> sortedChorbiesByStars = this.chorbiService.sortedChorbiesByStars();
		res.addObject("sortedChorbiesByStars", sortedChorbiesByStars);

		return res;
	}
}
