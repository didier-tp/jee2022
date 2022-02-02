package tp.jeeApp.service;

import java.util.List;

import tp.jeeApp.entity.Client;
import tp.jeeApp.exception.AlreadyExistsException;
import tp.jeeApp.exception.NotFoundException;

public interface ClientService {
	Client rechercherClientParNumero(long numero);
	List<Client> rechercherTousLesClients(); //retourne liste vide si rien trouvé

	Client creerClient(Client client) throws AlreadyExistsException; //en retour compte avec numero auto incrémenté
	Client modifierClient(Client client) throws NotFoundException;

	void supprimerClient(long numClient) throws NotFoundException;
	
	
}
