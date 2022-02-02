package tp.jeeApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tp.jeeApp.dao.DaoClient;
import tp.jeeApp.dao.DaoCompte;
import tp.jeeApp.entity.Client;
import tp.jeeApp.entity.Compte;
import tp.jeeApp.exception.AlreadyExistsException;
import tp.jeeApp.exception.NotFoundException;

@Service
@Transactional( /* propagation = Propagation.REQUIRED par defaut */)
public class CompteServiceImpl implements CompteService {
	
	@Autowired
	private DaoCompte daoCompte; //dao principal
	
	@Autowired
	private DaoClient daoClient;//dao secondaire

	public CompteServiceImpl() {
	}

	@Override
	public Compte rechercherCompteParNumero(long numero) {
		return daoCompte.findById(numero).orElse(null);
	}

	@Override
	public List<Compte> rechercherTousLesComptes() {
		return (List<Compte>) daoCompte.findAll();
	}

	@Override
	public List<Compte> rechercherComptesDuclient(long numClient) {
		return daoCompte.findByClientNumero(numClient);
	}

	@Override
	public Compte creerCompte(Compte compte) throws AlreadyExistsException {
		if( compte.getNumero() == null || !daoCompte.existsById(compte.getNumero())) {
		    daoCompte.save(compte);// saveOrUpdate
		}else {
			throw new AlreadyExistsException("compte existe déjà");
		}
		return compte;
	}

	@Override
	public Compte creerCompte(Compte compte, long numClient) throws AlreadyExistsException {
		Client client  = daoClient.findById(numClient).orElse(null);
		if(client == null)
			throw new RuntimeException("client inexistant pour numClient=" + numClient); 
		
		compte.setClient(client); //on rattache le client au compte
		
		if( compte.getNumero() == null || !daoCompte.existsById(compte.getNumero())) {
		    daoCompte.save(compte);// saveOrUpdate
		}else {
			throw new AlreadyExistsException("compte existe déjà");
		}
		return compte;
	}

	@Override
	public Compte modifierCompte(Compte compte) throws NotFoundException {
		if( compte.getNumero() != null && daoCompte.existsById(compte.getNumero())) {
			Compte compteEnBase = daoCompte.findById(compte.getNumero()).get();
			compteEnBase.setLabel(compte.getLabel());
			compteEnBase.setSolde(compte.getSolde());
			//NB: on sauvegarde compteEnBase avec éventuels nouveaus label et solde
			//plutot que compte pour ne pas écraser le lien avec le client
			compte = daoCompte.save(compteEnBase);// saveOrUpdate (---> merge JPA --> update sql)
		}else {
			throw new NotFoundException("compte inexistant");
		}
		return compte;
	}

	@Override
	public void supprimerCompte(long numCompte) throws NotFoundException {
		if( daoCompte.existsById(numCompte)) {
			   daoCompte.deleteById(numCompte);// saveOrUpdate
			}else {
				throw new NotFoundException("compte inexistant");
			}
	}

	@Override
	public void effectuerVirement(double montant, long numCptDeb, long numCptCred) {
		Compte cptDeb = daoCompte.findById(numCptDeb).get();
		cptDeb.setSolde(cptDeb.getSolde()-montant);
		//daoCompte.save(cptDeb); //effectué automatiquement en fin de fonction 
		                          //dans un contexte @Transactionnal
		
		Compte cptCred = daoCompte.findById(numCptCred).get();
		cptCred.setSolde(cptCred.getSolde()+montant);
		//daoCompte.save(cptCred); //cptCred est à l'état persistant 
		                           //dans un contexte @Transactionnal

	}

}
