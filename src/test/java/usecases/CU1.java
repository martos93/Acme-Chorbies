
package usecases;

import java.util.Calendar;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import repositories.ChorbiRepository;
import services.ChorbiService;
import utilities.AbstractTest;
import domain.Chorbi;
import domain.Coordinates;
import domain.CreditCard;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU1 extends AbstractTest {

	@Autowired
	private ChorbiService		chorbiService;

	@Autowired
	private ChorbiRepository	chorbiRepository;


	//CU 1 - Un usuario mayor de edad no logueado ve un banner aleatorio en la main page y se registra como nuevo chorbi, después se loguea con esta cuenta.
	//RF: A user who is not authenticated must be able to:
	//	- See a welcome page with a banner that advertises Acme projects, including Acme
	//	  Pad-Thai, Acme BnB, and Acme Car’n go! The banners must be selected randomly.
	//	- Login to the system using his or her credentials.
	//	- Register to the system as a chorbi. As of the time of registering, a chorbi is not required
	//	  to provide a credit card. No person under 18 is allowed to register to the system.

	@Test
	public void registerChorbi() {
		final Object[][] testingData = {
			{
				null, null, "manolay"
			}, {
				null, DataIntegrityViolationException.class, "chorbi1"
			}, {
				null, DataIntegrityViolationException.class, ""
			},

		};

		for (int i = 0; i < testingData.length; i++)
			this.template((String) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2]);
	}

	protected void template(final String username, final Class<?> expected, final String useraccount) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Chorbi chorbi = this.chorbiService.create();

			chorbi.getUserAccount().setUsername(useraccount);
			chorbi.getUserAccount().setPassword(useraccount);

			chorbi.setName("Manuel");
			chorbi.setSurname("Lagartón");
			chorbi.setEmail("email@gmail.com");
			chorbi.setPhoneNumber("123123123");
			chorbi.setPicture("http://www.google.es/asd.jpg");
			chorbi.setDescription("Me gusta mucho montar a caballo");
			chorbi.setKindRelationship("LOVE");
			chorbi.setGenre("MAN");
			final Calendar calendar = Calendar.getInstance();
			calendar.set(1993, 9, 9);
			final Date years = calendar.getTime();

			chorbi.setBirthDate(years);
			final Coordinates location = new Coordinates();
			location.setCity("Alcala");
			location.setCountry("España");
			location.setProvince("Sevilla");
			location.setState("Andalucia");
			chorbi.setLocation(location);
			final CreditCard cd = new CreditCard();
			cd.setBrandName("VISA");
			cd.setCvvCode(222);
			cd.setExpirationMonth(12);
			cd.setExpirationYear(2020);
			cd.setHolderName("MANUEL LAGARTON SANCHEZ");
			cd.setNumber("4556327836268273");
			chorbi.setCreditCard(cd);
			Assert.isTrue(this.chorbiService.edad(chorbi) >= 18);
			this.chorbiService.register(chorbi);
			this.chorbiRepository.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
