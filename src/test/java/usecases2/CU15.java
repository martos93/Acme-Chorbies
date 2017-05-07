package usecases2;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Manager;
import domain.CreditCard;
import repositories.ManagerRepository;
import services.ManagerService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
	@RunWith(SpringJUnit4ClassRunner.class)
	@Transactional
public class CU15 extends AbstractTest{
	
	@Autowired
	private ManagerService		managerService;

	@Autowired
	private ManagerRepository	managerRepository;


	//CU 15 - Un usuario logueado como Admin registra un manager
	

	@Test
	public void registerManager() {
		final Object[][] testingData = {
			{
				"admin", null, "manager999"
			}, 
			{
				null, IllegalArgumentException.class, "manager998"
			}, 
			{
				"chorbi1", IllegalArgumentException.class, "manager997"
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
			final Manager manager = this.managerService.create();

			manager.getUserAccount().setUsername(useraccount);
			manager.getUserAccount().setPassword(useraccount);

			manager.setName("Manuel");
			manager.setSurname("Lagartón");
			manager.setEmail("email@gmail.com");
			manager.setPhoneNumber("123123123");
			manager.setCompany("Company");
			manager.setVAT("VAT");
		
			final CreditCard cd = new CreditCard();
			cd.setBrandName("VISA");
			cd.setCvvCode(222);
			cd.setExpirationMonth(12);
			cd.setExpirationYear(2020);
			cd.setHolderName("MANUEL LAGARTON SANCHEZ");
			cd.setNumber("4556327836268273");
			manager.setCreditCard(cd);

			this.managerService.register(manager);
			this.managerRepository.flush();

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
