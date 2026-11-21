package clienti;
import Esercizio.Cliente;
import Esercizio.EPatente;
public class ClientiFactory {
	private static int clientiGenerati=1;
	
	private ClientiFactory() {
	}
	public static Cliente creaClientePrivato(String nome, String cognome, double latitudine, double longitudine, EPatente patente,
			int eta) {
		return new ClientePrivato("CP-"+(clientiGenerati++),nome,cognome,latitudine,longitudine,patente,eta);
		
	}
	
	public static Cliente creaClienteAziendale(String ragioneSociale,int eta, EPatente patente, double latitudine, double longitudine) {
		return new ClienteAziendale("CA-"+(clientiGenerati++),ragioneSociale,new Dipendente(eta, patente, latitudine,longitudine));
	}
	public static int getClientiGenerati() {
		return clientiGenerati;
	}
	public static void setClientiGenerati(int clientiGenerati) {
		ClientiFactory.clientiGenerati = clientiGenerati;
	}
	
	
}
