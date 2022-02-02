package tp.jeeApp.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import tp.jeeApp.entity.Compte;

public interface DaoCompte extends CrudRepository<Compte,Long>{
	/*
	//principales méthodes héritées:
	Optional<Compte> findById(Long numCompte); 
	List<Compte> findAll();
	Compte save(Compte compte); //saveOrUpdate 
	void deleteById(Long numCpte);
	//....
	 */
	
	List<Compte> findByClientNumero(long numClient);  //convention de nommage springData findBy.... 
	                                                  //ClientNumero au sens .client.numero  
}
