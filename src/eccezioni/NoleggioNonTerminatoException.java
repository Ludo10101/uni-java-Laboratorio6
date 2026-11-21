package eccezioni;

public class NoleggioNonTerminatoException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public NoleggioNonTerminatoException(String msg) {
		super(msg);
	}
}
