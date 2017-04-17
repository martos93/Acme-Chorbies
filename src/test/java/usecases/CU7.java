package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Chirp;
import repositories.ChirpRepository;
import services.ChirpService;
import services.ChorbiService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
	@RunWith(SpringJUnit4ClassRunner.class)
	@Transactional
public class CU7 extends AbstractTest{
	
	@Autowired 
	ChirpRepository chirpRepository;
	
	@Autowired 
	ChirpService chirpService;
	
	@Autowired
	ChorbiService chorbiService;

	//CU7: Un usuario logueado como chorbi manda un chirp (mensaje) a otro chorbi (en la vista de mensajes 
	//propios o en la de listar los chorbis).
	//RF: An actor who is authenticated as a chorbi must be able to: Chirp to another chorbi.
	
	@Test
	public void sendChirp() {
		final Object[][] testingData = {
			{
				"chorbi1", 26, null
			}, {
				"chorbi2", 27, null
			}, {
				"chorbi3", 28, null
			}, 
			{
				null, 49, IllegalArgumentException.class
			}, 
			{
				"admin", 25, NullPointerException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.sendChirp((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void sendChirp(final String username, final int id, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Chirp chirp = this.chirpService.create();
			chirp.setReceiver(this.chorbiService.findOne(id));
			chirp.setSubject("Ey amigo");
			chirp.setText("k tal?");
			
			chirpService.sendChirp(chirp);
			this.chirpRepository.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
