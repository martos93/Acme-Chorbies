
package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Chorbi;
import domain.Template;
import repositories.ChorbiRepository;
import services.ChorbiService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU4 extends AbstractTest {

	@Autowired
	private ChorbiService		chorbiService;

	//Repositories----------------------------------------------------------------
	@Autowired
	private ChorbiRepository	chorbiRepository;

	//Tests-------------------------------------------------------------------

	//CU4: Un usuario logueado como chorbi modifica su template de búsqueda y realiza una búsqueda con
	//ese template, mostrando la lista de chorbies que cumplían las restricciones.


	//RF: An actor who is authenticated as a chorbi must be able to:
	//Change his or her search template.
	//Browse the results of his or her search template as long as he or she's registered a valid credit card.
	//Note that the validity of the credit card must be checked every time the results of the search template
	//are displayed. The results of search templates must be cached for at least 12 hours.

	@Test
	public void searchChorbies() {
		final Object[][] testingData = {
			{
				null, "Brenes", IllegalArgumentException.class
			}, {
				"admin", "Brenes", IllegalArgumentException.class
			}, {
				"chorbi1", "Brenes", null
			}, {
				"chorbi2", "Brenes", null
			}, {
				"chorbi3", "Brenes", IllegalArgumentException.class
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.searchChorbies((String) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][1]);
	}

	protected void searchChorbies(final String username, final String keyword, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Chorbi c = this.chorbiService.findByPrincipal();
			final Template t = c.getTemplate();
			t.setKeyword(keyword);
			this.chorbiService.getChorbiesByTemplate(t);

			this.chorbiRepository.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
