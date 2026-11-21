package clienti;
import Esercizio.EPatente;
public class Dipendente {
	private int eta;
	private EPatente patente;
	private double latitudine;
	private double longitudine;
	public Dipendente(int eta, EPatente patente, double latitudine, double longitudine) {
		super();
		this.eta = eta;
		this.patente = patente;
		this.latitudine = latitudine;
		this.longitudine = longitudine;
	}
	public int getEta() {
		return eta;
	}
	public void setEta(int eta) {
		this.eta = eta;
	}
	public EPatente getPatente() {
		return patente;
	}
	public void setPatente(EPatente patente) {
		this.patente = patente;
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
	
	
}
