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
public class CU8 extends AbstractTest{
	
	@Autowired 
	ChirpRepository chirpRepository;
	
	@Autowired 
	ChirpService chirpService;
	
	@Autowired
	ChorbiService chorbiService;

	//CU8: - Un usuario logueado como chorbi ve todos los chirps que le han llegado o 
	//que ha enviado, y decide borrar uno tras previa confirmación.
	//RF: An actor who is authenticated as a chorbi must be able to: Erase any of the chirps that 
	//he or she’s got or sent, which requires previous confir-mation.
	
	@Test
	public void deleteChirp() {
		final Object[][] testingData = {
			{
				"chorbi1", 30, null
			}, {
				"chorbi2", 31, null
			}, {
				"chorbi3", 32, null
			}, 
			{
				null, 30, IllegalArgumentException.class
			}, 
			{
				"admin", 31, NullPointerException.class
			},
			{
				"chorbi1", 33, NullPointerException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.deleteChirp((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}
	protected void deleteChirp(final String username, final int id, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			
			chirpService.deleteChirp(id);
			
			this.chirpRepository.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
