package tp.jeeApp.init;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import tp.jeeApp.entity.Client;
import tp.jeeApp.entity.Compte;
import tp.jeeApp.service.ClientService;
import tp.jeeApp.service.CompteService;

@Component
@Profile("dev") //pour activer ce code qu'en profil "dev" (pas en production)
public class InitDataSet {
	
	@Value("${exemple.comment}")
	private String comment;
	
	@Autowired
	private CompteService compteService;
	
	@Autowired //équivalent de @Inject
	private ClientService clientService; 
	
	@Autowired(required = false)
	private Ix x;
	
	@PostConstruct
	public void init() {
		System.out.println("dans methode préfixée par @PostConstruct, compteService="+compteService);
		System.out.println("dans methode préfixée par @PostConstruct, x="+x);
		System.out.println("dans methode préfixée par @PostConstruct, comment="+comment);
		Client client1 = new Client(null,"jean","Bon");
		client1 = clientService.creerClient(client1);
		
		Client client2 = new Client(null,"alex","Therieur");
		client2 = clientService.creerClient(client2);
		
		Compte compteA = new Compte(null,"compteA" , 150.0);
		compteA = compteService.creerCompte(compteA,client1.getNumero());
		
		Compte compteB = new Compte(null,"compteB" , 200.0);
		compteB = compteService.creerCompte(compteB,client1.getNumero());
		Compte compteC = new Compte(null,"compteC" , 150.0);
		compteC = compteService.creerCompte(compteC,client2.getNumero());
	
		Compte compteD = new Compte(null,"compteD" , 200.0);
		compteD = compteService.creerCompte(compteD,client2.getNumero());
	}

	public InitDataSet() {
		System.out.println("dans constructeur de InitDataSet , compteService="+compteService);
	}

}
