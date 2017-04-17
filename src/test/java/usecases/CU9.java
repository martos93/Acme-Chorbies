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
public class CU9 extends AbstractTest{
	
	@Autowired 
	ChirpRepository chirpRepository;
	
	@Autowired 
	ChirpService chirpService;
	
	@Autowired
	ChorbiService chorbiService;

	//CU9: Un usuario logueado como chorbi ve todos los chirps que le han llegado y decide responder a uno de ellos.
	//RF: An actor who is authenticated as a chorbi must be able to: Browse the list of chirps that he or she’s got, 
	//and reply to any of them.
	
	@Test
	public void replyChirp() {
		final Object[][] testingData = {
			{
				"chorbi1", 26, 30,null
			}, {
				"chorbi2", 27, 31,null
			}, {
				"chorbi3", 28, 32,null
			}, 
			{
				null, 49, 30, IllegalArgumentException.class
			}, 
			{
				"admin", 25, 30, NullPointerException.class
			},
			{
				"chorbi1", 26, 33, NullPointerException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.replyChirp((String) testingData[i][0], (int) testingData[i][1], (int) testingData[i][2], (Class<?>) testingData[i][3]);
	}
	protected void replyChirp(final String username, final int id, final int id2, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Chirp chirp = this.chirpService.findOne(id2);
			
			chirpService.replyChirp(chirp);
			this.chirpRepository.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
