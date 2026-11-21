package clienti;
import Esercizio.Cliente;
import Esercizio.EPatente;

public class ClienteAziendale extends Cliente{
	private String ragioneSociale;
	private Dipendente conducente;
	
	@Override
	public EPatente getConducentePatente() {
		return conducente.getPatente();
	}
	
	@Override 
	public int getConducenteEta() {
		return conducente.getEta();
	}
	@Override
	public double getConducenteLatitudine() {
		return conducente.getLatitudine();
	}
	@Override
	public double getConducenteLongitudine() {
		return conducente.getLongitudine();
	}
	public ClienteAziendale(String id,String ragioneSociale, Dipendente conducente) {
		super(id);
		this.ragioneSociale = ragioneSociale;
		this.conducente = conducente;
	}
	public String getRagioneSociale() {
		return ragioneSociale;
	}
	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}
	public Dipendente getConducente() {
		return conducente;
	}
	public void setConducente(Dipendente conducente) {
		this.conducente = conducente;
	}
	
	
	
}

