package usecases2;

import domain.Event;
import forms.ChirpManagerForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import repositories.ChirpRepository;
import services.ChirpService;
import services.EventService;
import utilities.AbstractTest;

import javax.transaction.Transactional;

@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU13 extends AbstractTest {
    @Autowired
    ChirpService chirpService;

    @Autowired
    EventService eventService;

    @Autowired
    ChirpRepository chirpRepository;

    //Un usuario logueado como manager envía un chirp a todos los chorbies apuntados
    // en un evento que él o ella gestiona.
    //RF: An actor who is authenticated as a manager must be able to:
    //Broadcast a chirp to the chorbies who have registered to any of the events that he or she manages.

    @Test
    public void broadcastChirp() {
        final Object[][] testingData = {
                {
                        "manager1",63, null
                }, {
                    "admin", 63, NullPointerException.class
        }, {
                    "manager2", 63, IllegalArgumentException.class
        }
        };

        for (int i = 0; i < testingData.length; i++)
            this.template((String) testingData[i][0],(int) testingData[i][1], (Class<?>) testingData[i][2]);
    }

    protected void template(final String username,int id, final Class<?> expected) {
        Class<?> caught;
        caught = null;
        try {
            this.authenticate(username);
            Event event = this.eventService.findOne(id);
            ChirpManagerForm chirpManagerForm = new ChirpManagerForm();
            chirpManagerForm.setSubject("Prueba manager");
            chirpManagerForm.setText("Pureba manager");

            this.chirpService.broadcastChirps(event, chirpManagerForm, null);
            this.chirpRepository.flush();

        } catch (final Throwable oops) {
            caught = oops.getClass();
        }
        this.checkExceptions(expected, caught);
    }
}
