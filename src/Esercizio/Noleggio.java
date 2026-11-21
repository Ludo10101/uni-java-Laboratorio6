package Esercizio;

public class Noleggio {
	private Cliente client;
	private Mezzo transport;
	private double oraInizio;
	private double oraFine;
	public Noleggio(Cliente client, Mezzo transport, double oraInizio) {
		super();
		this.client = client;
		this.transport = transport;
		this.oraInizio = oraInizio;
		this.oraFine=-1;
	}
	public Cliente getClient() {
		return client;
	}
	public void setClient(Cliente client) {
		this.client = client;
	}
	public Mezzo getTransport() {
		return transport;
	}
	public void setTransport(Mezzo transport) {
		this.transport = transport;
	}
	public double getOraInizio() {
		return oraInizio;
	}
	public void setOraInizio(double oraInizio) {
		this.oraInizio = oraInizio;
	}
	public double getOraFine() {
		return oraFine;
	}
	public void setOraFine(double oraFine) {
		this.oraFine = oraFine;
	}
	
	

}
