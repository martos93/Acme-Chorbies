package controllers.administrator;

import domain.Chorbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import services.AdministratorService;
import services.ChorbiService;

import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController {
    //Services
    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private ChorbiService chorbiService;

    //Dashboard
    @RequestMapping("")
    public ModelAndView dashboard() {
        ModelAndView res = new ModelAndView("administrator/dashboard");

        List<String> chorbiesByCity = administratorService.chorbiesByCity();
        res.addObject("chorbiesByCity", chorbiesByCity);

        List<String> chorbiesByCountry = administratorService.chorbiesByCountry();
        res.addObject("chorbiesByCountry", chorbiesByCountry);

        Object[] minMaxAvgAgesChorbies = administratorService.minMaxAvgAgesChorbies();
        res.addObject("minMaxAvgAgesChorbies", minMaxAvgAgesChorbies);

        Double ratioChorbiesInvalidCreditCard = administratorService.ratioChorbiesInvalidCreditCard();
        res.addObject("ratioChorbiesInvalidCreditCard", ratioChorbiesInvalidCreditCard);

        Double ratioChorbiesSearchActivites = administratorService.ratioChorbiesSearchActivites();
        res.addObject("ratioChorbiesSearchActivites", ratioChorbiesSearchActivites);

        Double ratioChorbiesSearchFriendShip = administratorService.ratioChorbiesSearchFriendShip();
        res.addObject("ratioChorbiesSearchFriendShip", ratioChorbiesSearchFriendShip);

        Double ratioChorbiesSearchLove = administratorService.ratioChorbiesSearchLove();
        res.addObject("ratioChorbiesSearchLove", ratioChorbiesSearchLove);

        List<Chorbi> chorbiesSortedByLikes = chorbiService.chorbiesSortedByLikes();
        res.addObject("chorbiesSortedByLikes", chorbiesSortedByLikes);

        Object[] minMaxAvgLikesPerChorbi = administratorService.minMaxAvgLikesPerChorbi();
        res.addObject("minMaxAvgLikesPerChorbi", minMaxAvgLikesPerChorbi);

        Object[] minMaxAvgChirpsRecieved = administratorService.minMaxAvgChirpsRecieved();
        res.addObject("minMaxAvgChirpsRecieved", minMaxAvgChirpsRecieved);

        Object[] minMaxAvgChirpsSended = administratorService.minMaxAvgChirpsSended();
        res.addObject("minMaxAvgChirpsSended", minMaxAvgChirpsSended);

        Collection<Chorbi> chorbiMoreChirpsRecieved = chorbiService.chorbiMoreChirpsRecieved();
        res.addObject("chorbiMoreChirpsRecieved", chorbiMoreChirpsRecieved);

        Collection<Chorbi> chorbiMoreChirpsSended = chorbiService.chorbiMoreChirpsSended();
        res.addObject("chorbiMoreChirpsSended", chorbiMoreChirpsSended);

        return res;
    }
}
