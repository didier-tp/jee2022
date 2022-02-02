package tp.jeeApp.service;

import java.util.List;

import tp.jeeApp.entity.Compte;
import tp.jeeApp.exception.AlreadyExistsException;
import tp.jeeApp.exception.NotFoundException;

public interface CompteService {
	Compte rechercherCompteParNumero(long numero);
	List<Compte> rechercherTousLesComptes();
	List<Compte> rechercherComptesDuclient(long numClient);
	
	Compte creerCompte(Compte compte) throws AlreadyExistsException;  //en retour compte avec numero auto incrémenté
	Compte creerCompte(Compte compte,long numClient) throws AlreadyExistsException;  //en retour compte avec numero auto incrémenté
	Compte modifierCompte(Compte compte) throws NotFoundException;
	
	void supprimerCompte(long numCompte) throws NotFoundException;
	
	void effectuerVirement(double montant,long numCptDeb,long numCptCred);
	
}
