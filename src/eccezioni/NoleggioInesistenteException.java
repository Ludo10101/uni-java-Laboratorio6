package eccezioni;

public class NoleggioInesistenteException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public NoleggioInesistenteException(String msg) {
		super(msg);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
