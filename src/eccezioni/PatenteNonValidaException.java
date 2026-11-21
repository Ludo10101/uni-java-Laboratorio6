package eccezioni;
import Esercizio.EPatente;
public class PatenteNonValidaException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private EPatente patenteInserita;
	private EPatente patenteRichiesta;
	
	public PatenteNonValidaException(String msg, EPatente patenteInserita,EPatente patenteRichiesta) {
		super(msg);
		this.patenteInserita=patenteInserita;
		this.patenteRichiesta=patenteRichiesta;
	}

	public EPatente getPatenteInserita() {
		return patenteInserita;
	}

	public void setPatenteInserita(EPatente patenteInserita) {
		this.patenteInserita = patenteInserita;
	}

	public EPatente getPatenteRichiesta() {
		return patenteRichiesta;
	}

	public void setPatenteRichiesta(EPatente patenteRichiesta) {
		this.patenteRichiesta = patenteRichiesta;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}
