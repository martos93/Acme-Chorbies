package usecases;

import domain.Chorbi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import services.AdministratorService;
import services.ChorbiService;
import utilities.AbstractTest;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU15 extends AbstractTest {
    //Services
    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private ChorbiService chorbiService;

    @Test
    public void dashboard(){
        final Object[][] testingData = {
                {
                        null, IllegalArgumentException.class
                }, {
                "admin", null
        }, {
                "customer1", IllegalArgumentException.class
        }
        };

        for (int i = 0; i < testingData.length; i++) {
            this.template1((String) testingData[i][0], (Class<?>) testingData[i][1]);
            this.template2((String) testingData[i][0], (Class<?>) testingData[i][1]);
            this.template3((String) testingData[i][0], (Class<?>) testingData[i][1]);
            this.template4((String) testingData[i][0], (Class<?>) testingData[i][1]);
            this.template5((String) testingData[i][0], (Class<?>) testingData[i][1]);
            this.template6((String) testingData[i][0], (Class<?>) testingData[i][1]);
            this.template7((String) testingData[i][0], (Class<?>) testingData[i][1]);
            this.template8((String) testingData[i][0], (Class<?>) testingData[i][1]);
            this.template9((String) testingData[i][0], (Class<?>) testingData[i][1]);
            this.template10((String) testingData[i][0], (Class<?>) testingData[i][1]);
            this.template11((String) testingData[i][0], (Class<?>) testingData[i][1]);
            this.template12((String) testingData[i][0], (Class<?>) testingData[i][1]);
            this.template13((String) testingData[i][0], (Class<?>) testingData[i][1]);
        }
    }

    protected void template1(final String username, final Class<?> expected){
        Class<?> caught;
        caught = null;
        try{
            this.authenticate(username);
            List<String> chorbiesByCity = administratorService.chorbiesByCity();
            this.unauthenticate();
        }catch (final Throwable oops) {
            caught = oops.getClass();
        }
        this.checkExceptions(expected, caught);
    }

    protected void template2(final String username, final Class<?> expected){
        Class<?> caught;
        caught = null;
        try{
            this.authenticate(username);
            List<String> chorbiesByCountry = administratorService.chorbiesByCountry();
            this.unauthenticate();
        }catch (final Throwable oops) {
            caught = oops.getClass();
        }
        this.checkExceptions(expected, caught);
    }
    protected void template3(final String username, final Class<?> expected){
        Class<?> caught;
        caught = null;
        try{
            this.authenticate(username);
            Object[] minMaxAvgAgesChorbies = administratorService.minMaxAvgAgesChorbies();
            this.unauthenticate();
        }catch (final Throwable oops) {
            caught = oops.getClass();
        }
        this.checkExceptions(expected, caught);
    }

    protected void template4(final String username, final Class<?> expected){
        Class<?> caught;
        caught = null;
        try{
            this.authenticate(username);
            Double ratioChorbiesInvalidCreditCard = administratorService.ratioChorbiesInvalidCreditCard();
            this.unauthenticate();
        }catch (final Throwable oops) {
            caught = oops.getClass();
        }
        this.checkExceptions(expected, caught);
    }

    protected void template5(final String username, final Class<?> expected){
        Class<?> caught;
        caught = null;
        try{
            this.authenticate(username);
            Double ratioChorbiesSearchActivites = administratorService.ratioChorbiesSearchActivites();
            this.unauthenticate();
        }catch (final Throwable oops) {
            caught = oops.getClass();
        }
        this.checkExceptions(expected, caught);
    }

    protected void template6(final String username, final Class<?> expected){
        Class<?> caught;
        caught = null;
        try{
            this.authenticate(username);
            Double ratioChorbiesSearchFriendShip = administratorService.ratioChorbiesSearchFriendShip();
            this.unauthenticate();
        }catch (final Throwable oops) {
            caught = oops.getClass();
        }
        this.checkExceptions(expected, caught);
    }

    protected void template7(final String username, final Class<?> expected){
        Class<?> caught;
        caught = null;
        try{
            this.authenticate(username);
            Double ratioChorbiesSearchLove = administratorService.ratioChorbiesSearchLove();
            this.unauthenticate();
        }catch (final Throwable oops) {
            caught = oops.getClass();
        }
        this.checkExceptions(expected, caught);
    }
    protected void template8(final String username, final Class<?> expected){
        Class<?> caught;
        caught = null;
        try{
            this.authenticate(username);
            List<Chorbi> chorbiesSortedByLikes = chorbiService.chorbiesSortedByLikes();
            this.unauthenticate();
        }catch (final Throwable oops) {
            caught = oops.getClass();
        }
        this.checkExceptions(expected, caught);
    }

    protected void template9(final String username, final Class<?> expected){
        Class<?> caught;
        caught = null;
        try{
            this.authenticate(username);
            Object[] minMaxAvgLikesPerChorbi = administratorService.minMaxAvgLikesPerChorbi();
            this.unauthenticate();
        }catch (final Throwable oops) {
            caught = oops.getClass();
        }
        this.checkExceptions(expected, caught);
    }

    protected void template10(final String username, final Class<?> expected){
        Class<?> caught;
        caught = null;
        try{
            this.authenticate(username);
            Object[] minMaxAvgChirpsRecieved = administratorService.minMaxAvgChirpsRecieved();
            this.unauthenticate();
        }catch (final Throwable oops) {
            caught = oops.getClass();
        }
        this.checkExceptions(expected, caught);
    }
    protected void template11(final String username, final Class<?> expected){
        Class<?> caught;
        caught = null;
        try{
            this.authenticate(username);
            Object[] minMaxAvgChirpsSended = administratorService.minMaxAvgChirpsSended();
            this.unauthenticate();
        }catch (final Throwable oops) {
            caught = oops.getClass();
        }
        this.checkExceptions(expected, caught);
    }

    protected void template12(final String username, final Class<?> expected){
        Class<?> caught;
        caught = null;
        try{
            this.authenticate(username);
            Collection<Chorbi> chorbiMoreChirpsRecieved = chorbiService.chorbiMoreChirpsRecieved();
            this.unauthenticate();
        }catch (final Throwable oops) {
            caught = oops.getClass();
        }
        this.checkExceptions(expected, caught);
    }

    protected void template13(final String username, final Class<?> expected){
        Class<?> caught;
        caught = null;
        try{
            this.authenticate(username);
            Collection<Chorbi> chorbiMoreChirpsSended = chorbiService.chorbiMoreChirpsSended();
            this.unauthenticate();
        }catch (final Throwable oops) {
            caught = oops.getClass();
        }
        this.checkExceptions(expected, caught);
    }

}
