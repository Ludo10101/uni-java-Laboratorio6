package eccezioni;
import Esercizio.Mezzo;
public class MezzoGiaPresenteException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private Mezzo c;
	public MezzoGiaPresenteException(String msg, Mezzo c) {
		super(msg);
		this.c=c;
	}
	public Mezzo getC() {
		return c;
	}
	public void setC(Mezzo c) {
		this.c = c;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
