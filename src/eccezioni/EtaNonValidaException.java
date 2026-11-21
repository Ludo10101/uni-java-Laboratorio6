package eccezioni;

public class EtaNonValidaException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private int etaInserita;
	private int minEtaRichiesta;
	private int maxEtaRichiesta;
	
	public EtaNonValidaException(String msg,int etaInserita, int minEtaRichiesta, int maxEtaRichiesta) {
		super(msg);
		this.etaInserita=etaInserita;
		this.minEtaRichiesta=minEtaRichiesta;
		this.maxEtaRichiesta=maxEtaRichiesta;
	}

	public int getEtaInserita() {
		return etaInserita;
	}

	public void setEtaInserita(int etaInserita) {
		this.etaInserita = etaInserita;
	}

	public int getMinEtaRichiesta() {
		return minEtaRichiesta;
	}

	public void setMinEtaRichiesta(int etaRichiesta) {
		this.minEtaRichiesta = etaRichiesta;
	}
	
	public int getMaxEtaRichiesta() {
		return maxEtaRichiesta;
	}

	public void setMaxEtaRichiesta(int maxEtaRichiesta) {
		this.maxEtaRichiesta = maxEtaRichiesta;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}