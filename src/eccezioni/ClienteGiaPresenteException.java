package eccezioni;
import Esercizio.Cliente;
public class ClienteGiaPresenteException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private Cliente c;
	public ClienteGiaPresenteException(String msg, Cliente c) {
		super(msg);
		this.c=c;
	}
	public Cliente getC() {
		return c;
	}
	public void setC(Cliente c) {
		this.c = c;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
