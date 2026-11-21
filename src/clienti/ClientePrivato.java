package clienti;
import Esercizio.Cliente;
import Esercizio.EPatente;
public class ClientePrivato  extends Cliente{
	private String nome;
	private String cognome;
	private double latitudine;
	private double longitudine;
	private EPatente patente;
	private int eta;
	
	
	public ClientePrivato(String id,String nome, String cognome, double latitudine, double longitudine, EPatente patente,
			int eta) {
		super(id);
		this.nome = nome;
		this.cognome = cognome;
		this.latitudine = latitudine;
		this.longitudine = longitudine;
		this.patente = patente;
		this.eta = eta;
	}
	@Override
	public EPatente getConducentePatente() {
		return getPatente();
	}
	
	@Override 
	public int getConducenteEta() {
		return getEta();
	}
	@Override
	public double getConducenteLatitudine() {
		return getLatitudine();
	}
	@Override
	public double getConducenteLongitudine() {
		return getLongitudine();
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public double getLatitudine() {
		return latitudine;
	}
	public void setLatitudine(double latitudine) {
		this.latitudine = latitudine;
	}
	public double getLongitudine() {
		return longitudine;
	}
	public void setLongitudine(double longitudine) {
		this.longitudine = longitudine;
	}
	public EPatente getPatente() {
		return patente;
	}
	public void setPatente(EPatente patente) {
		this.patente = patente;
	}
	public int getEta() {
		return eta;
	}
	public void setEta(int eta) {
		this.eta = eta;
	}
	
	

}
