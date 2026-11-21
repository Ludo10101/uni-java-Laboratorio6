package eccezioni;

public class PrenotazioneInesistenteException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public PrenotazioneInesistenteException(String msg) {
		super(msg);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
