package tp.jeeApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tp.jeeApp.dao.DaoClient;
import tp.jeeApp.entity.Client;
import tp.jeeApp.exception.AlreadyExistsException;
import tp.jeeApp.exception.NotFoundException;

//@Component
@Service(/* value = "clientServiceImpl" nom par defaut = NomClasse_avec_minuscule_au_debut */)  // composant spring de type service métier
@Transactional
public class ClientServiceImpl implements ClientService {
	
	@Autowired
	private DaoClient daoClient;

	@Override
	public Client rechercherClientParNumero(long numero) {
		return daoClient.findById(numero).orElse(null);
	}

	@Override
	public List<Client> rechercherTousLesClients() {
		return (List<Client>) daoClient.findAll();
	}

	@Override
	public Client creerClient(Client client) throws AlreadyExistsException{
		if( client.getNumero() == null || !daoClient.existsById(client.getNumero())) {
		    daoClient.save(client);// saveOrUpdate
		}else {
			throw new AlreadyExistsException("client existe déjà");
		}
		return client;
	}

	@Override
	public Client modifierClient(Client client) throws NotFoundException{
		if( client.getNumero() != null && daoClient.existsById(client.getNumero())) {
		    client = daoClient.save(client);// saveOrUpdate
		}else {
			throw new NotFoundException("client inexistant");
		}
		return client;
	}

	@Override
	public void supprimerClient(long numClient) throws NotFoundException{
		if( daoClient.existsById(numClient)) {
		   daoClient.deleteById(numClient);// saveOrUpdate
		}else {
			throw new NotFoundException("client inexistant");
		}
	}

}
