package usecases;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import repositories.BannerRepository;
import services.BannerService;
import utilities.AbstractTest;

import javax.transaction.Transactional;

@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU13 extends AbstractTest {
    //Services
    @Autowired
    private BannerService bannerService;

    @Autowired
    private BannerRepository bannerRepository;

    //CU13:  Un usuario logueado como admin modifica la lista de banners que se muestran aleatoriamente en la página de bienvenida.
    //RF: An actor who is authenticated as an administrator must be able to:
    //Change the banners that are displayed on the welcome page.

    @Test
    public void modifyBanner(){
        final Object[][] testingData = {
                {
                    "admin", "http://k18.kn3.net/A763E0093.png", null
        },{
                null, "http://k18.kn3.net/A763E0093.png",IllegalArgumentException.class
        }
        };
        for (int i = 0; i < testingData.length; i++)
            this.template((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
    }

    protected void template(final String username,final String url, final Class<?> expected){
        Class<?> caught;
        caught = null;
        try{
            this.authenticate(username);
            bannerService.addBanner(url);
            bannerService.deleteBanner(url);
            bannerRepository.flush();
        }catch (final Throwable oops) {
            caught = oops.getClass();
        }
        this.checkExceptions(expected, caught);
    }
}
