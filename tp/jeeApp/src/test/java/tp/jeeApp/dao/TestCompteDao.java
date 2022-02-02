package tp.jeeApp.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import tp.jeeApp.JeeAppApplication;
import tp.jeeApp.entity.Compte;

//@RunWith(SpringRunner.class) //si junit4
@ExtendWith(SpringExtension.class) //si junit5/jupiter
@SpringBootTest(classes= {JeeAppApplication.class})
@ActiveProfiles("h2") //profils activés durant ce test
public class TestCompteDao {
	
	
	@Autowired //équivalent de @Inject
	private DaoCompte daoCompte; // à tester
	
	@Test
	public void testCrud() {
		Compte compteA = new Compte(null,"compteA" , 150.0);
		compteA = daoCompte.save(compteA);
		Assertions.assertNotNull(compteA.getNumero()); //verif auto_incr
		
		Compte compteABis  = daoCompte.findById(compteA.getNumero()).orElse(null);
		System.out.println("compteABis=" + compteABis.toString());
	}

}
