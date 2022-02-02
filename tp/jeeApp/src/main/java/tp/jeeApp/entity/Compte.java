package tp.jeeApp.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class Compte {
	@Id//pk
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_incr qui remonyte en mémoire dans l'objet java
	private Long numero;
	
	private String label;
	
	private Double solde;
	
	@ManyToOne
	@JsonIgnore //un peu comme @XmlTransient
	@JoinColumn(name = "numClient") //nom de la colonne "clef etrangère" (fk) dans la table
	private Client client;
	

	@Override
	public String toString() {
		return "Compte [numero=" + numero + ", label=" + label + ", solde=" + solde + "]";
	}

	public Compte(Long numero, String label, Double solde) {
		super();
		this.numero = numero;
		this.label = label;
		this.solde = solde;
	}
	
	

}
