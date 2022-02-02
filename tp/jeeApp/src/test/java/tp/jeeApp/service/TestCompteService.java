package tp.jeeApp.service;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import tp.jeeApp.JeeAppApplication;
import tp.jeeApp.entity.Client;
import tp.jeeApp.entity.Compte;

//@RunWith(SpringRunner.class) //si junit4
@ExtendWith(SpringExtension.class) //si junit5/jupiter
@SpringBootTest(classes= {JeeAppApplication.class})
@ActiveProfiles("h2") //profils activés durant ce test
public class TestCompteService {
	
	
	@Autowired //équivalent de @Inject
	private CompteService compteService; // à tester
	
	@Autowired //équivalent de @Inject
	private ClientService clientService; // à aider à tester
	
	@Test
	public void testBonVirement() {
		
		Compte compteA = new Compte(null,"compteA" , 150.0);
		compteA = compteService.creerCompte(compteA);
		Assertions.assertNotNull(compteA.getNumero()); //verif auto_incr
		
		Compte compteB = new Compte(null,"compteB" , 200.0);
		compteB = compteService.creerCompte(compteB);
		Assertions.assertNotNull(compteB.getNumero()); //verif auto_incr
		
		compteService.effectuerVirement(50.0, compteA.getNumero(), compteB.getNumero());
		double soldeA_apres = compteService.rechercherCompteParNumero(compteA.getNumero()).getSolde();
		double soldeB_apres = compteService.rechercherCompteParNumero(compteB.getNumero()).getSolde();
		Assertions.assertEquals(150.0 -50.0, soldeA_apres,0.0001);
		Assertions.assertEquals(200.0 +50.0, soldeB_apres,0.0001);
		System.out.println("apres bon virement soldeA = " + soldeA_apres + " soldeB=" + soldeB_apres);
	}
	
	@Test
	public void testMauvaisVirement() {
		
		Compte compteA = new Compte(null,"compteA" , 150.0);
		compteA = compteService.creerCompte(compteA);
		Assertions.assertNotNull(compteA.getNumero()); //verif auto_incr
		
		
		try {
			compteService.effectuerVirement(50.0, compteA.getNumero(), -1L /*compte inexistant*/);
			Assertions.fail("bug : exception qui aurait du remonter");
		} catch (Exception e) {
			//exception normale:
			System.err.println("echec normal virement : " + e.getMessage());
		}
		
		double soldeA_apres = compteService.rechercherCompteParNumero(compteA.getNumero()).getSolde();
		System.out.println("apres mauvais virement, soldeA = " + soldeA_apres);
		Assertions.assertEquals(150.0 -0.0, soldeA_apres,0.0001);
		
	}
	
	@Test
	public void testComptesRattachesAuclient() {
		Client client1 = new Client(null,"jean","Bon");
		client1 = clientService.creerClient(client1);
		
		Client client2 = new Client(null,"alex","Therieur");
		client2 = clientService.creerClient(client2);
		
		
		Compte compteA = new Compte(null,"compteA" , 150.0);
		compteA = compteService.creerCompte(compteA,client1.getNumero());
		Assertions.assertNotNull(compteA.getNumero()); //verif auto_incr
		
		Compte compteB = new Compte(null,"compteB" , 200.0);
		compteB = compteService.creerCompte(compteB,client1.getNumero());
		Assertions.assertNotNull(compteB.getNumero()); //verif auto_incr
		
		Compte compteC = new Compte(null,"compteC" , 150.0);
		compteC = compteService.creerCompte(compteC,client2.getNumero());
		Assertions.assertNotNull(compteC.getNumero()); //verif auto_incr
		
		Compte compteD = new Compte(null,"compteD" , 200.0);
		compteD = compteService.creerCompte(compteD,client2.getNumero());
		Assertions.assertNotNull(compteD.getNumero()); //verif auto_incr
		
		List<Compte> listeComptes = compteService.rechercherComptesDuclient(client2.getNumero());
		Assertions.assertTrue(listeComptes.size() == 2);
		System.out.println("listeComptes=" + listeComptes);
		
		
	}

}
