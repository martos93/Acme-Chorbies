package usecases2;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import repositories.LoveRepository;
import services.ActorService;
import services.ChorbiService;
import services.LoveService;
import utilities.AbstractTest;


@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
	@RunWith(SpringJUnit4ClassRunner.class)
	@Transactional
public class CU14 extends AbstractTest{
	
	@Autowired
	private LoveService			loveService;
	@Autowired
	private ActorService 		actorService;
	@Autowired
	private ChorbiService		chorbiService;

	@Autowired
	private LoveRepository		loveRepository;
	

	//CU 14 - Un usuario logueado como chorbi accede a un catálogo de los chorbis que le gustan. 
	//Solo podrá acceder a esta vista si tiene una tarjeta de crédito válida. Si él o ella no registra una tarjeta válida, 
	//entonces el sistema debe pedirle que lo haga; El sistema debe informar a los chorbies que sus tarjetas de crédito no se cargarán
	//RF:An actor who is authenticated as a chorbi must be able to:
	//	-Browse the catalogue of chorbies who have liked him or her as long as he or she has registered a valid credit card. 
	//	If he or she’s not register a valid card, then the system must ask him or her to do so; the system must inform the chorbies that their 
	//	credit cards will not be charged.

	@Test
	public void chorbiesILike() {
		final Object[][] testingData = {
			{
				"chorbi1", null
			}, 
			{
				null, IllegalArgumentException.class
			}, 
			{
				"admin", IllegalArgumentException.class
			},
			{
				"manager1", IllegalArgumentException.class
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void template(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			loveService.findAllLove();
			actorService.checkCreditCard(chorbiService.findByPrincipal().getCreditCard());
			
			this.loveRepository.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
