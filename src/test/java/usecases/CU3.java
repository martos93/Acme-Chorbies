
package usecases;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Chorbi;
import repositories.ChorbiRepository;
import services.ActorService;
import services.ChorbiService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU3 extends AbstractTest {

	@Autowired
	private ChorbiService		chorbiService;
	@Autowired
	private ActorService		actorService;

	@Autowired
	private ChorbiRepository	chorbiRepository;


	//CU3 - Un usuario logueado como chorbi modifica su perfil, introduciendo los datos de una tarjeta de crédito válida entre otros datos de usuario
	//RF: An actor who is authenticated as a chorbi must be able to:
	//  -Change his or her profile
	@Test
	public void editChorbi() {
		final Object[][] testingData = {
			{
				"chorbi1", null, "4556327836268273"
			}, {
				"chorbi1", ConstraintViolationException.class, "aaaaa"
			},
		};

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2]);
	}

	protected void template(final String username, final Class<?> expected, final String arg) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Chorbi chorbi = this.chorbiService.getLoggedChorbi();
			if (arg == "4556327836268273")
				chorbi.getCreditCard().setNumber(arg);
			else
				chorbi.setPicture(arg);
			this.chorbiService.save(chorbi);
			this.chorbiRepository.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
