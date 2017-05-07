package usecases2;

import domain.Chorbi;
import domain.Love;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import repositories.LoveRepository;
import services.ChorbiService;
import services.LoveService;
import utilities.AbstractTest;

import javax.transaction.Transactional;

@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU12  extends AbstractTest {
    @Autowired
    LoveService loveService;

    @Autowired
    ChorbiService chorbiService;

    @Autowired
    LoveRepository loveRepository;

    //CU 7 - Un usuario logueado como chorbi da like a otro chorbi. Con la particularidad de poder indicar
    // un rango de estrellas de 0 a 3.

    ////RF: An actor who is authenticated as a chorbi must be able to:
    // Like another chorbi; a like may be cancelled at any time.

    //RI:  When a chorbi likes another chorbi, he or she should be able to indicate
    // a number of stars in range zero ? three.

    @Test
    public void giveLove() {
        final Object[][] testingData = {
                {
                        "chorbi1","chorbi2", null
                }, {
                "chorbi2","chorbi2", IllegalArgumentException.class
        }, {
                "chorbi2","admin", IllegalArgumentException.class
        }
        };

        for (int i = 0; i < testingData.length; i++)
            this.template((String) testingData[i][0],(String) testingData[i][1], (Class<?>) testingData[i][2]);
    }

    protected void template(final String usernameLover,String usernameLoved, final Class<?> expected) {
        Class<?> caught;
        caught = null;
        try {
            this.authenticate(usernameLover);
            final Chorbi chorbiLoved = this.chorbiService.findChorbiByUsername(usernameLoved);
            Love love = loveService.create();
            love.setLoved(chorbiLoved);
            love.setStars(2);
            love.setComment("He disfrutado");
            loveService.addLove(love);
            this.loveRepository.flush();

        } catch (final Throwable oops) {
            caught = oops.getClass();
        }
        this.checkExceptions(expected, caught);
    }
}
