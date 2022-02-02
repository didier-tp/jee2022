package tp.jeeApp.dao;

import org.springframework.data.repository.CrudRepository;

import tp.jeeApp.entity.Client;

public interface DaoClient extends CrudRepository<Client,Long>{
	
	
}
