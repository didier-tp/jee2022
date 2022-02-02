package tp.jeeApp.dto;

public class Dto {

	public record CompteRecord(Long numero,String label,Double solde) {};
	public record MessageDetails(String message,String details) {};
	public record DemandeVirement(double montant,long numCptDeb, long numCptCred) {};
	public record ConfirmationVirement(DemandeVirement demandeVirement,boolean ok, String message) {};

}
