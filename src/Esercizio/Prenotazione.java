package Esercizio;

public class Prenotazione {
	private Cliente client;
	private Mezzo transport;
	public Prenotazione(Cliente client, Mezzo transport) {
		super();
		this.client = client;
		this.transport = transport;
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
	
	

}
