package Esercizio;


public abstract class Mezzo {
	private String targa;
	private EAlimentazione alimentazione;
	private double latitudine;
	private double longitudine;
	private boolean disponibilita;
	private EPatente patenteRichiesta;
	private int maxEta;
	private int minEta;
	private double tariffaMinuto;
	
	public Mezzo(String targa, EAlimentazione alimentazione, double latitudine, double longitudine,
			boolean disponibilita, EPatente patenteRichiesta, int minEta, int maxEta, double tariffaMinuto) {
		super();
		this.targa = targa;
		this.alimentazione = alimentazione;
		this.latitudine = latitudine;
		this.longitudine = longitudine;
		this.disponibilita = disponibilita;
		this.patenteRichiesta = patenteRichiesta;
		this.maxEta = maxEta;
		this.minEta = minEta;
		this.tariffaMinuto = tariffaMinuto;
	}

	
	public abstract String getTipologiaMezzo();
	
	
	
	public double calcolaDistanza(Cliente c) {
		return Math.sqrt(Math.pow(latitudine-c.getConducenteLatitudine(),2)+Math.pow(longitudine-c.getConducenteLongitudine(),2));
	}

	
	public String getTarga() {
		return targa;
	}

	public void setTarga(String targa) {
		this.targa = targa;
	}

	public EAlimentazione getAlimentazione() {
		return alimentazione;
	}

	public void setAlimentazione(EAlimentazione alimentazione) {
		this.alimentazione = alimentazione;
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

	public boolean isDisponibilita() {
		return disponibilita;
	}

	public void setDisponibilita(boolean disponibilita) {
		this.disponibilita = disponibilita;
	}

	public EPatente getPatenteRichiesta() {
		return patenteRichiesta;
	}

	public void setPatenteRichiesta(EPatente patenteRichiesta) {
		this.patenteRichiesta = patenteRichiesta;
	}

	public int getMaxEta() {
		return maxEta;
	}

	public void setMaxEta(int maxEta) {
		this.maxEta = maxEta;
	}

	public int getMinEta() {
		return minEta;
	}

	public void setMinEta(int minEta) {
		this.minEta = minEta;
	}

	public double getTariffaMinuto() {
		return tariffaMinuto;
	}

	public void setTariffaMinuto(double tariffaMinuto) {
		this.tariffaMinuto = tariffaMinuto;
	}
	
}
