package tp.jeeApp.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tp.jeeApp.dto.Dto;
import tp.jeeApp.dto.ErreurDetaillee;
import tp.jeeApp.entity.Compte;
import tp.jeeApp.exception.NotFoundException;
import tp.jeeApp.service.CompteService;


@RestController
@RequestMapping(value="/api-bank/compte" , headers="Accept=application/json")
public class CompteRestCtrl {
	
	private CompteService compteService;

	public CompteRestCtrl(CompteService compteService) {
		//injection Spring par constructeur
		this.compteService = compteService;
	}
	
	//RECHERCHE UNIQUE selon RESOURCE-ID:
	//URL de déclenchement: http://localhost:8080/jeeApp/api-bank/compte/1
	@RequestMapping(value="/{numero}" , method=RequestMethod.GET)
	public ResponseEntity<?> getCompteByNum(@PathVariable("numero") Long numero) {
		Compte compte = compteService.rechercherCompteParNumero(numero);
		if(compte != null)
	        return new ResponseEntity<Dto.CompteRecord>(new Dto.CompteRecord(compte.getNumero(),compte.getLabel(),compte.getSolde()),HttpStatus.OK);
		else {
			//return new ResponseEntity<Compte>(HttpStatus.NOT_FOUND); //V1
			/*
			//V2 (avec map):
			Map<String,String> mapErr= new HashMap<>();
			mapErr.put("message", "not found");
			mapErr.put("details", "pas compte pour numero=" + numero);
			return new ResponseEntity<Map<String,String>>(mapErr,HttpStatus.NOT_FOUND);
			*/
			//V3 (DTO d'erreur):
			return new ResponseEntity<ErreurDetaillee>(new ErreurDetaillee("not found","pas compte pour numero=" + numero),
					                                   HttpStatus.NOT_FOUND);
		}
	}
	//http://localhost:8080/jeeApp/api-bank/compte/4
	@DeleteMapping(value="/{numero}")
	public ResponseEntity<Dto.MessageDetails> deleteCompteByNum(@PathVariable("numero") Long numero) {
		try {
			compteService.supprimerCompte(numero);
			return new ResponseEntity<Dto.MessageDetails>(new Dto.MessageDetails("compte bien supprime","..."),HttpStatus.OK); //200
		} catch (NotFoundException e) {
			return new ResponseEntity<Dto.MessageDetails>(new Dto.MessageDetails("compte inexistant","..."),HttpStatus.NOT_FOUND); //404
			//e.printStackTrace();
		}
	}
	
	//RECHERCHE MULTIPLE :
	//URL de déclenchement: http://localhost:8080/jeeApp/api-bank/compte
	//ou                    http://localhost:8080/jeeApp/api-bank/compte?numClient=1
	@RequestMapping(value="" , method=RequestMethod.GET)
	public List<Dto.CompteRecord> getComptesByCriteria(
			@RequestParam(value="numClient",required=false) Long numClient) {
		List<Compte> listeComptes ;
		if(numClient==null)
			listeComptes =  compteService.rechercherTousLesComptes();
		else
			listeComptes = compteService.rechercherComptesDuclient(numClient);
		return listeComptes.stream()
				.map((cpt) -> new Dto.CompteRecord(cpt.getNumero(),cpt.getLabel(),cpt.getSolde()))
				.toList();
	}
	
	//URL de déclenchement: http://localhost:8080/jeeApp/api-bank/compte
	//avec dans la partie body de la requete des données JSON de ce genre:
	// { "label" : "nouveauCompte" , "solde" : 50.0 }
	//ou bien   { "numero": null , "label" : "nouveauCompte" , "solde" : 50.0 }
	@PostMapping(value=""  )
	Dto.CompteRecord createCompte(@RequestBody Dto.CompteRecord compteDto) {
	 System.out.println("compte to insert/create:" + compteDto);
	 Compte cpt = compteService.creerCompte(new Compte(compteDto.numero(),compteDto.label(),compteDto.solde()));
	 return new Dto.CompteRecord(cpt.getNumero(),cpt.getLabel(),cpt.getSolde());
	}
	
	//URL de déclenchement: http://localhost:8080/jeeApp/api-bank/compte/virement
	//avec dans la partie body de la requete des données JSON de ce genre:
	// { "montant" : 50 , "numCptDeb" : 1 , "numCptCred" : 2 }
	@PostMapping(value="virement"  )
	ResponseEntity<Dto.ConfirmationVirement> postVirement(@RequestBody Dto.DemandeVirement demandeVirement) {
		 System.out.println("demandeVirement:" + demandeVirement);
		try {
			compteService.effectuerVirement(demandeVirement.montant(), demandeVirement.numCptDeb(), demandeVirement.numCptCred());
		    return new ResponseEntity<Dto.ConfirmationVirement>(
		    		new Dto.ConfirmationVirement(demandeVirement,true,"virement bien effectue"),
		    		HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Dto.ConfirmationVirement>(
					new Dto.ConfirmationVirement(demandeVirement,false,"echec virement"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//URL de déclenchement: http://localhost:8080/jeeApp/api-bank/compte
	//avec dans la partie body de la requete des données JSON de ce genre:
	// { "numero": 4 , "label" : "nouveauLabel" , "solde" : 50.0 }
	@PutMapping(value=""  )
	Dto.CompteRecord updateCompte(@RequestBody Dto.CompteRecord compteDto) {
		 System.out.println("compte to update:" + compteDto);
		 Compte cpt = compteService.modifierCompte(new Compte(compteDto.numero(),compteDto.label(),compteDto.solde()));
		 return new Dto.CompteRecord(cpt.getNumero(),cpt.getLabel(),cpt.getSolde());
	}

}
