package eccezioni;
import Esercizio.Mezzo;
public class MezzoGiaPrenotatoException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private Mezzo m;
	
	public MezzoGiaPrenotatoException(String msg,Mezzo m) {
		super(msg);
		this.m=m;
	}

	public Mezzo getM() {
		return m;
	}

	public void setM(Mezzo m) {
		this.m = m;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}
