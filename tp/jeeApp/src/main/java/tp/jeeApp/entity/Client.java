package tp.jeeApp.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class Client {
	@Id//pk
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_incr qui remonyte en mémoire dans l'objet java
	private Long numero;
	
	private String prenom;
	private String nom;
	
	
	@OneToMany(mappedBy = "client") //nom java de la relation inverse
	private List<Compte> comptes;
	
	void addCompte(Compte cpt){
		if(comptes == null) {
			comptes = new ArrayList<Compte>();
		}
		comptes.add(cpt);
		cpt.setClient(this); //lien inverse à rendre cohérent de manière explicite
	}


	@Override
	public String toString() {
		return "Client [numero=" + numero + ", prenom=" + prenom + ", nom=" + nom + "]";
	}


	public Client(Long numero, String prenom, String nom) {
		super();
		this.numero = numero;
		this.prenom = prenom;
		this.nom = nom;
	}
	
	

}
