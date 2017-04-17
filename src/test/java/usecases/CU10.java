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
public class CU10 extends AbstractTest{
	
	@Autowired 
	ChirpRepository chirpRepository;
	
	@Autowired 
	ChirpService chirpService;
	
	@Autowired
	ChorbiService chorbiService;

	//CU10: Un usuario logueado como chorbi ve todos los chirps que ha enviado y decide reenviar uno de ellos.
	//RF: An actor who is authenticated as a chorbi must be able to: Browse the list of chirps that he or she’s 
	//sent, and re-send any of them.
	
	@Test
	public void forwardChirp() {
		final Object[][] testingData = {
			{
				"chorbi1", 27, 30,null
			}, {
				"chorbi2", 28, 31,null
			}, {
				"chorbi3", 26, 32,null
			}, 
			{
				null, 49, 30, NullPointerException.class
			}, 
			{
				"admin", 25, 30, NullPointerException.class
			},
			{
				"chorbi1", 26, 33, NullPointerException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.forwardChirp((String) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	protected void forwardChirp(final String username, final int id, final int id2, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Chirp chirp = this.chirpService.findOne(id2);
			chirp.setReceiver(chorbiService.findOne(id));
			
			chirpService.forwardChirp(chirp);
			this.chirpRepository.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
